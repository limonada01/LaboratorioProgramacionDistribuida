package Tp1;
import java.io.*;
import java.net.*;
import java.util.logging.*;
public class ServidorPronosticoClimaHilo extends Thread{
    private final String[] climas={
            "lluvia",
            "soleado",
            "nublado",
            "viento"};
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

    private void desconectar() {
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
            System.out.println("Consulta de ServidorCentral para servidorClimaHilo"+idSession+": "+fechaSolicitada);
            String respuesta=getClima();
            dosCliente.writeUTF("Clima para fecha: "+fechaSolicitada+ ": "+respuesta);//respuesta final para cliente (osea Servidor-Central en este caso)
        } catch (IOException ex) {

            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
    }

    private String getClima(){
        int indexClimasRandom=(int)(Math.random()*climas.length);
        return  climas[indexClimasRandom];
    }
}