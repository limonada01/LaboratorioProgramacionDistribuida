package RMI.Ejemplos.Ej1.Servidor;// Implementación del servicio

import java.rmi.*;
import java.rmi.server.*;

class ServicioEcoImpl extends UnicastRemoteObject implements ServicioEco {
    ServicioEcoImpl() throws RemoteException {
    }
    public String eco(String s) throws RemoteException {
        return s.toUpperCase();
    }
}


