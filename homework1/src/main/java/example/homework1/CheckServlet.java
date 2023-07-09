package example.homework1;

import java.io.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "checkServlet", value = "/check")
public class CheckServlet extends HttpServlet {
    private int javaStat = 0;
    private int jsStat = 0;
    private int dayStat = 0;
    private int distanceStat = 0;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String language = req.getParameter("language");
        String education = req.getParameter("education");
        if (language.equals("java")){
            javaStat ++;
        } else {
            jsStat ++;
        }
        if (education.equals("day")){
            dayStat++;
        } else {
            distanceStat++;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("language", language);
        session.setAttribute("education", education);
        session.setAttribute("java", javaStat);
        session.setAttribute("java-script", jsStat);
        session.setAttribute("day", dayStat);
        session.setAttribute("distance", distanceStat);
        try {
            resp.sendRedirect("index.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.sendRedirect("index.html");
    }

    public void destroy() {
    }
}