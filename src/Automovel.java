public class Automovel extends Veiculo{

    private static final long serialVersionUID = 1L;

    private String motorizacao;
    @Override
    public String utilidade() {
        return "Útil para transporte de poucas pessoas";
    }

    public Automovel(String marca, String modelo, int ano, int quilometragem, String placa, String motorizacao) {
        super(marca, modelo, ano, quilometragem, placa);
        this.tipo = "Automóvel";
        this.motorizacao = motorizacao;
    }

    public String toString() {
        String retorno = super.toString();
        retorno += "Motorização: Motor"+ this.motorizacao + "\n";
        return retorno;
    }
}
