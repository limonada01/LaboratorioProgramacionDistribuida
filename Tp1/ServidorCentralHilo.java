package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;
public class ServidorCentralHilo extends Thread {
    private Socket socket;

    protected Socket skHoroscopo;
    protected Socket skClima;
    private DataOutputStream dosCliente;
    private DataInputStream disCliente;

    private DataOutputStream dosHoroscopo;
    private DataInputStream disHoroscopo;

    private DataOutputStream dosClima;
    private DataInputStream disClima;

    private int idSession;
    public ServidorCentralHilo(Socket socket, int id) {
        this.socket = socket;
        this.idSession = id;
        //Un socket para cada conexion a server satelite o solo uno para ambos???

        try {
            dosCliente = new DataOutputStream(socket.getOutputStream());
            disCliente = new DataInputStream(socket.getInputStream());

            //puertos diferentes para cada conexion satelite??
            skHoroscopo = new Socket("127.0.0.1", 20000);
            skClima = new Socket("127.0.0.1", 20001);

            dosHoroscopo = new DataOutputStream(skHoroscopo.getOutputStream());//buffer de salida
            disHoroscopo = new DataInputStream(skHoroscopo.getInputStream());//buffer de entrada

            dosClima = new DataOutputStream(skClima.getOutputStream());//buffer de salida
            disClima = new DataInputStream(skClima.getInputStream());//buffer de entrada

        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconnectar() {
        try {
            socket.close();
            skHoroscopo.close();//????? 
            skClima.close();//????
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        String accion = "";
        try {
            accion = disCliente.readUTF();
            String[] consultas=separadorConsultas(accion);//[0]=signo,[1]=fecha
            
            String respuestaSigno = consultaHoroscopo(consultas[0]);
            String respuestaClima = consultaClima(consultas[1]);

            dosCliente.writeUTF("Horoscopo: "+respuestaSigno+" Clima: "+respuestaClima );//respuesta final para cliente
            
            /* 
              if(accion.equals("hola")){
                System.out.println("El cliente con idSesion "+this.idSessio+" saluda");
                dos.writeUTF("adios");
            }
            */
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }

    private String consultaHoroscopo(String signo) {
        String respuesta="";
        try {
            dosHoroscopo.writeUTF(signo);
            respuesta = disHoroscopo.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String consultaClima(String fecha) {
        String respuesta="";
        try {
            dosCliente.writeUTF(fecha);
            respuesta = disCliente.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String[] separadorConsultas(String consultaOrigen){
        String[] consultasSeparadas=consultaOrigen.split("-");
        return consultasSeparadas;
    }
}
