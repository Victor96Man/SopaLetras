package com.example.sopadeletras;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private char[][] tablero = new char[10][10];
    private final String aLetras="abcdefghijklmnopqrstuvwxyz";
    private Palabras[] palabras;
    Context contexto;
    int contador=0;



    /**Constructor llamando a los métodos anteriores para dejar el tablero terminado.
     *
     * @param palabras2
     */
    public Game(Palabras[] palabras2, Context context2) {
        palabras = palabras2;
        contexto = context2;
        colocarPalabras();
        colocarLetrasAleatoreas();

    }


    /** Método para dispersar las letras en el tablero que ya contiene las palabras. Necesitarás generar números aleatorios.
     * Utiliza charAt(n) para devolver el carácter de una cadena correspondiente al índice utilizado.
     */
    private void colocarLetrasAleatoreas() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(tablero[i][j] == 0) {
                    Random random = new Random();
                    tablero[i][j] = aLetras.charAt(random.nextInt(26));
                }
            }
        }
    }

    /**
     * Método para colocar las palabras de forma aleatorea en el tablero
     * (vertical, horizontal, diagonal, de izquierda a derecha y viceversa)
     */
    private void colocarPalabras() {

        for(int numPalabra = 0; numPalabra<palabras.length; numPalabra++) {
            Random random1 = new Random();
            int orientacion = random1.nextInt(3); //numero random para saber que orientacion va a tener la palabra
            int sentido = random1.nextInt(2);     //numero random para saber que sentido va a tener la palabra
            contador=0;
            try {
                switch (orientacion) {

                    case 0:
                        palabraHorizontal(numPalabra, sentido); //horizontal
                        break;
                    case 1:
                        palabraVertical(numPalabra, sentido); //vertical
                        break;
                    case 2:
                        palabraDiagonal(numPalabra, sentido); //diagonal
                        break;
                }
            }catch (Exception e){
                Toast.makeText(contexto,"dc",Toast.LENGTH_SHORT).show();

            }
        }

    }

    /**
     * Método que pinta la palabra de forma aleatorea Horizontalmente
     *
     * @param numP
     * @param sentido
     */
    public void palabraHorizontal(int numP, int sentido){
        Random random2 = new Random();
        int ejeYInicio = 10;
        int ejeX=0;
        int ejeYFinal = 0;
        String palabra = palabras[numP].getPalabra();
        int numLetP= palabras[numP].getLength();
        ejeX = random2.nextInt(10);

        do {
            ejeYInicio = random2.nextInt(10-numLetP);
        }while (ejeYInicio+numLetP>tablero.length);

        ejeYFinal = ejeYInicio + numLetP -1;

        if (sentido == 0) { //sentido inverso
            palabra = Invertir(palabras[numP].getPalabra());    //Cambiamos el orden de las letras en la palabra
            palabras[numP].setColumnaInicial(ejeYFinal);        //Como ahora el orden es inverso hay que invertir las columnas
            palabras[numP].setColumnaFinal(ejeYInicio);
        }else {
            palabras[numP].setColumnaInicial(ejeYInicio);
            palabras[numP].setColumnaFinal(ejeYFinal);
        }
        //El ejeX siempre sera el mismo porque estamos en modo horizontal
        palabras[numP].setFilaInicial(ejeX);
        palabras[numP].setFilaFinal(ejeX);
        int j =0;
        ArrayList<int[]> coincidencias = new ArrayList<>();
        for (int i = ejeYInicio; i <=ejeYFinal; i++){
            if (tablero[ejeX][i]==0) {
                tablero[ejeX][i] = palabra.charAt(j);
                coincidencias.add(new int[] {ejeX,i});
                j++;
            }else {
                if (tablero[ejeX][i] != palabra.charAt(j)) {
                    if (j == 0) { //la primera letra
                        if (contador == 20) {
                            Toast.makeText(contexto,"No se ha podido configurar el tablero bien, intentalo de nuevo",Toast.LENGTH_LONG).show();
                            ((Sopa) contexto).finish();
                        }else {
                            contador++;
                            palabraHorizontal(numP, sentido);
                            i = 10;
                        }
                    } else { //borrar las letras que se habían metido antes

                        for( int[] v : coincidencias){
                            tablero[v[0]][v[1]] = 0;
                        }

                        if (contador == 20) {
                            Toast.makeText(contexto,"No se ha podido configurar el tablero bien, intentalo de nuevo",Toast.LENGTH_LONG).show();
                            ((Sopa) contexto).finish();
                        }else {
                            contador++;
                            palabraHorizontal(numP, sentido);
                            i = 10;
                        }
                    }
                } else {
                    j++;

                }
            }
        }
    }


    /**
     * Metodo que pinta la palabra de forma aleatorea Verticalmente
     * @param numP
     * @param sentido
     */
    public void palabraVertical(int numP, int sentido){
        Random random2 = new Random();
        int ejeXInicio = 10;
        int ejeY=0;
        int ejeXFinal = 0;
        String palabra = palabras[numP].getPalabra();
        int numLetP= palabras[numP].getLength();

        ejeY = random2.nextInt(10);

        do {
            ejeXInicio = random2.nextInt(10-numLetP);
        }while (ejeXInicio+numLetP>tablero.length);

        ejeXFinal = ejeXInicio + numLetP -1;

        if (sentido == 0) { //sentido inverso
            palabra = Invertir(palabras[numP].getPalabra());    //Cambiamos el orden de las letras en la palabra
            palabras[numP].setFilaInicial(ejeXFinal);           //Como ahora el orden es inverso hay que invertir las filas
            palabras[numP].setFilaFinal(ejeXInicio);
        }else{
            palabras[numP].setFilaInicial(ejeXInicio);
            palabras[numP].setFilaFinal(ejeXFinal);
        }


        //El ejeY siempre sera el mismo porque estamos en modo vertical
        palabras[numP].setColumnaInicial(ejeY);
        palabras[numP].setColumnaFinal(ejeY);
        int j =0;
        ArrayList<int[]> coincidencias = new ArrayList<>();
        for (int i = ejeXInicio; i <= ejeXFinal; i++){
            if (tablero[i][ejeY]==0) {
                tablero[i][ejeY] = palabra.charAt(j);
                coincidencias.add(new int[] {i,ejeY});
                j++;
            }else{
                if (tablero[i][ejeY]!=palabra.charAt(j)){
                    if (j==0) { //la primera letra
                        if (contador == 20) {
                            Toast.makeText(contexto,"No se ha podido configurar el tablero bien, intentalo de nuevo",Toast.LENGTH_LONG).show();
                            ((Sopa) contexto).finish();
                        }else {
                            contador++;
                            palabraVertical(numP, sentido);
                            i = 10;
                        }
                    }else{ //borrar las letras que se habian metido antes

                        for( int[] v :coincidencias) {
                            tablero[v[0]][v[1]] = 0;
                        }
                        if (contador == 20) {
                            Toast.makeText(contexto,"No se ha podido configurar el tablero bien, intentalo de nuevo",Toast.LENGTH_LONG).show();
                            ((Sopa) contexto).finish();
                        }else {
                            contador++;
                            palabraVertical(numP, sentido);
                            i = 10;
                        }
                    }
                }else{
                    j++;
                }
            }
        }
    }


    /**
     * Metodo que pinta la palabra de forma aleatorea Diagonalmente
     * @param numP
     * @param sentido
     */
    public void palabraDiagonal(int numP,int sentido) {

        Random random2 = new Random();
        int ejeYInicio = 10;
        int ejeXInicio = 10;

        int ejeYFinal = 0;
        int ejeXFinal = 0;

        String palabra = palabras[numP].getPalabra();
        int numLetP = palabras[numP].getLength();

        do {
            ejeYInicio = random2.nextInt(10-numLetP);
            ejeXInicio = random2.nextInt(10-numLetP);
        } while (ejeYInicio + numLetP > tablero.length || ejeXInicio + numLetP > tablero.length);

        ejeXFinal = ejeXInicio + numLetP - 1;
        ejeYFinal = ejeYInicio + numLetP - 1;

        if (sentido == 0) { //sentido inverso
            palabra = Invertir(palabras[numP].getPalabra());    //Cambiamos el orden de las letras en la palabra
            palabras[numP].setFilaInicial(ejeXFinal);           //Como ahora el orden es inverso hay que invertir
            palabras[numP].setFilaFinal(ejeXInicio);            // las filas y las columnas
            palabras[numP].setColumnaInicial(ejeYFinal);
            palabras[numP].setColumnaFinal(ejeYInicio);
        } else {
            palabras[numP].setFilaInicial(ejeXInicio);
            palabras[numP].setFilaFinal(ejeXFinal);
            palabras[numP].setColumnaInicial(ejeYInicio);
            palabras[numP].setColumnaFinal(ejeYFinal);
        }
        int j=0;
        ArrayList<int[]> coincidencias = new ArrayList<>();
        int k=ejeYInicio;
        for (int i = ejeXInicio; i <= ejeXFinal; i++) {
            if (tablero[i][k] == 0) {
                tablero[i][k] = palabra.charAt(j);
                coincidencias.add(new int[] {i,k});
                j++;
                k++;
            } else {
                if (tablero[i][k]!=palabra.charAt(j)){
                    if (j==0) { //la primera letra
                        if (contador == 20) {
                            Toast.makeText(contexto,"No se ha podido configurar el tablero bien, intentalo de nuevo",Toast.LENGTH_LONG).show();
                            ((Sopa) contexto).finish();
                        }else {
                            contador++;
                            palabraDiagonal(numP, sentido);
                            i = 10;
                        }
                    }else{ //borrar las letras que se habian metido antes

                        for(int[] v : coincidencias){
                            tablero[v[0]][v[1]] = 0;
                        }

                        if (contador == 20) {
                            Toast.makeText(contexto,"No se ha podido configurar el tablero bien, intentalo de nuevo",Toast.LENGTH_LONG).show();
                            ((Sopa) contexto).finish();
                        }else {
                            contador++;
                            palabraDiagonal(numP, sentido);
                            i = 10;
                        }
                    }
                }else{
                    j++;
                    k++;
                }
            }
        }
    }

    /**
     * Metodo que pasandole una palabra cambia el orden de las letras.
     *
     * @param palabra
     * @return
     */
    public String Invertir (String palabra){
        String sCadenaInvertida= "";
        for (int x=palabra.length()-1;x>=0;x--) {
            sCadenaInvertida = sCadenaInvertida + palabra.charAt(x);
        }
        return sCadenaInvertida;
    }

    /**
     * Método que devolverá la letra del tablero que corresponde a la fila y columna introducida.
     * La utilizará la clase principal del ejercicio para mostrar la letra en el layout.
     */

    public char getLetra(int fila, int columna){

        return tablero[fila][columna];
    }

    public String compruebaAcierto(int filaInicio, int columInicio, int filaFinal, int columFinal){
        for (Palabras palabra : palabras) {
             if( palabra.isAcertada()==false && filaInicio == palabra.getFilaInicial() && columInicio == palabra.getColumnaInicial() && filaFinal == palabra.getFilaFinal() && columFinal == palabra.getColumnaFinal()){
                 palabra.setAcertada(true);
                 return palabra.getPalabra();
             }
        }
        return "no";
    }
}