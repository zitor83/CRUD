package tutores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import CRUD.CRUD;


public class TutoresService implements CRUD<Tutor> {
    Connection conn;

    public TutoresService(Connection conn) {
        this.conn = conn;
    }

        
    public ArrayList<Tutor> requestAll() throws SQLException {
        ArrayList<Tutor> result = new ArrayList<>();
        Statement statement = this.conn.createStatement();
        String sql = "SELECT id, nombre, apellidos FROM tutor";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            long id = rs.getLong("id");
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");



            result.add(new Tutor(id, nombre, apellidos));
        }

        rs.close();
        statement.close();
        return result;
    }
public Tutor requestById(long id) throws SQLException {
        Statement statement = null;
        Tutor result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT id, nombre, apellidos FROM tutor WHERE id=%d", id);
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        if (querySet.next()) {
            String nombre = querySet.getString("nombre");
            String apellidos = querySet.getString("apellidos");
            result = new Tutor(id, nombre, apellidos);
        }
        statement.close();
        return result;

    }
    public long create(Tutor object) throws SQLException {

        String sqlaux = String.format("INSERT INTO tutor (nombre, apellidos) VALUES (?, ?)");
        PreparedStatement prepst = this.conn.prepareStatement(sqlaux, Statement.RETURN_GENERATED_KEYS);

        prepst.setString(1, object.getNombre());
        prepst.setString(2, object.getApellidos());

        

       
       
        prepst.execute();
        ResultSet keys = prepst.getGeneratedKeys();
        if (keys.next()) {
            long id = keys.getLong(1);
            prepst.close();
            return id;
        } else {
            throw new SQLException("Creating tutor failed, no rows affected.");
        }

    }
    public int update(Tutor object) throws SQLException {
        long id = object.getId();
        String nombre = object.getNombre();
        String apellidos = object.getApellidos();
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE tutor SET nombre = '%s', apellidos = '%s' WHERE id=%d", nombre,
                apellidos, id);
        // Ejecución de la consulta
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Updating tutor failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM tutor WHERE id=%d", id);
        // Ejecución de la consulta
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
