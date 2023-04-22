package TpLab2.servidor_horoscopo;
import java.rmi.*;

public interface Servicio_Horoscopo extends Remote{
    public String getHoroscopo (String signo) throws RemoteException; 
}
