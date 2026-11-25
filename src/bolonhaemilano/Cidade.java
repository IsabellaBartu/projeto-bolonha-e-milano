package bolonhaemilano;

public class Cidade {
    private final String nome;
    private final Jogador dono;
    private int anoAtual = 1400;
    private int servos = 200;
    private int florins = 1000;
    private int terras = 10000;
    private int graos = 500;

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

    public int getGraos() {
        return graos;
    }

    public void adicionarGraos(int quantidade) {
        if (quantidade > 0) {
            graos = Math.addExact(graos, quantidade);
        }
    }

    public boolean removerGraos(int quantidade) {
        if (quantidade < 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    public int getTerras() {
        return terras;
    }

    public void adicionarTerras(int quantidade) {
        if (quantidade > 0) {
            terras = Math.addExact(terras, quantidade);
        }
    }

    public boolean removerTerras(int quantidade) {
        if (quantidade < 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    public int getFlorins() {
        return florins;
    }

    public void adicionarFlorins(int quantidade) {
        if (quantidade > 0) {
            florins = Math.addExact(florins, quantidade);
        }
    }

    public boolean removerFlorins(int quantidade) {
        if (quantidade < 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    public int getServos() {
        return servos;
    }

    public void adicionarServos(int quantidade) {
        if (quantidade > 0) {
            servos = Math.addExact(servos, quantidade);
        }
    }

    public boolean removerServos(int quantidade) {
        if (quantidade < 0 || quantidade > servos) {
            return false;
        }
        servos -= quantidade;
        return true;
    }

    public void exibirStatus() {
        System.out.println("Cidade: " + nome);
        System.out.printf("Governante: %s (%s)%n", dono.getNome(), dono.getTitulo());
        System.out.println("Ano: " + anoAtual);
        System.out.println("Servos: " + servos);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Grãos em estoque: " + graos);
    }
}
