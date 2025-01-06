package br.ufjf.dcc.dcc025;

public class Tabuleiro {
    private Elemento[][] grid; // Representa o tabuleiro do Sudoku com Elementos

    // Construtor
    public Tabuleiro() {
        grid = new Elemento[9][9]; // Inicializa um tabuleiro 9x9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Elemento(i, j, 0); // Inicializa cada elemento com valor 0
            }
        }
    }

    public Elemento getElemento(int linha, int coluna) {
        return grid[linha][coluna];
    }

    // Método para definir um valor no tabuleiro
    public boolean setValor(int linha, int coluna, int valor) {
        Elemento elemento = grid[linha][coluna];
        if (elemento.getPossibleValues().contains(valor)) {
            elemento.setValor(valor);
            atualizarPossiveis(linha, coluna, valor); // Remove o valor das possíveis posições da linha, coluna e quadrante
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
        // Adiciona o valor de volta nas possíveis posições na linha
        for (int i = 0; i < 9; i++) {
            if (i != coluna && grid[linha][i].getValor() == 0) { // Se a célula estiver vazia
                if (!grid[linha][i].getPossibleValues().contains(valorRemovido)) {
                    grid[linha][i].addPossibleValue(valorRemovido);
                }
            }
        }

        // Adiciona o valor de volta nas possíveis posições na coluna
        for (int i = 0; i < 9; i++) {
            if (i != linha && grid[i][coluna].getValor() == 0) { // Se a célula estiver vazia
                if (!grid[i][coluna].getPossibleValues().contains(valorRemovido)) {
                    grid[i][coluna].addPossibleValue(valorRemovido);
                }
            }
        }

        // Adiciona o valor de volta nas possíveis posições no quadrante 3x3
        int quadInicioLinha = (linha / 3) * 3;
        int quadInicioColuna = (coluna / 3) * 3;
        for (int i = quadInicioLinha; i < quadInicioLinha + 3; i++) {
            for (int j = quadInicioColuna; j < quadInicioColuna + 3; j++) {
                if ((i != linha || j != coluna) && grid[i][j].getValor() == 0) { // Se a célula estiver vazia
                    if (!grid[i][j].getPossibleValues().contains(valorRemovido)) {
                        grid[i][j].addPossibleValue(valorRemovido);
                    }
                }
            }
        }
    }

    public void setValorImutavel(int linha, int coluna, int valor) {
        grid[linha][coluna].deixandoImutavel(); // Torna o valor imutável
    }

    // Método para obter um valor do tabuleiro
    public int getValor(int linha, int coluna) {
        return grid[linha][coluna].getValor();
    }

    // Método para imprimir o tabuleiro
    public void imprimirTabuleiro() {
        for (int i = 0; i < 9; i++) {
            // Adiciona uma linha horizontal a cada 3 linhas
            if (i % 3 == 0 && i != 0) {
                System.out.println("---------------------");
            }

            for (int j = 0; j < 9; j++) {
                // Adiciona um separador vertical a cada 3 colunas
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }

                // Imprime o valor de cada célula
                System.out.print(grid[i][j].getValor() + " ");
            }
            System.out.println(); // Muda para a próxima linha após imprimir uma linha completa
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

    // Método que atualiza as listas de valores possíveis na linha, coluna e quadrante 3x3
    private void atualizarPossiveis(int linha, int coluna, int valor) {
        // Remover valor das possíveis posições na linha
        for (int i = 0; i < 9; i++) {
            if (i != coluna) {
                grid[linha][i].removePossibleValue(valor);
            }
        }

        // Remover valor das possíveis posições na coluna
        for (int i = 0; i < 9; i++) {
            if (i != linha) {
                grid[i][coluna].removePossibleValue(valor);
            }
        }

        // Remover valor das possíveis posições no quadrante 3x3
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
