package br.ufjf.dcc.dcc025;
import java.util.Scanner;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean geral = true;                                                                           //chave para nao quebrar o laco de boas-vindas
        while (geral){
            Tabuleiro tabuleiro = new Tabuleiro();
            System.out.println("Bem-vindo ao Sudoku!");
            boolean key = true;                                                                         //chave do laco de escolha do modo de iniciacao do tabuleiro
            while(key)
            {
                System.out.println("Escolha o modo de jogo:\n1 - Modo Aleatório\n2 - Modo Manual");
                String entrada = scanner.next();
                switch (entrada)                                                                        //para cada entrada, um mode de inciacao e suas funcoes
                {
                    case "1":
                        boolean key1 = true;
                        while (key1) {
                            System.out.println("Quantas entradas você deseja preencher?");
                            String entrada_1 = scanner.next();
                            try {
                                int iEntrada_1 = Integer.parseInt(entrada_1);
                                if (iEntrada_1 <= 0 || iEntrada_1 > 81) {                                 //metodo para tratamento de erros, caso de um erro, nao quebra a execucao e somente atribui uma mensagem
                                    throw new NumberFormatException("");
                                }
                                Random random = new Random();
                                int casasPreenchidas = 0;
                                boolean chave = true;

                                while (chave) {
                                    tabuleiro = new Tabuleiro();                                         // Reinicia o tabuleiro

                                    int linha, coluna;

                                    chave = !tabuleiro.verificaValidade() || !tabuleiro.ehResolvido();    // Verifica a validade e resolvibilidade do tabuleiro parcial

                                    tabuleiro.zeraValoresPossiveis();

                                    if (!chave) {                                                         //sorteia os elementos iniciais do tabuleiro
                                        int i = 0;
                                        while (i < 81 - iEntrada_1)                                       //preenche toda tabela e logo apos, remove 81 - entrada
                                        {
                                            linha = random.nextInt(9);
                                            coluna = random.nextInt(9);

                                            if (tabuleiro.pegaValor(linha, coluna) != 0) {                 //verifica se toda tabela esta preenchida
                                                tabuleiro.pegaElemento(linha, coluna).deixaMutavel();      //deixa todos elementos iniciais mutaveis para logo zerar os demais
                                                tabuleiro.defineValorRemove(linha, coluna, tabuleiro.pegaValor(linha, coluna));         //remove os valores
                                                i++;
                                            }
                                        }

                                        tabuleiro.setandoValoresPossiveis();                                //atualiza a lista de possiveis valores de cada elemento

                                        for (int j = 0; j < 9; j++){
                                            for (int k = 0; k < 9; k++){
                                                if (tabuleiro.pegaElemento(j,k).pegaValor() != 0)
                                                {
                                                    tabuleiro.pegaElemento(j,k).deixaImutavel();                //para cada elemento nao removido, ele torna imutavel, para respertar as regras do jogo
                                                }
                                            }
                                        }

                                        System.out.println("Tabuleiro gerado automaticamente:");
                                        tabuleiro.imprimeTabuleiro();                                           //apos toda a implementaçao, ele imprime o tabuleiro corretamente preenchido aleatoriamente
                                        key1 = false;
                                    } else {
                                        System.out.println("Tabuleiro inválido gerado, tentando novamente...");         //mensagem de erro para tabuleiro, segurança
                                    }
                                }


                            }
                            catch (NumberFormatException e) {
                                System.out.println("Erro: Insira um número inteiro positivo válido.");                  //tratamento de erro para entrada invalida
                            }
                        }
                        key = false;                                                                                     //torna a chave falsa para nao perguntar o modo de preenchimento infinitamente
                        break;
                    case "2":
                        scanner.nextLine();
                        boolean inserirValores = true;
                        while (inserirValores) {
                            System.out.println("Insira os valores no formato ([linha],[coluna],[valor]) ou (-1,-1,-1) para terminar:");
                            String entradaManual = scanner.nextLine();
                            if (entradaManual.equals("(-1,-1,-1)")) {                                                   //condiçao para o termino da insercao manual
                                inserirValores = false;
                                System.out.println("Encerrando inserção de valores.");
                            }
                            else {                                                                                      //para cada entrada valida, realiza operacoes desse bloco
                                if (entradaManual.matches("^(\\(\\d,\\d,\\d\\))+")) {                             //verifica o modelo de entrada
                                    String[] entradas = entradaManual.split("\\)\\(");                            //serapa as multiplas entradas
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", ""); //remove os parenteses de cada entrada individual
                                        String[] valores = item.split(",");                                         //remove as virgulas e separa cada numero
                                        int linha = Integer.parseInt(valores[0]);                                       //atribui cada valor a um elemento da lista, linha, coluna, valor
                                        int coluna = Integer.parseInt(valores[1]);
                                        int valor = Integer.parseInt(valores[2]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 && valor > 0 && valor <= 9) {      //verifica a validade e os limites de cada valor
                                           boolean veracidade = tabuleiro.defineValor(linha, coluna, valor);
                                           if(veracidade)
                                               tabuleiro.defineValorImutavel(linha, coluna, valor);
                                        } else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna e valor.");
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");                           //mensagem de erro para entradas invalidas
                                }
                            }
                        }
                        System.out.println("Tabuleiro manual :");
                        tabuleiro.imprimeTabuleiro();                                                                   //imprime o tabuleiro manual apos todas operacoes
                        key = false;
                        break;
                    default:
                        System.out.println("Entrada inválida, tente novamente!");                                       //mensagem de erro generica para opcao nao existente
                        break;
                }
            }
                boolean key_jogadas = true;
                boolean vitoria = false;
                while (key_jogadas){                                                                                    //inicia o laco para fazer jogadas, ate que a opcao sair seja inserida
                    System.out.println("Escolha a jogada:\n1 - Adicionar elemento - (L,C,V)\n2 - Remover elemento - (L,C)\n3 - Dica - (L,C)\n4 - Sair do jogo");
                    String jogadas = scanner.next();
                        switch (jogadas){
                            case "1":
                                String jogadaAdd = scanner.next();
                                if (jogadaAdd.matches("^(\\(\\d,\\d,\\d\\))+")) {                                 //verifica o modelo de entrada
                                    String[] entradas = jogadaAdd.split("\\)\\(");                                //serapa as multiplas entradas
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", ""); //remove os parenteses
                                        String[] valores = item.split(",");                                         //remove as virgulas
                                        int linha = Integer.parseInt(valores[0]);                                       //atribui cada valor a um elemento da lista, linha, coluna, valor
                                        int coluna = Integer.parseInt(valores[1]);
                                        int valor = Integer.parseInt(valores[2]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 && valor > 0 && valor <= 9) {      //verifica a validade e os limites de cada valor
                                            tabuleiro.defineValor(linha, coluna, valor);                                            //insere o valor na posicao desejada
                                            vitoria = tabuleiro.checaVitoria();                                         //verifica vitoria a cada insercao
                                        }
                                        else
                                        {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna e valor.");     //mensagem de erro para valores invalidos
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");                                       //mensagem de erro para modelo de entrada invalido
                             }
                                tabuleiro.imprimeTabuleiro();
                                break;
                            case "2":
                                String jogadaRemove = scanner.next();
                                if (jogadaRemove.matches("^(\\(\\d,\\d\\))+")) {                                    //verifica o modelo de entrada
                                    String[] entradas = jogadaRemove.split("\\)\\(");                               //serapa as multiplas entradas
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", "");      //remove os parenteses
                                        String[] valores = item.split(",");                                             //remove as virgulas
                                        int linha = Integer.parseInt(valores[0]);                                         //atribui cada valor a um elemento da lista, linha, coluna
                                        int coluna = Integer.parseInt(valores[1]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 && tabuleiro.pegaElemento(linha, coluna).mutavel) {  //verifica se os elementos estao nas posicoes validas e se esse elemento nao e imutavel
                                            tabuleiro.defineValorRemove(linha, coluna, 0);                                                      //remove os elemento do tabuleiro e atribui o valor como 0
                                        } else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna.");         //mensagem de erro para entradas de valores invalidos
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");                                       //mensagem de erro para modelo de entrada invalida
                                }
                                tabuleiro.imprimeTabuleiro();
                                break;
                            case "3":
                                String jogadaDica = scanner.next();
                                if (jogadaDica.matches("^(\\(\\d,\\d\\))+")) {                                      //verifica o modelo de entrada
                                    String[] entradas = jogadaDica.split("\\)\\(");                                 //separa as multiplas entradas
                                    for (String item : entradas) {
                                        item = item.replace("(", "").replace(")", "");  //remove os parenteses
                                        String[] valores = item.split(",");                                           //remove as virgulas
                                        int linha = Integer.parseInt(valores[0]);                                       //atribui cada valor a um elemento da lista, linha, coluna
                                        int coluna = Integer.parseInt(valores[1]);
                                        if (linha >= 0 && linha < 9 && coluna >= 0 && coluna < 9 ) {                    //verifica os limites
                                            Elemento elemento = tabuleiro.pegaElemento(linha, coluna);                  //acessa o elemento em questao
                                            System.out.println("Valores possíveis: " + elemento.pegaValoresPossiveis());            //imprime a lista de valores possiveis
                                        }
                                        else {
                                            System.out.println("Valores fora dos limites permitidos. Insira valores de 1 a 9 para linha, coluna.");         //mensagem de erro para valores invalidos
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Formato inválido. Tente novamente.");                                       //mensagem de erro para formato invalido
                                }
                                break;
                            case "4":
                                key_jogadas = false;                                                                        //transforma a chave pra false para terminar as jogadas validas, e sai do tabuleiro atual
                                break;
                            default:
                                System.out.println("Entrada inválida, tente novamente.");                                  //mensagem de erro generica para opcao nao existente
                        }

                    if (vitoria) {                                                                                          //verifica se a chave vitoria se tornou verdadeira, ou seja, o tabuleiro esta completo
                        System.out.println("Parabéns! Você ganhou!");
                        System.out.println("Você deseja jogar novamente [Y]? ");
                        String jogarNovamente = scanner.next();                                                         //entrada para verificar se o usuario quer jogar novamente
                        if (!jogarNovamente.equalsIgnoreCase("Y")) {                                        //caso entrada (Y), joga novamernte, caso diferente, encerra a execucao
                            System.out.println("Jogo encerrado.");
                            System.exit(0); // Encerra o programa
                        }
                        break;

                    }

                }
        }
    }
}
