package alumnos;

import java.sql.Date;

public class Alumno{
    long id;
    String nombre;
    String apellidos;
    Date fecha_nac;
    Long grupo;
 
    public Alumno(){
        this(0,"","", null, null);
    }

    public Alumno(Alumno al){
        this.id = al.id;
        this.nombre = al.nombre;
        this.apellidos = al.apellidos;
        this.fecha_nac = al.fecha_nac;
        this.grupo = al.grupo;
    }    

    public Alumno(long id, String nombre, String apellidos, Date fecha_nac, Long grupo){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nac = fecha_nac;
        this.grupo = grupo;
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

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Long getGrupo() {
        return grupo;
    }

    public void setGrupo(Long grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Apellidos: %s, Fecha Nac: %s, Grupo: %s", this.id, this.nombre, this.apellidos, this.fecha_nac, this.grupo==null?"Sin grupo":this.grupo);
    }

}
