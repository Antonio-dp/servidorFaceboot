/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import controladores.ControladorUsuario;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import entidades.Usuario;
import peticiones.PeticionUsuario;
import principales.ClientManager;

/**
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class EditarPerfil implements IEvento {

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
    public EditarPerfil() {
        this.conversor = new JsonToObject();
        this.controladorUsuario = new ControladorUsuario();
    }

    /**
     * Método que ejecuta la petición, obtiene el id de la petición.
     * y notifica lo sucedido
     * @param peticion a realizar
     * @param cliente que solicitó la operación
     */
    @Override
    public void ejecutar(String peticion, ClientManager cliente) {
        PeticionUsuario peticionUsuario = conversor.convertirPeticionUsuario(peticion);
        Usuario usuarioActualizado = controladorUsuario.editarPerfil(peticionUsuario.getUsuario());
        PeticionUsuario respuesta;
        if (usuarioActualizado != null) {
            respuesta = new PeticionUsuario(Eventos.editarPerfil, 200, usuarioActualizado);
        } else {
            respuesta = new PeticionUsuario(Eventos.editarPerfil, 404, "Usuario no Encontrado");
        }
        cliente.enviarMensaje(conversor.convertirObjetoString(respuesta));
    }
}
