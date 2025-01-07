package br.ufjf.dcc.dcc025;
import java.util.Scanner;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean geral = true;
        while (geral){
            Tabuleiro tabuleiro = new Tabuleiro();
            System.out.println("Bem-vindo ao Sudoku!");
            boolean key = true;
            while(key)
            {
                System.out.println("Escolha o modo de jogo:\n1 - Modo Aleatório\n2 - Modo Manual");
                String entrada = scanner.next();
                switch (entrada)
                {
                    case "1":
                        boolean key1 = true;
                        while (key1) {
                            System.out.println("Quantas entradas você deseja preencher?");
                            String entrada_1 = scanner.next();
                            try {
                                int iEntrada_1 = Integer.parseInt(entrada_1);
                                if (iEntrada_1 <= 0 || iEntrada_1 > 81) {
                                    throw new NumberFormatException("");
                                }
                                Random random = new Random();
                                int casasPreenchidas = 0;
                                boolean chave = true;

                                while (chave) {
                                    tabuleiro = new Tabuleiro(); // Reinicie o tabuleiro

                                    int linha, coluna;

                                    // Verifica a validade e resolvibilidade do tabuleiro parcial
                                    chave = !tabuleiro.verificaValidade() || !tabuleiro.ehResolvido();

                                    if (!chave) {
                                        int i = 0;
                                        while (i < 81 - iEntrada_1)
                                        {
                                            linha = random.nextInt(9);
                                            coluna = random.nextInt(9);

                                            if (tabuleiro.pegaValor(linha, coluna) != 0) {
                                                tabuleiro.pegaElemento(linha, coluna).deixaMutavel();
                                                tabuleiro.defineValorRemove(linha, coluna, tabuleiro.pegaValor(linha, coluna));
                                                i++;
                                            }
                                        }
                                        System.out.println("Tabuleiro gerado automaticamente:");
                                        tabuleiro.imprimeTabuleiro();
                                        key1 = false;
                                    } else {
                                        System.out.println("Tabuleiro inválido gerado, tentando novamente...");
                                    }
                                }


                            }
                            catch (NumberFormatException e) {
                                System.out.println("Erro: Insira um número inteiro positivo válido.");
                            }
                        }
                        key = false;
                        break;
                    case "2":
                        scanner.nextLine();
                        boolean inserirValores = true;
                        while (inserirValores) {
                            System.out.println("Insira os valores no formato ([linha],[coluna],[valor]) ou (-1,-1,-1) para terminar:");
                            String entradaManual = scanner.nextLine();
                            if (entradaManual.equals("(-1,-1,-1)")) {
                                inserirValores = false;
                                System.out.println("Encerrando inserção de valores.");
                            }
                            else {
                                if (entradaManual.matches("^(\\(\\d,\\d,\\d\\))+")) {
                                    String[] entradas = entradaManual.split("\\)\\(");
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", "");
                                        String[] valores = item.split(",");
                                        int linha = Integer.parseInt(valores[0]);
                                        int coluna = Integer.parseInt(valores[1]);
                                        int valor = Integer.parseInt(valores[2]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 && valor > 0 && valor <= 9) {
                                           boolean veracidade = tabuleiro.defineValor(linha, coluna, valor);
                                           if(veracidade)
                                               tabuleiro.defineValorImutavel(linha, coluna, valor);
                                        } else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna e valor.");
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");
                                }
                            }
                        }
                        System.out.println("Tabuleiro manual :");
                        tabuleiro.imprimeTabuleiro();
                        key = false;
                        break;
                    default:
                        System.out.println("Entrada inválida, tente novamente!");
                        break;
                }
            }
                boolean key_jogadas = true;
                boolean vitoria = false;
                while (key_jogadas){
                    System.out.println("Escolha a jogada:\n1 - Adicionar elemento - (L,C,V)\n2 - Remover elemento - (L,C)\n3 - Dica - (L,C)\n4 - Sair do jogo");
                    String jogadas = scanner.next();
                        switch (jogadas){
                            case "1":
                                String jogadaAdd = scanner.next();
                                if (jogadaAdd.matches("^(\\(\\d,\\d,\\d\\))+")) {
                                    String[] entradas = jogadaAdd.split("\\)\\(");
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", "");
                                        String[] valores = item.split(",");
                                        int linha = Integer.parseInt(valores[0]);
                                        int coluna = Integer.parseInt(valores[1]);
                                        int valor = Integer.parseInt(valores[2]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 && valor > 0 && valor <= 9) {
                                            tabuleiro.defineValor(linha, coluna, valor);
                                            vitoria = tabuleiro.checaVitoria();
                                        }
                                        else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna e valor.");
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");
                             }
                                tabuleiro.imprimeTabuleiro();
                                break;
                            case "2":
                                String jogadaRemove = scanner.next();
                                if (jogadaRemove.matches("^(\\(\\d,\\d\\))+")) {
                                    String[] entradas = jogadaRemove.split("\\)\\(");
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", "");
                                        String[] valores = item.split(",");
                                        int linha = Integer.parseInt(valores[0]);
                                        int coluna = Integer.parseInt(valores[1]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 ) {
                                            tabuleiro.defineValorRemove(linha, coluna, 0);
                                        } else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna.");
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");
                                }
                                tabuleiro.imprimeTabuleiro();
                                break;
                            case "3":
                                String jogadaDica = scanner.next();
                                if (jogadaDica.matches("^(\\(\\d,\\d\\))+")) {
                                    String[] entradas = jogadaDica.split("\\)\\(");
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", "");
                                        String[] valores = item.split(",");
                                        int linha = Integer.parseInt(valores[0]);
                                        int coluna = Integer.parseInt(valores[1]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 ) {
                                            Elemento elemento = tabuleiro.pegaElemento(linha, coluna);
                                            System.out.println("Valores possíveis: " + elemento.pegaValoresPossiveis());
                                        }
                                        else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna.");
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");
                                }
                                break;
                            case "4":
                                key_jogadas = false;
                                break;
                            default:
                                System.out.println("Entrada inválida, tente novamente.");
                        }

                    if (vitoria) {
                        System.out.println("Parabéns! Você ganhou!");
                        System.out.println("Você deseja jogar novamente [Y]? ");
                        String jogarNovamente = scanner.next();
                        if (!jogarNovamente.equalsIgnoreCase("Y")) {
                            System.out.println("Jogo encerrado.");
                            System.exit(0); // Encerra o programa
                        }
                        break; // Sai do loop e reinicia o jogo1

                    }

                }
        }
    }
}
