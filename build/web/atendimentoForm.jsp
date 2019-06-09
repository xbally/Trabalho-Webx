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
        <title><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Resolução Atendimento\" : \"Novo Atendimento\"}"/></title>
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
            <h1><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Resolução Atendimento\" : \"Novo Atendimento\"}"/></h1>
            <form action="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"AtendimentoServlet?action=update\" : \"AtendimentoServlet?action=new\"}"/>" method="POST">
                <c:if test="${(!(empty param.form) || param.form == \"alterar\")}" >
                    <input type="hidden" name="id" value="<c:out value="${atendimento.id}"/>">
                </c:if>
                <div class="form-row mt-4">
                    <div class="form-group col-md-4">
                        <label for="nome">Tipo Atendimento:</label>
                        <select id="idTipo" name="idTipoAtendimento" class="custom-select" <c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"disabled\" : \"required\"}"/>>
                        <c:forEach items="${tipos}" var="tipo">
                            <option <c:out value="${(atendimento.tipo.id == tipo.id) ? \"selected\" : \"\" }"/> value="<c:out value="${tipo.id}"/>"><c:out value="${tipo.nome}"/></option>
                        </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="nome">Produto:</label>
                        <select id="idTipo" name="idProduto" class="custom-select" <c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"disabled\" : \"required\"}"/>>
                        <c:forEach items="${produtos}" var="produto">
                            <option <c:out value="${(atendimento.produto.id == produto.id) ? \"selected\" : \"\" }"/> value="<c:out value="${produto.id}"/>"><c:out value="${produto.nome}"/></option>
                        </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-4">
                        <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${atendimento.dataHora}" var="dataHora" />
                        <label for="nome">Data/Hora:</label>
                        <input class="form-control" type="text" name="dataHora" id="datetimepicker" maxlength="100" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? dataHora : \"\"}"/>" <c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"disabled\" : \"required\"}"/>/><br/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-10 offset-md-1">
                        <label for="nome">Descrição:</label>
                        <textarea class="form-control" name="descricao" rows="5" <c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"disabled\" : \"required\"}"/>><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? atendimento.descricao : \"\"}"/></textarea>
                    </div>
                </div>
            <c:if test="${(!(empty param.form) || param.form == \"alterar\")}">
                <div class="form-row">
                    <div class="form-group col-md-10 offset-md-1">
                        <label for="nome">Solução:</label>
                        <textarea class="form-control" name="solucao" rows="5" required></textarea>
                    </div>
                </div>
            </c:if>
                <input class="btn btn-outline-success" type="submit" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Resolver\" : \"Salvar\"}"/>"/>
                <a class="btn btn-outline-danger" href="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"AtendimentoServlet?action=list\" : \"AtendimentoServlet?action=listCliente\"}"/>">Cancelar</a>
            </form>
        </div>
    </body>
    <script src="js/jquery.datetimepicker.full.js" type="text/javascript"></script>
    <script>
        /*jslint browser:true*/
        /*global jQuery, document*/
            
        jQuery(document).ready(function () {
            jQuery.datetimepicker.setLocale('pt');
            jQuery('#datetimepicker').datetimepicker({
                format:'d/m/Y H:m'
            });
        });
    </script>
</html>
