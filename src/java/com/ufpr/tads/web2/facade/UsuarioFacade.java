/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Usuario;
import com.ufpr.tads.web2.dao.UsuarioDAO;
import java.sql.SQLException;

/**
 *
 * @author Gabriel
 */
public class UsuarioFacade {
    
       
    public static Usuario buscarUsuarioByEmail(String email) throws SQLException, ClassNotFoundException {
        UsuarioDAO usuariodao = new UsuarioDAO();
        return usuariodao.selectUsuarioByEmail(email);
    }
}