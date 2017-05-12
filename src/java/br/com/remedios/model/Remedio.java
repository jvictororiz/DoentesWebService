package br.com.remedios.model;

import br.com.remedios.enums.Tarja;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by joaov on 09/04/2017.
 */

public class Remedio implements Serializable{
    private int id;
    private String nome;
    private Tarja tarja;
    private Date dataCadastro;
    private Date horaIncial;
    private String observacao;
    private int frequencia;
    private boolean isNotificar;
    private boolean isNotificarCompanheiro;
    public Remedio() {
    }

}
