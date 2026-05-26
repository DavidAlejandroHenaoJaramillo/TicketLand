package model;

public abstract class Persona {

    protected int id;
    protected String nombre;
    protected String correo;
    protected String telefono;
    protected String password;

    public Persona(int id, String nombre, String correo, String telefono) {
        this(id, nombre, correo, telefono, "1234");
    }

    public Persona(int id, String nombre, String correo, String telefono, String password) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}