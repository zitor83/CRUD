import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import alumnos.Alumno;
import alumnos.AlumnosGrupoService;
import alumnos.AlumnosService;
import grupos.GruposService;
import grupos.Grupo;

import connection.ConnectionPool;

public class AlumnosApp {
    AlumnosService alumnosSvc;
    GruposService gruposSvc;
    AlumnosGrupoService alumnosGrupoSvc;
    ConnectionPool pool;

    public AlumnosApp() {
        this.pool = new ConnectionPool(
                "jdbc:mysql://localhost:3306/alumnos2",
                "jose",
                "1234");
        try {
            Connection conn = pool.getConnection();
            this.alumnosSvc = new AlumnosService(conn);
            this.gruposSvc = new GruposService(conn);
            this.alumnosGrupoSvc = new AlumnosGrupoService(alumnosSvc);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ocurrió un error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Termina el programa si la conexión falla
        }

    }

    private void mostrarMenu() {
        System.out.println("1. Gestión de alumnos");
        System.out.println("2. Gestión de grupos");
        System.out.println("3. Salir");
    }

    private void mostrarMenuAlumnos() {
        System.out.println("1. Listar alumnos");
        System.out.println("2. Crear un alumno");
        System.out.println("3. Actualizar un alumno");
        System.out.println("4. Borrar un alumno");
        System.out.println("5. Atrás");
    }

    private void mostrarMenuGrupos() {
        System.out.println("1. Listar grupos");
        System.out.println("2. Listar alumnos de un grupo");
        System.out.println("3. Crear un grupo");
        System.out.println("4. Actualizar un grupo");
        System.out.println("5. Borrar un grupo");
        System.out.println("6. Matricular alumno en grupo");
        System.out.println("7. Atrás");

    }

    private void mostrarSubmenu(int opcion) {
        switch (opcion) {
            case 1:
                mostrarMenuAlumnos();
                break;
            case 2:
                mostrarMenuGrupos();
                break;
            case 3:
            default:
        }
    }

    private void listarAlumnosGrupo() {
        try {
            System.out.println("Listado de grupos");
            for (Grupo gr : gruposSvc.requestAll()) {
                System.out.println(gr);
            }
            System.out.print("Introduzca el grupo a listar: ");
            Long grupo = Long.parseLong(System.console().readLine());
            Grupo gr = gruposSvc.requestById(grupo);
            System.out.println("Alumnos del grupo: " + gr.getNombre());
            for (Alumno al : alumnosGrupoSvc.filtrarAlumnos(grupo)) {
                System.out.println(al);
            }
            System.out.println("Pulse intro para continuar...");
            System.console().readLine();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void listarAlumnos() {
        try {
            ArrayList<Alumno> alumnos = alumnosSvc.requestAll();
            for (Alumno al : alumnos) {
                System.out.println(al);
            }
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    private void crearAlumno() {
        try {
            System.out.println("Introduzca el nombre:");
            String nombre = System.console().readLine();
            System.out.println("Introduzca el Apellido:");
            String apellido = System.console().readLine();
            System.out.print("Introduce la fecha de nacimiento (YYYY-MM-DD): ");
            String fechaStr = System.console().readLine();
            Date fecha_nac = Date.valueOf(fechaStr); // java.sql.Date

            Alumno al = new Alumno(0L, nombre, apellido, fecha_nac, null);

            Long nuevoid = alumnosSvc.create(al);
            al.setId(nuevoid);
            System.out.println(al);
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void modificarAlumno() {
        try {
            System.out.print("Introduzca el id: ");
            Long id = Long.parseLong(System.console().readLine());

            Alumno alumno = alumnosSvc.requestById(id);

            System.out.println("Actualizarás el alumno con ID " + id);
            System.out.println(alumno);

            System.out.print("Nombre: ");
            String nombre = System.console().readLine();
            System.out.print("Apellidos: ");
            String apellidos = System.console().readLine();
            System.out.println("Fecha de nacimiento (YYYY-MM-DD):");

            String fechaStr = System.console().readLine();
            Date fechaNac = Date.valueOf(fechaStr); // java.sql.Date

            System.out.println("Grupo:");
            Long grupo = Long.parseLong(System.console().readLine());

            alumno.setNombre(nombre);
            alumno.setApellidos(apellidos);
            alumno.setFecha_nac(fechaNac);
            alumno.setGrupo(grupo);
            System.out.println("El alumno actualizado es:");
            System.out.println(alumno);
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void borrarAlumno() {
        try {
            System.out.println("Introduzca el id a borrar: ");
            Long id = Long.parseLong(System.console().readLine());
            Alumno alumno = alumnosSvc.requestById(id);
            System.out.println("El alumno a borrar es: " + alumno);
            System.out.println("¿Está seguro? (S/N)");
            String confirmacion = System.console().readLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                if (alumnosSvc.delete(id))
                    System.out.println("El alumno se ha eliminado");
                else
                    System.out.println("Ha ocurrido un error");
            } else {
                System.out.println("Operación cancelada");
            }
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void listarGrupos() {
        try {
            ArrayList<Grupo> grupos = gruposSvc.requestAll();
            for (Grupo gr : grupos) {
                System.out.println(gr);
            }
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
private void crearGrupo() {
        try {
            System.out.println("Introduzca el nombre del grupo:");
            String nombre = System.console().readLine();


            Grupo gr = new Grupo(0L, nombre);

            Long nuevoid = gruposSvc.create(gr);
            gr.setId(nuevoid);
            System.out.println(gr);

            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

private void actualizarGrupo() {
        try {
            System.out.print("Introduzca el id del grupo a modificar: ");
            Long id = Long.parseLong(System.console().readLine());

            Grupo grupo = gruposSvc.requestById(id);

            System.out.println("Actualizarás el grupo con ID " + id);
            System.out.println(grupo);

            System.out.print("Nombre del grupo: ");
            String nombre = System.console().readLine();

            grupo.setNombre(nombre);

            System.out.println("El grupo actualizado es:");
            System.out.println(grupo);
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void borrarGrupo() {
        try {
            System.out.println("Introduzca el id del grupo a borrar: ");
            Long id = Long.parseLong(System.console().readLine());
            Grupo grupo = gruposSvc.requestById(id);
            System.out.println("El grupo a borrar es: " + grupo);
            System.out.println("¿Está seguro? (S/N)");
            String confirmacion = System.console().readLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                if (gruposSvc.delete(id))
                    System.out.println("El grupo se ha eliminado");
                else
                    System.out.println("Ha ocurrido un error");
            } else {
                System.out.println("Operación cancelada");
            }
            System.out.println("Pulsa intro para continuar...");
            System.console().readLine();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void matricularAlumno() {
        try {
            System.out.println("Listado de alumnos: ");
            ArrayList<Alumno> alumnos = alumnosSvc.requestAll();
            for (Alumno al : alumnos) {
                System.out.println(al);
            }
            System.out.println("Indique el identificador del alumno a matricular: ");
            Long id = Long.parseLong(System.console().readLine());
            Alumno al = alumnosSvc.requestById(id);
            System.out.println("Listado de grupos");
            ArrayList<Grupo> grupos = gruposSvc.requestAll();
            for (Grupo gr : grupos) {
                System.out.println(gr);
            }
            System.out.println("Introduzca el id del grupo donde se va a matricular");
            id = Long.parseLong(System.console().readLine());
            Grupo gr = gruposSvc.requestById(id);
            al.setGrupo(gr.getId());
            if (al != null) {
                alumnosSvc.update(al);
            }

        } catch (SQLException e) {

        }
    }

    private int leerOpcion() {
        return Integer.parseInt(System.console().readLine());
    }

    public void init() {
        boolean salir = false;
        boolean salirSubmenu = false;
        int opcion1 = 1;
        int opcion2 = 1;
        while (!salir) {
            mostrarMenu();
            opcion1 = leerOpcion();
            switch (opcion1) {
                case 1:
                    salirSubmenu = false;
                    while (!salirSubmenu) {
                        mostrarSubmenu(opcion1);
                        opcion2 = leerOpcion();
                        switch (opcion2) {
                            case 1:
                                listarAlumnos();
                                break;
                            case 2:
                                crearAlumno();
                                break;
                            case 3:
                                modificarAlumno();
                                break;
                            case 4:
                                borrarAlumno();
                                break;
                            case 5:
                                salirSubmenu = true;
                                break;
                            default:
                                System.out.println("Opción incorrecta");
                        }
                    }

                    break;
                case 2:
                    salirSubmenu = false;
                    while (!salirSubmenu) {
                        mostrarSubmenu(opcion1);
                        opcion2 = leerOpcion();
                        switch (opcion2) {
                            case 1:
                                listarGrupos();
                                break;
                            case 2:
                                listarAlumnosGrupo();
                                break;
                            case 3:
                                crearGrupo();
                                break;
                            case 4:
                                actualizarGrupo();
                                break;
                            case 5:
                                borrarGrupo();
                                break;
                            case 6:
                                matricularAlumno();
                                break;
                            case 7:
                                salirSubmenu = true;
                                break;
                            default:
                                System.out.println("Opción incorrecta");
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        }
        /*
         * System.out.println("Hello, World!");
         * 
         * Connection conn = pool.getConnection();
         * AlumnosService alumnosSvc = new AlumnosService(conn);
         * ArrayList<Alumno> alumnos = alumnosSvc.requestAll();
         * for(Alumno alumno: alumnos){
         * System.out.println(alumno);
         * }
         * 
         * Alumno al = new Alumno(0, "bartolo", "vaquero", null);
         * Long nuevoid = alumnosSvc.create(al);
         * al.setId(nuevoid);
         * System.out.println(al);
         * 
         * System.out.print("Introduzca el id: ");
         * Long id = Long.parseLong(System.console().readLine());
         * Alumno alumno = alumnosSvc.requestById(id);
         * System.out.println(alumno);
         * 
         * System.out.println("Acutaliza alumno 1:");
         * System.out.print("Nombre: ");
         * String nombre = System.console().readLine();
         * System.out.print("Apellidos: ");
         * String apellidos = System.console().readLine();
         * alumno = new Alumno(1, nombre, apellidos, null);
         * alumno.setId(alumnosSvc.update(alumno));
         * System.out.println("El alumno actualizado es:");
         * System.out.println(alumno);
         * 
         * System.out.println("Introduzca el id a borrar: ");
         * id = Long.parseLong(System.console().readLine());
         * if(alumnosSvc.delete(id))
         * System.out.println("El alumno se ha eliminado");
         * else
         * System.out.println("Ha ocurrido un error");
         * conn.close();
         * pool.closeAll();
         */

    }
}
