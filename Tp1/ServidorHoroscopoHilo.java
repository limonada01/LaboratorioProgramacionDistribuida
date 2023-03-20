package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorHoroscopoHilo extends Thread{
    private final String[] horoscopos={"[Esto seria el horoscopo Sagitario]","[Esto seria el horoscopo Leo]","[Esto seria el horoscopo Capricornio]"};
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

    public void desconnectar() {
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
            System.out.println("Consulta de ServidorCentral para servidorHoroscopoHilo: "+signoSolicitado);
            String respuesta=getHoroscopo(signoSolicitado);
            dosCliente.writeUTF("Horoscopo de "+signoSolicitado+ ": "+respuesta);//respuesta final para cliente (osea Servidor-Central en este caso)
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();

    }

    public String getHoroscopo(String signo){
        String respuesta="";
        
        switch(signo){
            case "sagitario": respuesta=horoscopos[0];break;
            case "leo": respuesta=horoscopos[1];break;
            case "capricornio": respuesta=horoscopos[2];break;
            default: respuesta="signo incorrecto";
        }
        return respuesta;
    }
}
