<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Monstros | Guilda Hunters</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Lista de Monstros</h1>
        <div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/dashboard">Voltar</a>
            <c:if test="${sessionScope.usuarioLogado.papel == 'ADMIN'}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/monstros?action=form">Novo Monstro</a>
            </c:if>
        </div>
    </div>

    <table class="table table-striped table-hover align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Nivel de Ameaca</th>
            <th>Fraqueza</th>
            <c:if test="${sessionScope.usuarioLogado.papel == 'ADMIN'}"><th>Acoes</th></c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${monstros}" var="m">
            <tr>
                <td>${m.id}</td>
                <td>${m.nome}</td>
                <td>${m.nivelAmeaca}</td>
                <td>${m.fraqueza}</td>
                <c:if test="${sessionScope.usuarioLogado.papel == 'ADMIN'}">
                    <td>
                        <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/monstros?action=form&id=${m.id}">Editar</a>
                        <a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/monstros?action=remover&id=${m.id}">Remover</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${sessionScope.usuarioLogado.papel == 'CACADOR'}">
        <a class="btn btn-success" href="${pageContext.request.contextPath}/cacar">Ir para Caçar Monstro</a>
    </c:if>
</div>
</body>
</html>
