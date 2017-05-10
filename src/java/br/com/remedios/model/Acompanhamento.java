
package br.com.remedios.model;

import java.io.Serializable;


public class Acompanhamento implements Serializable{
    private Integer id_acompanhamento ;
    private Usuario usuario;
    private Integer id_acompanhante;

    public Acompanhamento() {
    }

    public Acompanhamento(Usuario usuario, Integer id_acompanhante) {
        this.usuario = usuario;
        this.id_acompanhante = id_acompanhante;
    }

    public Integer getId_acompanhamento() {
        return id_acompanhamento;
    }

    public void setId_acompanhamento(Integer id_acompanhamento) {
        this.id_acompanhamento = id_acompanhamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getId_acompanhante() {
        return id_acompanhante;
    }

    public void setId_acompanhante(Integer id_acompanhante) {
        this.id_acompanhante = id_acompanhante;
    }
    
    
}
