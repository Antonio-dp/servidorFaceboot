/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principales;

import conversors.IJsonToObject;
import conversors.JsonToObject;
import eventos.ManejadorEventos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ClientManager implements Runnable{
    
    /**
     * Variable socket del cliente
     */
    private Socket clientSocket;
    
    /**
     * Variable para recibir mensajes
     */
    private BufferedReader in;
    
    /**
     * Variable para enviar mensajes
     */
    public BufferedWriter out;
    
    /**
     * Variable id
     */
    private Integer id;
    
    /**
     * Lista de los clientes conectados
     */
    private static List<ClientManager> clientesConectados;
    
    /**
     * Constructor de la clase que inicializa las variables de la clase
     * @param clientSocket socket del cliente
     * @param clientesConectados Lista de clientes conectados
     * @throws IOException Excepción que se puede lanzar
     */
    public ClientManager(Socket clientSocket, List<ClientManager> clientesConectados) throws IOException {
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));;
        this.clientesConectados = clientesConectados;
    }

    /**
     * Método run que se encarga de recibir los mensajes y los deserializa los mensajes
     * y se encarga de ejecutar los eventos
     */
    @Override
    public void run() {
        IJsonToObject conversor = new JsonToObject();
        while(clientSocket.isConnected()){
            try{
                String mensajeCliente = in.readLine();
                ManejadorEventos.ejecutarEvento(mensajeCliente, this);
            } catch(IOException io){
                eliminarCliente();
                cerrarTodo();
                break;
            }
        }
    }
    
    /**
     * Método que se encarga de notificar todos los clientes conectados
     * @param mensaje para enviar a los clientes
     */
    public void notificarTodos(String mensaje){
        for(ClientManager cliente: clientesConectados){
                try{
                    cliente.out.write(mensaje);
                    cliente.out.newLine();
                    cliente.out.flush();
                } catch(IOException io){
                    cerrarTodo();
                    break;
                }
        }
    }
    
    /**
     * Método que se encarga de enviar un mensaje a un solo cliente
     * @param mensaje a enviar
     */
    public void enviarMensaje(String mensaje){
        try{
            out.write(mensaje);
            out.newLine();
            out.flush();
        } catch(IOException io){
            cerrarTodo();
        }
    }
    
    /**
     * Método para eliminar al cliente de la lista de clientes conectados
     */
    public void eliminarCliente(){
        clientesConectados.remove(this);
    }
    
    /**
     * Método para cerrar todo
     */
    public void cerrarTodo(){
        try{
            clientSocket.close();
            in.close();
            out.close();
        } catch(IOException io){
            io.printStackTrace();
        }
    }

    /**
     * Método para obtener el id del cliente
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método para colocar el id de cliente
     * @param id parametro a colocar
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
}
