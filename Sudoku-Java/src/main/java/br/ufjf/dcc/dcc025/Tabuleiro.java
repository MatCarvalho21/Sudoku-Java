package br.ufjf.dcc.dcc025;

import java.util.ArrayList;

public class Tabuleiro {
    private Elemento[][] grid;

    // OK
    public Tabuleiro()
    {
        grid = new Elemento[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Elemento(i, j, 0);
            }
        }
    }

    // OK
    public Elemento pegaElemento(int linha, int coluna)
    {
        return grid[linha][coluna];
    }

    // OK - USADO AQUI
    private void atualizaValoresPossiveis(int linha, int coluna, int valor)
    {
        // Atualiza as células na mesma linha
        for (int i = 0; i < 9; i++) {
            if (i != coluna) { // Evita atualizar a célula onde o valor foi inserido
                grid[linha][i].removeValorPossivel(valor);
            }
        }

        // Atualiza as células na mesma coluna
        for (int i = 0; i < 9; i++) {
            if (i != linha) { // Evita atualizar a célula onde o valor foi inserido
                grid[i][coluna].removeValorPossivel(valor);
            }
        }

        // Atualiza as células no mesmo bloco 3x3
        int blocoLinha = (linha / 3) * 3; // Encontra o início do bloco na linha
        int blocoColuna = (coluna / 3) * 3; // Encontra o início do bloco na coluna
        for (int i = blocoLinha; i < blocoLinha + 3; i++) {
            for (int j = blocoColuna; j < blocoColuna + 3; j++) {
                if (i != linha || j != coluna) { // Evita atualizar a célula onde o valor foi inserido
                    grid[i][j].removeValorPossivel(valor);
                }
            }
        }
    }

    // OK
    public boolean defineValor(int linha, int coluna, int valor)
    {
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

    // OK - USADO AQUI
    private void zeraValoresPossiveis()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].valoresPossiveis = new ArrayList<>();
            }
        }
    }

    // OK
    public void defineValorRemove(int linha, int coluna, int valor)
    {
        Elemento elemento = grid[linha][coluna];
        if(elemento.pegaValor() != 0){
            int valorAtual = elemento.pegaValor();
            elemento.defineValor(0);
            atualizaValoresPossiveisRemocao(linha, coluna, valorAtual);
        }
        else
            System.out.println("Não há valores nessa posição");
    }

    // OK - USADO AQUI
    private void atualizaValoresPossiveisRemocao(int linha, int coluna, int valorRemovido)
    {
        for (int i = 0; i < 9; i++) {
            if (grid[linha][i].pegaValor() == 0) {
                if (!grid[linha][i].pegaValoresPossiveis().contains(valorRemovido)) {
                    grid[linha][i].adicionaValorPossivel(valorRemovido);
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i != linha && grid[i][coluna].pegaValor() == 0) {
                if (!grid[i][coluna].pegaValoresPossiveis().contains(valorRemovido)) {
                    grid[i][coluna].adicionaValorPossivel(valorRemovido);
                }
            }
        }


        int quadInicioLinha = (linha / 3) * 3;
        int quadInicioColuna = (coluna / 3) * 3;
        for (int i = quadInicioLinha; i < quadInicioLinha + 3; i++) {
            for (int j = quadInicioColuna; j < quadInicioColuna + 3; j++) {
                if ((i != linha || j != coluna) && grid[i][j].pegaValor() == 0) {
                    if (!grid[i][j].pegaValoresPossiveis().contains(valorRemovido)) {
                        grid[i][j].adicionaValorPossivel(valorRemovido);
                    }
                }
            }
        }
    }

    // OK
    public void defineValorImutavel(int linha, int coluna, int valor)
    {
        grid[linha][coluna].deixaImutavel();
    }

    // OK
    public int pegaValor(int linha, int coluna)
    {
        return grid[linha][coluna].pegaValor();
    }

    // OK
    public void imprimeTabuleiro()
    {
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

    // OK
    public boolean checaVitoria()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].pegaValor() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    // OK
    public boolean ehResolvido()
    {
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
                    return false; // Se nenhum valor for possível, não é resolvível
                }
            }
        }
        this.zeraValoresPossiveis();
        return true; // Se não há células vazias, o tabuleiro é resolvido
    }

    // OK
    private boolean verificaLinha(int linha, int valor)
    {
        for (int coluna = 0; coluna < 9; coluna++) {
            if (grid[linha][coluna].pegaValor() == valor) {
                return false; // O valor já existe na linha
            }
        }
        return true;
    }

    // OK
    private boolean verificaColuna(int coluna, int valor)
    {
        for (int linha = 0; linha < 9; linha++) {
            if (grid[linha][coluna].pegaValor() == valor) {
                return false; // O valor já existe na coluna
            }
        }
        return true;
    }

    // OK
    private boolean verificaBloco(int linha, int coluna, int valor)
    {
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

    // OK - USADO AQUI
    private boolean podeColocarValor(int linha, int coluna, int valor)
    {
        // Verifica as regras do Sudoku
        return this.verificaLinha(linha, valor) && this.verificaColuna(coluna, valor) && this.verificaBloco(linha, coluna, valor);
    }

    // OK
    public boolean verificaValidade()
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].pegaValoresPossiveis() == null)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
