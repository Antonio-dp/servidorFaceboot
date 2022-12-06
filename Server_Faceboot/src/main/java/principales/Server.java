/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principales;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class Server implements Runnable{

    /**
     * Lista de clientes conectados
     */
    private static List<ClientManager> clientesConectados = new ArrayList();
    
    /**
     * Variable del puerto
     */
    private int puerto;
    
    /**
     * Variable del ServerSocket
     */
    private ServerSocket servidor;
 
    /**
     * Variable del server
     */
    private static Server server;
    
    /**
     * constructor que inicializa el puerto
     * @param puerto a guardar
     */
    private Server(int puerto) {
        this.puerto = puerto;
    }
    
    /**
     * Singleton getInstance del Server
     * @return Server
     */
    public static Server getInstance(){
        if(server == null){
            server = new Server(9000);
        }
        return server;
    }
    
    /**
     * Metodo Run en el cual se conectan los clientes a la lista y 
     * se crea un hilo que esta a la espera de algún mensaje que le pueda llegar
     */
    @Override
    public void run() {
        Socket sc = null;
        try{
            servidor = new ServerSocket(puerto); //Se crea el servidor
            System.out.println("Servidor Iniciado");
            while(true){ //De esta forma el servidor siempre va a estar escuchando peticiones     
                sc = servidor.accept();
                System.out.println("Cliente Conectado");
                ClientManager cliente = new ClientManager(sc, clientesConectados);
                clientesConectados.add(cliente);
                new Thread(cliente).start();
            }
        } catch(IOException io){
            io.printStackTrace();
        }
    }
    
    /**
     * Método que se encarga de notificar a todos los clientes sobre algún evento ocurrido
     * @param id del que lo envia
     * @param mensaje que se transmitirá
     */
    public void notificarTodos(Integer id, String mensaje){
        //System.out.println("Notificando a todos en el server");
        for(ClientManager cliente: clientesConectados){
            System.out.println("Id de Cliente: "+cliente.getId());
            System.out.println("Id que se manda: "+id);
            if(cliente.getId() != id){
                System.out.println("Eviando Publicacion: "+mensaje);
                try{
                    cliente.out.write(mensaje);
                    cliente.out.newLine();
                    cliente.out.flush();
                } catch(IOException io){
                    io.printStackTrace();
                    break;
                }
            }    
        }
    }

    /**
     * Cierra los server sockets
     */
    public void cerrarServerSocket(){
        try{
            if(servidor != null){
                servidor.close();
            }
        } catch(IOException io){
            io.printStackTrace();
        }
    }
    
    /**
     * Método main que genera el hilo del servidor
     * @param args 
     */
    public static void main(String[] args){
        new Thread(Server.getInstance()).start();
    }
}


