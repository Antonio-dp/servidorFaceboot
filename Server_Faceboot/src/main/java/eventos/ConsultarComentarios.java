/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import controladores.ControladorComentario;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import peticiones.PeticionId;
import peticiones.PeticionComentarios;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ConsultarComentarios implements IEvento{
    
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
    public ConsultarComentarios() {
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
        PeticionId peticionId = conversor.convertirPeticionId(peticion);
        PeticionComentarios peticionRespuesta = controladorComentario.consultarComentarios(peticionId.getId());
        cliente.enviarMensaje(conversor.convertirObjetoString(peticionRespuesta));
    }
}
