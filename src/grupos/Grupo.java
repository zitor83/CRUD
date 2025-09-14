package grupos;


public class Grupo{
    long id;
    String nombre;
    
    public Grupo(){
        this(0,"");
    }

    public Grupo(Grupo al){
        this.id = al.id;
        this.nombre = al.nombre;
        
    }    

    public Grupo(long id, String nombre){
        this.id = id;
        this.nombre = nombre;
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

    

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s", this.id, this.nombre);
    }

}
