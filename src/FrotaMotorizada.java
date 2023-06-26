import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class FrotaMotorizada {
    private ArrayList<Veiculo> veiculos;

    public FrotaMotorizada() {
        this.veiculos = new ArrayList<Veiculo>();
    }
    public String[] leValores (String [] dadosIn){
        String [] dadosOut = new String [dadosIn.length];
        for (int i = 0; i < dadosIn.length; i++)
            dadosOut[i] = JOptionPane.showInputDialog  ("Entre com " + dadosIn[i]+ ": ");
        return dadosOut;
    }

    public Automovel leAutomovel (){

        String [] valores = new String [6];
        String [] nomeVal = {"Marca", "Modelo", "Ano", "Quilometragem", "Placa", "Motorização"};
        valores = leValores (nomeVal);

        int ano = this.retornaInteiro(valores[2]);
        int quilometragem = this.retornaInteiro(valores[3]);

        Automovel automovel = new Automovel (valores[0],valores[1],ano,quilometragem,valores[4],valores[5]);
        return automovel;
    }

    public Caminhao leCaminhao (){

        String [] valores = new String [6];
        String [] nomeVal = {"Marca", "Modelo", "Ano", "Quilometragem", "Placa", "Carga Máxima"};
        valores = leValores (nomeVal);

        int ano = this.retornaInteiro(valores[2]);
        int quilometragem = this.retornaInteiro(valores[3]);
        int cargaMaxima = this.retornaInteiro(valores[5]);

        Caminhao caminhao = new Caminhao (valores[0],valores[1],ano,quilometragem,valores[4],cargaMaxima);
        return caminhao;
    }
    public Onibus leOnibus (){

        String [] valores = new String [6];
        String [] nomeVal = {"Marca", "Modelo", "Ano", "Quilometragem", "Placa", "Assentos"};
        valores = leValores (nomeVal);

        int ano = this.retornaInteiro(valores[2]);
        int quilometragem = this.retornaInteiro(valores[3]);
        int assentos = this.retornaInteiro(valores[5]);

        Onibus onibus = new Onibus (valores[0],valores[1],ano,quilometragem,valores[4],assentos);
        return onibus;
    }

    private boolean intValido(String s) {
        try {
            Integer.parseInt(s); // Método estático, que tenta tranformar uma string em inteiro
            return true;
        } catch (NumberFormatException e) { // Não conseguiu tranformar em inteiro e gera erro
            return false;
        }
    }
    public int retornaInteiro(String entrada) { // retorna um valor inteiro
        int numInt;
        //Enquanto não for possível converter o valor de entrada para inteiro, permanece no loop
        while (!this.intValido(entrada)) {
            entrada = JOptionPane.showInputDialog(null, "Valor incorreto!\n\nDigite um número inteiro.");
        }
        return Integer.parseInt(entrada);
    }

    public void salvaVeiculos (ArrayList<Veiculo> veiculos){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream
                    (new FileOutputStream("c:\\temp\\veiculos.dados"));
            for (int i=0; i < veiculos.size(); i++)
                outputStream.writeObject(veiculos.get(i));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Impossível criar arquivo!");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {  //Close the ObjectOutputStream
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @SuppressWarnings("finally")
    public ArrayList<Veiculo> recuperaVeiculos (){
        ArrayList<Veiculo> veiculosTemp = new ArrayList<Veiculo>();

        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream
                    (new FileInputStream("c:\\temp\\veiculos.dados"));
            Object obj = null;
            while ((obj = inputStream.readObject()) != null) {
                if (obj instanceof Veiculo) {
                    veiculosTemp.add((Veiculo) obj);
                }
            }
        } catch (EOFException ex) { // when EOF is reached
            System.out.println("Fim de arquivo.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Arquivo com veículos NÃO existe!");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {  //Close the ObjectInputStream
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
            return veiculosTemp;
        }
    }

    public void menuFrotaMotorizada (){

        String menu = "";
        String entrada;
        int    opc1, opc2;

        do {
            menu = "Controle Frota Motorizada\n" +
                    "Opções:\n" +
                    "1. Entrar Veículos\n" +
                    "2. Exibir Veículos\n" +
                    "3. Limpar Veículos\n" +
                    "4. Gravar Veículos\n" +
                    "5. Recuperar Veículos\n" +
                    "9. Sair";
            entrada = JOptionPane.showInputDialog (menu + "\n\n");
            opc1 = this.retornaInteiro(entrada);

            switch (opc1) {
                case 1:// Entrar dados
                    menu = "Entrada de Veículos\n" +
                            "Opções:\n" +
                            "1. Automóvel\n" +
                            "2. Ônibus\n" +
                            "3. Caminhão\n";

                    entrada = JOptionPane.showInputDialog (menu + "\n\n");
                    opc2 = this.retornaInteiro(entrada);

                    switch (opc2){
                        case 1: veiculos.add((Veiculo) leAutomovel());
                            break;
                        case 2: veiculos.add((Veiculo)leOnibus());
                            break;
                        case 3: veiculos.add((Veiculo)leCaminhao());
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"Veículos para entrada NÃO escolhido!");
                    }

                    break;
                case 2: // Exibir dados
                    if (veiculos.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Entre com veículos primeiramente");
                        break;
                    }
                    String dados = "";
                    for (int i=0; i < veiculos.size(); i++)	{
                        dados += veiculos.get(i).toString() + "---------------\n";
                    }
                    JOptionPane.showMessageDialog(null,dados);
                    break;
                case 3: // Limpar Dados
                    if (veiculos.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Entre com veículos primeiramente");
                        break;
                    }
                    veiculos.clear();
                    JOptionPane.showMessageDialog(null,"Dados LIMPOS com sucesso!");
                    break;
                case 4: // Grava Dados
                    if (veiculos.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Entre com veículos primeiramente");
                        break;
                    }
                    salvaVeiculos(veiculos);
                    JOptionPane.showMessageDialog(null,"Dados SALVOS com sucesso!");
                    break;
                case 5: // Recupera Dados
                    veiculos = recuperaVeiculos();
                    if (veiculos.size() == 0) {
                        JOptionPane.showMessageDialog(null,"Sem dados para apresentar.");
                        break;
                    }
                    JOptionPane.showMessageDialog(null,"Dados RECUPERADOS com sucesso!");
                    break;
                case 9:
                    JOptionPane.showMessageDialog(null,"Fim do aplicativo Frota Motorizada");
                    break;
            }
        } while (opc1 != 9);
    }


    public static void main (String [] args){

        FrotaMotorizada frota = new FrotaMotorizada ();
        frota.menuFrotaMotorizada();
    }
}

