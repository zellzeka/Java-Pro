<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Bank Statistics</title>
    </head>
    <body>
        <% Long transactions = (Long) session.getAttribute("transactions");
            BigDecimal balance = (BigDecimal) session.getAttribute("balance");
        %>
        <h1>Статистика банківського додатку</h1>
        <br/>
        <h2>Було виконано <%= transactions%>  транзакцій</h2>
        <br/>
        <h2> Загальній баланс на всіх банківскіх рахунках - <%= balance%> гривень</h2>
    </body>
</html>
