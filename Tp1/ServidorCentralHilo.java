package Tp1;
import java.io.*;
import java.net.*;
import java.util.logging.*;
public class ServidorCentralHilo extends Thread {
    private Socket socket, skHoroscopo, skClima;
    private DataOutputStream dosCliente, dosHoroscopo, dosClima;
    private DataInputStream disCliente, disHoroscopo, disClima;

    private Cache cache;
    private int idSession;
    private String ipSH,ipSClima;
    private int puertoSH,puertoSClima;
    public ServidorCentralHilo(Socket socket, int id, Cache cache, String ipSH, String ipSClima, int puertoSH, int puertoSClima) {
        this.socket = socket;
        this.idSession = id;
        this.cache = cache;
        this.ipSH=ipSH;
        this.ipSClima=ipSClima;
        this.puertoSH=puertoSH;
        this.puertoSClima=puertoSClima;

        try {
            dosCliente = new DataOutputStream(socket.getOutputStream());
            disCliente = new DataInputStream(socket.getInputStream());

        } catch (IOException ex) {

            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconnectar() {
        try {
            if(skHoroscopo!=null) skHoroscopo.close();
            if(skClima!=null) skClima.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        String accion = "";
        try {
            accion = disCliente.readUTF();
            System.out.println("Consulta de cliente para servidorCentralHilo"+idSession+": "+accion);
            String[] consultas=separadorConsultas(accion);//[0]=signo,[1]=fecha
            String respuestaSigno = consultaHoroscopo(consultas[0]);
            String respuestaClima = consultaClima(consultas[1]);
            dosCliente.writeUTF(respuestaSigno+" y "+respuestaClima );//respuesta final para cliente
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }

    private String consultaHoroscopo(String signo) {
        String respuesta="";
        try {
            respuesta= cache.getConsulta(signo);
            if(respuesta==null){
                if(cache.realizarConsulta(signo)) { //Si el hilo fue elegido para hacer la consulta
                    this.establecerConexionHoroscopo();
                    dosHoroscopo.writeUTF(signo);
                    respuesta = disHoroscopo.readUTF();
                    cache.putRespuesta(signo, respuesta);
                }else{
                    respuesta= cache.getConsulta(signo);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private String consultaClima(String fecha) {
        String respuesta="";
        try {
            respuesta= cache.getConsulta(fecha);
            if(respuesta==null){ //Si no lo pudo obtener de cache
                if(cache.realizarConsulta(fecha)){ //Si el hilo fue elegido para hacer la consulta
                    this.establecerConexionClima();
                    dosClima.writeUTF(fecha);
                    respuesta = disClima.readUTF();
                    cache.putRespuesta(fecha, respuesta);
                }else{//Si el hilo viene de estar bloqueado esperando a que se cargue en cache la saca de ahi
                    respuesta= cache.getConsulta(fecha);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    private void establecerConexionHoroscopo(){
        try {
            //Cada servidor debe arrancar en un puerto diferente, aca hacemos referencia al puerto al cual nos queremos conectar
                skHoroscopo = new Socket(ipSH, puertoSH);
                dosHoroscopo = new DataOutputStream(skHoroscopo.getOutputStream());//buffer de salida
                disHoroscopo = new DataInputStream(skHoroscopo.getInputStream());//buffer de entrada
        } catch (IOException ex) {

            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void establecerConexionClima(){
        try {
            //Cada servidor debe arrancar en un puerto diferente, aca hacemos referencia al puerto al cual nos queremos conectar
            skClima = new Socket(ipSClima, puertoSClima);
            dosClima = new DataOutputStream(skClima.getOutputStream());//buffer de salida
            disClima = new DataInputStream(skClima.getInputStream());//buffer de entrada


        } catch (IOException ex) {

            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] separadorConsultas(String consultaOrigen){
        String consultaLowerCase= consultaOrigen.toLowerCase();
        String[] consultasSeparadas=consultaLowerCase.split("-");
        return consultasSeparadas;
    }
}
