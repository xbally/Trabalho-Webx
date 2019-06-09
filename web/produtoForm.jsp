<%-- 
    Document   : clientesAlterar
    Created on : 07/04/2018, 18:55:04
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ufpr.tads.web2.beans.Produto"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Alterar Produto\" : \"Novo Produto\"}"/></title>
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
            <h1><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Alterar Produto\" : \"Novo Produto\"}"/></h1>
            <form action="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"ProdutoServlet?action=update\" : \"ProdutoServlet?action=new\"}"/>" method="POST">
                <c:if test="${(!(empty param.form) || param.form == \"alterar\")}" >
                    <input type="hidden" name="id" value="<c:out value="${produto.id}"/>">
                </c:if>
                <div class="form-row mt-4">
                    <div class="form-group col-md-5">
                        <label for="nome">Nome:</label>
                        <input class="form-control" type="text" name="nome" maxlength="200" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? produto.nome : \"\"}"/>" required/><br/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="idCategoria">Categoria do Produto:</label>
                        <select id="idCategoria" name="idCategoria" class="custom-select" required>
                        <c:forEach items="${categorias}" var="categoria">
                            <option <c:out value="${(produto.categoria.id == categoria.id) ? \"selected\" : \"\" }"/> value="<c:out value="${categoria.id}"/>"><c:out value="${categoria.nome}"/></option>
                        </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="peso">Peso(gramas):</label>
                        <input class="form-control peso" type="text" name="peso" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? produto.peso : \"\"}"/>" required/><br/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-10 offset-md-1">
                        <label for="nome">Descrição:</label>
                        <textarea class="form-control" name="descricao" rows="5" required><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? produto.descricao : \"\"}"/></textarea>
                    </div>
                </div>
                <input class="btn btn-outline-success" type="submit" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Alterar\" : \"Salvar\"}"/>"/>
                <a class="btn btn-outline-danger" href="ProdutoServlet">Cancelar</a>
            </form>
        </div>
    <script type="text/javascript">
        $(document).ready(function() {
            $('.peso').maskWeight({
                integerDigits: 6,
                decimalDigits: 2,
                decimalMark: ',',
            <c:choose>
                <c:when test="${(!(empty param.form) || param.form == \"alterar\")}">
                initVal: <c:out value="${produto.peso}"/>,//'000,000',
                </c:when>
                <c:otherwise>
                initVal: '',
                </c:otherwise>
            </c:choose>
                roundingZeros: true,
                digitsCount: 8,
                callBack: null,
                doFocus: true
            });
        });
    </script>
    </body>
</html>
