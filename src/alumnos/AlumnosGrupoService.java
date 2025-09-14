package alumnos;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumnosGrupoService {

    private AlumnosService aService;
    public AlumnosGrupoService(AlumnosService aService){
        this.aService = aService;
    }

    public ArrayList<Alumno> filtrarAlumnos(Long grupo) throws SQLException{
        ArrayList<Alumno> alumnos = new ArrayList<>();
        for(Alumno al: aService.requestAll()){
            if(al.getGrupo()==grupo){
                alumnos.add(al);
            }
        }                
        return alumnos;
    }
    
}
