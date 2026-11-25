package bolonhaemilano;

public class Construcoes {
    private static final int PRECO_SOLDADO = 5;
    private static final int SERVOS_POR_SOLDADO = 1;
    private static final int PRECO_CONSTRUCAO = 20;

    public int getPrecoSoldado() {
        return PRECO_SOLDADO;
    }

    public int getPrecoConstrucao() {
        return PRECO_CONSTRUCAO;
    }

    public boolean recrutarSoldados(Cidade cidade, int quantidade) {
        long custo = (long) quantidade * PRECO_SOLDADO;
        long servosNecessarios = (long) quantidade * SERVOS_POR_SOLDADO;
        if (quantidade <= 0 || custo > cidade.getFlorins() || servosNecessarios > cidade.getServos()
                || quantidade > Integer.MAX_VALUE - cidade.getSoldados()) {
            return false;
        }
        cidade.removerFlorins((int) custo);
        cidade.removerServos((int) servosNecessarios);
        cidade.adicionarSoldados(quantidade);
        return true;
    }

    public boolean construir(Cidade cidade, int quantidade) {
        long custo = (long) quantidade * PRECO_CONSTRUCAO;
        if (quantidade <= 0 || custo > cidade.getFlorins()
                || quantidade > Integer.MAX_VALUE - cidade.getConstrucoes()) {
            return false;
        }
        cidade.removerFlorins((int) custo);
        cidade.adicionarConstrucoes(quantidade);
        return true;
    }
}
