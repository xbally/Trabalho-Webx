/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.TipoAtendimento;
import com.ufpr.tads.web2.beans.Usuario;
import com.ufpr.tads.web2.facade.AtendimentoFacade;
import com.ufpr.tads.web2.facade.ProdutoFacade;
import com.ufpr.tads.web2.facade.TipoAtendimentoFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AtendimentoServlet", urlPatterns = {"/AtendimentoServlet"})
public class AtendimentoServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        String acao = request.getParameter("action");

        String email = request.getParameter("email");

        Usuario us = (Usuario) session.getAttribute("usuario");
        if (us == null) {
            request.setAttribute("msg", "Usuário deve se autenticar para acessar o sistema.");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
            return;
        }
        Cliente cli = (Cliente) session.getAttribute("cliente");
        if (acao.equals("listCliente")) { 
            List<Atendimento> atendimentos;
            try {
                atendimentos = AtendimentoFacade.buscarTodosAtendimentosCliente(cli.getIdCliente());
                request.setAttribute("atendimentos", atendimentos);
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoListarCliente.jsp");
            rd.forward(request, response);
        } else if (acao.equals("list")) {
            List<Atendimento> atendimentos;
            try {
                atendimentos = AtendimentoFacade.buscarTodosAtendimentos();
                request.setAttribute("atendimentos", atendimentos);
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoListar.jsp");
            rd.forward(request, response);
        } else if (acao.equals("listAberto")) {
           List<Atendimento> atendimentos;
            try {
                atendimentos = AtendimentoFacade.buscarTodosAtendimentosAbertos();
                request.setAttribute("atendimentos", atendimentos);
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoListar.jsp");
            rd.forward(request, response); 
        } else {
            if (acao.equals("show")) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Atendimento atendimento;
                    List<TipoAtendimento> tipos = TipoAtendimentoFacade.buscarTodasCategoriasProdutos();
                    List<Produto> produtos = ProdutoFacade.buscarTodosProdutos();
                    atendimento = AtendimentoFacade.buscar(id);
                    request.setAttribute("atendimento", atendimento);
                    request.setAttribute("tipos", tipos);
                    request.setAttribute("produtos", produtos);
                } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                    rd.forward(request, response);
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoVisualizar.jsp");
                rd.forward(request, response);

            } else {
                if (acao.equals("formUpdate")) {
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        Atendimento atendimento;
                        atendimento = AtendimentoFacade.buscar(id);
                        List<TipoAtendimento> tipos = TipoAtendimentoFacade.buscarTodasCategoriasProdutos();
                        List<Produto> produtos = ProdutoFacade.buscarTodosProdutos();
                        request.setAttribute("atendimento", atendimento);
                        request.setAttribute("tipos", tipos);
                        request.setAttribute("produtos", produtos);
                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                        request.setAttribute("exception", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                        rd.forward(request, response);
                    }

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoForm.jsp?form=alterar");
                    rd.forward(request, response);

                } else {
                    if (acao.equals("update")) {
                        Atendimento atendimento = new Atendimento();
                        try {
                            int id = Integer.parseInt(request.getParameter("id"));
                            atendimento.setId(id);
                        } catch (NumberFormatException ex) {
                            request.setAttribute("exception", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                            rd.forward(request, response);
                        }

                        atendimento.setSolucao(request.getParameter("solucao"));

                        try {
                            AtendimentoFacade.alterarSolucao(atendimento);
                        } catch (SQLException | ClassNotFoundException ex) {
                            request.setAttribute("exception", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                            rd.forward(request, response);
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/AtendimentoServlet?action=list");
                        rd.forward(request, response);

                    } else {
                        if (acao.equals("formNew")) {
                            try {
                                List<TipoAtendimento> tipos = TipoAtendimentoFacade.buscarTodasCategoriasProdutos();
                                List<Produto> produtos = ProdutoFacade.buscarTodosProdutos();
                                request.setAttribute("tipos", tipos);
                                request.setAttribute("produtos", produtos);
                            } catch (SQLException | ClassNotFoundException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/atendimentoForm.jsp");
                            rd.forward(request, response);
                        } else {
                            if (acao.equals("new")) {
                                Atendimento atendimento = new Atendimento();
                                try {
                                    int idTipo = Integer.parseInt(request.getParameter("idTipoAtendimento"));
                                    TipoAtendimento tipo = new TipoAtendimento();
                                    tipo.setId(idTipo);
                                    atendimento.setTipo(tipo);
                                    Cliente cliente = new Cliente();
                                    cliente.setId(cli.getIdCliente());
                                    atendimento.setCliente(cliente);
                                    int idProduto = Integer.parseInt(request.getParameter("idProduto"));
                                    Produto produto = new Produto();
                                    produto.setId(idProduto);
                                    atendimento.setProduto(produto);
                                } catch (NumberFormatException ex) {
                                    request.setAttribute("exception", ex);
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                    rd.forward(request, response);
                                }
                                
                                String dataString = request.getParameter("dataHora");
                                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                Date data;
                                try {
                                    data = new Date(fmt.parse(dataString).getTime());
                                    atendimento.setDataHora(data);
                                } catch (ParseException ex) {
                                    request.setAttribute("exception", ex);
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                    rd.forward(request, response);
                                }
                                
                                atendimento.setSituacao(false);
                                atendimento.setDescricao(request.getParameter("descricao"));
                                atendimento.setSolucao("");
                                try {
                                    AtendimentoFacade.inserir(atendimento);
                                } catch (SQLException | ClassNotFoundException ex) {
                                    request.setAttribute("exception", ex);
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                    rd.forward(request, response);
                                }

                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/AtendimentoServlet?action=listCliente");
                                rd.forward(request, response);
                            } else {
                                if (acao.equals("remove")) {
                                    try {
                                        int id = Integer.parseInt(request.getParameter("id"));
                                        AtendimentoFacade.remover(id);
                                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
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
