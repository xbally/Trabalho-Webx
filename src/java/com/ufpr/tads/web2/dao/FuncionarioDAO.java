/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Funcionario;
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
public class FuncionarioDAO {
    private Connection conn;

    public FuncionarioDAO() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public String selectNameById (int id) throws SQLException {
        String sql = "SELECT nome_funcionario "
                + "FROM tb_funcionario "
                + "WHERE id_funcionario = (?);";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        String nome = "";
        
        while (rs.next()) {
            nome = rs.getString("nome_funcionario");
        }
        return nome;
    }
    
    public int selectIdByData(Funcionario funcionario) throws SQLException {
        String sql = "SELECT id_funcionario from tb_funcionario WHERE nome_funcionario=(?) AND telefone_funcionario=(?) AND cpf_funcionario=(?) LIMIT 1;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, funcionario.getNome());
        stmt.setString(2, funcionario.getTelefone());
        stmt.setString(3, funcionario.getCpf());

        ResultSet res = stmt.executeQuery();
        int id = 0;
        while (res.next()) {
            id = res.getInt("id_funcionario");
        }
        return id;
    }
    
    
   
    public List<Funcionario> selectFuncionarios() throws SQLException {

        List<Funcionario> resultados = new ArrayList<>();

        String sql = "SELECT * FROM tb_funcionario "
                + "INNER JOIN tb_usuario ON id_funcionario = id_referencia AND tipo_usuario = 'f'";
        PreparedStatement st = conn.prepareStatement(sql);

        ResultSet res = st.executeQuery();

        while (res.next()) {
            Funcionario seleciona_funcionario = new Funcionario();
            seleciona_funcionario.setIdFuncionario(res.getInt("id_funcionario"));
            seleciona_funcionario.setId(res.getInt("id_usuario"));
            seleciona_funcionario.setEmail(res.getString("email_usuario"));
            seleciona_funcionario.setSenha(res.getString("senha_usuario"));
            seleciona_funcionario.setNome(res.getString("nome_funcionario"));
            seleciona_funcionario.setCpf(res.getString("cpf_funcionario"));
            seleciona_funcionario.setTelefone(res.getString("telefone_funcionario"));
            resultados.add(seleciona_funcionario);
        }
        return resultados;
    }
    
    public Funcionario selectFuncionarioById(int id) throws SQLException {

        String sql = "SELECT * FROM tb_funcionario "
                + "INNER JOIN tb_usuario ON id_funcionario = id_referencia AND tipo_usuario = 'f' "
                + "WHERE id_funcionario=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            Funcionario seleciona_funcionario = new Funcionario();
            seleciona_funcionario.setIdFuncionario(res.getInt("id_funcionario"));
            seleciona_funcionario.setId(res.getInt("id_usuario"));
            seleciona_funcionario.setEmail(res.getString("email_usuario"));
            seleciona_funcionario.setSenha(res.getString("senha_usuario"));
            seleciona_funcionario.setNome(res.getString("nome_funcionario"));
            seleciona_funcionario.setCpf(res.getString("cpf_funcionario"));
            seleciona_funcionario.setTelefone(res.getString("telefone_funcionario"));
            return seleciona_funcionario;
        }
        return null;
    }

    
    public void insertFuncionario(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "INSERT INTO tb_funcionario (nome_funcionario, cpf_funcionario, telefone_funcionario) VALUES ((?), (?), (?))";
        PreparedStatement st = conn.prepareStatement(sql);

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(funcionario.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        String senha = hexString.toString();

        st.setString(1, funcionario.getNome());
        st.setString(2, funcionario.getCpf());
        st.setString(3, funcionario.getTelefone());
        st.executeUpdate();

        funcionario.setIdFuncionario(selectIdByData(funcionario));

        sql = "INSERT INTO tb_usuario (email_usuario, senha_usuario, id_referencia, tipo_usuario) VALUES ((?), (?), (?), 'f')";
        st = conn.prepareStatement(sql);

        st.setString(1, funcionario.getEmail());
        st.setString(2, senha);
        st.setInt(3, funcionario.getIdFuncionario());
        st.executeUpdate();
    }
    
    public void updateFuncionario(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_funcionario SET nome_funcionario=(?), cpf_funcionario=(?), telefone_funcionario=(?) WHERE id_funcionario=(?);";
        PreparedStatement st = conn.prepareStatement(sql);

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(funcionario.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        String senha = hexString.toString();

        st.setString(1, funcionario.getNome());
        st.setString(2, funcionario.getCpf());
        st.setString(3, funcionario.getTelefone());
        st.setInt(4, funcionario.getIdFuncionario());
        st.executeUpdate();

        sql = "UPDATE tb_usuario email_usuario=(?), senha_usuario=(?) WHERE id_referencia=(?) AND tipo_usuario='f';";
        st = conn.prepareStatement(sql);

        st.setString(1, funcionario.getEmail());
        st.setString(2, senha);
        st.setInt(3, funcionario.getIdFuncionario());
        st.executeUpdate();
    }
    
    public void updateFuncionarioWithoutSenha(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_funcionario SET nome_funcionario=(?), cpf_funcionario=(?), telefone_funcionario=(?) WHERE id_funcionario=(?);";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, funcionario.getNome());
        st.setString(2, funcionario.getCpf());
        st.setString(3, funcionario.getTelefone());
        st.setInt(4, funcionario.getIdFuncionario());
        st.executeUpdate();

        sql = "UPDATE tb_usuario email_usuario=(?) WHERE id_referencia=(?) AND tipo_usuario='f';";
        st = conn.prepareStatement(sql);

        st.setString(1, funcionario.getEmail());
        st.setInt(2, funcionario.getIdFuncionario());
        st.executeUpdate();
    }
    
    public void removeFuncionarioById(int id) throws SQLException {
        String sql = "DELETE FROM tb_usuario WHERE id_referencia=(?) AND tipo_usuario = 'f';";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        
        sql = "DELETE FROM tb_funcionario WHERE id_funcionario=(?);";

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
