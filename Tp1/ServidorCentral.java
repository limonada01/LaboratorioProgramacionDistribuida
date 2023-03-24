package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;
public class ServidorCentral {
    public static void main(String args[]) throws IOException {
        Cache cache = new Cache();
        ServerSocket ss;
        System.out.print("Inicializando servidor Central ");
        try {
            ss = new ServerSocket(10578);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante en servidor central: "+socket);
                ((ServidorCentralHilo) new ServidorCentralHilo(socket, idSession, cache)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR en SC");
        }
    }
}
