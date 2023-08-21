package com.example.homework8;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;

import bank.Account;
import bank.Main;
import bank.Rate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/stat")
public class StatServlet extends HttpServlet {
    static EntityManagerFactory emf;
    static EntityManager em;

    public void init() {
        emf = Persistence.createEntityManagerFactory("Bank");
        em = emf.createEntityManager();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Transaction c", Long.class);
        long transQuantity = query.getSingleResult();

        TypedQuery<Account> accountQuery = em.createQuery("SELECT c FROM Account c", Account.class);
        List<Account> accountsList = accountQuery.getResultList();
        BigDecimal totalBalance = BigDecimal.ZERO;
        for (Account account: accountsList) {
            Rate transactionRate = new Rate(account.getCurrency(), "UAH");
            BigDecimal accountBalance = (account.getBalance() != null) ? account.getBalance() : BigDecimal.ZERO;
            BigDecimal convertedBalance = accountBalance.multiply(BigDecimal.valueOf(transactionRate.getExchangeRate()));
            totalBalance = totalBalance.add(convertedBalance);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("transactions", transQuantity);
        session.setAttribute("balance", totalBalance);
        try {
            response.sendRedirect("stat.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
        em.close();
        emf.close();
    }
}