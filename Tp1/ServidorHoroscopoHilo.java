package Tp1;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class ServidorHoroscopoHilo extends Thread{
    List<String> signos= Arrays.asList(
            "acuario",
            "piscis",
            "aries",
            "tauro",
            "géminis",
            "cáncer",
            "leo",
            "virgo",
            "libra",
            "escorpio",
            "sagitario",
            "capricornio");
    private final String[] horoscopos={
            "horoscopoRandom1",
            "horoscopoRandom2",
            "horoscopoRandom3",
            "horoscopoRandom5",
            "horoscopoRandom6",
            "horoscopoRandom7",
            "horoscopoRandom8",
            "horoscopoRandom9",
    };
    private Socket socket;
    private int idSession;
    private DataOutputStream dosCliente;
    private DataInputStream disCliente;
    
    public ServidorHoroscopoHilo(Socket socket, int id){
        this.socket = socket;
        this.idSession = id;
        try {
            dosCliente = new DataOutputStream(socket.getOutputStream());
            disCliente = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        String signoSolicitado = "";
        try {
            signoSolicitado = disCliente.readUTF();
            System.out.println("Consulta de ServidorCentral para servidorHoroscopoHilo"+idSession+": "+signoSolicitado);
            if(validacionSigno(signoSolicitado)){
                String respuesta=getHoroscopo();
                dosCliente.writeUTF("Horoscopo de "+signoSolicitado+ ": "+respuesta);//respuesta final para cliente (osea Servidor-Central en este caso)
            }else{
                dosCliente.writeUTF("ERROR: Signo del zodiaco no valido");//respuesta final para cliente (osea Servidor-Central en este caso)
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();

    }
    private String getHoroscopo(){
        int indexHoroscopoRandom=(int)(Math.random()*horoscopos.length);
        return  horoscopos[indexHoroscopoRandom];
    }

    private boolean validacionSigno(String consulta){
        return signos.contains(consulta);
    }
}
