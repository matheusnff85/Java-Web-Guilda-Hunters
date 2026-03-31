<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Caçar Monstro | Guilda Hunters</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Registrar Caçada</h1>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/dashboard">Voltar</a>
    </div>

    <c:if test="${not empty flash}">
        <div class="alert alert-info">${flash}</div>
    </c:if>

    <div class="card mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/cacar" method="post">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label" for="monstroId">Monstro</label>
                        <select class="form-select" id="monstroId" name="monstroId" required>
                            <option value="">Selecione...</option>
                            <c:forEach items="${monstros}" var="m">
                                <option value="${m.id}">#${m.id} | ${m.nome} | Nível ${m.nivelAmeaca} | Fraqueza: ${m.fraqueza}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label" for="localId">Local de Caçada</label>
                        <select class="form-select" id="localId" name="localId" required>
                            <option value="">Selecione...</option>
                            <c:forEach items="${locais}" var="l">
                                <option value="${l.id}">${l.nome} | ${l.bioma} | Perigo ${l.nivelPerigo}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <button class="btn btn-success mt-3" type="submit">Caçar Monstro</button>
            </form>
        </div>
    </div>

    <h2 class="h5">Ultimos Registros</h2>
    <table class="table table-striped table-hover">
        <thead>
        <tr><th>ID</th><th>Data</th><th>Cacador</th><th>Monstro</th><th>Local</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${huntLogs}" var="h">
            <tr>
                <td>${h.id}</td>
                <td>${h.dataCacadaFormatada}</td>
                <td>${h.cacador.nome}</td>
                <td>#${h.monstro.id} (${h.monstro.nome} | Ameaça ${h.monstro.nivelAmeaca})</td>
                <td>${h.localCacada.nome}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
