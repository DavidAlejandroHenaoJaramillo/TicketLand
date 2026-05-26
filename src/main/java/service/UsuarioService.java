package service;

import model.Usuario;
import model.Rol;
import java.util.List;

public class UsuarioService {

    private List<Usuario> usuarios;

    public UsuarioService(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // RF-012: crear/actualizar/eliminar/listar usuarios
    public Usuario crearUsuario(String nombre, String correo,
                                String telefono, String contrasena, Rol rol) {
        // validar que el correo no exista
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                throw new IllegalArgumentException("El correo ya está registrado.");
            }
        }
        Usuario nuevo = new Usuario(usuarios.size() + 1, nombre, correo, telefono);
        usuarios.add(nuevo);
        return nuevo;
    }

    public boolean eliminarUsuario(String idUsuario) {
        return usuarios.removeIf(u -> String.valueOf(u.getId()).equals(idUsuario));
    }

    public Usuario buscarPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) return u;
        }
        return null;
    }

    public List<Usuario> listarTodos() {
        return usuarios;
    }

    public void actualizarUsuario(String idUsuario, String nombre,
                                  String correo, String telefono) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) u.actualizarPerfil(nombre, correo, telefono);
    }

    private Usuario buscarPorId(String id) {
        for (Usuario u : usuarios) {
            if (String.valueOf(u.getId()).equals(id)) return u;
        }
        return null;
    }
}