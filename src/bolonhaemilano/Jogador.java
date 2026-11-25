package bolonhaemilano;

public class Jogador {
    private final String nome;
    private final String titulo = "Cavaleiro";

    public Jogador(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }
}
