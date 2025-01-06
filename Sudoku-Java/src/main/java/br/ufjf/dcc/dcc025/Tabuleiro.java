package br.ufjf.dcc.dcc025;

import java.util.ArrayList;

public class Tabuleiro {
    private Elemento[][] grid;


    public Tabuleiro() {
        grid = new Elemento[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Elemento(i, j, 0);
            }
        }
    }

    public Elemento getElemento(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public void atualizarPossiveis(int linha, int coluna, int valor) {
        // Atualiza as células na mesma linha
        for (int i = 0; i < 9; i++) {
            if (i != coluna) { // Evita atualizar a célula onde o valor foi inserido
                grid[linha][i].removePossibleValue(valor);
            }
        }

        // Atualiza as células na mesma coluna
        for (int i = 0; i < 9; i++) {
            if (i != linha) { // Evita atualizar a célula onde o valor foi inserido
                grid[i][coluna].removePossibleValue(valor);
            }
        }

        // Atualiza as células no mesmo bloco 3x3
        int blocoLinha = (linha / 3) * 3; // Encontra o início do bloco na linha
        int blocoColuna = (coluna / 3) * 3; // Encontra o início do bloco na coluna
        for (int i = blocoLinha; i < blocoLinha + 3; i++) {
            for (int j = blocoColuna; j < blocoColuna + 3; j++) {
                if (i != linha || j != coluna) { // Evita atualizar a célula onde o valor foi inserido
                    grid[i][j].removePossibleValue(valor);
                }
            }
        }
    }



    public boolean setValor(int linha, int coluna, int valor) {
        Elemento elemento = grid[linha][coluna];
        if (elemento.getPossibleValues().contains(valor)) {
            elemento.setValor(valor);
            atualizarPossiveis(linha, coluna, valor);
            return true;
        } else {
            System.out.println("Valor inválido para essa posição.");
            return false;
        }
    }

    public void zeraPossiveisValores()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].possibleValues = new ArrayList<>();
            }
        }
    }

    public void setValorRemove(int linha, int coluna, int valor){
        Elemento elemento = grid[linha][coluna];
        if(elemento.getValor() != 0){
            int valorAtual = elemento.getValor();
            elemento.setValor(0);
            atualizarPossiveisRemocao(linha, coluna, valorAtual);
        }
        else
            System.out.println("Não há valores nessa posição");
    }

    public void atualizarPossiveisRemocao(int linha, int coluna, int valorRemovido) {
        for (int i = 0; i < 9; i++) {
            if (grid[linha][i].getValor() == 0) {
                if (!grid[linha][i].getPossibleValues().contains(valorRemovido)) {
                    grid[linha][i].addPossibleValue(valorRemovido);
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i != linha && grid[i][coluna].getValor() == 0) {
                if (!grid[i][coluna].getPossibleValues().contains(valorRemovido)) {
                    grid[i][coluna].addPossibleValue(valorRemovido);
                }
            }
        }


        int quadInicioLinha = (linha / 3) * 3;
        int quadInicioColuna = (coluna / 3) * 3;
        for (int i = quadInicioLinha; i < quadInicioLinha + 3; i++) {
            for (int j = quadInicioColuna; j < quadInicioColuna + 3; j++) {
                if ((i != linha || j != coluna) && grid[i][j].getValor() == 0) {
                    if (!grid[i][j].getPossibleValues().contains(valorRemovido)) {
                        grid[i][j].addPossibleValue(valorRemovido);
                    }
                }
            }
        }
    }

    public void setValorImutavel(int linha, int coluna, int valor) {
        grid[linha][coluna].deixandoImutavel();
    }


    public int getValor(int linha, int coluna) {
        return grid[linha][coluna].getValor();
    }

    public void imprimirTabuleiro() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("---------------------");
            }

            for (int j = 0; j < 9; j++) {

                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }

                System.out.print(grid[i][j].getValor() + " ");
            }
            System.out.println();
        }
    }


    public boolean checkagemVitoria(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getValor() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean ehResolvido() {
        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
                if (grid[linha][coluna].getValor() == 0) {
                    for (int valor : grid[linha][coluna].getPossibleValues()) {
                        if (podeColocarValor(linha, coluna, valor)) {
                            grid[linha][coluna].setValor(valor);
                            if (ehResolvido()) {
                                this.zeraPossiveisValores();
                                return true;
                            }
                            grid[linha][coluna].setValor(0); // Backtrack
                        }
                    }
                    return false; // Se nenhum valor for possível, não é resolvível
                }
            }
        }
        this.zeraPossiveisValores();
        return true; // Se não há células vazias, o tabuleiro é resolvido
    }

    private boolean verificaLinha(int linha, int valor) {
        for (int coluna = 0; coluna < 9; coluna++) {
            if (grid[linha][coluna].getValor() == valor) {
                return false; // O valor já existe na linha
            }
        }
        return true;
    }

    private boolean verificaColuna(int coluna, int valor) {
        for (int linha = 0; linha < 9; linha++) {
            if (grid[linha][coluna].getValor() == valor) {
                return false; // O valor já existe na coluna
            }
        }
        return true;
    }

    private boolean verificaBloco(int linha, int coluna, int valor) {
        int linhaInicial = (linha / 3) * 3;
        int colunaInicial = (coluna / 3) * 3;

        for (int i = linhaInicial; i < linhaInicial + 3; i++) {
            for (int j = colunaInicial; j < colunaInicial + 3; j++) {
                if (grid[i][j].getValor() == valor) {
                    return false; // O valor já existe no bloco 3x3
                }
            }
        }
        return true;
    }

    public boolean podeColocarValor(int linha, int coluna, int valor) {
        // Verifica as regras do Sudoku
        return this.verificaLinha(linha, valor) && this.verificaColuna(coluna, valor) && this.verificaBloco(linha, coluna, valor);
    }


    public boolean verificaValidade()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getPossibleValues() == null)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
