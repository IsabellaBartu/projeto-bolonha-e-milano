package bolonhaemilano;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in, "UTF-8");
    private boolean emExecucao = true;
    private static final String[] CIDADES_PADRAO = {"Bolonha", "Milano", "Florença", "Veneza"};
    private Partida partida;
    private final Nobreza nobreza = new Nobreza();
    private final Construcoes construcoes = new Construcoes();
    private final Governo governo = new Governo();
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
        if (cidade != null) {
            System.out.printf("Ano %d | Vez de %s (%s), de %s%n", partida.getAnoAtual(),
                    cidade.getDono().getNome(), cidade.getDono().getTitulo(), cidade.getNome());
        }
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construções");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificação");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
    }

    private int lerOpcao() {
        System.out.print("Escolha uma opção: ");
        if (!scanner.hasNextLine()) {
            return 0;
        }
        return lerInteiro();
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
                abrirGoverno();
                break;
            case 6:
                abrirConstrucoes();
                break;
            case 7:
                encerrarTurno();
                break;
            case 8:
                exibirClassificacao();
                break;
            case 9:
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
        int quantidade = lerQuantidade("Quantos jogadores (1 a 4)? ");
        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) {
            System.out.println("Quantidade inválida. A partida terá 2 jogadores.");
            quantidade = 2;
        }
        partida = new Partida();
        for (int i = 0; i < quantidade; i++) {
            String nome = lerTexto("Nome do jogador " + (i + 1) + ": ", "Jogador " + (i + 1));
            String padrao = CIDADES_PADRAO[i];
            String nomeCidade = lerTexto("Nome da cidade de " + nome + " (Enter = " + padrao + "): ", padrao);
            partida.adicionarCidade(new Cidade(nomeCidade, new Jogador(nome)));
        }
        cidade = partida.getCidadeAtual();
        System.out.printf("Novo jogo iniciado! Ano %d. A partida vai até o fim de %d ou até alguém virar Rei/Rainha.%n",
                partida.getAnoAtual(), partida.getAnoFinal());
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
        if (!exigirJogoEmAndamento()) {
            return;
        }
        cidade.exibirStatus();
    }

    private int lerQuantidade(String pergunta) {
        System.out.print(pergunta);
        return lerInteiro();
    }

    private int lerInteiro() {
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
        if (!exigirJogoEmAndamento()) {
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
        if (!exigirJogoEmAndamento()) {
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
        if (hectares == -1) {
            System.out.println("A colheita deste ano já foi feita.");
            return;
        }
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

    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        while (true) {
            System.out.printf("%n--- Governo | taxa %d%% | justiça %s ---%n",
                    cidade.getTaxaImposto(), cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nível de justiça");
            System.out.println("0 - Voltar");
            switch (lerOpcao()) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    int taxa = lerQuantidade("Taxa (0 a 50): ");
                    if (governo.definirTaxaImposto(cidade, taxa)) {
                        System.out.println("Taxa definida.");
                    } else {
                        System.out.println("Use uma taxa inteira entre 0 e 50.");
                    }
                    break;
                case 3:
                    escolherJustica();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void arrecadarImpostos() {
        int florins = governo.arrecadarImpostos(cidade);
        if (florins == -1) {
            System.out.println("Os impostos deste ano já foram arrecadados.");
            return;
        }
        System.out.println("Florins arrecadados: " + florins);
    }

    private void escolherJustica() {
        System.out.println("1 - Justa | 2 - Moderada | 3 - Severa");
        String[] niveis = {"Justa", "Moderada", "Severa"};
        int opcao = lerOpcao();
        if (opcao < 1 || opcao > niveis.length) {
            System.out.println("Nível de justiça inválido.");
            return;
        }
        governo.definirJustica(cidade, niveis[opcao - 1]);
        System.out.println("Justiça definida como " + cidade.getNivelJustica() + ".");
    }

    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        while (true) {
            System.out.printf("%n--- Construções | %d florins | %d servos ---%n",
                    cidade.getFlorins(), cidade.getServos());
            System.out.printf("1 - Recrutar soldados (%d florins e 1 servo cada)%n", construcoes.getPrecoSoldado());
            System.out.printf("2 - Construir (%d florins cada)%n", construcoes.getPrecoConstrucao());
            System.out.println("0 - Voltar");
            switch (lerOpcao()) {
                case 1:
                    informarResultado(construcoes.recrutarSoldados(cidade, lerQuantidade("Soldados: ")),
                            "Soldados recrutados.");
                    break;
                case 2:
                    informarResultado(construcoes.construir(cidade, lerQuantidade("Construções: ")),
                            "Construções concluídas.");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        int ano = partida.getAnoAtual();
        System.out.println("Turno de " + cidade.getDono().getNome() + " encerrado.");
        if (partida.passarTurno()) {
            System.out.println("=== FIM DO ANO " + ano + " ===");
            for (Cidade promovida : partida.encerrarAno(nobreza)) {
                System.out.printf("%s, de %s, agora é %s!%n", promovida.getDono().getNome(),
                        promovida.getNome(), promovida.getDono().getTitulo());
            }
            exibirClassificacao();
            if (partida.terminou()) {
                encerrarPartida();
                return;
            }
            System.out.println("Começa o ano " + partida.getAnoAtual() + ".");
        }
        cidade = partida.getCidadeAtual();
        System.out.printf("Agora é a vez de %s (%s), de %s.%n",
                cidade.getDono().getNome(), cidade.getDono().getTitulo(), cidade.getNome());
    }

    private void exibirClassificacao() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        System.out.println("=== CLASSIFICAÇÃO ===");
        for (Cidade participante : partida.classificar(nobreza)) {
            Jogador dono = participante.getDono();
            int prestigio = nobreza.calcularPrestigio(participante);
            int meta = nobreza.prestigioParaProximoTitulo(dono);
            System.out.printf("%s | %s (%s) | prestígio %d", participante.getNome(),
                    dono.getNome(), dono.getTitulo(), prestigio);
            if (meta != -1) {
                System.out.printf(" | próximo título: %d (faltam %d)", meta, Math.max(0, meta - prestigio));
            }
            System.out.println();
        }
    }

    private void encerrarPartida() {
        Cidade campea = partida.determinarVencedor(nobreza);
        System.out.printf("FIM DE JOGO. Vencedor: %s (%s), de %s!%n",
                campea.getDono().getNome(), campea.getDono().getTitulo(), campea.getNome());
        partida = null;
        cidade = null;
    }

    private boolean exigirJogoEmAndamento() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento.");
            return false;
        }
        return true;
    }

    private void exibirSobre() {
        System.out.println("Governe uma cidade medieval e dispute títulos de nobreza.");
        System.out.println("De 1 a 4 jogadores no mesmo computador. Colha e arrecade uma vez por ano.");
        System.out.println("Investimentos, população e terras aumentam o prestígio da cidade.");
        System.out.println("A partida acaba ao fim de 1410 ou quando alguém vira Rei/Rainha.");
    }
}
