/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.CategoriaProduto;
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
public class CategoriaProdutoDAO {
    private final Connection conn;

    public CategoriaProdutoDAO() throws SQLException, ClassNotFoundException {
        this.conn = (Connection) new ConnectionFactory().getConnection();
    }

    public List<CategoriaProduto> getCategoriasProdutos() throws SQLException {
        ResultSet rs;
        List<CategoriaProduto> catprodutos = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String query = "select * from tb_categoria_produto;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id_categoria_produto");
            String nome = rs.getString("nome_categoria_produto");
            CategoriaProduto catproduto = new CategoriaProduto();
            catproduto.setId(id);
            catproduto.setNome(nome);
            catprodutos.add(catproduto);
        }
        return catprodutos;
    }
    
    public CategoriaProduto getCategoriaProdutoById(int id) throws SQLException {
        ResultSet rs;
        String query = "select * from tb_categoria_produto where id_categoria_produto=(?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt("id_categoria_produto");
            String nome = rs.getString("nome_categoria_produto");
            CategoriaProduto catproduto = new CategoriaProduto();
            catproduto.setId(id);
            catproduto.setNome(nome);
            return catproduto;
        }
        return null;
    }
    
    public void removeCategoriaProdutoById(int id) throws SQLException {
        String sql = "DELETE FROM tb_categoria_produto where id_categoria_produto=(?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public CategoriaProduto updateCategoriaProdutoById(CategoriaProduto catproduto) throws SQLException {
        String sql = "UPDATE tb_categoria_produto SET nome_categoria_produto=(?) where id_categoria_produto=(?);";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, catproduto.getNome());
        stmt.setInt(2, catproduto.getId());
        stmt.executeUpdate();
        return catproduto;
    }

    public CategoriaProduto insertCategoriaProduto(CategoriaProduto catproduto) throws SQLException {
        String sql = "insert into tb_categoria_produto(nome_categoria_produto) values((?));";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, catproduto.getNome());

        stmt.executeUpdate();
        return catproduto;
    }
}
