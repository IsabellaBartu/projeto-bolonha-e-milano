package bolonhaemilano;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in, "UTF-8");
    private boolean emExecucao = true;
    private final Agricultura agricultura = new Agricultura();
    private final Mercado mercado = new Mercado();
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
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Sobre o jogo");
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
                abrirMercado();
                break;
            case 4:
                abrirAgricultura();
                break;
            case 5:
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

    private int lerQuantidade(String pergunta) {
        System.out.print(pergunta);
        if (!scanner.hasNextLine()) {
            return -1;
        }
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException erro) {
            return -1;
        }
    }

    private void informarResultado(boolean sucesso, String mensagem) {
        System.out.println(sucesso ? mensagem : "Operação recusada. Confira a quantidade e os recursos.");
    }

    private void abrirMercado() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento.");
            return;
        }
        while (true) {
            System.out.printf("%n--- Mercado | %d florins ---%n", cidade.getFlorins());
            System.out.printf("1 - Comprar grãos (%d florins cada)%n", mercado.getPrecoGrao());
            System.out.println("2 - Vender grãos");
            System.out.printf("3 - Comprar terras (%d florim por hectare)%n", mercado.getPrecoTerra());
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            switch (lerOpcao()) {
                case 1:
                    informarResultado(mercado.comprarGraos(cidade, lerQuantidade("Grãos: ")), "Grãos comprados.");
                    break;
                case 2:
                    informarResultado(mercado.venderGraos(cidade, lerQuantidade("Grãos: ")), "Grãos vendidos.");
                    break;
                case 3:
                    informarResultado(mercado.comprarTerras(cidade, lerQuantidade("Hectares: ")), "Terras compradas.");
                    break;
                case 4:
                    informarResultado(mercado.venderTerras(cidade, lerQuantidade("Hectares: ")), "Terras vendidas.");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void abrirAgricultura() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento.");
            return;
        }
        while (true) {
            System.out.printf("%n--- Agricultura | %d ha | %d servos | %d grãos ---%n",
                    cidade.getTerras(), cidade.getServos(), cidade.getGraos());
            System.out.println("1 - Colher");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            switch (lerOpcao()) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void colher() {
        int hectares = agricultura.processarColheita(cidade);
        System.out.printf("Colheita: %d hectares cultivados. Estoque: %d grãos.%n", hectares, cidade.getGraos());
    }

    private void distribuirAlimentos() {
        int servosAntes = cidade.getServos();
        int percentual = lerQuantidade("Porcentagem do estoque (20 a 80): ");
        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.printf("Alimentos distribuídos. Servos: %d → %d.%n", servosAntes, cidade.getServos());
        } else {
            System.out.println("Use uma porcentagem inteira de 20 a 80.");
        }
    }

    private void exibirSobre() {
        System.out.println("Governe uma cidade medieval e dispute títulos de nobreza.");
    }
}
