package bolonhaemilano;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in, "UTF-8");
    private boolean emExecucao = true;

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
        System.out.println("2 - Sobre o jogo");
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
        System.out.println("Novo jogo iniciado!");
    }

    private void exibirSobre() {
        System.out.println("Governe uma cidade medieval e dispute títulos de nobreza.");
    }
}
