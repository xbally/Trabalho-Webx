/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.web2.beans.CategoriaProduto;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.facade.CategoriaProdutoFacade;
import com.ufpr.tads.web2.facade.ProdutoFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "ProdutoServlet", urlPatterns = {"/ProdutoServlet"})
public class ProdutoServlet extends HttpServlet {

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

        /*String email = request.getParameter("email");
        System.out.print(email);

        Usuario us = (Usuario) session.getAttribute("usuario");
        if (us == null) {
            request.setAttribute("msg", "Usuário deve se autenticar para acessar o sistema.");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
            return;
        }*/
        if (acao == null || acao.equals("list")) {
            List<Produto> produtos;
            try {
                produtos = ProdutoFacade.buscarTodosProdutos();
                request.setAttribute("produtos", produtos);
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtoListar.jsp");
            rd.forward(request, response);
        } else {
            if (acao.equals("show")) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Produto produto;
                    produto = ProdutoFacade.buscar(id);
                    request.setAttribute("produto", produto);
                } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                    request.setAttribute("exception", ex);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                    rd.forward(request, response);
                }
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtoVisualizar.jsp");
                rd.forward(request, response);

            } else {
                if (acao.equals("formUpdate")) {
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        Produto produto = ProdutoFacade.buscar(id);
                        List<CategoriaProduto> categorias = CategoriaProdutoFacade.buscarTodasCategoriasProdutos();
                        request.setAttribute("produto", produto);
                        request.setAttribute("categorias", categorias);
                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                        request.setAttribute("exception", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                        rd.forward(request, response);
                    }

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtoForm.jsp?form=alterar");
                    rd.forward(request, response);

                } else {
                    if (acao.equals("update")) {
                        Produto produto = new Produto();
                        CategoriaProduto categoria = new CategoriaProduto();
                        try {
                            int id = Integer.parseInt(request.getParameter("id"));
                            produto.setId(id);
                            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
                            categoria.setId(idCategoria);
                            produto.setCategoria(categoria);
                            String strPeso = request.getParameter("peso");
                            strPeso = strPeso.replaceAll("[,]", ".");
                            double peso = Double.parseDouble(strPeso);
                            produto.setPeso(peso);
                        } catch (NumberFormatException ex) {
                            request.setAttribute("exception", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                            rd.forward(request, response);
                        }

                        produto.setNome(request.getParameter("nome"));
                        produto.setDescricao(request.getParameter("descricao"));

                        try {
                            ProdutoFacade.alterar(produto);
                        } catch (SQLException | ClassNotFoundException ex) {
                            request.setAttribute("exception", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                            rd.forward(request, response);
                        }
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProdutoServlet?action=list");
                        rd.forward(request, response);

                    } else {
                        if (acao.equals("formNew")) {
                            try {
                                List<CategoriaProduto> categorias = CategoriaProdutoFacade.buscarTodasCategoriasProdutos();
                                request.setAttribute("categorias", categorias);
                            } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                request.setAttribute("exception", ex);
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                rd.forward(request, response);
                            }
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/produtoForm.jsp");
                            rd.forward(request, response);
                        } else {
                            if (acao.equals("new")) {
                                Produto produto = new Produto();
                                CategoriaProduto categoria = new CategoriaProduto();
                                try {
                                    int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
                                    categoria.setId(idCategoria);
                                    produto.setCategoria(categoria);
                                    String strPeso = request.getParameter("peso");;
                                    strPeso = strPeso.replaceAll("[,]", ".");
                                    double peso = Double.parseDouble(strPeso);
                                    produto.setPeso(peso);
                                } catch (NumberFormatException ex) {
                                    request.setAttribute("exception", ex);
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                    rd.forward(request, response);
                                }

                                produto.setNome(request.getParameter("nome"));
                                produto.setDescricao(request.getParameter("descricao"));

                                try {
                                    ProdutoFacade.inserir(produto);
                                } catch (SQLException | ClassNotFoundException ex) {
                                    request.setAttribute("exception", ex);
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                    rd.forward(request, response);
                                }
                                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProdutoServlet?action=list");
                                rd.forward(request, response);
                            } else {
                                if (acao.equals("remove")) {
                                    try {
                                        int id = Integer.parseInt(request.getParameter("id"));
                                        ProdutoFacade.remover(id);
                                    } catch (NumberFormatException | SQLException | ClassNotFoundException ex) {
                                        request.setAttribute("exception", ex);
                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                                        rd.forward(request, response);
                                    }
                                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ProdutoServlet?action=list");
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
