package br.ufjf.dcc.dcc025;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o tabuleiro de um jogo de Sudoku.
 * Gerencia as regras do jogo, os valores inseridos, e os valores possíveis.
 */
public class Tabuleiro {
    private Elemento[][] grid;

    /**
     * Construtor que inicializa o tabuleiro 9x9.
     * Todas as células começam com valor 0.
     */
    public Tabuleiro() {
        grid = new Elemento[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Elemento(i, j, 0);
            }
        }
    }

    /**
     * Retorna o elemento em uma posição específica no tabuleiro.
     *
     * @param linha  A linha do elemento.
     * @param coluna A coluna do elemento.
     * @return O elemento correspondente na posição.
     */
    public Elemento pegaElemento(int linha, int coluna) {
        return grid[linha][coluna];
    }

    /**
     * Atualiza os valores possíveis para as células relacionadas (linha, coluna e bloco 3x3)
     * quando um valor é inserido em uma célula.
     *
     * @param linha A linha da célula que recebeu o valor.
     * @param coluna A coluna da célula que recebeu o valor.
     * @param valor O valor inserido na célula.
     */
    private void atualizaValoresPossiveis(int linha, int coluna, int valor) {
        // Atualiza as células na mesma linha
        for (int i = 0; i < 9; i++) {
            if (i != coluna) {
                grid[linha][i].removeValorPossivel(valor);
            }
        }

        // Atualiza as células na mesma coluna
        for (int i = 0; i < 9; i++) {
            if (i != linha) {
                grid[i][coluna].removeValorPossivel(valor);
            }
        }

        // Atualiza as células no mesmo bloco 3x3
        int blocoLinha = (linha / 3) * 3;
        int blocoColuna = (coluna / 3) * 3;
        for (int i = blocoLinha; i < blocoLinha + 3; i++) {
            for (int j = blocoColuna; j < blocoColuna + 3; j++) {
                if (i != linha || j != coluna) {
                    grid[i][j].removeValorPossivel(valor);
                }
            }
        }
    }

    /**
     * Define um valor para uma célula no tabuleiro e atualiza os valores possíveis das células relacionadas.
     *
     * @param linha A linha da célula.
     * @param coluna A coluna da célula.
     * @param valor O valor a ser definido.
     * @return True se o valor foi definido com sucesso, False caso contrário.
     */
    public boolean defineValor(int linha, int coluna, int valor) {
        Elemento elemento = grid[linha][coluna];
        if (elemento.pegaValoresPossiveis().contains(valor)) {
            elemento.defineValor(valor);
            atualizaValoresPossiveis(linha, coluna, valor);
            return true;
        } else {
            System.out.println("Valor inválido para essa posição.");
            return false;
        }
    }

    /**
     * Remove todos os valores possíveis de todas as células do tabuleiro.
     * Útil para reinicialização ou limpeza do estado do tabuleiro.
     */
    public void zeraValoresPossiveis() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].valoresPossiveis = new ArrayList<>();
            }
        }
    }

    /**
     * Remove o valor de uma célula, restaurando os valores possíveis nas células relacionadas.
     *
     * @param linha A linha da célula.
     * @param coluna A coluna da célula.
     * @param valor O valor a ser removido.
     */
    public void defineValorRemove(int linha, int coluna, int valor) {
        Elemento elemento = grid[linha][coluna];
        if (elemento.pegaValor() != 0) {
            int valorAtual = elemento.pegaValor();
            elemento.defineValor(0);
            atualizaValoresPossiveisRemocao(linha, coluna, valorAtual);
        } else {
            System.out.println("Não há valores nessa posição");
        }
    }

    /**
     * Atualiza os valores possíveis de todas as células do tabuleiro
     * com base nos valores atualmente definidos.
     */
    public void setandoValoresPossiveis() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].pegaValor() == 0) {
                    List<Integer> valoresPossiveis = new ArrayList<>();
                    for (int valor = 1; valor <= 9; valor++) {
                        if (podeInserirValor(i, j, valor)) {
                            valoresPossiveis.add(valor);
                        }
                    }
                    grid[i][j].atualizaValoresPossiveis(valoresPossiveis);
                }
            }
        }
    }

    /**
     * Verifica se um valor pode ser inserido em uma célula, considerando
     * as regras de Sudoku (linha, coluna e bloco).
     *
     * @param linha A linha da célula.
     * @param coluna A coluna da célula.
     * @param valor O valor a ser verificado.
     * @return True se o valor pode ser inserido, False caso contrário.
     */
    private boolean podeInserirValor(int linha, int coluna, int valor) {
        // Verifica a linha
        for (int j = 0; j < 9; j++) {
            if (grid[linha][j].pegaValor() == valor) {
                return false;
            }
        }

        // Verifica a coluna
        for (int i = 0; i < 9; i++) {
            if (grid[i][coluna].pegaValor() == valor) {
                return false;
            }
        }

        // Verifica o quadrante 3x3
        int quadInicioLinha = (linha / 3) * 3;
        int quadInicioColuna = (coluna / 3) * 3;
        for (int i = quadInicioLinha; i < quadInicioLinha + 3; i++) {
            for (int j = quadInicioColuna; j < quadInicioColuna + 3; j++) {
                if (grid[i][j].pegaValor() == valor) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Atualiza os valores possíveis para as células após a remoção de um valor de uma célula específica.
     *
     * @param linha         A linha da célula que teve o valor removido.
     * @param coluna        A coluna da célula que teve o valor removido.
     * @param valorRemovido O valor que foi removido da célula.
     */
    private void atualizaValoresPossiveisRemocao(int linha, int coluna, int valorRemovido) {
        // Atualizar valores possíveis na linha
        for (int i = 0; i < 9; i++) {
            if (grid[linha][i].pegaValor() == 0) {
                if (!grid[linha][i].pegaValoresPossiveis().contains(valorRemovido)) {
                    grid[linha][i].adicionaValorPossivel(valorRemovido);
                }
            }
        }

        // Atualizar valores possíveis na coluna
        for (int i = 0; i < 9; i++) {
            if (i != linha && grid[i][coluna].pegaValor() == 0) {
                boolean valorPresenteNaLinha = false;
                for (int j = 0; j < 9; j++) {
                    if (grid[i][j].pegaValor() == valorRemovido) {
                        valorPresenteNaLinha = true;
                        break;
                    }
                }
                if (!valorPresenteNaLinha && !grid[i][coluna].pegaValoresPossiveis().contains(valorRemovido)) {
                    grid[i][coluna].adicionaValorPossivel(valorRemovido);
                }
            }
        }

        // Atualizar valores possíveis no bloco 3x3
        int quadInicioLinha = (linha / 3) * 3;
        int quadInicioColuna = (coluna / 3) * 3;
        for (int i = quadInicioLinha; i < quadInicioLinha + 3; i++) {
            for (int j = quadInicioColuna; j < quadInicioColuna + 3; j++) {
                if ((i != linha || j != coluna) && grid[i][j].pegaValor() == 0) {
                    boolean valorPresenteNaLinha = false;
                    for (int k = 0; k < 9; k++) {
                        if (grid[i][k].pegaValor() == valorRemovido) {
                            valorPresenteNaLinha = true;
                            break;
                        }
                    }
                    if (!valorPresenteNaLinha && !grid[i][j].pegaValoresPossiveis().contains(valorRemovido)) {
                        grid[i][j].adicionaValorPossivel(valorRemovido);
                    }
                }
            }
        }
    }

    /**
     * Define uma célula como imutável, impedindo que seu valor seja alterado.
     *
     * @param linha  A linha da célula.
     * @param coluna A coluna da célula.
     * @param valor  O valor a ser definido como imutável.
     */
    public void defineValorImutavel(int linha, int coluna, int valor) {
        grid[linha][coluna].deixaImutavel();
    }

    /**
     * Retorna o valor de uma célula específica.
     *
     * @param linha  A linha da célula.
     * @param coluna A coluna da célula.
     * @return O valor da célula.
     */
    public int pegaValor(int linha, int coluna) {
        return grid[linha][coluna].pegaValor();
    }

    /**
     * Imprime o tabuleiro no console, formatado com divisões de blocos 3x3.
     */
    public void imprimeTabuleiro() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("---------------------");
            }

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[i][j].pegaValor() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Verifica se o jogo foi vencido, ou seja, se todas as células estão preenchidas.
     *
     * @return true se o tabuleiro estiver completo, false caso contrário.
     */
    public boolean checaVitoria() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].pegaValor() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Resolve o tabuleiro de Sudoku utilizando backtracking.
     *
     * @return true se o tabuleiro foi resolvido, false caso contrário.
     */
    public boolean ehResolvido() {
        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
                if (grid[linha][coluna].pegaValor() == 0) {
                    for (int valor : grid[linha][coluna].pegaValoresPossiveis()) {
                        if (podeColocarValor(linha, coluna, valor)) {
                            grid[linha][coluna].defineValor(valor);
                            if (ehResolvido()) {
                                this.zeraValoresPossiveis();
                                return true;
                            }
                            grid[linha][coluna].defineValor(0); // Backtrack
                        }
                    }
                    return false; // Se nenhum valor for possível, o tabuleiro não é resolvível
                }
            }
        }
        this.zeraValoresPossiveis();
        return true; // Se não há células vazias, o tabuleiro é resolvido
    }

    /**
     * Verifica se uma linha já contém um valor específico.
     *
     * @param linha O índice da linha.
     * @param valor O valor a ser verificado.
     * @return true se o valor não estiver na linha, false caso contrário.
     */
    private boolean verificaLinha(int linha, int valor) {
        for (int coluna = 0; coluna < 9; coluna++) {
            if (grid[linha][coluna].pegaValor() == valor) {
                return false; // O valor já existe na linha
            }
        }
        return true;
    }

    /**
     * Verifica se uma coluna já contém um valor específico.
     *
     * @param coluna O índice da coluna.
     * @param valor O valor a ser verificado.
     * @return true se o valor não estiver na coluna, false caso contrário.
     */
    private boolean verificaColuna(int coluna, int valor) {
        for (int linha = 0; linha < 9; linha++) {
            if (grid[linha][coluna].pegaValor() == valor) {
                return false; // O valor já existe na coluna
            }
        }
        return true;
    }

    /**
     * Verifica se um bloco 3x3 já contém um valor específico.
     *
     * @param linha  O índice da linha da célula.
     * @param coluna O índice da coluna da célula.
     * @param valor  O valor a ser verificado.
     * @return true se o valor não estiver no bloco, false caso contrário.
     */
    private boolean verificaBloco(int linha, int coluna, int valor) {
        int linhaInicial = (linha / 3) * 3;
        int colunaInicial = (coluna / 3) * 3;

        for (int i = linhaInicial; i < linhaInicial + 3; i++) {
            for (int j = colunaInicial; j < colunaInicial + 3; j++) {
                if (grid[i][j].pegaValor() == valor) {
                    return false; // O valor já existe no bloco 3x3
                }
            }
        }
        return true;
    }

    /**
     * Verifica se um valor pode ser colocado em uma célula específica do tabuleiro.
     *
     * @param linha  O índice da linha da célula.
     * @param coluna O índice da coluna da célula.
     * @param valor  O valor a ser verificado.
     * @return true se o valor pode ser colocado na célula, false caso contrário.
     */
    private boolean podeColocarValor(int linha, int coluna, int valor) {
        // Verifica as regras do Sudoku
        return this.verificaLinha(linha, valor) &&
                this.verificaColuna(coluna, valor) &&
                this.verificaBloco(linha, coluna, valor);
    }

    /**
     * Verifica se todos os elementos do tabuleiro possuem listas de valores possíveis.
     *
     * @return true se todas as células tiverem valores possíveis definidos, false caso contrário.
     */
    public boolean verificaValidade() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].pegaValoresPossiveis() == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
