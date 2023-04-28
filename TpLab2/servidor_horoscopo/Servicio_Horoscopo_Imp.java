package TpLab2.servidor_horoscopo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class Servicio_Horoscopo_Imp extends UnicastRemoteObject implements Servicio_Horoscopo {
    
    List<String> signos= Arrays.asList(
            "acuario",
            "piscis",
            "aries",
            "tauro",
            "géminis",
            "cáncer",
            "leo",
            "virgo",
            "libra",
            "escorpio",
            "sagitario",
            "capricornio");
    private final String[] horoscopos={
            "horoscopoRandom1",
            "horoscopoRandom2",
            "horoscopoRandom3",
            "horoscopoRandom5",
            "horoscopoRandom6",
            "horoscopoRandom7",
            "horoscopoRandom8",
            "horoscopoRandom9",
    };

    protected Servicio_Horoscopo_Imp() throws RemoteException
    { super(); }

    @Override
    public String getHoroscopo(String signo) throws RemoteException {
        int indexHoroscopoRandom = -1;
        String respuesta = "ERROR en la consulta horoscopo";
        if(validacionSigno(signo)){
            indexHoroscopoRandom=(int)(Math.random()*horoscopos.length);
            respuesta = horoscopos[indexHoroscopoRandom];
        } 
        return respuesta; 
    }
    
    private boolean validacionSigno(String consulta){
        return signos.contains(consulta);
    }
}
