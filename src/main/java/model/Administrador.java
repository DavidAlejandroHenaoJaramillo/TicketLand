package model;

public class Administrador extends Persona {

    private String permisosAdministrador;

    public Administrador(int id, String nombre, String correo, String telefono, String permisosAdministrador) {

        super(id, nombre, correo, telefono);
        this.permisosAdministrador = permisosAdministrador;
    }

    public String getPermisosAdministrador() {
        return permisosAdministrador;
    }

    public void setPermisosAdministrador(String permisosAdministrador) {
        this.permisosAdministrador = permisosAdministrador;
    }

    public String gestionarEventos() {
        return "Eventos gestionados correctamente";
    }

    public String gestionarUsuarios() {
        return "Usuarios gestionados correctamente";
    }

    public String gestionarRecintos() {
        return "Recintos gestionados correctamente";
    }

    @Override
    public String toString() {
        return nombre + " - ADMIN";
    }
}