/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Gerente;
import com.ufpr.tads.web2.dao.GerenteDAO;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class GerenteFacade {
    public static List<Gerente> buscarTodosGerentes() throws SQLException, ClassNotFoundException {
        GerenteDAO gerentedao = new GerenteDAO();
        return gerentedao.selectGerentes();
    }

    public static Gerente buscar(int id) throws SQLException, ClassNotFoundException {
        GerenteDAO gerentedao = new GerenteDAO();
        return gerentedao.selectGerenteById(id);
    }
    
    public static String buscarNomePorId(int id) throws SQLException, ClassNotFoundException {
        GerenteDAO gerentedao = new GerenteDAO();
        return gerentedao.selectNameById(id);
    }
       
    public static void inserir(Gerente gerente) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        GerenteDAO gerentedao = new GerenteDAO();
        gerentedao.insertGerente(gerente);
    }
    
    public static void alterar(Gerente gerente) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        GerenteDAO gerentedao = new GerenteDAO();
        gerentedao.updateGerente(gerente);
    }
    
    public static void alterarSemSenha(Gerente gerente) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        GerenteDAO gerentedao = new GerenteDAO();
        gerentedao.updateGerenteWithoutSenha(gerente);
    }
    
    public static void remover(int id) throws ClassNotFoundException, SQLException {
        GerenteDAO gerentedao = new GerenteDAO();
        gerentedao.removeGerenteById(id);
    }
    
    public static int buscarIdPorDadosGerente(Gerente gerente) throws SQLException, ClassNotFoundException {
        GerenteDAO gerentedao = new GerenteDAO();
        return gerentedao.selectIdByData(gerente);
    }
}
