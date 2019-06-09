/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Produto;
import com.ufpr.tads.web2.dao.ProdutoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ProdutoFacade {
    public static List<Produto> buscarTodosProdutos() throws SQLException, ClassNotFoundException {
        ProdutoDAO dao = new ProdutoDAO();
        return dao.getProdutos();
    }
    
    public static Produto buscar(int id) throws SQLException, ClassNotFoundException {
        ProdutoDAO dao = new ProdutoDAO();
        return dao.getProdutoById(id);
    }
    
    public static void remover(int id) throws SQLException, ClassNotFoundException {
        ProdutoDAO dao = new ProdutoDAO();
        dao.removeProdutoById(id);
    }
    
    public static void alterar(Produto produto) throws SQLException, ClassNotFoundException {
        ProdutoDAO clientedao = new ProdutoDAO();
        clientedao.updateProdutoById(produto);
    }
    
    public static void inserir(Produto produto) throws ClassNotFoundException, SQLException {
        ProdutoDAO dao = new ProdutoDAO();
        dao.insertProduto(produto);
    }
}
