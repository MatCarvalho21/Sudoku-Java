package br.ufjf.dcc.dcc025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elemento {
    private int pos_x, pos_y, valor;
    public List<Integer> valoresPossiveis;
    private boolean mutavel;

    // OK
    public Elemento(int pos_x, int pos_y, int valor) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.valor = valor;
        this.mutavel = true;
        this.valoresPossiveis = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            this.valoresPossiveis.add(i);
        }
    }

    // OK
    public void deixaMutavel()
    {
        this.mutavel = true;
    }

    // OK
    public void deixaImutavel()
    {
        this.mutavel = false;
    }

    // OK
    public int pegaValor()
    {
        return valor;
    }

    // OK
    public void defineValor(int valor)
    {
        if (mutavel) {
            this.valor = valor;
        } else {
            System.out.printf("Não é possível alterar um elemento original.");
        }
    }

    // OK
    public List<Integer> pegaValoresPossiveis()
    {
        return new ArrayList<>(valoresPossiveis);
    }

    // OK
    public void adicionaValorPossivel(int valor)
    {
        if (!valoresPossiveis.contains(valor)) {
            this.valoresPossiveis.add(valor);
            Collections.sort(this.valoresPossiveis);
        }
    }

    // OK
    public void removeValorPossivel(int valor)
    {
        this.valoresPossiveis.remove(Integer.valueOf(valor));
    }

    // OK
    @Override
    public String toString()
    {
        return "Elemento{" +
                "pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", valor=" + valor +
                ", valoresPossiveis=" + valoresPossiveis +
                ", mutavel=" + mutavel +
                '}';
    }
}
