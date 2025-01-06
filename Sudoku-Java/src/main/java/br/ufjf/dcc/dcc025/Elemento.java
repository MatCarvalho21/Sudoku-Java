package br.ufjf.dcc.dcc025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elemento {
    private int pos_x, pos_y, valor;
    public List<Integer> possibleValues;
    private boolean mutavel;

    public Elemento(int pos_x, int pos_y, int valor) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.valor = valor;
        this.mutavel = true;
        this.possibleValues = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            this.possibleValues.add(i);
        }
    }

    public void changeMutavel()
    {
        this.mutavel = true;
    }

    public void zeraElemento()
    {
        this.valor = 0;
    }

    public void deixandoImutavel() {
        this.mutavel = false;
    }

    public int getPosX() {
        return pos_x;
    }

    public void setPosX(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPosY() {
        return pos_y;
    }

    public void setPosY(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        if (mutavel) {
            this.valor = valor;
        } else {
            System.out.printf("Não é possível alterar um elemento original.");
        }
    }

    public List<Integer> getPossibleValues() {
        return new ArrayList<>(possibleValues);
    }

    public void addPossibleValue(int valor) {
        if (!possibleValues.contains(valor)) {
            this.possibleValues.add(valor);
            Collections.sort(this.possibleValues);
        }
    }

    public void removePossibleValue(int valor) {
        this.possibleValues.remove(Integer.valueOf(valor));
    }

    @Override
    public String toString() {
        return "Elemento{" +
                "pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", valor=" + valor +
                ", possibleValues=" + possibleValues +
                ", mutavel=" + mutavel +
                '}';
    }
}
