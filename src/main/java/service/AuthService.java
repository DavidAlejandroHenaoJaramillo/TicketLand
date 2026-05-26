package service;

import model.Rol;
import model.Usuario;
import java.util.List;

// RF-001: Registrarse/iniciar sesión (separado por SRP — RF-047)
public class AuthService {

    private final UsuarioService usuarioService;

    public AuthService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario login(String correo, String clave) {
        Usuario u = usuarioService.buscarPorCorreo(correo);
        // Simulación: en producción se verificaría el hash de la contraseña
        if (u != null) return u;
        throw new IllegalArgumentException("Credenciales inválidas.");
    }

    public Usuario registrar(String nombre, String correo,
                             String telefono, String clave, Rol rol) {
        return usuarioService.crearUsuario(nombre, correo, telefono, clave, rol);
    }
}