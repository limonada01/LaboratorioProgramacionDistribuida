package TpLab2.servidor_clima;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servicio_Clima extends Remote{
    public String getClima(String fecha) throws RemoteException;
}
