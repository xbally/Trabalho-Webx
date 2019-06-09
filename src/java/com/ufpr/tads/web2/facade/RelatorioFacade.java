/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.dao.RelatorioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Gabriel
 */
public class RelatorioFacade {
    public static int atendimentosFinalizados() throws SQLException, ClassNotFoundException {
        RelatorioDAO dao = new RelatorioDAO();
        return dao.getAtendimentosTotalFinalizados();
    }
    
    public static int atendimentosAbertos() throws SQLException, ClassNotFoundException {
        RelatorioDAO dao = new RelatorioDAO();
        return dao.getAtendimentosTotalAbertos();
    }
    
    public static int atendimentos() throws SQLException, ClassNotFoundException {
        RelatorioDAO dao = new RelatorioDAO();
        return dao.getAtendimentosTotal();
    }
    
    public static ArrayList<HashMap<String, String>> atendimentosTipos() throws SQLException, ClassNotFoundException {
        RelatorioDAO dao = new RelatorioDAO();
        return dao.getAtendimentosTipos();
    }
}
