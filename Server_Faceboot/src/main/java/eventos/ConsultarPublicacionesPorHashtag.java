/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import controladores.ControladorPublicacion;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import peticiones.PeticionPublicaciones;
import peticiones.PeticionString;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ConsultarPublicacionesPorHashtag implements IEvento {

    /**
     * Variable conversor de JSON
     */
    private IJsonToObject conversor;
    
    /**
     * Variable de controlador
     */
    private ControladorPublicacion controladorPublicacion;

    /**
     * Constructor que inicializa las variables de la clase
     */
    public ConsultarPublicacionesPorHashtag() {
        this.conversor = new JsonToObject();
        this.controladorPublicacion = new ControladorPublicacion();
    }
    
    /**
     * Método que ejecuta la petición, obtiene el id de la petición y la realiza
     * y envia mensaje
     * @param peticion a realizar
     * @param cliente que solicitó la operación
     */
    @Override
    public void ejecutar(String peticion, ClientManager cliente) {
        PeticionString peticionEnviada = conversor.convertirPeticionString(peticion);
        PeticionPublicaciones respuesta = controladorPublicacion.consultarPublicacionesPorEtiqueta(peticionEnviada.getStr());
        cliente.enviarMensaje(conversor.convertirObjetoString(respuesta));
    }
    
}
