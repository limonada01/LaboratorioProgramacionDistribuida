package Tp1;

import java.io.*;
import java.net.Socket;
import java.util.logging.*;
public class ClienteHilo extends Thread {

    static String[] signos={"SagitarIo","LeO","capricornio"};
    static String[] fechas={"11/05/2023","12/05/2023","13/05/2023"};
    private int test=1;
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id,puertoDestino;
    private String ipHost;
    public ClienteHilo(int id, String ipHost, int puertoDestino) {
        this.id = id;
        this.ipHost=ipHost;
        this.puertoDestino=puertoDestino;
    }
    @Override
    public void run() {
        try {
            sk = new Socket(ipHost,puertoDestino);
            dos = new DataOutputStream(sk.getOutputStream());//buffer de salida
            dis = new DataInputStream(sk.getInputStream());//buffer de entrada
            
            int num=(int)(Math.random()*signos.length);
            String consulta=signos[num]+"-"+fechas[(int)(Math.random()*fechas.length)];
            System.out.println("Persona "+id + " Consulta horoscopo y clima: "+consulta);
            dos.writeUTF(consulta);

            String respuesta="";
            respuesta = dis.readUTF();
            System.out.println("Servidor devuelve: " + respuesta+ ". como respuesta a persona"+id);
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
}

