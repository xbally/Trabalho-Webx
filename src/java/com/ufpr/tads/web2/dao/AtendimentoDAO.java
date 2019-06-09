/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.beans.TipoAtendimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class AtendimentoDAO {
    private final Connection conn;

    public AtendimentoDAO() throws SQLException, ClassNotFoundException {
        this.conn = (Connection) new ConnectionFactory().getConnection();
    }

    public List<Atendimento> getAtendimentos() throws SQLException {
        ResultSet rs;
        List<Atendimento> atendimentos = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String query = "select * from tb_atendimento a "
                + "inner join tb_cliente c on c.id_cliente = a.id_cliente "
                + "order by a.data_hora_atendimento desc;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id_atendimento");
            int idTipo = rs.getInt("id_tipo_atendimento");
            int idCliente = rs.getInt("id_cliente");
            int idProduto = rs.getInt("id_produto");
            Timestamp dataHora = rs.getTimestamp("data_hora_atendimento");
            boolean situacao = rs.getBoolean("situacao_atendimento");
            String descricao = rs.getString("descricao_atendimento");
            String solucao = rs.getString("solucao_atendimento");
            String nomeCliente = rs.getString("nome_cliente");
            Atendimento atendimento = new Atendimento();
            atendimento.setId(id);
            TipoAtendimento tipo = new TipoAtendimento();
            tipo.setId(idTipo);
            atendimento.setTipo(tipo);
            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
            atendimento.setCliente(cliente);
            Produto produto = new Produto();
            produto.setId(idProduto);
            atendimento.setProduto(produto);
            atendimento.setDataHora(dataHora);
            atendimento.setSituacao(situacao);
            atendimento.setDescricao(descricao);
            atendimento.setSolucao(solucao);
            atendimentos.add(atendimento);
        }
        return atendimentos;
    }
    
    public List<Atendimento> getAtendimentosAberto() throws SQLException {
        ResultSet rs;
        List<Atendimento> atendimentos = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String query = "select * from tb_atendimento a "
                + "inner join tb_cliente c on c.id_cliente = a.id_cliente "
                + "where a.situacao_atendimento = false "
                + "order by a.data_hora_atendimento desc;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id_atendimento");
            int idTipo = rs.getInt("id_tipo_atendimento");
            int idCliente = rs.getInt("id_cliente");
            int idProduto = rs.getInt("id_produto");
            Timestamp dataHora = rs.getTimestamp("data_hora_atendimento");
            boolean situacao = rs.getBoolean("situacao_atendimento");
            String descricao = rs.getString("descricao_atendimento");
            String solucao = rs.getString("solucao_atendimento");
            String nomeCliente = rs.getString("nome_cliente");
            Atendimento atendimento = new Atendimento();
            atendimento.setId(id);
            TipoAtendimento tipo = new TipoAtendimento();
            tipo.setId(idTipo);
            atendimento.setTipo(tipo);
            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
            atendimento.setCliente(cliente);
            Produto produto = new Produto();
            produto.setId(idProduto);
            atendimento.setProduto(produto);
            atendimento.setDataHora(dataHora);
            atendimento.setSituacao(situacao);
            atendimento.setDescricao(descricao);
            atendimento.setSolucao(solucao);
            atendimentos.add(atendimento);
        }
        return atendimentos;
    }
    
    public List<Atendimento> getAtendimentosByIdCliente(int idCliente) throws SQLException {
        ResultSet rs;
        List<Atendimento> atendimentos = new ArrayList<>();
        String query = "select * from tb_atendimento a "
                + "inner join tb_cliente c on c.id_cliente = a.id_cliente "
                + "where a.id_cliente=(?) "
                + "order by a.data_hora_atendimento desc;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, idCliente);
        rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_atendimento");
            int idTipo = rs.getInt("id_tipo_atendimento");
            int idProduto = rs.getInt("id_produto");
            Timestamp dataHora = rs.getTimestamp("data_hora_atendimento");
            boolean situacao = rs.getBoolean("situacao_atendimento");
            String descricao = rs.getString("descricao_atendimento");
            String solucao = rs.getString("solucao_atendimento");
            String nomeCliente = rs.getString("nome_cliente");
            Atendimento atendimento = new Atendimento();
            atendimento.setId(id);
            TipoAtendimento tipo = new TipoAtendimento();
            tipo.setId(idTipo);
            atendimento.setTipo(tipo);
            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
            atendimento.setCliente(cliente);
            Produto produto = new Produto();
            produto.setId(idProduto);
            atendimento.setProduto(produto);
            atendimento.setDataHora(dataHora);
            atendimento.setSituacao(situacao);
            atendimento.setDescricao(descricao);
            atendimento.setSolucao(solucao);
            atendimentos.add(atendimento);
        }
        return atendimentos;
    }
    
    public Atendimento getAtendimentoById(int id) throws SQLException {
        ResultSet rs;
        String query = "select * from tb_atendimento a "
                + "inner join tb_cliente c on c.id_cliente = a.id_cliente "
                + "inner join tb_tipo_atendimento ta on ta.id_tipo_atendimento = a.id_tipo_atendimento "
                + "inner join tb_produto p on p.id_produto = a.id_produto "
                + "where a.id_atendimento=(?);";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
            int idTipo = rs.getInt("id_tipo_atendimento");
            int idCliente = rs.getInt("id_cliente");
            int idProduto = rs.getInt("id_produto");
            Timestamp dataHora = rs.getTimestamp("data_hora_atendimento");
            boolean situacao = rs.getBoolean("situacao_atendimento");
            String descricao = rs.getString("descricao_atendimento");
            String solucao = rs.getString("solucao_atendimento");
            String nomeCliente = rs.getString("nome_cliente");
            String nomeProduto = rs.getString("nome_Produto");
            String nomeTipo = rs.getString("nome_tipo_atendimento");
            Atendimento atendimento = new Atendimento();
            atendimento.setId(id);
            TipoAtendimento tipo = new TipoAtendimento();
            tipo.setId(idTipo);
            tipo.setNome(nomeTipo);
            atendimento.setTipo(tipo);
            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
            atendimento.setCliente(cliente);
            Produto produto = new Produto();
            produto.setId(idProduto);
            produto.setNome(nomeProduto);
            atendimento.setProduto(produto);
            atendimento.setDataHora(dataHora);
            atendimento.setSituacao(situacao);
            atendimento.setDescricao(descricao);
            atendimento.setSolucao(solucao);
            return atendimento;
        }
        return null;
    }
    
    public void removeAtendimentoById(int id) throws SQLException {
        String sql = "DELETE FROM tb_atendimento where id_atendimento=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public Atendimento updateSolucaoById(Atendimento atendimento) throws SQLException {
        String sql = "UPDATE tb_atendimento SET solucao_atendimento=(?), situacao_atendimento = true where id_atendimento=(?);";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, atendimento.getSolucao());
        stmt.setInt(2, atendimento.getId());
        stmt.executeUpdate();
        return atendimento;
    }

    public Atendimento insertAtendimento(Atendimento atendimento) throws SQLException {
        String sql = "insert into tb_atendimento(id_tipo_atendimento, id_cliente, id_produto, data_hora_atendimento, situacao_atendimento, descricao_atendimento, solucao_atendimento) values((?), (?), (?), (?), (?), (?), (?));";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, atendimento.getTipo().getId());
        stmt.setInt(2, atendimento.getCliente().getId());
        stmt.setInt(3, atendimento.getProduto().getId());
        stmt.setTimestamp(4, new java.sql.Timestamp(atendimento.getDataHora().getTime()));
        stmt.setBoolean(5, atendimento.isSituacao());
        stmt.setString(6, atendimento.getDescricao());
        stmt.setString(7, atendimento.getSolucao());

        stmt.executeUpdate();
        return atendimento;
    }
}
