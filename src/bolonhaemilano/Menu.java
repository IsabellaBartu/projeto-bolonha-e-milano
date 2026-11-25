package bolonhaemilano;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in, "UTF-8");
    private boolean emExecucao = true;
    private Cidade cidade;

    public void executar() {
        try (Scanner entrada = scanner) {
            while (emExecucao) {
                exibirOpcoes();
                processarOpcao(lerOpcao());
            }
        }
    }

    private void exibirOpcoes() {
        System.out.println("\n=== Bolonha & Milano ===");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Sobre o jogo");
        System.out.println("0 - Sair");
    }

    private int lerOpcao() {
        System.out.print("Escolha uma opção: ");
        if (!scanner.hasNextLine()) {
            return 0;
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException erro) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                exibirSobre();
                break;
            case 0:
                emExecucao = false;
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void iniciarNovoJogo() {
        String nomeJogador = lerTexto("Nome do jogador: ", "Jogador");
        String nomeCidade = lerTexto("Nome da cidade: ", "Bolonha");
        cidade = new Cidade(nomeCidade, new Jogador(nomeJogador));
        exibirStatus();
    }

    private String lerTexto(String pergunta, String padrao) {
        System.out.print(pergunta);
        if (!scanner.hasNextLine()) {
            return padrao;
        }
        String texto = scanner.nextLine().trim();
        return texto.isEmpty() ? padrao : texto;
    }

    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento.");
            return;
        }
        cidade.exibirStatus();
    }

    private void exibirSobre() {
        System.out.println("Governe uma cidade medieval e dispute títulos de nobreza.");
    }
}
