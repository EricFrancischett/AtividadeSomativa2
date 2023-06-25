public class Caminhao extends Veiculo {

    private static final long serialVersionUID = 1L;

    private int cargaMaxima;

    @Override
    public String utilidade() {
        return "Útil para transporte de muitas pessoas";
    }

    public Caminhao(String marca, String modelo, int ano, int quilometragem, String placa, int cargaMaxima) {
        super(marca, modelo, ano, quilometragem, placa);
        this.tipo = "Caminhão";
        this.cargaMaxima = cargaMaxima;
    }

    public String toString() {
        String retorno = super.toString();
        retorno += "Carga Máxima: "+ this.cargaMaxima + "toneladas\n";
        return retorno;
    }
}
