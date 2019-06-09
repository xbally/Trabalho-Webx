/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gabriel
 */
public class Atendimento implements Serializable {
    private int id;
    private Date dataHora;
    private boolean situacao;
    private String descricao;
    private String solucao;
    private TipoAtendimento tipo;
    private Cliente cliente;
    private Produto produto;

    public Atendimento() {
    }

    public Atendimento(int id, Date dataHora, boolean situacao, String descricao, String solucao, TipoAtendimento tipo, Cliente cliente, Produto produto) {
        this.id = id;
        this.dataHora = dataHora;
        this.situacao = situacao;
        this.descricao = descricao;
        this.solucao = solucao;
        this.tipo = tipo;
        this.cliente = cliente;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public TipoAtendimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtendimento tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
