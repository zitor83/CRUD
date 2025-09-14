package grupos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Types;

import CRUD.CRUD;


public class GruposService implements CRUD<Grupo>{
    Connection conn;
    public GruposService(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Grupo> requestAll() throws SQLException{        
        ArrayList<Grupo> result = new ArrayList<Grupo>();
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = "SELECT id, nombre FROM grupos";
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        while(querySet.next()) {
            int id = querySet.getInt("id");
            String nombre = querySet.getString("nombre");
            result.add(new Grupo(id, nombre));            
        } 
        statement.close();    
        return result;
    }

    public Grupo requestById(long id) throws SQLException{
        Statement statement = null;
        Grupo result = null;
        statement = this.conn.createStatement();    
        String sql = String.format("SELECT id, nombre FROM grupos WHERE id=%d", id);
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        if(querySet.next()) {
            String nombre = querySet.getString("nombre");
            result = new Grupo(id, nombre);                                 
        }
        statement.close();    
        return result;
        
    }

    public long create(Grupo object) throws SQLException{
        
        //Statement statement = null;
        //statement = this.conn.createStatement();    
        
        //String sql = String.format("INSERT INTO alumnos (nombre, apellidos, grupo_id) VALUES ('%s', '%s', NULL)", nombre, apellidos);
        String sqlaux = String.format("INSERT INTO grupos (nombre) VALUES (?)");
        PreparedStatement prepst = this.conn.prepareStatement(sqlaux, Statement.RETURN_GENERATED_KEYS);
        prepst.setString(1, object.getNombre());        
        

        // Ejecución de la consulta
        //int affectedRows = statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        prepst.execute();
        ResultSet keys = prepst.getGeneratedKeys();
        if(keys.next()){
            long id = keys.getLong(1);
                prepst.close();
                return id;
        }
        else{
            throw new SQLException("Creating user failed, no rows affected.");
        }
        
         
    }

    public int update(Grupo object) throws SQLException{
        long id = object.getId();
        String nombre = object.getNombre();
        
        Statement statement = null;
        statement = this.conn.createStatement();    
        String sql = String.format("UPDATE grupos SET nombre = '%s' WHERE id=%d", nombre, id);
        // Ejecución de la consulta
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Creating user failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(long id) throws SQLException{
        Statement statement = null;
        statement = this.conn.createStatement();    
        String sql = String.format("DELETE FROM grupos WHERE id=%d", id);
        // Ejecución de la consulta
        int result = statement.executeUpdate(sql);
        statement.close();
        return result==1;
    }
}
