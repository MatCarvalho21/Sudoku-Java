package br.ufjf.dcc.dcc025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que representa um elemento de um tabuleiro de Sudoku.
 * Um elemento contém sua posição no tabuleiro, o valor atual,
 * uma lista de valores possíveis e um indicador de mutabilidade.
 */
public class Elemento {
    private int pos_x, pos_y, valor; // Coordenadas (x, y) e valor atual do elemento
    public List<Integer> valoresPossiveis; // Lista de valores possíveis para este elemento
    public boolean mutavel; // Indica se o elemento pode ser alterado

    /**
     * Construtor da classe.
     * Inicializa o elemento com suas coordenadas, valor inicial e valores possíveis.
     * Por padrão, o elemento é mutável e sua lista de valores possíveis contém os números de 1 a 9.
     *
     * @param pos_x Posição X do elemento no tabuleiro.
     * @param pos_y Posição Y do elemento no tabuleiro.
     * @param valor Valor inicial do elemento.
     */
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

    /**
     * Atualiza a lista de valores possíveis para o elemento.
     * Apenas atualiza se o elemento for mutável.
     *
     * @param novosValores Nova lista de valores possíveis.
     */
    public void atualizaValoresPossiveis(List<Integer> novosValores) {
        if (mutavel) { // Apenas atualiza se o elemento for mutável
            this.valoresPossiveis.clear(); // Remove valores antigos
            this.valoresPossiveis.addAll(novosValores); // Adiciona os novos valores
        }
    }

    /**
     * Torna o elemento mutável, permitindo alterações no valor e na lista de valores possíveis.
     */
    public void deixaMutavel() {
        this.mutavel = true;
    }

    /**
     * Torna o elemento imutável, impedindo alterações no valor e na lista de valores possíveis.
     */
    public void deixaImutavel() {
        this.mutavel = false;
    }

    /**
     * Retorna o valor atual do elemento.
     *
     * @return O valor do elemento.
     */
    public int pegaValor() {
        return valor;
    }

    /**
     * Define um novo valor para o elemento, caso ele seja mutável.
     * Se o elemento for imutável, exibe uma mensagem de erro.
     *
     * @param valor Novo valor a ser atribuído ao elemento.
     */
    public void defineValor(int valor) {
        if (mutavel) {
            this.valor = valor;
        } else {
            System.out.println("Não é possível alterar um elemento original.");
        }
    }

    /**
     * Retorna uma cópia da lista de valores possíveis do elemento.
     *
     * @return Lista de valores possíveis.
     */
    public List<Integer> pegaValoresPossiveis() {
        return new ArrayList<>(valoresPossiveis);
    }

    /**
     * Adiciona um valor à lista de valores possíveis, se ele ainda não estiver presente.
     * A lista é mantida ordenada após a adição.
     *
     * @param valor Valor a ser adicionado.
     */
    public void adicionaValorPossivel(int valor) {
        if (!valoresPossiveis.contains(valor)) {
            this.valoresPossiveis.add(valor);
            Collections.sort(this.valoresPossiveis);
        }
    }

    /**
     * Remove um valor da lista de valores possíveis.
     *
     * @param valor Valor a ser removido.
     */
    public void removeValorPossivel(int valor) {
        this.valoresPossiveis.remove(Integer.valueOf(valor));
    }

    /**
     * Retorna uma representação textual do elemento, incluindo suas coordenadas,
     * valor atual, lista de valores possíveis e estado de mutabilidade.
     *
     * @return String representando o elemento.
     */
    @Override
    public String toString() {
        return "Elemento{" +
                "pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", valor=" + valor +
                ", valoresPossiveis=" + valoresPossiveis +
                ", mutavel=" + mutavel +
                '}';
    }
}
