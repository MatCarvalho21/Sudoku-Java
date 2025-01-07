package br.ufjf.dcc.dcc025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elemento {
    private int pos_x, pos_y, valor;
    public List<Integer> possibleValues;
    private boolean mutavel;

    // OK
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

    // OK
    public void changeMutavel()
    {
        this.mutavel = true;
    }

    // OK
    public void deixandoImutavel()
    {
        this.mutavel = false;
    }

    // OK
    public int getValor()
    {
        return valor;
    }

    // OK
    public void setValor(int valor)
    {
        if (mutavel) {
            this.valor = valor;
        } else {
            System.out.printf("Não é possível alterar um elemento original.");
        }
    }

    // OK
    public List<Integer> getPossibleValues()
    {
        return new ArrayList<>(possibleValues);
    }

    // OK
    public void addPossibleValue(int valor)
    {
        if (!possibleValues.contains(valor)) {
            this.possibleValues.add(valor);
            Collections.sort(this.possibleValues);
        }
    }

    // OK
    public void removePossibleValue(int valor)
    {
        this.possibleValues.remove(Integer.valueOf(valor));
    }

    // OK
    @Override
    public String toString()
    {
        return "Elemento{" +
                "pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", valor=" + valor +
                ", possibleValues=" + possibleValues +
                ", mutavel=" + mutavel +
                '}';
    }
}
