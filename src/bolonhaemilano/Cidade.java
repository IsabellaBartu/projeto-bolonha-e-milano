package bolonhaemilano;

public class Cidade {
    private final String nome;
    private final Jogador dono;
    private int anoAtual = 1400;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
    }

    public String getNome() {
        return nome;
    }

    public Jogador getDono() {
        return dono;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public void avancarAno() {
        anoAtual++;
    }

    public void exibirStatus() {
        System.out.println("Cidade: " + nome);
        System.out.printf("Governante: %s (%s)%n", dono.getNome(), dono.getTitulo());
        System.out.println("Ano: " + anoAtual);
    }
}
