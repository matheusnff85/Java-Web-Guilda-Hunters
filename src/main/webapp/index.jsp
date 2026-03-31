<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <%
        for(int i = 0; i < 10; i++) {
            %>
            <p>Valor de i: <%= i %></p>
            <%
        }        
    %>
</body>
</html>