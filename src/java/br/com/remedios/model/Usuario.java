package br.com.remedios.model;

import java.util.Date;
import java.util.List;

/**
 * Created by joaov on 10/04/2017.
 */
public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String dataNascimento;
    private String senha;
    private List<Remedio> remedios;
    private List<Acompanhamento> acompanhantes;
    

    public Usuario() {

    }

    public List<Acompanhamento> getAcompanhantes() {
        return acompanhantes;
    }

    public void setAcompanhantes(List<Acompanhamento> acompanhantes) {
        this.acompanhantes = acompanhantes;
    }  
    

    public Usuario(long id, String nome, String email, String telefone, String dataNascimento, String senha, List<Remedio> remedios) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
        this.remedios = remedios;
    }

    public List<Remedio> getRemedios() {
        return remedios;
    }

    public void setRemedios(List<Remedio> remedios) {
        this.remedios = remedios;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {   
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
