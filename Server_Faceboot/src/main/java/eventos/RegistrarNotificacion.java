/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import controladores.ControladorNotificacion;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import peticiones.PeticionNotificacion;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class RegistrarNotificacion implements IEvento{

    /**
     * Variable de controlador
     */
    private ControladorNotificacion controladorNotificacion;
    
    /**
     * Variable conversor de JSON
     */
    private IJsonToObject conversor;

    /**
     * Constructor que inicializa las variables de la clase
     */
    public RegistrarNotificacion() {
        this.conversor = new JsonToObject();
        this.controladorNotificacion = new ControladorNotificacion();
    }

    /**
     * Método que ejecuta la petición, obtiene el id de la petición y la realiza
     * y envia mensaje
     * @param peticion a realizar
     * @param cliente que solicitó la operación
     */
    @Override
    public void ejecutar(String peticion, ClientManager cliente) {
        PeticionNotificacion peticionNotificacion = conversor.convertirNotificacion(peticion);
        PeticionNotificacion respuesta = controladorNotificacion.registrarNotificacion(peticionNotificacion.getNotificacion());
        cliente.enviarMensaje(conversor.convertirObjetoString(respuesta));
    }
    
}
