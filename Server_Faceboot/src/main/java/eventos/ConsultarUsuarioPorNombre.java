/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import controladores.ControladorUsuario;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import peticiones.PeticionString;
import peticiones.PeticionUsuario;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ConsultarUsuarioPorNombre implements IEvento {
    
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
    public ConsultarUsuarioPorNombre() {
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
        PeticionString peticionNombre = conversor.convertirPeticionString(peticion);
        PeticionUsuario peticionUsuario = controladorUsuario.consultarUsuarioPorNombre(peticionNombre.getStr());
        cliente.enviarMensaje(conversor.convertirObjetoString(peticionUsuario));
    }
}
