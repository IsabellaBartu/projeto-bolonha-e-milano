package bolonhaemilano;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Partida {
    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;
    private final List<Cidade> cidades = new ArrayList<>();
    private int indiceAtual;

    public boolean adicionarCidade(Cidade cidade) {
        if (cidade == null || cidades.size() >= MAXIMO_JOGADORES) {
            return false;
        }
        cidades.add(cidade);
        return true;
    }

    public List<Cidade> getCidades() {
        return Collections.unmodifiableList(cidades);
    }

    public Cidade getCidadeAtual() {
        return cidades.isEmpty() ? null : cidades.get(indiceAtual);
    }

    public int getAnoAtual() {
        return cidades.isEmpty() ? 0 : cidades.get(0).getAnoAtual();
    }

    public int getAnoFinal() {
        return ANO_FINAL;
    }

    public boolean passarTurno() {
        if (cidades.isEmpty()) {
            return false;
        }
        indiceAtual++;
        if (indiceAtual < cidades.size()) {
            return false;
        }
        indiceAtual = 0;
        return true;
    }

    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidade.avancarAno();
            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }
        return promovidas;
    }

    public boolean terminou() {
        if (getAnoAtual() > ANO_FINAL) {
            return true;
        }
        for (Cidade cidade : cidades) {
            if (cidade.getDono().ehMonarca()) {
                return true;
            }
        }
        return false;
    }

    public List<Cidade> classificar(Nobreza nobreza) {
        List<Cidade> classificacao = new ArrayList<>(cidades);
        classificacao.sort(Comparator
                .comparingInt((Cidade cidade) -> cidade.getDono().getNivelTitulo())
                .thenComparingInt(nobreza::calcularPrestigio)
                .reversed());
        return classificacao;
    }

    public Cidade determinarVencedor(Nobreza nobreza) {
        List<Cidade> classificacao = classificar(nobreza);
        return classificacao.isEmpty() ? null : classificacao.get(0);
    }
}
