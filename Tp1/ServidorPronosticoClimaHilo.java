package Tp1;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServidorPronosticoClimaHilo extends Thread{
    private final String[] climas={
            "lluvia",
            "soleado",
            "nublado",
            "viento"};
    private Socket socket;
    private int idSession;
    private DataOutputStream dosCliente;
    private DataInputStream disCliente;
    
    public ServidorPronosticoClimaHilo(Socket socket, int id){
        this.socket = socket;
        this.idSession = id;
        try {
            dosCliente = new DataOutputStream(socket.getOutputStream());
            disCliente = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        String fechaSolicitada = "";
        try {
            fechaSolicitada = disCliente.readUTF();
            System.out.println("Consulta de ServidorCentral para servidorClimaHilo"+idSession+": "+fechaSolicitada);
            if(validarFecha(fechaSolicitada)){
                String respuesta=getClima();
                dosCliente.writeUTF("Clima para fecha: "+fechaSolicitada+ ": "+respuesta);//respuesta final para cliente (osea Servidor-Central en este caso)
            }else{
                dosCliente.writeUTF("ERROR: Formato de la fecha incorrecto");
            }


        } catch (IOException ex) {

            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
    }

    private String getClima(){
        int indexClimasRandom=(int)(Math.random()*climas.length);
        return  climas[indexClimasRandom];
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