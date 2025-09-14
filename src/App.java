import java.sql.Connection;
import java.util.ArrayList;

import alumnos.Alumno;
import alumnos.AlumnosService;
import connection.ConnectionPool;

public class App {
    public static void main(String[] args) throws Exception {
        AlumnosApp app = new AlumnosApp();
        app.init();        
    }
}