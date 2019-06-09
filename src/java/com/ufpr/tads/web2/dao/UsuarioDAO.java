/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gabriel
 */
public class UsuarioDAO {
    
   private Connection conn;

    public UsuarioDAO() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public Usuario selectUsuarioByEmail(String email) throws SQLException {
        String sql = "SELECT * from tb_usuario where email_usuario=(?);";
		
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,email);

        ResultSet res = stmt.executeQuery();
        Usuario usuario = new Usuario();

        while (res.next())
        { 
            usuario.setId(res.getInt("id_usuario"));
            usuario.setEmail(res.getString("email_usuario"));
            usuario.setSenha(res.getString("senha_usuario"));
            usuario.setTipo(res.getString("tipo_usuario"));
            usuario.setIdReferencia(res.getInt("id_referencia"));
            return usuario;
        }
        return null;
    }
  
    
        public void updateSenhaById(Cliente usuario) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_usuario SET senha_usuario=(?) where id_usuario=(?);";

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(usuario.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, hexString.toString());
        stmt.setInt(2, usuario.getId());
        stmt.executeUpdate();
    }

}
