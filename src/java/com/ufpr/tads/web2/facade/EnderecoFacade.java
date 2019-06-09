/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Endereco;
import com.ufpr.tads.web2.dao.EnderecoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author gabriel
 */
public class EnderecoFacade {
    
    public static Endereco buscar(int id) throws SQLException, ClassNotFoundException {
        EnderecoDAO endereçodao = new EnderecoDAO();
        return endereçodao.selectEndereçoById(id);
    }
    
    public static Endereco buscarPorReferencia(int id, String referencia) throws SQLException, ClassNotFoundException {
        EnderecoDAO endereçodao = new EnderecoDAO();
        return endereçodao.selectEndereçoByIdReferenciaAndReferencia(id, referencia);
    }
    
    public static void remover(int id) throws SQLException, ClassNotFoundException {
        EnderecoDAO endereçodao = new EnderecoDAO();
        endereçodao.deleteEndereçoById(id);
    }
    
    public static void alterar(Endereco endereço) throws SQLException, ClassNotFoundException {
        EnderecoDAO endereçodao = new EnderecoDAO();
        endereçodao.updateEndereçoById(endereço);
    }
    
    public static void alterarPorIdReferencia(Endereco endereço) throws SQLException, ClassNotFoundException {
        EnderecoDAO endereçodao = new EnderecoDAO();
        endereçodao.updateEndereçoByIdRefencia(endereço);
    }
    
    public static void inserir(Endereco endereço) throws ClassNotFoundException, SQLException {
        EnderecoDAO endereçodao = new EnderecoDAO();
        endereçodao.insertEndereço(endereço);
    }
}
