package bolonhaemilano;

public class Governo {
    private static final int TAXA_MAXIMA = 50;
    private static final double MULTIPLICADOR_JUSTA = 0.8;
    private static final double MULTIPLICADOR_MODERADA = 1.0;
    private static final double MULTIPLICADOR_SEVERA = 1.3;

    public int arrecadarImpostos(Cidade cidade) {
        long base = (long) cidade.getServos() * cidade.getTaxaImposto() / 100;
        double multiplicador = multiplicadorPara(cidade.getNivelJustica());
        int arrecadado = (int) Math.min(base * multiplicador, Integer.MAX_VALUE - cidade.getFlorins());
        cidade.adicionarFlorins(arrecadado);
        return arrecadado;
    }

    public boolean definirTaxaImposto(Cidade cidade, int taxa) {
        if (taxa < 0 || taxa > TAXA_MAXIMA) {
            return false;
        }
        cidade.setTaxaImposto(taxa);
        return true;
    }

    public boolean definirJustica(Cidade cidade, String nivel) {
        if ("Justa".equalsIgnoreCase(nivel)) {
            cidade.setNivelJustica("Justa");
        } else if ("Moderada".equalsIgnoreCase(nivel)) {
            cidade.setNivelJustica("Moderada");
        } else if ("Severa".equalsIgnoreCase(nivel)) {
            cidade.setNivelJustica("Severa");
        } else {
            return false;
        }
        return true;
    }

    private double multiplicadorPara(String nivel) {
        if ("Justa".equals(nivel)) {
            return MULTIPLICADOR_JUSTA;
        }
        if ("Severa".equals(nivel)) {
            return MULTIPLICADOR_SEVERA;
        }
        return MULTIPLICADOR_MODERADA;
    }
}
