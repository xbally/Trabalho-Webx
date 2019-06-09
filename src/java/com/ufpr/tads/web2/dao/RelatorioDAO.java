/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Gabriel
 */
public class RelatorioDAO {
    private final Connection conn;
    
    public RelatorioDAO() throws SQLException, ClassNotFoundException {
        this.conn = (Connection) new ConnectionFactory().getConnection();
    }

    public int getAtendimentosTotalFinalizados() throws SQLException, ClassNotFoundException {
        ResultSet rs;
        int total = 0;
        String sql = "SELECT COUNT(*) as total FROM tb_atendimento a WHERE a.situacao_atendimento = true;";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            //++total;
            total = rs.getInt("total");
        }
        this.conn.close();
        return total;
    }
    
    public int getAtendimentosTotalAbertos() throws SQLException, ClassNotFoundException {
        ResultSet rs;
        int total = 0;
        String sql = "SELECT COUNT(*) as total FROM tb_atendimento a WHERE a.situacao_atendimento = false;";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            //++total;
            total = rs.getInt("total");
        }
        this.conn.close();
        return total;
    }
    
    public int getAtendimentosTotal() throws SQLException, ClassNotFoundException {
        ResultSet rs;
        int total = 0;
        String sql = "SELECT COUNT(*) as total FROM tb_atendimento a;";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            //++total;
            total = rs.getInt("total");
        }
        this.conn.close();
        return total;
    }

    public ArrayList<HashMap<String, String>> getAtendimentosTipos() throws SQLException {
        ResultSet rs;
        ArrayList<HashMap<String, String>> lista = new ArrayList<>();;
        String sql = "SELECT ta.nome_tipo_atendimento, COUNT(*) as total FROM tb_atendimento a " +
        "INNER JOIN tb_tipo_atendimento ta ON ta.id_tipo_atendimento = a.id_tipo_atendimento " +
        "GROUP BY ta.nome_tipo_atendimento;";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            HashMap<String, String> hm = new HashMap<>();
            String nomeEvento = rs.getString("nome_tipo_atendimento");
            int total = rs.getInt("total");
            hm.put("total", Integer.toString(total));
            hm.put("nome", nomeEvento);
            lista.add(hm);
        }
        this.conn.close();
        return lista;
    }
}
