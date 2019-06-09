/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.web2.beans;

import java.io.Serializable;

/**
 *
 * @author Gabriel
 */
public class Usuario implements Serializable {
    private int id;
    private String email;
    private String senha;
    private String tipo;
    private int idReferencia;
    private Endereco endereco;

    public Usuario() {
    }

    public Usuario(int id, String email, String senha, String tipo, Endereco endereco) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public int getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(int idReferencia) {
        this.idReferencia = idReferencia;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}