<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cacadores | Guilda Hunters</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Gerenciar Cacadores</h1>
        <div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/dashboard">Voltar</a>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/cacadores?action=form">Novo Cacador</a>
        </div>
    </div>

    <table class="table table-striped table-hover">
        <thead><tr><th>ID</th><th>Nome</th><th>Login</th><th>Papel</th><th>Acoes</th></tr></thead>
        <tbody>
        <c:forEach items="${cacadores}" var="c">
            <tr>
                <td>${c.id}</td>
                <td>${c.nome}</td>
                <td>${c.login}</td>
                <td>${c.papel}</td>
                <td>
                    <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/cacadores?action=form&id=${c.id}">Editar</a>
                    <a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/cacadores?action=remover&id=${c.id}">Remover</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
