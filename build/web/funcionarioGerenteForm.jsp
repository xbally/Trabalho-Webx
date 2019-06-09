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
            <title><c:out value="${(!(empty param.form) && param.form == \"alterar\") ? \"Alterar Funcionario\" : \"Novo Funcionario\"}"/></title>
        </c:when>
        <c:when test="${(!(empty param.tipo) && param.tipo == \"g\")}">
            <title><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Alterar Gerente\" : \"Novo Gerente\"}"/></title>
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
                    <h1><c:out value="${(!(empty param.form) && param.form == \"alterar\") ? \"Alterar Funcionario\" : \"Novo Funcionario\"}"/></h1>
                </c:when>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"g\")}">
                    <h1><c:out value="${(!(empty param.form) || param.form == \"alterar\") ? \"Alterar Gerente\" : \"Novo Gerente\"}"/></h1>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"f\")}">
                    <form action="<c:out value="${(!(empty param.form) || param.form == 'alterar') ? 'FuncionarioGerenteServlet?action=update&tipo=f' : 'FuncionarioGerenteServlet?action=new&tipo=f'}"/>" method="POST">
                        <c:if test="${(!(empty param.form) || param.form == \"alterar\")}" >
                            <input type="hidden" name="id" value="<c:out value="${funcionario.idFuncionario}"/>">
                        </c:if>
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="nome">Nome:</label>
                                <input class="form-control" type="text" name="nome" maxlength="100" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.nome : \"\"}"/>" required/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-8">
                                <label for="email">E-mail:</label>
                                <input class="form-control" type="email" name="email" maxlength="100" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.email : \"\"}"/>" required/><br/>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="senha">Senha:</label>
                                <input class="form-control" type="password" name="senha" value=""/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="rg">Telefone:</label>
                                <input  class="form-control telefone" type="text" name="telefone" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.telefone : \"\"}"/>" id="telefone" required/><br/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="cpf">CPF:</label>
                                <input class="form-control cpf" type="text" name="cpf"  maxlength="11" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.cpf : \"\"}"/>" id="cpf" required/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="rua">Rua:</label>
                                <input class="form-control" type="text" name="rua" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.endereco.rua : \"\"}"/>"/><br/>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="numero">Número:</label>
                                <input class="form-control" type="number" name="numero" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.endereco.numero : \"\"}"/>"/><br/>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="cep">CEP:</label>
                                <input class="form-control" type="text" name="cep" maxlength="8" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? funcionario.endereco.cep : \"\"}"/>"/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-2">
                                <label for="uf">UF:</label>
                                <select id="estado" name="uf" class="custom-select" required>
                                <c:forEach items="${estados}" var="estado">
                                    <option <c:out value="${(funcionario.endereco.cidade.estado.id == estado.id) ? \"selected\" : \"\" }"/> value="<c:out value="${estado.id}"/>"><c:out value="${estado.sigla}"/></option>
                                </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-md-10">  
                                <label for="cidade">Cidade:</label>
                                <select id="cidade" name="cidade" class="custom-select" required>
                                    <c:if test="${(!(empty param.form) || param.form == \"alterar\")}" >
                                    <option selected value="<c:out value="${funcionario.endereco.cidade.id}"/>"><c:out value="${funcionario.endereco.cidade.nome}"/></option>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <input class="btn btn-outline-success" type="submit" value="<c:out value="${(!(empty param.form) || param.form == 'alterar') ? 'Alterar' : 'Salvar'}"/>"/>
                        <a class="btn btn-outline-danger" href="FuncionarioGerenteServlet">Cancelar</a>
                    </form>
                </c:when>
                <c:when test="${(!(empty param.tipo) && param.tipo == \"g\")}">
                    <form action="<c:out value="${(!(empty param.form) || param.form == 'alterar') ? 'FuncionarioGerenteServlet?action=update&tipo=g' : 'FuncionarioGerenteServlet?action=new&tipo=g'}"/>" method="POST">
                        <c:if test="${(!(empty param.form) || param.form == \"alterar\")}" >
                            <input type="hidden" name="id" value="<c:out value="${gerente.idGerente}"/>">
                        </c:if>
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="nome">Nome:</label>
                                <input class="form-control" type="text" name="nome" maxlength="100" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.nome : \"\"}"/>" required/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-8">
                                <label for="email">E-mail:</label>
                                <input class="form-control" type="email" name="email" maxlength="100" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.email : \"\"}"/>" required/><br/>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="senha">Senha:</label>
                                <input class="form-control" type="password" name="senha" value=""/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="rg">Telefone:</label>
                                <input  class="form-control telefone" type="text" name="telefone" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.telefone : \"\"}"/>" id="telefone" required/><br/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="cpf">CPF:</label>
                                <input class="form-control cpf" type="text" name="cpf"  maxlength="11" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.cpf : \"\"}"/>" id="cpf" required/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="rua">Rua:</label>
                                <input class="form-control" type="text" name="rua" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.endereco.rua : \"\"}"/>"/><br/>
                            </div>
                            <div class="form-group col-md-2">
                                <label for="numero">Número:</label>
                                <input class="form-control" type="number" name="numero" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.endereco.numero : \"\"}"/>"/><br/>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="cep">CEP:</label>
                                <input class="form-control" type="text" name="cep" maxlength="8" value="<c:out value="${(!(empty param.form) || param.form == \"alterar\") ? gerente.endereco.cep : \"\"}"/>"/><br/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-2">
                                <label for="uf">UF:</label>
                                <select id="estado" name="uf" class="custom-select" required>
                                <c:forEach items="${estados}" var="estado">
                                    <option <c:out value="${(gerente.endereco.cidade.estado.id == estado.id) ? \"selected\" : \"\" }"/> value="<c:out value="${estado.id}"/>"><c:out value="${estado.sigla}"/></option>
                                </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-md-10">  
                                <label for="cidade">Cidade:</label>
                                <select id="cidade" name="cidade" class="custom-select" required>
                                    <c:if test="${(!(empty param.form) || param.form == \"alterar\")}" >
                                    <option selected value="<c:out value="${gerente.endereco.cidade.id}"/>"><c:out value="${gerente.endereco.cidade.nome}"/></option>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <input class="btn btn-outline-success" type="submit" value="<c:out value="${(!(empty param.form) || param.form == 'alterar') ? 'Alterar' : 'Salvar'}"/>"/>
                        <a class="btn btn-outline-danger" href="FuncionarioGerenteServlet">Cancelar</a>
                    </form>
                </c:when>
            </c:choose>
        </div>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.cpf').mask('999.999.999-99');
                $('.telefone').mask('(99)999999999');
                $("#estado").change(function () {
                    getCidades();
                });
            });
            
            function getCidades() {
                var estadoId = $("#estado").val();
                var url = "CidadeServlet";
                $.ajax({
                    url: url, // URL da sua Servlet
                    data: {
                        estadoId: estadoId
                    }, // Parâmetro passado para a Servlet
                    dataType: 'json',
                    success: function (data) {
                        // Se sucesso, limpa e preenche a combo de cidade
                        // alert(JSON.stringify(data));
                        $("#cidade").empty();
                        $.each(data, function (i, obj) {
                            $("#cidade").append('<option value=' + obj.id + '>' + obj.nome + '</option>');
                        });
                    },
                    error: function (request, textStatus, errorThrown) {
                        alert(request.status + ', Error: ' + request.statusText);
                        // Erro
                    }
                });
            }
        </script>
    </body>
</html>
