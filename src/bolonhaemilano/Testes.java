package bolonhaemilano;

public class Testes {
    private static int aprovados;
    private static int reprovados;

    public static void main(String[] args) {
        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();
        System.out.printf("Aprovados: %d | Reprovados: %d%n", aprovados, reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK] " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static void testarCidade() {
        Cidade cidade = novaCidade();
        verificar("Cidade: ano e estoques iniciais", cidade.getAnoAtual() == 1400
                && cidade.getFlorins() == 1000 && cidade.getGraos() == 500 && cidade.getTerras() == 10000);
        verificar("Cidade: população, construções e defesa iniciais",
                cidade.getServos() == 200 && cidade.getSoldados() == 0 && cidade.getConstrucoes() == 0);
        verificar("Cidade: imposto de 10% e justiça moderada",
                cidade.getTaxaImposto() == 10 && cidade.getNivelJustica().equals("Moderada"));
        boolean retirou = cidade.removerFlorins(1001);
        verificar("Cidade: retirada acima do saldo preserva o cofre", !retirou && cidade.getFlorins() == 1000);

        cidade.marcarColheitaFeita();
        cidade.marcarImpostosArrecadados();
        cidade.avancarAno();
        verificar("Cidade: avançar ano libera colheita e impostos", cidade.getAnoAtual() == 1401
                && !cidade.isColheitaFeita() && !cidade.isImpostosArrecadados());
    }

    private static void testarMercado() {
        Cidade cidade = novaCidade();
        Mercado mercado = new Mercado();
        boolean comprou = mercado.comprarGraos(cidade, 100);
        verificar("Mercado: compra 100 grãos por 200 florins",
                comprou && cidade.getGraos() == 600 && cidade.getFlorins() == 800);
        boolean vendeu = mercado.venderGraos(cidade, 100);
        verificar("Mercado: vende 100 grãos por 200 florins",
                vendeu && cidade.getGraos() == 500 && cidade.getFlorins() == 1000);
        boolean comprouTerras = mercado.comprarTerras(cidade, 150);
        verificar("Mercado: compra 150 ha por 150 florins",
                comprouTerras && cidade.getTerras() == 10150 && cidade.getFlorins() == 850);
        boolean vendeuTerras = mercado.venderTerras(cidade, 150);
        verificar("Mercado: vende 150 ha por 150 florins",
                vendeuTerras && cidade.getTerras() == 10000 && cidade.getFlorins() == 1000);
        boolean comprouNegativo = mercado.comprarGraos(cidade, -1);
        verificar("Mercado: quantidade negativa é recusada", !comprouNegativo && cidade.getGraos() == 500);
        boolean comprouSemSaldo = mercado.comprarGraos(cidade, Integer.MAX_VALUE);
        verificar("Mercado: compra caríssima preserva saldo e estoque",
                !comprouSemSaldo && cidade.getFlorins() == 1000 && cidade.getGraos() == 500);
        boolean vendeuSemEstoque = mercado.venderGraos(cidade, 501);
        verificar("Mercado: venda sem estoque preserva saldo e estoque",
                !vendeuSemEstoque && cidade.getFlorins() == 1000 && cidade.getGraos() == 500);
    }

    private static void testarAgricultura() {
        Cidade cidade = novaCidade();
        Agricultura agricultura = new Agricultura();
        int hectares = agricultura.processarColheita(cidade);
        verificar("Agricultura: cultiva 1000 ha e termina com 2000 grãos",
                hectares == 1000 && cidade.getGraos() == 2000);
        int repeticao = agricultura.processarColheita(cidade);
        verificar("Agricultura: segunda colheita no ano é recusada", repeticao == -1 && cidade.getGraos() == 2000);
        boolean distribuiu = agricultura.distribuirAlimentos(cidade, 50);
        verificar("Agricultura: alimentação suficiente aumenta população em 5%",
                distribuiu && cidade.getServos() == 210 && cidade.getGraos() == 1000);
        boolean distribuiuInvalido = agricultura.distribuirAlimentos(cidade, 19);
        verificar("Agricultura: rejeita porcentagem abaixo de 20",
                !distribuiuInvalido && cidade.getGraos() == 1000 && cidade.getServos() == 210);
        Cidade escassez = novaCidade();
        escassez.removerGraos(490);
        int hectaresComEscassez = agricultura.processarColheita(escassez);
        verificar("Agricultura: 10 sementes limitam a colheita a 20 ha",
                hectaresComEscassez == 20 && escassez.getGraos() == 40);
        boolean alimentou = agricultura.distribuirAlimentos(escassez, 50);
        verificar("Agricultura: déficit de 20 grãos reduz população em 20",
                alimentou && escassez.getServos() == 180);
    }

    private static void testarGoverno() {
        Cidade cidade = novaCidade();
        Governo governo = new Governo();
        boolean mudouJustica = governo.definirJustica(cidade, "Severa");
        verificar("Governo: aceita o nível Severa", mudouJustica && cidade.getNivelJustica().equals("Severa"));
        int arrecadado = governo.arrecadarImpostos(cidade);
        verificar("Governo: justiça severa com taxa de 10% arrecada 26",
                arrecadado == 26 && cidade.getFlorins() == 1026);
        int repeticao = governo.arrecadarImpostos(cidade);
        verificar("Governo: segunda arrecadação no ano é recusada", repeticao == -1 && cidade.getFlorins() == 1026);
        boolean alterouTaxa = governo.definirTaxaImposto(cidade, 60);
        verificar("Governo: taxa de 60% é recusada", !alterouTaxa && cidade.getTaxaImposto() == 10);
        boolean alterouJustica = governo.definirJustica(cidade, "Abusiva");
        verificar("Governo: nível desconhecido é recusado", !alterouJustica && cidade.getNivelJustica().equals("Severa"));
    }

    private static void testarConstrucoes() {
        Cidade cidade = novaCidade();
        Construcoes construcoes = new Construcoes();
        boolean construiu = construcoes.construir(cidade, 10);
        verificar("Construções: dez obras custam 200 florins",
                construiu && cidade.getConstrucoes() == 10 && cidade.getFlorins() == 800);
        cidade.removerServos(190);
        boolean recrutouSemServos = construcoes.recrutarSoldados(cidade, 11);
        verificar("Construções: recrutamento sem servos preserva os recursos",
                !recrutouSemServos && cidade.getServos() == 10 && cidade.getFlorins() == 800 && cidade.getSoldados() == 0);
        boolean recrutou = construcoes.recrutarSoldados(cidade, 10);
        verificar("Construções: dez soldados custam 50 florins e dez servos",
                recrutou && cidade.getServos() == 0 && cidade.getFlorins() == 750 && cidade.getSoldados() == 10);
        boolean construiuSemFlorins = construcoes.construir(cidade, 40);
        verificar("Construções: obra sem saldo é recusada",
                !construiuSemFlorins && cidade.getConstrucoes() == 10 && cidade.getFlorins() == 750);
    }

    private static void testarJogadorENobreza() {
        Cidade cidade = novaCidade();
        Nobreza nobreza = new Nobreza();
        Jogador dono = cidade.getDono();
        verificar("Jogador: começa como Cavaleiro", dono.getNivelTitulo() == 0 && dono.getTitulo().equals("Cavaleiro"));
        int inicial = nobreza.calcularPrestigio(cidade);
        boolean promoveuSemPrestigio = nobreza.verificarPromocao(cidade);
        verificar("Nobreza: prestígio inicial 300 não promove", inicial == 300 && !promoveuSemPrestigio);
        cidade.adicionarConstrucoes(50);
        boolean promoveu = nobreza.verificarPromocao(cidade);
        verificar("Nobreza: mesmo com 800 pontos, promove só até Barão por vez",
                promoveu && dono.getNivelTitulo() == 1 && dono.getTitulo().equals("Barão"));
        for (int i = 0; i < 4; i++) {
            dono.promover();
        }
        boolean ultrapassou = dono.promover();
        verificar("Jogador: não ultrapassa Rei/Rainha",
                dono.ehMonarca() && !ultrapassou && nobreza.prestigioParaProximoTitulo(dono) == -1);
    }

    private static void testarPartida() {
        Partida partida = new Partida();
        Nobreza nobreza = new Nobreza();
        Cidade ana = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade beto = new Cidade("Milano", new Jogador("Beto"));
        partida.adicionarCidade(ana);
        partida.adicionarCidade(beto);
        boolean primeiro = partida.passarTurno();
        verificar("Partida: primeiro turno passa para Beto no mesmo ano",
                !primeiro && partida.getCidadeAtual() == beto && partida.getAnoAtual() == 1400);
        boolean segundo = partida.passarTurno();
        verificar("Partida: segundo turno fecha o ano e devolve a vez a Ana", segundo && partida.getCidadeAtual() == ana);

        beto.adicionarConstrucoes(10);
        verificar("Partida: desempata títulos pelo prestígio", partida.determinarVencedor(nobreza) == beto);
        partida.encerrarAno(nobreza);
        verificar("Partida: avança o ano das duas cidades e promove Beto", ana.getAnoAtual() == 1401
                && beto.getAnoAtual() == 1401 && beto.getDono().getTitulo().equals("Barão"));
        ana.adicionarConstrucoes(20);
        verificar("Partida: título tem prioridade sobre prestígio", partida.determinarVencedor(nobreza) == beto);

        for (int ano = 1401; ano < 1410; ano++) {
            partida.encerrarAno(nobreza);
        }
        verificar("Partida: 1410 ainda pode ser jogado", !partida.terminou());
        partida.encerrarAno(nobreza);
        verificar("Partida: jogo acaba depois de 1410", partida.terminou());

        Partida outra = new Partida();
        for (int i = 0; i < Partida.MAXIMO_JOGADORES; i++) {
            outra.adicionarCidade(novaCidade());
        }
        boolean aceitouQuinta = outra.adicionarCidade(novaCidade());
        verificar("Partida: recusa a quinta cidade", !aceitouQuinta && outra.getCidades().size() == 4);
        for (int i = 0; i < 5; i++) {
            outra.getCidadeAtual().getDono().promover();
        }
        verificar("Partida: monarca encerra o jogo antes de 1410", outra.terminou() && outra.getAnoAtual() == 1400);
    }
}
