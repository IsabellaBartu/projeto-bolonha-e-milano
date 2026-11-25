package bolonhaemilano;

public class Mercado {
    private static final int PRECO_GRAO = 2;
    private static final int PRECO_TERRA = 1;

    public int getPrecoGrao() {
        return PRECO_GRAO;
    }

    public int getPrecoTerra() {
        return PRECO_TERRA;
    }

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        long custo = (long) quantidade * PRECO_GRAO;
        if (quantidade <= 0 || custo > cidade.getFlorins()
                || quantidade > Integer.MAX_VALUE - cidade.getGraos()) {
            return false;
        }
        cidade.removerFlorins((int) custo);
        cidade.adicionarGraos(quantidade);
        return true;
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        long receita = (long) quantidade * PRECO_GRAO;
        if (quantidade <= 0 || receita > Integer.MAX_VALUE - cidade.getFlorins()
                || !cidade.removerGraos(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins((int) receita);
        return true;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        long custo = (long) quantidade * PRECO_TERRA;
        if (quantidade <= 0 || custo > cidade.getFlorins()
                || quantidade > Integer.MAX_VALUE - cidade.getTerras()) {
            return false;
        }
        cidade.removerFlorins((int) custo);
        cidade.adicionarTerras(quantidade);
        return true;
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        long receita = (long) quantidade * PRECO_TERRA;
        if (quantidade <= 0 || receita > Integer.MAX_VALUE - cidade.getFlorins()
                || !cidade.removerTerras(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins((int) receita);
        return true;
    }
}
