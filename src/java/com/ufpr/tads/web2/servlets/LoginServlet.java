/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.servlets;

import com.ufpr.tads.tcc.exceptions.UsuarioSenhaInvalidosException;
import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Usuario;
import com.ufpr.tads.web2.facade.ClienteFacade;
import com.ufpr.tads.web2.facade.FuncionarioFacade;
import com.ufpr.tads.web2.facade.GerenteFacade;
import com.ufpr.tads.web2.facade.UsuarioFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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


        String acao = request.getParameter("action");
        if (acao == null || acao.equals("login")) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            StringBuffer hexString = new StringBuffer();
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                byte[] hash = md.digest(senha.getBytes("UTF-8"));
                for (int i = 0; i < hash.length; i++) {
                    if ((0xff & hash[i]) < 0x10) {
                        hexString.append("0"
                                + Integer.toHexString((0xFF & hash[i])));
                    } else {
                        hexString.append(Integer.toHexString(0xFF & hash[i]));
                    }
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }

            Usuario usuario = new Usuario();

            try {
                usuario = UsuarioFacade.buscarUsuarioByEmail(email);
            } catch (SQLException | ClassNotFoundException ex) {
                request.setAttribute("exception", ex);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                rd.forward(request, response);
            }
            try {
                if (usuario != null && usuario.getEmail().equals(email) && usuario.getSenha().equals(hexString.toString())) {
                    Usuario us = new Usuario();
                    us.setId(usuario.getId());
                    us.setEmail(usuario.getEmail());
                    us.setTipo(usuario.getTipo());
                    us.setIdReferencia(usuario.getIdReferencia());
                    String nome             = "";
                    String redirecionamento   = "";
                    HttpSession session = request.getSession();
                    try {
                        switch (us.getTipo()) {

                            case "c" :
                                nome                = ClienteFacade.buscarNomePorId(us.getIdReferencia());
                                Cliente cliente     = ClienteFacade.buscar(us.getIdReferencia());
                                session.setAttribute("cliente", cliente);
                                redirecionamento    = "/AtendimentoServlet?action=listCliente";
                                break;
                            case "g" : 
                                nome                = GerenteFacade.buscarNomePorId(us.getIdReferencia());
                                redirecionamento    = "/RelatorioServlet?action=dashboard";
                                break;
                            case "f" :
                                nome                = FuncionarioFacade.buscarNomePorId(us.getIdReferencia());
                                redirecionamento    = "/AtendimentoServlet?action=list";
                                break;
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        request.setAttribute("exception", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/erro.jsp");
                        rd.forward(request, response);
                    }
                    session.setAttribute("usuario", us);
                    session.setAttribute("nome", nome);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher(redirecionamento);
                    rd.forward(request, response);

                } else {
                    throw new UsuarioSenhaInvalidosException("Usuário/Senha inválidos.");
                }
            } catch (UsuarioSenhaInvalidosException ex) {
                request.setAttribute("msg", ex.getMessage());

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }
        
        } else if (acao.equals("logout")) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            request.setAttribute("msg", "Usuário desconectado com sucesso");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
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
