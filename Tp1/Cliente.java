package Tp1;
import java.util.*;
public class Cliente {
    public static void main(String[] args) {
        //args[0]=cantidadDeClientes args[1]=ipHost args[2]=puertoDestino
        ArrayList<Thread> clientes = new ArrayList<Thread>();
        int cantidadClientes=Integer.parseInt(args[0]);
        String ipHost=args[1];
        int puertoDestino=Integer.parseInt(args[2]);
        

        for (int i = 0; i < cantidadClientes; i++) {
            clientes.add(new ClienteHilo(i,ipHost,puertoDestino));
        }
        for (Thread thread : clientes) {
            thread.start();
        }
    }
}

