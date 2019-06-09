/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.tcc.exceptions.EmailDuplicadoException;
import com.ufpr.tads.web2.beans.Cidade;
import com.ufpr.tads.web2.beans.Endereco;
import com.ufpr.tads.web2.beans.Estado;
import com.ufpr.tads.web2.beans.Funcionario;
import com.ufpr.tads.web2.beans.Gerente;
import com.ufpr.tads.web2.beans.Usuario;
import com.ufpr.tads.web2.facade.CidadeFacade;
import com.ufpr.tads.web2.facade.EnderecoFacade;
import com.ufpr.tads.web2.facade.EstadoFacade;
import com.ufpr.tads.web2.facade.FuncionarioFacade;
import com.ufpr.tads.web2.facade.GerenteFacade;
import com.ufpr.tads.web2.facade.UsuarioFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "FuncionarioGerenteServlet", urlPatterns = {"/FuncionarioGerenteServlet"})
public class FuncionarioGerenteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String acao = request.getParameter("action");

        Usuario us = (Usuario) session.getAttribute("usuario");
        if (us == null) {
            request.setAttribute("msg", "Usuário deve se autenticar para acessar o sistema.");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
            return;
        }
        if (acao == null || acao.equals("list")) {
            List<Gerente> gerentes;
            List<Funcionario> funcionarios;
            try {
                gerentes = GerenteFacade.buscarTodosGerentes();
                funcionarios = FuncionarioFacade.buscarTodosFuncionarios();
                request.setAttribute("gerentes", gerentes);
                request.setAttribute("funcionarios", funcionarios);
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionarioGerenteListar.jsp");
            rd.forward(request, response);
        } else {
            if (acao.equals("show")) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String tipo = request.getParameter("tipo");
                    if ( tipo.equals("f") ) {
                        Funcionario funcionario = FuncionarioFacade.buscar(id);
                        Endereco endereço = EnderecoFacade.buscarPorReferencia(id, "funcionario");
                        if (endereço.getId() != 0 ) {
                            endereço.setCidade(CidadeFacade.buscarCidade(endereço.getCidade().getId()));
                            funcionario.setEndereco(endereço);
                        } else {
                            endereço = new Endereco();
                            funcionario.setEndereco(endereço);
                        }
                        request.setAttribute("funcionario", funcionario);
                    } else if ( tipo.equals("g") ) {
                        Gerente gerente = GerenteFacade.buscar(id);
                        Endereco endereço = EnderecoFacade.buscarPorReferencia(id, "gerente");
                        if (endereço.getId() != 0 ) {
                            endereço.setCidade(CidadeFacade.buscarCidade(endereço.getCidade().getId()));
                            gerente.setEndereco(endereço);
                        } else {
                            endereço = new Endereco();
                            gerente.setEndereco(endereço);
                        }
                        request.setAttribute("gerente", gerente);
                    }
                } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                    rd.forward(request, response);
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionarioGerenteVisualizar.jsp");
                rd.forward(request, response);

            } else {
                if (acao.equals("formUpdate")) {
                    String tipo = request.getParameter("tipo");
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        List<Estado> estados = new ArrayList<>();
                        estados = EstadoFacade.buscarTodosEstados();
                        request.setAttribute("estados", estados);
                        if ( tipo.equals("f") ) {
                            Funcionario funcionario = FuncionarioFacade.buscar(id);
                            Endereco endereço = EnderecoFacade.buscarPorReferencia(id, "funcionario");
                            if (endereço.getId() != 0 ) {
                                endereço.setCidade(CidadeFacade.buscarCidade(endereço.getCidade().getId()));
                                System.out.println(endereço.getCidade().getEstado().getId());
                                funcionario.setEndereco(endereço);
                            } else {
                                endereço = new Endereco();
                                funcionario.setEndereco(endereço);
                            }
                            request.setAttribute("funcionario", funcionario);
                        } else if ( tipo.equals("g") ) {
                            Gerente gerente = GerenteFacade.buscar(id);
                            Endereco endereço = EnderecoFacade.buscarPorReferencia(id, "gerente");
                            if (endereço.getId() != 0 ) {
                                endereço.setCidade(CidadeFacade.buscarCidade(endereço.getCidade().getId()));
                                gerente.setEndereco(endereço);
                            } else {
                                endereço = new Endereco();
                                gerente.setEndereco(endereço);
                            }
                            request.setAttribute("gerente", gerente);
                        }
                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                        request.setAttribute("exception", ex);
                        request.setAttribute("javax.servlet.error.status_code", 500);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                        rd.forward(request, response);
                    }

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionarioGerenteForm.jsp?form=alterar&tipo=" + tipo);
                    rd.forward(request, response);

                } else {
                    if (acao.equals("update")) {
                        String tipo = request.getParameter("tipo");
                        if ( tipo.equals("f") ) {
                            Funcionario funcionario = new Funcionario();
                            try {
                                int id = Integer.parseInt(request.getParameter("id"));
                                funcionario.setIdFuncionario(id);
                                String email = request.getParameter("email");

                                us = (Usuario) UsuarioFacade.buscarUsuarioByEmail(request.getParameter("email"));
                                try {
                                    if (us != null && us.getIdReferencia() != funcionario.getIdFuncionario()) {
                                        throw new EmailDuplicadoException("E-mail já cadastrado no sistema.");
                                    }
                                } catch (EmailDuplicadoException ex) {
                                    request.setAttribute("msg", ex.getMessage());

                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                                    rd.forward(request, response);
                                }

                                funcionario.setEmail(email);
                            } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            String cpf;
                            String telefone;
                            funcionario.setNome(request.getParameter("nome"));
                            funcionario.setSenha(request.getParameter("senha"));

                            cpf = request.getParameter("cpf");
                            telefone = request.getParameter("telefone");

                            funcionario.setCpf(cpf.replaceAll("[.-]", ""));
                            funcionario.setTelefone(telefone.replaceAll("[()-]", ""));

                            Endereco endereço = new Endereco();
                            endereço.setRua(request.getParameter("rua"));
                            endereço.setCep(request.getParameter("cep"));
                            endereço.setReferencia("funcionario");
                            try {
                                endereço.setNumero(Integer.parseInt(request.getParameter("numero")));
                                Cidade cidade = new Cidade();
                                cidade.setId(Integer.parseInt(request.getParameter("cidade")));
                                endereço.setCidade(cidade);
                            } catch (NumberFormatException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }

                            try {
                                if (funcionario.getSenha() != null || funcionario.getSenha().equals("")) {
                                    FuncionarioFacade.alterarSemSenha(funcionario);
                                } else {
                                    FuncionarioFacade.alterar(funcionario);
                                }
                                endereço.setIdReferencia(funcionario.getIdFuncionario());
                                EnderecoFacade.alterarPorIdReferencia(endereço);
                            } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                            rd.forward(request, response);
                        } else if ( tipo.equals("g") ) {
                            Gerente gerente = new Gerente();
                            try {
                                int id = Integer.parseInt(request.getParameter("id"));
                                gerente.setIdGerente(id);
                                String email = request.getParameter("email");

                                us = (Usuario) UsuarioFacade.buscarUsuarioByEmail(request.getParameter("email"));
                                try {
                                    if (us != null && us.getIdReferencia() != gerente.getIdGerente()) {
                                        throw new EmailDuplicadoException("E-mail já cadastrado no sistema.");
                                    }
                                } catch (EmailDuplicadoException ex) {
                                    request.setAttribute("msg", ex.getMessage());

                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                                    rd.forward(request, response);
                                }

                                gerente.setEmail(email);
                            } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            String cpf;
                            String telefone;
                            gerente.setNome(request.getParameter("nome"));
                            gerente.setSenha(request.getParameter("senha"));

                            cpf = request.getParameter("cpf");
                            telefone = request.getParameter("telefone");

                            gerente.setCpf(cpf.replaceAll("[.-]", ""));
                            gerente.setTelefone(telefone.replaceAll("[()-]", ""));

                            Endereco endereço = new Endereco();
                            endereço.setRua(request.getParameter("rua"));
                            endereço.setCep(request.getParameter("cep"));
                            endereço.setReferencia("gerente");
                            try {
                                endereço.setNumero(Integer.parseInt(request.getParameter("numero")));
                                Cidade cidade = new Cidade();
                                cidade.setId(Integer.parseInt(request.getParameter("cidade")));
                                endereço.setCidade(cidade);
                            } catch (NumberFormatException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }

                            try {
                                if (gerente.getSenha() != null || gerente.getSenha().equals("")) {
                                    GerenteFacade.alterarSemSenha(gerente);
                                } else {
                                    GerenteFacade.alterar(gerente);
                                }
                                endereço.setIdReferencia(gerente.getIdGerente());
                                EnderecoFacade.alterarPorIdReferencia(endereço);
                            } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                            rd.forward(request, response);
                        }
                    } else {
                        if (acao.equals("formNew")) {
                            try {
                                List<Estado> estados = new ArrayList<>();
                                estados = EstadoFacade.buscarTodosEstados();
                                request.setAttribute("estados", estados);
                            } catch (SQLException | ClassNotFoundException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            String tipo = request.getParameter("tipo");
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionarioGerenteForm.jsp?tipo=" + tipo);
                            rd.forward(request, response);
                        } else {
                            if (acao.equals("new")) {
                                String tipo = request.getParameter("tipo");
                                if ( tipo.equals("f") ) {
                                    Funcionario funcionario = new Funcionario();
                                    try {
                                        String email = request.getParameter("email");

                                        us = (Usuario) UsuarioFacade.buscarUsuarioByEmail(request.getParameter("email"));
                                        try {
                                            if (us != null) {
                                                throw new EmailDuplicadoException("E-mail já cadastrado no sistema.");
                                            }
                                        } catch (EmailDuplicadoException ex) {
                                            request.setAttribute("msg", ex.getMessage());

                                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                                            rd.forward(request, response);
                                        }

                                        funcionario.setEmail(email);
                                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }
                                    String cpf;
                                    String telefone;
                                    funcionario.setNome(request.getParameter("nome"));
                                    funcionario.setSenha(request.getParameter("senha"));

                                    cpf = request.getParameter("cpf");
                                    telefone = request.getParameter("telefone");

                                    funcionario.setCpf(cpf.replaceAll("[.-]", ""));
                                    funcionario.setTelefone(telefone.replaceAll("[()-]", ""));

                                    Endereco endereço = new Endereco();
                                    endereço.setRua(request.getParameter("rua"));
                                    endereço.setCep(request.getParameter("cep"));
                                    endereço.setReferencia("funcionario");
                                    try {
                                        endereço.setNumero(Integer.parseInt(request.getParameter("numero")));
                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.parseInt(request.getParameter("cidade")));
                                        endereço.setCidade(cidade);
                                    } catch (NumberFormatException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }

                                    try {
                                        FuncionarioFacade.inserir(funcionario);
                                        funcionario.setIdFuncionario(FuncionarioFacade.buscarIdPorDadosFuncionario(funcionario));
                                        endereço.setIdReferencia(funcionario.getIdFuncionario());
                                        EnderecoFacade.inserir(endereço);
                                    } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }
                                } else if ( tipo.equals("g") ) {
                                    Gerente gerente = new Gerente();
                                    try {
                                        String email = request.getParameter("email");

                                        us = (Usuario) UsuarioFacade.buscarUsuarioByEmail(request.getParameter("email"));
                                        try {
                                            if (us != null) {
                                                throw new EmailDuplicadoException("E-mail já cadastrado no sistema.");
                                            }
                                        } catch (EmailDuplicadoException ex) {
                                            request.setAttribute("msg", ex.getMessage());

                                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                                            rd.forward(request, response);
                                        }

                                        gerente.setEmail(email);
                                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }
                                    String cpf;
                                    String telefone;
                                    gerente.setNome(request.getParameter("nome"));
                                    gerente.setSenha(request.getParameter("senha"));

                                    cpf = request.getParameter("cpf");
                                    telefone = request.getParameter("telefone");

                                    gerente.setCpf(cpf.replaceAll("[.-]", ""));
                                    gerente.setTelefone(telefone.replaceAll("[()-]", ""));

                                    Endereco endereço = new Endereco();
                                    endereço.setRua(request.getParameter("rua"));
                                    endereço.setCep(request.getParameter("cep"));
                                    endereço.setReferencia("gerente");
                                    try {
                                        endereço.setNumero(Integer.parseInt(request.getParameter("numero")));
                                        Cidade cidade = new Cidade();
                                        cidade.setId(Integer.parseInt(request.getParameter("cidade")));
                                        endereço.setCidade(cidade);
                                    } catch (NumberFormatException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }

                                    try {
                                        GerenteFacade.inserir(gerente);
                                        gerente.setIdGerente(GerenteFacade.buscarIdPorDadosGerente(gerente));
                                        endereço.setIdReferencia(gerente.getIdGerente());
                                        EnderecoFacade.inserir(endereço);
                                    } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }
                                }

                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                                rd.forward(request, response);
                            } else {
                                if (acao.equals("remove")) {
                                    try {
                                        int id = Integer.parseInt(request.getParameter("id"));
                                        String tipo = request.getParameter("tipo");
                                        if ( tipo.equals("f") ) {
                                            FuncionarioFacade.remover(id);
                                        } else if ( tipo.equals("g") ) {
                                            GerenteFacade.remover(id);
                                        }
                                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioGerenteServlet?action=list");
                                    rd.forward(request, response);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
