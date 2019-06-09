/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Cliente;
import com.ufpr.tads.web2.dao.ClienteDAO;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ClienteFacade {
    
    
    public static List<Cliente> buscarTodosClientes() throws SQLException, ClassNotFoundException {
        ClienteDAO cliente = new ClienteDAO();
        return cliente.selectClientes();
    }

    public static Cliente buscar(int id) throws SQLException, ClassNotFoundException {
        ClienteDAO cad = new ClienteDAO();
        return cad.selectClienteById(id);
    }
    
    public static Cliente buscarUsuario(int id) throws SQLException, ClassNotFoundException {
        ClienteDAO cad = new ClienteDAO();
        return cad.selectClienteByIdUsuario(id);
    }
    
    public static String buscarNomePorId(int id) throws SQLException, ClassNotFoundException {
        ClienteDAO cad = new ClienteDAO();
        return cad.selectNameById(id);
    }
       
    public static void inserir(Cliente cliente) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        ClienteDAO dao = new ClienteDAO();
        dao.insertCliente(cliente);
    }
    
    public static void alterar(Cliente cliente) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        ClienteDAO dao = new ClienteDAO();
        dao.updateCliente(cliente);
    }
    
    public static void alterarSemSenha(Cliente cliente) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        ClienteDAO dao = new ClienteDAO();
        dao.updateClienteWithoutSenha(cliente);
    }
    
    public static int buscarIdPorDadosCliente(Cliente cliente) throws SQLException, ClassNotFoundException {
        ClienteDAO comp = new ClienteDAO();
        return comp.selectIdByData(cliente);
    }

}
