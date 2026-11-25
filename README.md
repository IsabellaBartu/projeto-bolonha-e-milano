# Bolonha & Milano

Jogo de estratégia medieval em Java para 1 a 4 pessoas no mesmo computador. A partida começa em 1400. Cada jogador governa uma cidade e investe os recursos dela para conquistar títulos de nobreza.

## Executar

Use um **JDK 8 ou mais recente**. No Windows, extraia a pasta inteira e abra `iniciar.bat` para compilar e jogar. No terminal, a partir da raiz do projeto:

```sh
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
java -cp bin bolonhaemilano.Testes
```

No Windows, se o terminal não expandir `*.java`, use `iniciar.bat` para jogar e `iniciar.bat testes` para compilar e rodar os testes. No PowerShell, use `.\iniciar.bat testes`. A classe `Testes` roda sem bibliotecas externas e retorna código 1 se algum teste falhar.

## Jogar

| Opção | Ação |
| --- | --- |
| 1 | Iniciar um jogo e cadastrar de 1 a 4 governantes e suas cidades. |
| 2 | Consultar os recursos e o governo da cidade da vez. |
| 3 | Comprar ou vender grãos (2 florins por unidade) e terras (1 florim por hectare). |
| 4 | Colher uma vez por ano e distribuir de 20% a 80% do estoque de grãos à população. |
| 5 | Arrecadar uma vez por ano, definir a taxa (0% a 50%) e escolher o nível de justiça. |
| 6 | Recrutar soldados (5 florins e 1 servo cada) ou construir (20 florins por obra). |
| 7 | Encerrar o turno. A última pessoa da rodada fecha o ano. |
| 8 | Ver classificação e consultar a meta de prestígio do próximo título. |
| 9 | Ler o resumo do jogo. |
| 0 | Voltar nos submenus ou sair no menu principal. |

O prestígio é calculado por `construções × 10 + soldados × 2 + servos + terras ÷ 100`, com divisão inteira. Ao fechar o ano, `Partida.encerrarAno()` avança todas as cidades e chama `Nobreza.verificarPromocao()` para avaliar uma promoção por governante. A partida termina se alguém chegar a Rei/Rainha ou após o ano de 1410. O maior título vence. Entre títulos iguais, vale o maior prestígio. Em igualdade também no prestígio, prevalece a ordem do cadastro.

### Rodada de demonstração

1. No menu, escolha **1** e cadastre **Ana** e **Beto**. Pressione Enter para usar Bolonha e Milano.
2. Ana abre Agricultura com **4**, colhe com **1**, distribui 50% com **2** e volta com **0**. Em **5 → 3 → 3** escolhe a justiça Severa. Ainda em Governo, escolhe **1** para arrecadar. Volta com **0**, constrói 10 obras em **6 → 2** e volta com **0**.
3. Ana encerra com **7**. Beto pode colher e arrecadar para a cidade dele. Quando Beto escolhe **7**, o jogo exibe a promoção de Ana a Barão com **410 de prestígio** e começa 1401. A opção **8** permite mostrar a classificação de novo.

## Diagramas

- [Diagrama de classes](uml/classes.svg)
- [Diagrama de sequência do fim do ano](uml/sequencia-fim-do-ano.svg)
- [Diagrama de casos de uso](casos-de-uso/casos-de-uso.svg)
