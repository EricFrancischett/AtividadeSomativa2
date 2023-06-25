public class Onibus extends Veiculo{

    private static final long serialVersionUID = 1L;

    private int assentos;
    @Override
    public String utilidade() {
        return "Útil para transporte de muitas pessoas";
    }

    public Onibus(String marca, String modelo, int ano, int quilometragem, String placa, int assentos) {
        super(marca, modelo, ano, quilometragem, placa);
        this.tipo = "Onibus";
        this.assentos = assentos;
    }

    public String toString() {
        String retorno = super.toString();
        retorno += "Nº de assentos: "+ this.assentos + "\n";
        return retorno;
    }
}
