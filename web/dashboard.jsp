<%-- 
    Document   : clientesListar
    Created on : 07/04/2018, 15:55:25
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Dashboard</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/ae667db9ef.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.js"></script>

    </head>
    <body>
        <c:if test="${empty sessionScope.usuario}">
            <jsp:forward page="index.jsp">
                <jsp:param name="msg" value="Usuário deve se autenticar para acessar o sistema." />
            </jsp:forward>
        </c:if> 
        <nav class="navbar navbar-expand-md navbar-dark bg-dark">
            <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item"><a class="nav-link" href="AtendimentoServlet?action=listAberto">Atendimentos em Aberto</a></li>
                    <li class="nav-item"><a class="nav-link" href="AtendimentoServlet?action=list">Todos Atendimentos</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Funcionários</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Produtos</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Atendimentos</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Reclamações</a></li>
                </ul>
            </div>
            <div class="navbar-collapse collapse w-100 order-2 dual-collapse2">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item"><a class="nav-link" href="LoginServlet?action=logout">Sair</a></li>
                </ul>
            </div>
        </nav>
    <div class="container">
                    
                    <c:if test="${!(empty param.msg)}" >
                        <c:set var="mensagem" value="${param.msg}" />
                    </c:if>
                    <c:if test="${!(empty requestScope.msg)}">
                        <c:set var="mensagem" value="${requestScope.msg}" />
                    </c:if>
                    <c:if test="${!(empty mensagem)}" >
                        <div class="row">
                            <div class="col-sm-4"></div>
                            <div class="col-sm-4 alert alert-danger" role="alert">
                                <p><c:out value="${mensagem}" /></p>
                            </div>
                        </div>
                    </c:if>
        <div class="row mt-4 mb-4">
            <div class="col-md-4">
                <h5>Total Atendimentos: ${total}</h5>  
            </div>
            <div class="col-md-4">
                <h5>Atendimentos em Aberto: ${abertos}/<fmt:formatNumber value="${porcentagem}" type="percent"/></h5>  
            </div>
            <div class="col-md-4">
                <h5>Atendimentos Finalizados: ${finalizados}</h5>  
            </div>
        </div>
        <div class="row">
            <div class="col-md-10 offset-md-1">
                <table class="table table-striped">
                    <tr>
                        <th>Tipo de Atendimento</th>
                        <th>Quantidade</th>
                    </tr>
                <c:forEach items="${tipos}" var="tipo">
                    <tr>
                        <td><c:out value="${tipo.nome}"/></td>
                        <td><c:out value="${tipo.total}"/></td>
                    </tr>
                </c:forEach>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function() {
            $('.confirmation').on('click', function () {
                return confirm('Tem certeza que deseja remover?');
            });
        });
    </script>
    </body>
</html>
