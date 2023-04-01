package Tp1;
import java.util.*;
public class Cliente {
    static private ArrayList<Thread> clientes;
    static private int cantidadClientes;
    static private String ipHost;
    static private int puertoDestino;
    public static void main(String[] args) {
        //args[0]=cantidadDeClientes args[1]=ipHost args[2]=puertoDestino
        clientes = new ArrayList<Thread>();
        cantidadClientes=Integer.parseInt(args[0]);
        ipHost=args[1];
        puertoDestino=Integer.parseInt(args[2]);
        

        for (int i = 0; i < cantidadClientes; i++) {
            clientes.add(new ClienteHilo(i,ipHost,puertoDestino));
        }
        for (Thread thread : clientes) {
            thread.start();
        }
    }
}

