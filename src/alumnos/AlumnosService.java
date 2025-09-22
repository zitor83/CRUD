package alumnos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CRUD.CRUD;

public class AlumnosService implements CRUD<Alumno> {
    Connection conn;

    public AlumnosService(Connection conn) {
        this.conn = conn;
    }

    
    public ArrayList<Alumno> requestAll() throws SQLException {
        ArrayList<Alumno> result = new ArrayList<>();
        Statement statement = this.conn.createStatement();
        String sql = "SELECT id, nombre, apellidos, fecha_nac, grupo FROM alumnos";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            long id = rs.getLong("id");
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");

            // Leer fecha como string para evitar excepción por '0000-00-00'
            String fechaStr = rs.getString("fecha_nac"); // devuelve null si es SQL NULL
            java.sql.Date fecha_nac = null;
            if (fechaStr != null && !fechaStr.equals("0000-00-00")) {
                try {
                    fecha_nac = java.sql.Date.valueOf(fechaStr);
                } catch (IllegalArgumentException e) {
                    // si el formato fuera raro, dejamos null (o podrías loguearlo)
                    fecha_nac = null;
                }
            }

            // Manejar campo grupo distinguiendo NULL
            Long grupo = null;
            long grupoVal = rs.getLong("grupo");
            if (!rs.wasNull()) {
                grupo = grupoVal;
            }

            result.add(new Alumno(id, nombre, apellidos, fecha_nac, grupo));
        }

        rs.close();
        statement.close();
        return result;
    }




    public Alumno requestById(long id) throws SQLException {
        Statement statement = null;
        Alumno result = null;
        statement = this.conn.createStatement();
        String sql = String.format("SELECT id, nombre, apellidos, fecha_nac, grupo FROM alumnos WHERE id=%d", id);
        // Ejecución de la consulta
        ResultSet querySet = statement.executeQuery(sql);
        // Recorrido del resultado de la consulta
        if (querySet.next()) {
            String nombre = querySet.getString("nombre");
            String apellidos = querySet.getString("apellidos");
            long grupo = querySet.getInt("grupo");
            try {
                Date fecha_nac = querySet.getDate("fecha_nac");
                result = new Alumno(id, nombre, apellidos, fecha_nac, grupo == 0 ? null : grupo);
            } catch (Exception e) {
                result = new Alumno(id, nombre, apellidos, null, grupo == 0 ? null : grupo);
            }
        }
        statement.close();
        return result;

    }

    public long create(Alumno object) throws SQLException {

      
        String sqlaux = String.format("INSERT INTO alumnos (nombre, apellidos, fecha_nac, grupo) VALUES (?, ?, ?, ?)");
        PreparedStatement prepst = this.conn.prepareStatement(sqlaux, Statement.RETURN_GENERATED_KEYS);

        prepst.setString(1, object.getNombre());
        prepst.setString(2, object.getApellidos());

        if (object.getFecha_nac() == null)
            prepst.setNull(3, java.sql.Types.DATE);
        else
            prepst.setDate(3, object.getFecha_nac());

        if (object.getGrupo() != null)
            prepst.setLong(4, object.getGrupo());
        else
            prepst.setNull(4, java.sql.Types.BIGINT);

        // Ejecución de la consulta
       
        prepst.execute();
        ResultSet keys = prepst.getGeneratedKeys();
        if (keys.next()) {
            long id = keys.getLong(1);
            prepst.close();
            return id;
        } else {
            throw new SQLException("Creating user failed, no rows affected.");
        }

    }

    public int update(Alumno object) throws SQLException {
        long id = object.getId();
        String nombre = object.getNombre();
        String apellidos = object.getApellidos();
        Long grupo = object.getGrupo();
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("UPDATE alumnos SET nombre = '%s', apellidos = '%s', grupo = %d WHERE id=%d", nombre,
                apellidos, grupo, id);
        // Ejecución de la consulta
        int affectedRows = statement.executeUpdate(sql);
        statement.close();
        if (affectedRows == 0)
            throw new SQLException("Creating user failed, no rows affected.");
        else
            return affectedRows;
    }

    public boolean delete(long id) throws SQLException {
        Statement statement = null;
        statement = this.conn.createStatement();
        String sql = String.format("DELETE FROM alumnos WHERE id=%d", id);
        // Ejecución de la consulta
        int result = statement.executeUpdate(sql);
        statement.close();
        return result == 1;
    }
}
