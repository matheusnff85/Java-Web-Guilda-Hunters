<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${tituloFormulario}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="row justify-content-center">
        <div class="col-lg-7">
            <div class="card">
                <div class="card-body">
                    <h1 class="h4 mb-3">${tituloFormulario}</h1>
                    <form action="${formAction}" method="post">
                        <input type="hidden" name="id" value="${entidadeId}">

                        <c:forEach items="${campos}" var="campo">
                            <div class="mb-3">
                                <label for="${campo.nome}" class="form-label">${campo.label}</label>
                                <input type="${campo.tipo}" class="form-control" id="${campo.nome}" name="${campo.nome}" value="${campo.valor}" required>
                            </div>
                        </c:forEach>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Salvar</button>
                            <a href="${rotaVoltar}" class="btn btn-outline-secondary">Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
