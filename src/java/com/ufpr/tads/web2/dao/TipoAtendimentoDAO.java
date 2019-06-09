/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.dao;

import com.ufpr.tads.web2.beans.TipoAtendimento;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class TipoAtendimentoDAO {
    private final Connection conn;
    
    public TipoAtendimentoDAO() throws SQLException, ClassNotFoundException {
        this.conn = (Connection) new ConnectionFactory().getConnection();
    }

    public List<TipoAtendimento> getTiposAtendimentos() throws SQLException {
        ResultSet rs;
        List<TipoAtendimento> tipos = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String query = "select * from tb_tipo_atendimento;";
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id_tipo_atendimento");
            String nome = rs.getString("nome_tipo_atendimento");
            TipoAtendimento tipo = new TipoAtendimento();
            tipo.setId(id);
            tipo.setNome(nome);
            tipos.add(tipo);
        }
        return tipos;
    }
}
