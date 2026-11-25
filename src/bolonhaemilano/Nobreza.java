package bolonhaemilano;

public class Nobreza {
    private static final int PESO_CONSTRUCAO = 10;
    private static final int PESO_SOLDADO = 2;
    private static final int PESO_SERVO = 1;
    private static final int HECTARES_POR_PONTO = 100;
    private static final int[] PRESTIGIO_MINIMO = {0, 400, 550, 700, 850, 1000};

    public int calcularPrestigio(Cidade cidade) {
        long total = (long) cidade.getConstrucoes() * PESO_CONSTRUCAO
                + (long) cidade.getSoldados() * PESO_SOLDADO
                + (long) cidade.getServos() * PESO_SERVO
                + cidade.getTerras() / HECTARES_POR_PONTO;
        return (int) Math.min(total, Integer.MAX_VALUE);
    }

    public int prestigioParaProximoTitulo(Jogador jogador) {
        if (jogador.ehMonarca()) {
            return -1;
        }
        return PRESTIGIO_MINIMO[jogador.getNivelTitulo() + 1];
    }

    public boolean verificarPromocao(Cidade cidade) {
        Jogador dono = cidade.getDono();
        int meta = prestigioParaProximoTitulo(dono);
        return meta != -1 && calcularPrestigio(cidade) >= meta && dono.promover();
    }
}
