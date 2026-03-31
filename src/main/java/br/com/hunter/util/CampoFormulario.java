package br.com.hunter.util;

public class CampoFormulario {

    private final String nome;
    private final String label;
    private final String tipo;
    private final String valor;

    public CampoFormulario(String nome, String label, String tipo, String valor) {
        this.nome = nome;
        this.label = label;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getLabel() {
        return label;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }
}
