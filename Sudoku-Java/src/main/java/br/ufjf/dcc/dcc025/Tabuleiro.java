package br.ufjf.dcc.dcc025;

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

    private void atualizarPossiveisRemocao(int linha, int coluna, int valorRemovido) {
        for (int i = 0; i < 9; i++) {
            if (i != coluna && grid[linha][i].getValor() == 0) {
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


    private void atualizarPossiveis(int linha, int coluna, int valor) {
        for (int i = 0; i < 9; i++) {
            if (i != coluna) {
                grid[linha][i].removePossibleValue(valor);
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i != linha) {
                grid[i][coluna].removePossibleValue(valor);
            }
        }

        int quadInicioLinha = (linha / 3) * 3;
        int quadInicioColuna = (coluna / 3) * 3;
        for (int i = quadInicioLinha; i < quadInicioLinha + 3; i++) {
            for (int j = quadInicioColuna; j < quadInicioColuna + 3; j++) {
                if (i != linha || j != coluna) {
                    grid[i][j].removePossibleValue(valor);
                }
            }
        }
    }
}
