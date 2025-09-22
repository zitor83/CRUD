package tutores;

public class Tutor {
    long id;
    String nombre;
    String apellidos;

    public Tutor(){
        this(0,"","");
    }

    public Tutor(Tutor t){
        this.id = t.id;
        this.nombre = t.nombre;
        this.apellidos = t.apellidos;
    }    

    public Tutor(long id, String nombre, String apellidos){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;

    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Apellidos: %s", this.id, this.nombre, this.apellidos);
    }
   

}
    
    