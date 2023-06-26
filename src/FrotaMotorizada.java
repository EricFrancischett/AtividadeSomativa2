import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JOptionPane;


public class FrotaMotorizada {
    private ArrayList<Veiculo> veiculos;

    public FrotaMotorizada() {
        this.veiculos = new ArrayList<Veiculo>();
    }
    public String dialog(Object message) {
        String userInput = "";
        Object msg = message;
            while (true) {
                userInput = JOptionPane.showInputDialog(msg);
               if (userInput.isEmpty()) {
                msg += "\n Resposta Vazia! \n";
            continue;
        }
        break;
            }
        return userInput;
    }

    public Object[] leValores (Object [] dadosIn, String[]... intValores){
    Object [] dadosOut = new Object [dadosIn.length]; // modify the output array to hold Objects
    String[] intValoresMerged = Stream.of(intValores).flatMap(Stream::of).toArray(String[]::new);
    for (int i = 0; i < dadosIn.length; i++){
        if (Arrays.asList(intValoresMerged).contains(dadosIn[i])){
            String entrada = dialog("Entre com " + dadosIn[i]+ ": ");
            dadosOut[i] = this.retornaInteiro(entrada, "Entre com " + dadosIn[i]+ ": ");
        } else {
            dadosOut[i] = dialog("Entre com " + dadosIn[i]+ ": ");
        }
    }
    return dadosOut;
}

    public Automovel leAutomovel (){

        Object [] valores = new Object [6];
        Object [] nomeVal = {"Marca", "Modelo", "Ano", "Quilometragem", "Placa", "Motorização"};
        String [] intValores = {nomeVal[2].toString(), nomeVal[3].toString()};
        valores = leValores (nomeVal, intValores);


        String marca = (String) valores[0];
        String modelo = (String) valores[1];
        int ano = (int) valores[2];
        int quilometragem = (int) valores[3];
        String placa = (String) valores[4];
        String motorizacao = (String) valores[5];

        Automovel automovel = new Automovel (marca,modelo,ano,quilometragem,placa,motorizacao);
        return automovel;
    }

    public Caminhao leCaminhao (){

        Object [] valores = new Object [6];
        Object [] nomeVal = {"Marca", "Modelo", "Ano", "Quilometragem", "Placa", "Carga Máxima"};
        String [] intValores = {nomeVal[2].toString(), nomeVal[3].toString(), nomeVal[5].toString()};
        valores = leValores (nomeVal, intValores);

        String marca = (String) valores[0];
        String modelo = (String) valores[1];
        int ano = (int) valores[2];
        int quilometragem = (int) valores[3];
        String placa = (String) valores[4];
        int cargaMaxima = (int) valores[5];


        Caminhao caminhao = new Caminhao (marca,modelo,ano,quilometragem,placa,cargaMaxima);
        return caminhao;
    }
    public Onibus leOnibus (){

        Object [] valores = new Object [6];
        Object [] nomeVal = {"Marca", "Modelo", "Ano", "Quilometragem", "Placa", "Assentos"};
        String [] intValores = {nomeVal[2].toString(), nomeVal[3].toString(), nomeVal[5].toString()};
        valores = leValores (nomeVal, intValores);

        String marca = (String) valores[0];
        String modelo = (String) valores[1];
        int ano = (int) valores[2];
        int quilometragem = (int) valores[3];
        String placa = (String) valores[4];
        int assentos = (int) valores[5];

        Onibus onibus = new Onibus (marca,modelo,ano,quilometragem,placa,assentos);
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
    public int retornaInteiro(String entrada, String... currentmMessage) { // retorna um valor inteiro
        String message = currentmMessage.length > 0 ? "\n" + currentmMessage[0] + "\n\n" : "";
        //Enquanto não for possível converter o valor de entrada para inteiro, permanece no loop
        while (!this.intValido(entrada)) {
            entrada = JOptionPane.showInputDialog(null, "Valor incorreto!\n\nDigite um número inteiro.\n" + message);
        }
        return Integer.parseInt(entrada);
    }

    public void salvaVeiculos (ArrayList<Veiculo> veiculos){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream
                    (new FileOutputStream("veiculos.dados"));
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
                    (new FileInputStream("veiculos.dados"));
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
            opc1 = this.retornaInteiro(entrada, menu);

            switch (opc1) {
                case 1:// Entrar dados
                    menu = "Entrada de Veículos\n" +
                            "Opções:\n" +
                            "1. Automóvel\n" +
                            "2. Ônibus\n" +
                            "3. Caminhão\n";

                    entrada = JOptionPane.showInputDialog (menu + "\n\n");
                    opc2 = this.retornaInteiro(entrada, menu);

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

