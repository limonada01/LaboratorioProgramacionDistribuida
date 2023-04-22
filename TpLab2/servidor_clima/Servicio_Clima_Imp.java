package TpLab2.servidor_clima;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Servicio_Clima_Imp extends UnicastRemoteObject implements Servicio_Clima{
    
    private final String[] climas={
        "lluvia",
        "soleado",
        "nublado",
        "viento"
    };

    protected Servicio_Clima_Imp() throws RemoteException
    {super();}

    @Override
    public String getClima(String fecha) throws RemoteException {        
        int indexClimaRandom = -1;
        String respuesta = "ERROR en la consulta clima";
        if(validarFecha(fecha)){
            indexClimaRandom = (int)(Math.random()*climas.length);
            respuesta = climas[indexClimaRandom];
        } 
        return respuesta; 
    }
    
    private boolean validarFecha(String fecha){
        String formato= "dd/MM/yyyy";
        boolean exito=false;
        try {
            DateFormat df = new SimpleDateFormat(formato);
            df.setLenient(false);
            df.parse(fecha);
            Pattern patron = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
            Matcher matcher = patron.matcher(fecha);
            if (matcher.matches()) {
                System.out.println("La fecha " + fecha + " cumple con el formato " + formato);
                exito=true;
            } else {
                throw new ParseException("" + formato,0);
            }
        } catch (ParseException e) {
            System.out.println("La fecha " + fecha + " no cumple con el formato " + formato);
        }
        return exito;
    }
}
