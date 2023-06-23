package TpLab2.servidor_central;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servicio_Consulta extends Remote {
    public String realizar_consulta(String consulta) throws RemoteException;
}
