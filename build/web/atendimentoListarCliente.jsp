<%-- 
    Document   : clientesListar
    Created on : 07/04/2018, 15:55:25
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ufpr.tads.web2.beans.Atendimento"%>
<%@page import="com.ufpr.tads.web2.beans.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Atendimento Listar</title>
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
                    <li class="nav-item"><a class="nav-link" href="ClienteServlet?action=formUpdate&id=<c:out value="${cliente.idCliente}"/>"/>Alteração Usuário</a></li>
                    <li class="nav-item"><a class="nav-link" href="AtendimentoServlet?action=listCliente">Todos Seus Atendimentos</a></li>
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
        <div class="row mt-2 mb-4">
            <div class="col-md-3 offset-md-1">
              <a class="btn btn-outline-success" href="AtendimentoServlet?action=formNew">Novo Atendimento</a>  
            </div>
        </div>
        <c:choose>
            <c:when test="${atendimentos.size() gt 0}">
        <div class="row">
            <div class="col-md-10 offset-md-1">
                <table class="table table-striped">
                    <tr>
                        <th>Data/Hora</th>
                        <th>Descrição</th>
                        <th>Situação</th>
                        <th>Visualizar</th>
                        <th>Remover</th>
                    </tr>  
                <c:forEach items="${atendimentos}" var="atendimento">
                    <tr>
                        <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${atendimento.dataHora}" var="dataHora" />
                        <td><c:out value="${dataHora}"/></td>
                        <td><c:out value="${atendimento.descricao}"/></td>
                        <td><c:out value="${(atendimento.situacao) ? \"Fechado\" : \"Aberto\"}"/></td>
                        <td><a href="AtendimentoServlet?action=show&id=<c:out value="${atendimento.id}"/>"><i class="fas fa-eye"></i></a></td>
                    <c:choose>
                        <c:when test="${!atendimento.situacao}">
                        <td><a class="confirmation" href="AtendimentoServlet?action=remove&id=<c:out value="${atendimento.id}"/>"><i class="fas fa-trash"></i></a></td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                    </tr>
                </c:forEach>
                </table>
            </div>
        </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-6 offset-md-3">
                        <div class="card mt-4 mb-4 text-center">
                            <div class="card-body">
                                <p>Nenhuma categoria cadastrada</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <script type="text/javascript">
        $(document).ready(function() {
            $('.confirmation').on('click', function () {
                return confirm('Tem certeza que deseja remover esta categoria de produto?');
            });
        });
    </script>
    </body>
</html>
