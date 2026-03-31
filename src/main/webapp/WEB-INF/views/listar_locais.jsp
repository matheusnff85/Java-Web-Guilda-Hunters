<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Locais | Guilda Hunters</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Gerenciar Locais de Caçada</h1>
        <div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/dashboard">Voltar</a>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/locais?action=form">Novo Local</a>
        </div>
    </div>

    <table class="table table-striped table-hover">
        <thead><tr><th>ID</th><th>Nome</th><th>Bioma</th><th>Nível de Perigo</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach items="${locais}" var="l">
            <tr>
                <td>${l.id}</td>
                <td>${l.nome}</td>
                <td>${l.bioma}</td>
                <td>${l.nivelPerigo}</td>
                <td>
                    <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/locais?action=form&id=${l.id}">Editar</a>
                    <a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/locais?action=remover&id=${l.id}">Remover</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
