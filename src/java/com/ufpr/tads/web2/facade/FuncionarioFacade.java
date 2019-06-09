/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Funcionario;
import com.ufpr.tads.web2.dao.FuncionarioDAO;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class FuncionarioFacade {
    public static List<Funcionario> buscarTodosFuncionarios() throws SQLException, ClassNotFoundException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        return funcionariodao.selectFuncionarios();
    }

    public static Funcionario buscar(int id) throws SQLException, ClassNotFoundException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        return funcionariodao.selectFuncionarioById(id);
    }
    
    public static String buscarNomePorId(int id) throws SQLException, ClassNotFoundException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        return funcionariodao.selectNameById(id);
    }
       
    public static void inserir(Funcionario funcionario) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        funcionariodao.insertFuncionario(funcionario);
    }
    
    public static void alterar(Funcionario funcionario) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        funcionariodao.updateFuncionario(funcionario);
    }
    
    public static void alterarSemSenha(Funcionario funcionario) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        funcionariodao.updateFuncionarioWithoutSenha(funcionario);
    }
    
    public static void remover(int id) throws ClassNotFoundException, SQLException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        funcionariodao.removeFuncionarioById(id);
    }
    
    public static int buscarIdPorDadosFuncionario(Funcionario funcionario) throws SQLException, ClassNotFoundException {
        FuncionarioDAO funcionariodao = new FuncionarioDAO();
        return funcionariodao.selectIdByData(funcionario);
    }
}
