package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorHoroscopo {
    public static void main(String args[]) throws IOException {
        //args[0]=puerto servidor Horoscopo
        int puertoSH=Integer.parseInt(args[0]);
        ServerSocket ss;
        System.out.print("Inicializando servidor Horoscopo ");
        try {
            ss = new ServerSocket(puertoSH);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante en ServidorHoroscopo: "+socket);
                ((ServidorHoroscopoHilo) new ServidorHoroscopoHilo(socket, idSession)).start(); //Arranca los hilos
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
