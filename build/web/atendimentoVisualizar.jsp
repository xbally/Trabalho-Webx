<%-- 
    Document   : clientesAlterar
    Created on : 07/04/2018, 18:55:04
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ufpr.tads.web2.beans.Atendimento"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Visualizar Atendimento</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="js/jquery.mask.js" type="text/javascript"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="js/jquery.datetimepicker.full.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="css/jquery.datetimepicker.min.css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    </head>
    <body>
        <c:if test="${empty sessionScope.usuario}">
            <jsp:forward page="index.jsp">
                <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema." />
            </jsp:forward>
        </c:if>
        <div class="container">
            <h1>Visualizar Atendimento</h1>
            <div class="form-row mt-4">
                <div class="form-group col-md-4">
                    <label for="nome">Tipo Atendimento:</label>
                    <select id="idTipo" name="idTipoAtendimento" class="custom-select" disabled>
                        <option <c:out value="${(atendimento.tipo.id == tipo.id) ? \"selected\" : \"\" }"/> value="<c:out value="${atendimento.tipo.id}"/>"><c:out value="${atendimento.tipo.nome}"/></option>
                    </select>
                </div>
                <div class="form-group col-md-4">
                    <label for="nome">Produto:</label>
                    <select id="idTipo" name="idProduto" class="custom-select" disabled>
                    <c:forEach items="${produtos}" var="produto">
                        <option value="<c:out value="${atendimento.produto.id}"/>"><c:out value="${atendimento.produto.nome}"/></option>
                    </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-4">
                    <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${atendimento.dataHora}" var="dataHora" />
                    <label for="nome">Data/Hora:</label>
                    <input class="form-control" type="text" name="dataHora" id="datetimepicker" maxlength="100" value="<c:out value="${dataHora}"/>" disabled/><br/>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="nome">Cliente:</label>
                    <input class="form-control" type="text" name="idCliente" value="<c:out value="${atendimento.cliente.nome}"/>" disabled/><br/>
                </div>
                <div class="form-group col-md-6">
                    <label for="nome">Situação:</label>
                    <input class="form-control" type="text" name="situação" value="<c:out value="${(atendimento.situacao) ? \"Fechado\" : \"Aberto\" }"/>" disabled/><br/>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-10 offset-md-1">
                    <label for="nome">Descrição:</label>
                    <textarea class="form-control" name="descricao" rows="5" disabled><c:out value="${atendimento.descricao}"/></textarea>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-10 offset-md-1">
                    <label for="nome">Solução:</label>
                    <textarea class="form-control" name="solucao" rows="5" disabled><c:out value="${atendimento.solucao}"/></textarea>
                </div>
            </div>
            <a class="btn btn-outline-danger" href="<c:out value="${(!(empty cliente)) ? \"AtendimentoServlet?action=listCliente\" : \"AtendimentoServlet?action=list\"}"/>">Cancelar</a>
        </div>
    </body>
</html>
