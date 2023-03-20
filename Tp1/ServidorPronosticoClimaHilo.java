package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorPronosticoClimaHilo extends Thread{
    private final String[] climas={"lluvia","soleado","nublado","viento"};
    private Socket socket;
    private int idSession;

    private DataOutputStream dosCliente;
    private DataInputStream disCliente;
    
    public ServidorPronosticoClimaHilo(Socket socket, int id){
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
        String fechaSolicitada = "";

        try {
            fechaSolicitada = disCliente.readUTF();
            String respuesta=getClima(fechaSolicitada);
            dosCliente.writeUTF("Clima para fecha: "+fechaSolicitada+ ": "+respuesta);//respuesta final para cliente
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();

    }

    public String getClima(String fecha){
        String respuesta="";
        
        switch(fecha){
            case "11/05/2023": respuesta=climas[0];break;
            case "12/05/2023": respuesta=climas[1];break;
            case "13/05/2023": respuesta=climas[2];break;
            default: respuesta="fecha incorrecta";
        }
        return respuesta;
    }
}
