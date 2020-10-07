package com.example.parcial1eco;

public class Jugador {

    int x,y,r,g,b;
    String nombre;

    public Jugador(int x, int y, String nombre, int r, int g, int b) {

        this.x=x;
        this.y=y;
        this.r=r;
        this.g=g;
        this.b=b;
        this.nombre=nombre;

    }


    public Jugador() {

    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
