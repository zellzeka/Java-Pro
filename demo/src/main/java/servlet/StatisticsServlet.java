package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

//http://localhost:8080/stat
@WebServlet(name = "checkServlet", value = "/check")
public class StatisticsServlet extends HttpServlet {
    private int transQuantity;
    private BigDecimal totalAmount;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html"); // Content-Type: text/html

        // MIME types

        PrintWriter pw = resp.getWriter();
        pw.println("<html><head><title>Prog Academy Test</title></head>");
        pw.println("<body><h1>Hello, Java Junior :)</h1></body></html>");
    }
}
