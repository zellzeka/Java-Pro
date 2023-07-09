<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homework-1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  </head>
  <body>
    <div class="container">
      <% String language = (String) session.getAttribute("language");
        String education = (String) session.getAttribute("education");
        int java = (int) session.getAttribute("java");
        int js = (int) session.getAttribute("java-script");
        int day = (int) session.getAttribute("day");
        int dist = (int) session.getAttribute("distance");
      %>

      <h2>Ви обрали мову програмування <%= language %>. Форма навчання  <%= education %> </h2>
      <br>
      <h4>Мову програмування Java обрали -  <%= java %> раз</h4>
      <h4>Мову програмування Java Script обрали -  <%= js %> раз</h4>
      <h4>Денну форму навчання обрали -  <%= day %> раз</h4>
      <h4>Заочну форму навчання обрали -  <%= dist %> раз</h4>
      <br>
      <a href="/check">Обрати інший варіант</a>
    </div>
  </body>
</html>