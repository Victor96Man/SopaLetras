package com.example.sopadeletras;

public class Palabras {
    private String palabra;
    private int filaInicial;
    private int columnaInicial;
    private int filaFinal;
    private int columnaFinal;
    private boolean acertada = false;

    public Palabras(String palabra) {
        this.palabra = palabra;
    }

    public int getLength(){
        return palabra.length();
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getFilaInicial() {
        return filaInicial;
    }

    public void setFilaInicial(int filaInicial) {
        this.filaInicial = filaInicial;
    }

    public int getColumnaInicial() {
        return columnaInicial;
    }

    public void setColumnaInicial(int columnaInicial) {
        this.columnaInicial = columnaInicial;
    }

    public int getFilaFinal() {
        return filaFinal;
    }

    public void setFilaFinal(int filaFinal) {
        this.filaFinal = filaFinal;
    }

    public int getColumnaFinal() {
        return columnaFinal;
    }

    public void setColumnaFinal(int columnaFinal) {
        this.columnaFinal = columnaFinal;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public void setAcertada(boolean acertada) {
        this.acertada = acertada;
    }
}