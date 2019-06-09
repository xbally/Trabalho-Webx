<%-- 
    Document   : cadastro
    Created on : 15/05/2019, 20:48:07
    Author     : Gabriel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.ufpr.tads.web2.beans.Funcionario"%>
<%@page import="com.ufpr.tads.web2.beans.Gerente"%>
<%@page import="com.ufpr.tads.web2.beans.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
    <c:choose>
        <c:when test="${(!(empty param.tipo) && param.tipo == \"f\")}">
            <title>Visualizar Funcionario</title>
        </c:when>
        <c:when test="${(!(empty param.tipo) && param.tipo == \"g\")}">
            <title>Visualizar Gerente</title>
        </c:when>
    </c:choose>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="js/jquery/jquery.mask.js" type="text/javascript"></script>
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
            <c:choose>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"f\")}">
                    <h1>Visualizar Funcionario</h1>
                </c:when>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"g\")}">
                    <h1>Visualizar Gerente</h1>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"f\")}">
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nome">Nome:</label>
                            <input class="form-control" type="text" name="nome" maxlength="100" value="<c:out value="${funcionario.nome}"/>" disabled/><br/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-8">
                            <label for="email">E-mail:</label>
                            <input class="form-control" type="email" name="email" maxlength="100" value="<c:out value="${funcionario.email}"/>" disabled/><br/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="rg">Telefone:</label>
                            <input  class="form-control telefone" type="text" name="telefone" value="<c:out value="${funcionario.telefone}"/>" id="telefone" disabled/><br/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="cpf">CPF:</label>
                            <input class="form-control cpf" type="text" name="cpf"  maxlength="11" value="<c:out value="${funcionario.cpf}"/>" id="cpf" disabled/><br/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="rua">Rua:</label>
                            <input class="form-control" type="text" name="rua" value="<c:out value="${funcionario.endereco.rua}"/>" disabled/><br/>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="numero">Número:</label>
                            <input class="form-control" type="number" name="numero" value="<c:out value="${funcionario.endereco.numero}"/>" disabled/><br/>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cep">CEP:</label>
                            <input class="form-control" type="text" name="cep" maxlength="8" value="<c:out value="${funcionario.endereco.cep}"/>" disabled/><br/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="uf">UF:</label>
                            <select id="estado" name="uf" class="custom-select" disabled>
                                <option selected value="<c:out value="${funcionario.endereco.cidade.estado.id}"/>"><c:out value="${funcionario.endereco.cidade.estado.sigla}"/></option>
                            </select>
                        </div>
                        <div class="form-group col-md-10">  
                            <label for="cidade">Cidade:</label>
                            <select id="cidade" name="cidade" class="custom-select" disabled>
                                <option selected value="<c:out value="${funcionario.endereco.cidade.id}"/>"><c:out value="${funcionario.endereco.cidade.nome}"/></option>
                            </select>
                        </div>
                    </div>
                    <a class="btn btn-outline-danger" href="FuncionarioGerenteServlet">Voltar</a>
                </c:when>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"g\")}">
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="nome">Nome:</label>
                                <input class="form-control" type="text" name="nome" maxlength="100" value="<c:out value="${gerente.nome}"/>" disabled/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-8">
                                <label for="email">E-mail:</label>
                                <input class="form-control" type="email" name="email" maxlength="100" value="<c:out value="${gerente.email}"/>" disabled/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="rg">Telefone:</label>
                                <input  class="form-control telefone" type="text" name="telefone" value="<c:out value="${gerente.telefone}"/>" id="telefone" disabled/><br/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="cpf">CPF:</label>
                                <input class="form-control cpf" type="text" name="cpf"  maxlength="11" value="<c:out value="${gerente.cpf}"/>" id="cpf" disabled/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="rua">Rua:</label>
                                <input class="form-control" type="text" name="rua" value="<c:out value="${gerente.endereco.rua}"/>" disabled/><br/>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="numero">Número:</label>
                                <input class="form-control" type="number" name="numero" value="<c:out value="${gerente.endereco.numero}"/>" disabled/><br/>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="cep">CEP:</label>
                                <input class="form-control" type="text" name="cep" maxlength="8" value="<c:out value="${gerente.endereco.cep}"/>" disabled/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-2">
                                <label for="uf">UF:</label>
                                <select id="estado" name="uf" class="custom-select" disabled>
                                    <option selected value="<c:out value="${gerente.endereco.cidade.estado.id}"/>"><c:out value="${gerente.endereco.cidade.estado.sigla}"/></option>
                                </select>
                            </div>
                            <div class="form-group col-md-10">  
                                <label for="cidade">Cidade:</label>
                                <select id="cidade" name="cidade" class="custom-select" disabled>
                                    <option selected value="<c:out value="${gerente.endereco.cidade.id}"/>"><c:out value="${gerente.endereco.cidade.nome}"/></option>
                                </select>
                            </div>
                        </div>
                        <a class="btn btn-outline-danger" href="FuncionarioGerenteServlet">Voltar</a>
                </c:when>
            </c:choose>
        </div>
    </body>
</html>
