package br.com.remedios.model;

import br.com.remedios.enums.Tarja;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by joaov on 09/04/2017.
 */

public class Remedio implements Serializable{
    private Integer id;
    private String nome;
    private Tarja tarja;
    private Date dataCadastro;
    private Date horaIncial;
    private String observacao;
    private int frequencia;
    private boolean isNotificar;
    private boolean isNotificarCompanheiro;
    private Usuario usuario;

    public Remedio() {
    }

    public Remedio(Integer id, String nome, Tarja tarja, Date dataCadastro, Date horaIncial, String observacao, int frequencia, boolean isNotificar, boolean isNotificarCompanheiro, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.tarja = tarja;
        this.dataCadastro = dataCadastro;
        this.horaIncial = horaIncial;
        this.observacao = observacao;
        this.frequencia = frequencia;
        this.isNotificar = isNotificar;
        this.isNotificarCompanheiro = isNotificarCompanheiro;
        this.usuario = usuario;
    }

    
    public boolean isIsNotificar() {
        return isNotificar;
    }

    public void setIsNotificar(boolean isNotificar) {
        this.isNotificar = isNotificar;
    }

    public boolean isIsNotificarCompanheiro() {
        return isNotificarCompanheiro;
    }

    public void setIsNotificarCompanheiro(boolean isNotificarCompanheiro) {
        this.isNotificarCompanheiro = isNotificarCompanheiro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tarja getTarja() {
        return tarja;
    }

    public void setTarja(Tarja tarja) {
        this.tarja = tarja;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getHoraIncial() {
        return horaIncial;
    }

    public void setHoraIncial(Date horaIncial) {
        this.horaIncial = horaIncial;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public boolean isNotificar() {
        return isNotificar;
    }

    public void setNotificar(boolean notificar) {
        isNotificar = notificar;
    }

    public boolean isNotificarCompanheiro() {
        return isNotificarCompanheiro;
    }

    public void setNotificarCompanheiro(boolean notificarCompanheiro) {
        isNotificarCompanheiro = notificarCompanheiro;
    }
}
