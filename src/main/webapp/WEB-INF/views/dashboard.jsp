<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard | Guilda Hunters</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">Guilda Hunters</a>
        <div class="d-flex text-white">
            <span class="me-3">Logado como: ${sessionScope.usuarioLogado.nome} (${sessionScope.usuarioLogado.papel})</span>
            <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">Sair</a>
        </div>
    </div>
</nav>

<div class="container py-4">
    <h1 class="h3 mb-3">Painel da Guilda</h1>

    <div class="row g-3 mb-4">
        <div class="col-md-3"><div class="card"><div class="card-body"><h2 class="h6">Monstros</h2><p class="display-6">${totalMonstros}</p></div></div></div>
        <div class="col-md-3"><div class="card"><div class="card-body"><h2 class="h6">Armas</h2><p class="display-6">${totalArmas}</p></div></div></div>
        <div class="col-md-3"><div class="card"><div class="card-body"><h2 class="h6">Locais</h2><p class="display-6">${totalLocais}</p></div></div></div>
        <div class="col-md-3"><div class="card"><div class="card-body"><h2 class="h6">Cacadores</h2><p class="display-6">${totalCacadores}</p></div></div></div>
    </div>

    <div class="list-group">
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/monstros">Listar Monstros</a>
        <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/cacar">Caçar Monstro</a>

        <c:if test="${sessionScope.usuarioLogado.papel == 'ADMIN'}">
            <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/armas">Gerenciar Armas</a>
            <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/locais">Gerenciar Locais de Caçada</a>
            <a class="list-group-item list-group-item-action" href="${pageContext.request.contextPath}/cacadores">Gerenciar Cacadores</a>
        </c:if>
    </div>
</div>
</body>
</html>
