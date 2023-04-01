import java.io.*;
import java.net.*;
import java.util.logging.*;
public class ServidorEj {
    public static void main(String args[]) throws IOException {
        ServerSocket ss;
        System.out.print("Inicializando servidor... ");
        try {
            ss = new ServerSocket(10578);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante: "+socket);
                ((ServidorHilo) new ServidorHilo(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorEj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
