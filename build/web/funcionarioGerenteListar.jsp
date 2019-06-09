<%-- 
    Document   : clientesListar
    Created on : 07/04/2018, 15:55:25
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.ufpr.tads.web2.beans.Funcionario"%>
<%@page import="com.ufpr.tads.web2.beans.Gerente"%>
<%@page import="java.util.List"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Funcionarios/Gerente Listar</title>
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
                    <li class="nav-item"><a class="nav-link" href="AtendimentoServlet?action=listOpen">Atendimentos em Aberto</a></li>
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
        <div class="row mt-2 mb-4">
            <div class="col-md-2 offset-md-1">
              <a class="btn btn-outline-success" href="FuncionarioGerenteServlet?action=formNew&tipo=f">Novo Funcionario</a>  
            </div>
            <div class="col-md-2">  
              <a class="btn btn-outline-success" href="FuncionarioGerenteServlet?action=formNew&tipo=g">Novo Gerente</a>  
            </div>
        </div>
        <c:choose>
            <c:when test="${gerentes.size() gt 0 || funcionarios.size() gt 0 }">
        <div class="row">
            <div class="col-md-10 offset-md-1">
                <table class="table table-striped">
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Tipo</th>
                        <th>Visualizar</th>
                        <th>Alterar</th>
                        <th>Remover</th>
                    </tr>
                <c:forEach items="${gerentes}" var="gerente">
                    <tr>
                        <td><c:out value="${gerente.nome}"/></td>
                        <td><c:out value="${gerente.email}"/></td>
                        <td>Gerente</td>
                        <td><a href="FuncionarioGerenteServlet?action=show&id=<c:out value="${gerente.idGerente}"/>&tipo=g"><i class="fas fa-eye"></i></a></td>
                        <td><a href="FuncionarioGerenteServlet?action=formUpdate&id=<c:out value="${gerente.idGerente}"/>&tipo=g"><i class="fas fa-edit"></i></a></td>
                        <td><a class="confirmation" href="FuncionarioGerenteServlet?action=remove&id=<c:out value="${gerente.idGerente}"/>&tipo=g"><i class="fas fa-trash"></i></a></td>
                    </tr>
                </c:forEach>
                <c:forEach items="${funcionarios}" var="funcionario">
                    <tr>
                        <td><c:out value="${funcionario.nome}"/></td>
                        <td><c:out value="${funcionario.email}"/></td>
                        <td>Funcionario</td>
                        <td><a href="FuncionarioGerenteServlet?action=show&id=<c:out value="${funcionario.idFuncionario}"/>&tipo=f"><i class="fas fa-eye"></i></a></td>
                        <td><a href="FuncionarioGerenteServlet?action=formUpdate&id=<c:out value="${funcionario.idFuncionario}"/>&tipo=f"><i class="fas fa-edit"></i></a></td>
                        <td><a class="confirmation" href="FuncionarioGerenteServlet?action=remove&id=<c:out value="${funcionario.idFuncionario}"/>&tipo=f"><i class="fas fa-trash"></i></a></td>
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
                                <p>Nenhum funcionário/gerente cadastrado</p>
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
