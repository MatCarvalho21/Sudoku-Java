package br.ufjf.dcc.dcc025;
import java.util.Scanner;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Bem-vindo ao Sudoko!");
            boolean key = true;
            while(key)
            {
                System.out.println("Escolha o modo de jogo:\n1 - Modo Aleatório\n2 - Modo Manual");
                String entrada = scanner.next();
                Tabuleiro tabuleiro = new Tabuleiro();
                switch (entrada)
                {
                    case "1":
                        boolean key1 = true;
                        while (key1) {
                            System.out.println("Quantas entradas você deseja preencher?");
                            String entrada_1 = scanner.next();
                            try {
                                int iEntrada_1 = Integer.parseInt(entrada_1);

                                // Verifica se o número de entradas é válido
                                if (iEntrada_1 <= 0 || iEntrada_1 > 81) {
                                    throw new NumberFormatException("");
                                }

                                Random random = new Random();
                                int casasPreenchidas = 0;

                                // Preencher o tabuleiro automaticamente com o número de entradas desejado
                                while (casasPreenchidas < iEntrada_1) {
                                    int linha = random.nextInt(9); // Gera uma linha aleatória
                                    int coluna = random.nextInt(9); // Gera uma coluna aleatória
                                    int valor = random.nextInt(9) + 1; // Gera um valor aleatório de 1 a 9

                                    // Tenta inserir o valor na posição se for válida
                                    if (tabuleiro.getValor(linha, coluna) == 0 && tabuleiro.getElemento(linha, coluna).getPossibleValues().contains(valor)) {
                                        tabuleiro.setValor(linha, coluna, valor);
                                        tabuleiro.setValorImutavel(linha, coluna, valor);
                                        casasPreenchidas++;
                                    }
                                }

                                // Imprime o tabuleiro preenchido
                                System.out.println("Tabuleiro gerado automaticamente:");
                                tabuleiro.imprimirTabuleiro();

                                key1 = false;

                            } catch (NumberFormatException e) {
                                System.out.println("Erro: Insira um número inteiro positivo válido.");
                            }
                        }
                        key = false;
                        break;


                    // manual
                    case "2":
                        boolean key2 = true;
                        while (key2)
                        {
                            System.out.println("Quantas entradas você deseja preencher?");
                            String entrada_2 = scanner.next();
                            try
                            {
                                int iEntrada_2 = Integer.parseInt(entrada_2);

                                // caso seja inserido um inteiro inválido
                                if (iEntrada_2 <= 0 || iEntrada_2 > 81)
                                {
                                    throw new NumberFormatException("");
                                }

                                while (iEntrada_2 > 0)
                                {
                                    System.out.println("Insira seu comando no formato (L,C,V).");
                                    String entrada_3 = scanner.next();

                                    String regex = "^\\([0-8]+,[0-8]+,[1-9]+\\)$";

                                    if (entrada_3.matches(regex)) {
                                        
                                    } else {
                                        System.out.println("Formato inválido.");
                                    }
                                }

                                key2 = false;

                            // caso seja inserida uma entrada com tipagem inválida
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Erro: Insira um número inteiro positivo válido.");
                            }
                        }
                        key = false;
                        break;

                    default:
                        System.out.println("Entrada inválida, tente novamente!");
                        break;
                }
            }
            break;
        }
    }
}
