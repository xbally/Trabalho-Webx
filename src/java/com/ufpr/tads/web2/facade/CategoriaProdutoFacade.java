/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.CategoriaProduto;
import com.ufpr.tads.web2.dao.CategoriaProdutoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class CategoriaProdutoFacade {
    public static List<CategoriaProduto> buscarTodasCategoriasProdutos() throws SQLException, ClassNotFoundException {
        CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
        return dao.getCategoriasProdutos();
    }
    
    public static CategoriaProduto buscar(int id) throws SQLException, ClassNotFoundException {
        CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
        return dao.getCategoriaProdutoById(id);
    }
    
    public static void remover(int id) throws SQLException, ClassNotFoundException {
        CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
        dao.removeCategoriaProdutoById(id);
    }
    
    public static void alterar(CategoriaProduto catproduto) throws SQLException, ClassNotFoundException {
        CategoriaProdutoDAO clientedao = new CategoriaProdutoDAO();
        clientedao.updateCategoriaProdutoById(catproduto);
    }
    
    public static void inserir(CategoriaProduto catproduto) throws ClassNotFoundException, SQLException {
        CategoriaProdutoDAO dao = new CategoriaProdutoDAO();
        dao.insertCategoriaProduto(catproduto);
    }
}
