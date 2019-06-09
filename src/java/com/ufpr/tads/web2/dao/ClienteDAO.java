/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Cliente;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ClienteDAO {
    private Connection conn;

    public ClienteDAO() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public String selectNameById (int id) throws SQLException {
        String sql = "SELECT nome_cliente "
                + "FROM tb_cliente "
                + "WHERE id_cliente = (?);";
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        String nome = "";
        
        while (rs.next()) {
            nome = rs.getString("nome_cliente");
        }
        return nome;
    }
    
    public int selectIdByData(Cliente cliente) throws SQLException {
        String sql = "SELECT id_cliente from tb_cliente WHERE nome_cliente=(?) AND telefone_cliente=(?) AND cpf_cliente=(?) LIMIT 1;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getTelefone());
        stmt.setString(3, cliente.getCpf());

        ResultSet res = stmt.executeQuery();
        int id = 0;
        while (res.next()) {
            id = res.getInt("id_cliente");
        }
        return id;
    }
    
    
   
    public List<Cliente> selectClientes() throws SQLException {

        List<Cliente> resultados = new ArrayList<>();

        String sql = "SELECT * FROM tb_cliente "
                + "INNER JOIN tb_usuario ON id_cliente = id_referencia AND tipo_usuario = 'c'";
        PreparedStatement st = conn.prepareStatement(sql);

        ResultSet res = st.executeQuery();

        while (res.next()) {
            Cliente seleciona_cliente = new Cliente();
            seleciona_cliente.setIdCliente(res.getInt("id_cliente"));
            seleciona_cliente.setId(res.getInt("id_usuario"));
            seleciona_cliente.setEmail(res.getString("email_usuario"));
            seleciona_cliente.setSenha(res.getString("senha_usuario"));
            seleciona_cliente.setNome(res.getString("nome_cliente"));
            seleciona_cliente.setCpf(res.getString("cpf_cliente"));
            seleciona_cliente.setTelefone(res.getString("telefone_cliente"));
            resultados.add(seleciona_cliente);
        }
        return resultados;
    }
    
    public Cliente selectClienteById(int id) throws SQLException {

        String sql = "SELECT * FROM tb_cliente c "
                + "INNER JOIN tb_usuario u ON c.id_cliente = u.id_referencia AND u.tipo_usuario = 'c' "
                + "WHERE c.id_cliente=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            Cliente seleciona_cliente = new Cliente();
            seleciona_cliente.setIdCliente(res.getInt("id_cliente"));
            seleciona_cliente.setId(res.getInt("id_usuario"));
            seleciona_cliente.setEmail(res.getString("email_usuario"));
            seleciona_cliente.setSenha(res.getString("senha_usuario"));
            seleciona_cliente.setNome(res.getString("nome_cliente"));
            seleciona_cliente.setCpf(res.getString("cpf_cliente"));
            seleciona_cliente.setTelefone(res.getString("telefone_cliente"));
            return seleciona_cliente;
        }
        return null;
    }
    
    public Cliente selectClienteByIdUsuario(int id) throws SQLException {

        String sql = "SELECT * FROM tb_cliente "
                + "INNER JOIN tb_usuario ON id_cliente = id_referencia AND tipo_usuario = 'c' "
                + "WHERE u.id_usuario=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            Cliente seleciona_cliente = new Cliente();
            seleciona_cliente.setIdCliente(res.getInt("id_cliente"));
            seleciona_cliente.setId(res.getInt("id_usuario"));
            seleciona_cliente.setEmail(res.getString("email_usuario"));
            seleciona_cliente.setSenha(res.getString("senha_usuario"));
            seleciona_cliente.setNome(res.getString("nome_cliente"));
            seleciona_cliente.setCpf(res.getString("cpf_cliente"));
            seleciona_cliente.setTelefone(res.getString("telefone_cliente"));
            return seleciona_cliente;
        }
        return null;
    }

    
    public void insertCliente(Cliente cliente) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "INSERT INTO tb_cliente (nome_cliente, cpf_cliente, telefone_cliente) VALUES ((?), (?), (?))";
        PreparedStatement st = conn.prepareStatement(sql);

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(cliente.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        String senha = hexString.toString();

        st.setString(1, cliente.getNome());
        st.setString(2, cliente.getCpf());
        st.setString(3, cliente.getTelefone());
        st.executeUpdate();

        cliente.setIdCliente(selectIdByData(cliente));

        sql = "INSERT INTO tb_usuario (email_usuario, senha_usuario, id_referencia, tipo_usuario) VALUES ((?), (?), (?), 'c')";
        st = conn.prepareStatement(sql);

        st.setString(1, cliente.getEmail());
        st.setString(2, senha);
        st.setInt(3, cliente.getIdCliente());
        st.executeUpdate();
    }
    
    public void updateCliente(Cliente cliente) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_cliente SET nome_cliente=(?), cpf_cliente=(?), telefone_cliente=(?) WHERE id_cliente=(?);";
        PreparedStatement st = conn.prepareStatement(sql);

        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(cliente.getSenha().getBytes("UTF-8"));
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        String senha = hexString.toString();

        st.setString(1, cliente.getNome());
        st.setString(2, cliente.getCpf());
        st.setString(3, cliente.getTelefone());
        st.setInt(4, cliente.getIdCliente());
        st.executeUpdate();


        sql = "UPDATE tb_usuario SET email_usuario=(?), senha_usuario=(?) WHERE id_referencia=(?) AND tipo_usuario='c';";
        st = conn.prepareStatement(sql);

        st.setString(1, cliente.getEmail());
        st.setString(2, senha);
        st.setInt(3, cliente.getIdCliente());
        st.executeUpdate();
    }
    
    public void updateClienteWithoutSenha(Cliente cliente) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String sql = "UPDATE tb_cliente SET nome_cliente=(?), cpf_cliente=(?), telefone_cliente=(?) WHERE id_cliente=(?);";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, cliente.getNome());
        st.setString(2, cliente.getCpf());
        st.setString(3, cliente.getTelefone());
        st.setInt(4, cliente.getIdCliente());
        st.executeUpdate();


        sql = "UPDATE tb_usuario SET email_usuario=(?) WHERE id_referencia=(?) AND tipo_usuario='c';";
        st = conn.prepareStatement(sql);

        st.setString(1, cliente.getEmail());
        st.setInt(2, cliente.getIdCliente());
        st.executeUpdate();
    }
}
