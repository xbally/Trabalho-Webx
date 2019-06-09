/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.facade;

import com.ufpr.tads.web2.beans.TipoAtendimento;
import com.ufpr.tads.web2.dao.TipoAtendimentoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class TipoAtendimentoFacade {
    public static List<TipoAtendimento> buscarTodasCategoriasProdutos() throws SQLException, ClassNotFoundException {
        TipoAtendimentoDAO dao = new TipoAtendimentoDAO();
        return dao.getTiposAtendimentos();
    }
}
