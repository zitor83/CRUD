package grupos;

public class Grupo {
    long id;
    String nombre;
    String id_tutor;

    public Grupo() {
        this(0, "", null);
    }

    public Grupo(Grupo al) {
        this.id = al.id;
        this.nombre = al.nombre;
        this.id_tutor = al.id_tutor;

    }

    public Grupo(long id, String nombre, String id_tutor) {
        this.id = id;
        this.nombre = nombre;
        this.id_tutor = id_tutor;
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

    public String getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(String id_tutor) {
        this.id_tutor = id_tutor;

    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, ID Tutor: %s", this.id, this.nombre, this.id_tutor==null?"Sin tutor":this.id_tutor);
    }

}
