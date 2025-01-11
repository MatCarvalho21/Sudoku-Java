# Sudoku-Java

Este é um projeto desenvolvido como parte de um estudo acadêmico por:

- **Rômulo Amaral**: Aluno do 4º período de Ciência da Computação na UFJF.
- **Matheus Carvalho**: Aluno do 5º período de Ciência de Dados e Inteligência Artificial na FGV Rio de Janeiro.

---

## Descrição do Projeto

O objetivo deste projeto é implementar um jogo de Sudoku utilizando **Java** e **Maven**. A aplicação é executada no terminal e permite ao jogador escolher entre dois modos para iniciar o jogo:

1. **Modo Aleatório**: O programa preenche o tabuleiro automaticamente, com o usuário definindo o número de entradas iniciais.
2. **Modo Manual**: O jogador preenche manualmente os valores iniciais do tabuleiro.

O jogador pode adicionar, remover valores ou solicitar dicas durante a partida. O jogo verifica automaticamente se o tabuleiro foi completado corretamente.

---

## Estrutura do Projeto

O repositório está organizado da seguinte forma:

```plaintext
Sudoku-Java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br.ufjf.dcc.dcc025/
│   │   │       ├── Elemento.java
│   │   │       ├── Main.java
│   │   │       └── Tabuleiro.java
│   │   └── resources/
│   └── test/
├── target/
├── .gitignore
├── pom.xml
```

### Detalhes Importantes:

- **Maven**: O projeto utiliza Maven como gerenciador de dependências e build.
- **Classes Principais**:
  - `Main`: Contém a lógica principal do jogo, incluindo o fluxo de entrada e saída do usuário.
  - `Tabuleiro`: Representa o tabuleiro do Sudoku e contém métodos para manipulação e verificação de regras.
  - `Elemento`: Representa cada célula do tabuleiro, incluindo valores possíveis e restrições.

---

## Como Jogar

### Pré-requisitos

Certifique-se de ter o [Java](https://www.java.com/pt-BR/) e o [Maven](https://maven.apache.org/) instalados em sua máquina.

### Passo a Passo

1. **Clone o Repositório**:

   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd Sudoku-Java

2. **Compile o Projeto**:
    ```bash
    mvn clean compile

3. **Execute o Jogo**:
    ```bash
    mvn exec:java -Dexec.mainClass="br.ufjf.dcc.dcc025.Main"

