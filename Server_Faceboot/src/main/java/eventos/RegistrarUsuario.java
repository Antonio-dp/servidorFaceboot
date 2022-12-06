/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;

import controladores.ControladorUsuario;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import peticiones.PeticionUsuario;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class RegistrarUsuario implements IEvento {
    
    /**
     * Variable de controlador
     */
    private ControladorUsuario controladorUsuario;
    
    /**
     * Variable conversor de JSON
     */
    private IJsonToObject conversor;

    /**
     * Constructor que inicializa las variables de la clase
     */
    public RegistrarUsuario() {
        this.conversor = new JsonToObject();
        this.controladorUsuario = new ControladorUsuario();
    }
    
    /**
     * Método que ejecuta la petición, obtiene el id de la petición y la realiza
     * y envia mensaje
     * @param peticion a realizar
     * @param cliente que solicitó la operación
     */
    @Override
    public void ejecutar(String peticion, ClientManager cliente) {
        PeticionUsuario peticionUsuario = conversor.convertirPeticionUsuario(peticion);
        PeticionUsuario respuesta = controladorUsuario.registrarUsuario(peticionUsuario.getUsuario());
        cliente.enviarMensaje(conversor.convertirObjetoString(respuesta));
    }
    
}
