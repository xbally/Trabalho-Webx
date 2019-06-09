<%-- 
    Document   : clientesListar
    Created on : 07/04/2018, 15:55:25
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ufpr.tads.web2.beans.Atendimento"%>
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
                    <li class="nav-item"><a class="nav-link" href="AtendimentoServlet?action=listAberto">Atendimentos em Aberto</a></li>
                    <li class="nav-item"><a class="nav-link" href="AtendimentoServlet?action=list">Todos Atendimentos</a></li>
                <c:if test="${sessionScope.usuario.tipo.equals(\"g\")}">
                    <li class="nav-item"><a class="nav-link" href="RelatorioServlet?action=dashboard">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Funcionários</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Produtos</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Atendimentos</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Rel. Reclamações</a></li>
                </c:if>
                <c:if test="${sessionScope.usuario.tipo.equals(\"f\")}">
                    <li class="nav-item"><a class="nav-link" href="ProdutoServlet">Todos Produtos</a></li>
                    <li class="nav-item"><a class="nav-link" href="CategoriaProdutoServlet">Todas Categoria de Produtos</a></li>
                </c:if>
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
        <c:choose>
            <c:when test="${atendimentos.size() gt 0}">
        <div class="row">
            <div class="col-md-10 offset-md-1">
                <table class="table table-striped">
                    <tr>
                        <th>Cliente</th>
                        <th>Data/Hora</th>
                        <th>Descrição</th>
                        <th>Situação</th>
                        <th>Visualizar</th>
                        <th>Resolver</th>
                    </tr>
                <jsp:useBean id="now" class="java.util.Date" />
                <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${now}" var="data" />
                <c:forEach items="${atendimentos}" var="atendimento">
                    <tr class="<c:out value="${now.getTime() - atendimento.dataHora.getTime() > 7*24*60*60*1000 ? \"table-danger\" : \"table-warning\"}"/>">
                        <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${atendimento.dataHora}" var="dataHora" />
                        <td><c:out value="${atendimento.cliente.nome}"/></td>
                        <td><c:out value="${dataHora}"/></td>
                        <td><c:out value="${atendimento.descricao}"/></td>
                        <td><c:out value="${(atendimento.situacao) ? \"Fechado\" : \"Aberto\"}"/></td>
                        <td><a href="AtendimentoServlet?action=show&id=<c:out value="${atendimento.id}"/>"><i class="fas fa-eye"></i></a></td>
                    <c:choose>
                        <c:when test="${!atendimento.situacao}">
                        <td><a href="AtendimentoServlet?action=formUpdate&id=<c:out value="${atendimento.id}"/>"><i class="fas fa-edit"></i></a></td>
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
                                <p>Nenhum atendimento cadastrado</p>
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
                return confirm('Tem certeza que deseja remover?');
            });
        });
    </script>
    </body>
</html>
