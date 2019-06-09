/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.Atendimento;
import com.ufpr.tads.web2.dao.AtendimentoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class AtendimentoFacade {
    public static List<Atendimento> buscarTodosAtendimentos() throws SQLException, ClassNotFoundException {
        AtendimentoDAO dao = new AtendimentoDAO();
        return dao.getAtendimentos();
    }
    
    public static List<Atendimento> buscarTodosAtendimentosAbertos() throws SQLException, ClassNotFoundException {
        AtendimentoDAO dao = new AtendimentoDAO();
        return dao.getAtendimentosAberto();
    }
    
    public static List<Atendimento> buscarTodosAtendimentosCliente(int id) throws SQLException, ClassNotFoundException {
        AtendimentoDAO dao = new AtendimentoDAO();
        return dao.getAtendimentosByIdCliente(id);
    }
    
    public static Atendimento buscar(int id) throws SQLException, ClassNotFoundException {
        AtendimentoDAO dao = new AtendimentoDAO();
        return dao.getAtendimentoById(id);
    }
    
    public static void remover(int id) throws SQLException, ClassNotFoundException {
        AtendimentoDAO dao = new AtendimentoDAO();
        dao.removeAtendimentoById(id);
    }
    
    public static void alterarSolucao(Atendimento atendimento) throws SQLException, ClassNotFoundException {
        AtendimentoDAO clientedao = new AtendimentoDAO();
        clientedao.updateSolucaoById(atendimento);
    }
    
    public static void inserir(Atendimento atendimento) throws ClassNotFoundException, SQLException {
        AtendimentoDAO dao = new AtendimentoDAO();
        dao.insertAtendimento(atendimento);
    }
}
