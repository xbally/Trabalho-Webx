/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.tcc.exceptions.EmailDuplicadoException;
import com.ufpr.tads.web2.beans.Cidade;
import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Endereco;
import com.ufpr.tads.web2.beans.Estado;
import com.ufpr.tads.web2.beans.Usuario;
import com.ufpr.tads.web2.facade.CidadeFacade;
import com.ufpr.tads.web2.facade.ClienteFacade;
import com.ufpr.tads.web2.facade.EnderecoFacade;
import com.ufpr.tads.web2.facade.EstadoFacade;
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

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("action");
        
        if (acao == null || acao.equals("formNew")) {
                try {
                    List<Estado> estados = new ArrayList<>();
                    estados = EstadoFacade.buscarTodosEstados();
                    request.setAttribute("estados", estados);
                } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                    rd.forward(request, response);
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/clienteForm.jsp");
                rd.forward(request, response);
        } else {
            if (acao.equals("new")) {
                Cliente cliente = new Cliente();
                try {
                    String email = request.getParameter("email");

                    Usuario us = (Usuario) UsuarioFacade.buscarUsuarioByEmail(request.getParameter("email"));
                    try {
                        if (us != null) {
                            throw new EmailDuplicadoException("E-mail já cadastrado no sistema.");
                        }
                    } catch (EmailDuplicadoException ex) {
                        request.setAttribute("msg", ex.getMessage());

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                        rd.forward(request, response);
                    }

                    cliente.setEmail(email);
                } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                    rd.forward(request, response);
                }
                String cpf;
                String telefone;
                cliente.setNome(request.getParameter("nome"));
                cliente.setSenha(request.getParameter("senha"));

                cpf = request.getParameter("cpf");
                telefone = request.getParameter("telefone");

                cliente.setCpf(cpf.replaceAll("[.-]", ""));
                cliente.setTelefone(telefone.replaceAll("[()-]", ""));

                Endereco endereço = new Endereco();
                endereço.setRua(request.getParameter("rua"));
                endereço.setCep(request.getParameter("cep"));
                endereço.setReferencia("cliente");
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
                    ClienteFacade.inserir(cliente);
                    cliente.setIdCliente(ClienteFacade.buscarIdPorDadosCliente(cliente));
                    endereço.setIdReferencia(cliente.getIdCliente());
                    EnderecoFacade.inserir(endereço);
                } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                    rd.forward(request, response);
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            } else {
                if (acao.equals("formUpdate")) {
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        List<Estado> estados = new ArrayList<>();
                        estados = EstadoFacade.buscarTodosEstados();
                        Cliente cliente = ClienteFacade.buscar(id);
                        Endereco endereço = EnderecoFacade.buscarPorReferencia(id, "cliente");
                        if (endereço.getId() != 0 ) {
                            endereço.setCidade(CidadeFacade.buscarCidade(endereço.getCidade().getId()));
                            System.out.println(endereço.getCidade().getEstado().getId());
                            cliente.setEndereco(endereço);
                        } else {
                            endereço = new Endereco();
                            cliente.setEndereco(endereço);
                        }
                        request.setAttribute("cliente", cliente);
                        request.setAttribute("estados", estados);
                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                        request.setAttribute("exception", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                        rd.forward(request, response);
                    }
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/clienteForm.jsp?form=alterar");
                    rd.forward(request, response);
                } else {
                    if (acao.equals("update")) {
                        Cliente cliente = new Cliente();
                        try {
                            int id = Integer.parseInt(request.getParameter("id"));
                            cliente.setIdCliente(id);
                            String email = request.getParameter("email");

                            Usuario us = (Usuario) UsuarioFacade.buscarUsuarioByEmail(request.getParameter("email"));
                            try {
                                if (us != null && us.getIdReferencia() != cliente.getIdCliente()) {
                                    throw new EmailDuplicadoException("E-mail já cadastrado no sistema.");
                                }
                            } catch (EmailDuplicadoException ex) {
                                request.setAttribute("msg", ex.getMessage());

                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                                rd.forward(request, response);
                            }

                            cliente.setEmail(email);
                        } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                            request.setAttribute("exception", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                            rd.forward(request, response);
                        }
                        String cpf;
                        String telefone;
                        cliente.setNome(request.getParameter("nome"));
                        cliente.setSenha(request.getParameter("senha"));

                        cpf = request.getParameter("cpf");
                        telefone = request.getParameter("telefone");

                        cliente.setCpf(cpf.replaceAll("[.-]", ""));
                        cliente.setTelefone(telefone.replaceAll("[()-]", ""));

                        Endereco endereço = new Endereco();
                        endereço.setRua(request.getParameter("rua"));
                        endereço.setCep(request.getParameter("cep"));
                        endereço.setReferencia("cliente");
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
                            if (cliente.getSenha() != null || cliente.getSenha().equals("")) {
                                ClienteFacade.alterarSemSenha(cliente);
                            } else {
                                ClienteFacade.alterar(cliente);
                            }
                            endereço.setIdReferencia(cliente.getIdCliente());
                            EnderecoFacade.alterarPorIdReferencia(endereço);
                        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                            request.setAttribute("exception", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                            rd.forward(request, response);
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/AtendimentoServlet?action=listCliente");
                        rd.forward(request, response);
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
