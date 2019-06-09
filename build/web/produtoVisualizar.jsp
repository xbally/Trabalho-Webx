<%-- 
    Document   : clientesAlterar
    Created on : 07/04/2018, 18:55:04
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ufpr.tads.web2.beans.Produto"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Visualizar Produto</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="js/jQuery.weightMask.min.js"></script>
    </head>
    <body>
        <c:if test="${empty sessionScope.usuario}">
            <jsp:forward page="index.jsp">
                <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema." />
            </jsp:forward>
        </c:if>
        <div class="container">
            <h1>Visualizar Produto</h1>
            <div class="form-row mt-4">
                <div class="form-group col-md-5">
                    <label for="nome">Nome:</label>
                    <input class="form-control" type="text" name="nome" maxlength="200" value="<c:out value="${produto.nome}"/>" disabled/><br/>
                </div>
                <div class="form-group col-md-4">
                    <label for="idCategoria">Categoria do Produto:</label>
                    <select id="idCategoria" name="idCategoria" class="custom-select" disabled>
                        <option selected value="<c:out value="${produto.categoria.id}"/>"><c:out value="${produto.categoria.nome}"/></option>
                    </select>
                </div>
                <div class="form-group col-md-3">
                    <label for="peso">Peso(gramas):</label>
                    <input class="form-control peso" type="text" name="peso" value="<fmt:formatNumber value="${produto.peso}" type="number" maxFractionDigits="2"/>" disabled/><br/>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-10 offset-md-1">
                    <label for="nome">Descrição:</label>
                    <textarea class="form-control" name="descricao" rows="5" disabled><c:out value="${produto.descricao}"/></textarea>
                </div>
            </div>
            <a class="btn btn-outline-danger" href="ProdutoServlet">Voltar</a>
        </div>
    </body>
</html>
