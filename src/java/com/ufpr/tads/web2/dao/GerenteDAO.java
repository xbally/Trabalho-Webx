/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Gerente;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class GerenteDAO {
    private Connection conn;

    public GerenteDAO() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public String selectNameById (int id) throws SQLException {
        String sql = "SELECT nome_gerente "
                + "FROM tb_gerente "
                + "WHERE id_gerente = (?);";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        String nome = "";
        
        while (rs.next()) {
            nome = rs.getString("nome_gerente");
        }
        return nome;
    }
    
    public int selectIdByData(Gerente gerente) throws SQLException {
        String sql = "SELECT id_gerente from tb_gerente WHERE nome_gerente=(?) AND telefone_gerente=(?) AND cpf_gerente=(?) LIMIT 1;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, gerente.getNome());
        stmt.setString(2, gerente.getTelefone());
        stmt.setString(3, gerente.getCpf());

        ResultSet res = stmt.executeQuery();
        int id = 0;
        while (res.next()) {
            id = res.getInt("id_gerente");
        }
        return id;
    }
    
    
   
    public List<Gerente> selectGerentes() throws SQLException {

        List<Gerente> resultados = new ArrayList<>();

        String sql = "SELECT * FROM tb_gerente "
                + "INNER JOIN tb_usuario ON id_gerente = id_referencia AND tipo_usuario = 'g'";
        PreparedStatement st = conn.prepareStatement(sql);

        ResultSet res = st.executeQuery();

        while (res.next()) {
            Gerente seleciona_gerente = new Gerente();
            seleciona_gerente.setIdGerente(res.getInt("id_gerente"));
            seleciona_gerente.setId(res.getInt("id_usuario"));
            seleciona_gerente.setEmail(res.getString("email_usuario"));
            seleciona_gerente.setSenha(res.getString("senha_usuario"));
            seleciona_gerente.setNome(res.getString("nome_gerente"));
            seleciona_gerente.setCpf(res.getString("cpf_gerente"));
            seleciona_gerente.setTelefone(res.getString("telefone_gerente"));
            resultados.add(seleciona_gerente);
        }
        return resultados;
    }
    
    public Gerente selectGerenteById(int id) throws SQLException {

        String sql = "SELECT * FROM tb_gerente "
                + "INNER JOIN tb_usuario ON id_gerente = id_referencia AND tipo_usuario = 'g' "
                + "WHERE id_gerente=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            Gerente seleciona_gerente = new Gerente();
            seleciona_gerente.setIdGerente(res.getInt("id_gerente"));
            seleciona_gerente.setId(res.getInt("id_usuario"));
            seleciona_gerente.setEmail(res.getString("email_usuario"));
            seleciona_gerente.setSenha(res.getString("senha_usuario"));
            seleciona_gerente.setNome(res.getString("nome_gerente"));
            seleciona_gerente.setCpf(res.getString("cpf_gerente"));
            seleciona_gerente.setTelefone(res.getString("telefone_gerente"));
            return seleciona_gerente;
        }
        return null;
    }

    
    public void insertGerente(Gerente gerente) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "INSERT INTO tb_gerente (nome_gerente, cpf_gerente, telefone_gerente) VALUES ((?), (?), (?))";
        PreparedStatement st = conn.prepareStatement(sql);

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(gerente.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        String senha = hexString.toString();

        st.setString(1, gerente.getNome());
        st.setString(2, gerente.getCpf());
        st.setString(3, gerente.getTelefone());
        st.executeUpdate();

        gerente.setIdGerente(selectIdByData(gerente));

        sql = "INSERT INTO tb_usuario (email_usuario, senha_usuario, id_referencia, tipo_usuario) VALUES ((?), (?), (?), 'g')";
        st = conn.prepareStatement(sql);

        st.setString(1, gerente.getEmail());
        st.setString(2, senha);
        st.setInt(3, gerente.getIdGerente());
        st.executeUpdate();
    }
    
    public void updateGerente(Gerente gerente) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_gerente SET nome_gerente=(?), cpf_gerente=(?), telefone_gerente=(?) WHERE id_gerente=(?);";
        PreparedStatement st = conn.prepareStatement(sql);

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(gerente.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        String senha = hexString.toString();

        st.setString(1, gerente.getNome());
        st.setString(2, gerente.getCpf());
        st.setString(3, gerente.getTelefone());
        st.setInt(4, gerente.getIdGerente());
        st.executeUpdate();


        sql = "UPDATE tb_usuario SET email_usuario=(?), senha_usuario=(?) WHERE id_referencia=(?) AND tipo_usuario='g';";
        st = conn.prepareStatement(sql);

        st.setString(1, gerente.getEmail());
        st.setString(2, senha);
        st.setInt(3, gerente.getIdGerente());
        st.executeUpdate();
    }
    
    public void updateGerenteWithoutSenha(Gerente gerente) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_gerente SET nome_gerente=(?), cpf_gerente=(?), telefone_gerente=(?) WHERE id_gerente=(?);";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, gerente.getNome());
        st.setString(2, gerente.getCpf());
        st.setString(3, gerente.getTelefone());
        st.setInt(4, gerente.getIdGerente());
        st.executeUpdate();


        sql = "UPDATE tb_usuario SET email_usuario=(?) WHERE id_referencia=(?) AND tipo_usuario='g';";
        st = conn.prepareStatement(sql);

        st.setString(1, gerente.getEmail());
        st.setInt(2, gerente.getIdGerente());
        st.executeUpdate();
    }
    
    public void removeGerenteById(int id) throws SQLException {
        String sql = "DELETE FROM tb_usuario WHERE id_referencia=(?) AND tipo_usuario = 'g';";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        
        sql = " DELETE FROM tb_gerente WHERE id_gerente=(?);";

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
