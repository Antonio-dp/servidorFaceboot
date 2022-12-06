/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import controladores.ControladorComentario;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import peticiones.PeticionComentario;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class EliminarComentario implements IEvento {

    /**
     * Variable conversor de JSON
     */
    private IJsonToObject conversor;
    
    /**
     * Variable de controlador
     */
    private ControladorComentario controladorComentario;

    /**
     * Constructor que inicializa las variables de la clase
     */
    public EliminarComentario() {
        this.conversor = new JsonToObject();
        this.controladorComentario = new ControladorComentario();
    }
    
    /**
     * Método que ejecuta la petición, obtiene el id de la petición y la realiza
     * y envia mensaje
     * @param peticion a realizar
     * @param cliente que solicitó la operación
     */
    @Override
    public void ejecutar(String peticion, ClientManager cliente) {
        PeticionComentario peticionComentarioRecibido = conversor.convertirPeticionComentario(peticion);
        PeticionComentario peticionComentarioRespuesta = controladorComentario.eliminarComentario(peticionComentarioRecibido.getComentario());
        cliente.enviarMensaje(conversor.convertirObjetoString(peticionComentarioRespuesta));
    }
    
}
