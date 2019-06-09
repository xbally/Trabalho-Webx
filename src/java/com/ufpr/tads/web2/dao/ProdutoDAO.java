/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.CategoriaProduto;
import com.ufpr.tads.web2.beans.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ProdutoDAO {
    private final Connection conn;

    public ProdutoDAO() throws SQLException, ClassNotFoundException {
        this.conn = (Connection) new ConnectionFactory().getConnection();
    }

    public List<Produto> getProdutos() throws SQLException {
        ResultSet rs;
        List<Produto> produtos = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String query = "select * from tb_produto p "
                + "inner join tb_categoria_produto cp on cp.id_categoria_produto = p.id_categoria_produto;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id_produto");
            int idCategoria = rs.getInt("id_categoria_produto");
            String nome = rs.getString("nome_produto");
            String nomeCategoria = rs.getString("nome_categoria_produto");
            String descricao = rs.getString("descricao_produto");
            double peso = rs.getDouble("peso_produto");
            CategoriaProduto catproduto = new CategoriaProduto();
            catproduto.setId(idCategoria);
            catproduto.setNome(nomeCategoria);
            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPeso(peso);
            produto.setCategoria(catproduto);
            produtos.add(produto);
        }
        return produtos;
    }
    
    public Produto getProdutoById(int id) throws SQLException {
        ResultSet rs;
        String query = "select * from tb_produto p "
                + "inner join tb_categoria_produto cp on cp.id_categoria_produto = p.id_categoria_produto "
                + "where id_produto=(?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
            int idCategoria = rs.getInt("id_categoria_produto");
            String nome = rs.getString("nome_produto");
            String nomeCategoria = rs.getString("nome_categoria_produto");
            String descricao = rs.getString("descricao_produto");
            double peso = rs.getDouble("peso_produto");
            CategoriaProduto catproduto = new CategoriaProduto();
            catproduto.setId(idCategoria);
            catproduto.setNome(nomeCategoria);
            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPeso(peso);
            produto.setCategoria(catproduto);
            return produto;
        }
        return null;
    }
    
    public void removeProdutoById(int id) throws SQLException {
        String sql = "DELETE FROM tb_produto where id_produto=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public Produto updateProdutoById(Produto produto) throws SQLException {
        String sql = "UPDATE tb_produto SET nome_produto=(?), descricao_produto=(?), peso_produto=(?), id_categoria_produto=(?) where id_produto=(?);";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPeso());
        stmt.setInt(4, produto.getCategoria().getId());
        stmt.setInt(5, produto.getId());
        stmt.executeUpdate();
        return produto;
    }

    public Produto insertProduto(Produto produto) throws SQLException {
        String sql = "insert into tb_produto(nome_produto, descricao_produto, peso_produto, id_categoria_produto) values((?), (?), (?), (?));";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPeso());
        stmt.setInt(4, produto.getCategoria().getId());

        stmt.executeUpdate();
        return produto;
    }
}
