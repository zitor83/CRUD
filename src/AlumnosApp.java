import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import alumnos.Alumno;
import alumnos.AlumnosGrupoService;
import alumnos.AlumnosService;

import grupos.GruposService;
import grupos.Grupo;

import tutores.TutoresService;
import tutores.Tutor;

import connection.ConnectionPool;

public class AlumnosApp {
    AlumnosService alumnosSvc;
    GruposService gruposSvc;
    AlumnosGrupoService alumnosGrupoSvc;
    TutoresService tutoresSvc;
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
            this.tutoresSvc = new TutoresService(conn);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ocurrió un error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Termina el programa si la conexión falla
        }

    }

    private void mostrarMenu() {
        espaciadoPantallaSimple();
        System.out.println("==========================");
        System.out.println("======MENU PRINCIPAL======");
        System.out.println("==========================");
        System.out.println("");
        System.out.println("1. Gestión de alumnos");
        System.out.println("2. Gestión de grupos");
        System.out.println("3. Gestión de tutores");
        System.out.println("4. Salir");
        System.out.println();

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
                mostrarMenuTutores();
            default:
        }
    }

    private void mostrarMenuAlumnos() {
        espaciadoPantallaSimple();
        System.out.println("==========================");
        System.out.println("=======MENU ALUMNOS=======");
        System.out.println("==========================");
        System.out.println("");
        System.out.println("1. Listar alumnos");
        System.out.println("2. Crear un alumno");
        System.out.println("3. Actualizar un alumno");
        System.out.println("4. Borrar un alumno");
        System.out.println("5. Atrás");
        System.out.println("");

    }

    private void mostrarMenuTutores() {
        espaciadoPantallaSimple();
        System.out.println("==========================");
        System.out.println("=======MENU TUTORES=======");
        System.out.println("==========================");
        System.out.println("");
        System.out.println("1. Listar tutores");
        System.out.println("2. Crear un tutor");
        System.out.println("3. Actualizar un tutor");
        System.out.println("4. Borrar un tutor");
        System.out.println("5. Atrás");
        System.out.println("");

    }

    private void mostrarMenuGrupos() {
        espaciadoPantallaSimple();
        System.out.println("==========================");
        System.out.println("=======MENU GRUPOS========");
        System.out.println("==========================");
        System.out.println("");
        System.out.println("1. Listar grupos");
        System.out.println("2. Listar alumnos de un grupo");
        System.out.println("3. Crear un grupo");
        System.out.println("4. Actualizar un grupo");
        System.out.println("5. Borrar un grupo");
        System.out.println("6. Matricular alumno en grupo");
        System.out.println("7. Atrás");
        System.out.println("");

    }

    private long leerIdValido(String mensaje) {
        Long id = null;
        do {
            System.out.println(mensaje);
            String entrada = System.console().readLine();
            try {
                id = Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("El ID debe ser un número válido. Inténtelo de nuevo.");
            }
        } while (id == null);
        return id;
    }

    private Alumno leerAlumnoExistente() throws SQLException {
        Alumno alumno = null;
        do {
            Long id = leerIdValido("Introduzca el ID del alumno: ");
            alumno = alumnosSvc.requestById(id);
            if (alumno == null) {
                System.out.println("No existe ningún alumno con ese ID. Inténtelo de nuevo.");
            }
        } while (alumno == null);
        return alumno;
    }

    private Tutor leerTutorExistente() throws SQLException {
        Tutor tut = null;
        do {
            Long id = leerIdValido("Introduzca el ID del tutor: ");
            tut = tutoresSvc.requestById(id);
            if (tut == null) {
                System.out.println("No existe ningún tutor con ese ID. Inténtelo de nuevo.");
            }
        } while (tut == null);
        return tut;
    }

    private Grupo leerGrupoExistente() throws SQLException {
        Grupo grupo = null;
        do {
            Long id = leerIdValido("Introduzca el ID del grupo: ");
            grupo = gruposSvc.requestById(id);
            if (grupo == null) {
                System.out.println("No existe ningún grupo con ese ID. Inténtelo de nuevo.");
            }
        } while (grupo == null);
        return grupo;
    }

    private void listarAlumnos() {
        try {
            ArrayList<Alumno> alumnos = alumnosSvc.requestAll();
            for (Alumno al : alumnos) {
                System.out.println(al);
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
        espaciadoPantalla();
    }

    private void crearAlumno() {
        try {

            String nombre; // Validar nombre no vacíos
            do {
                System.out.print(" Introduzca el nombre: ");
                nombre = System.console().readLine();
                if (nombre == null || nombre.trim().isEmpty()) {
                    System.out.println(" El nombre no puede estar vacío. Por favor, introduzca un nombre válido.");
                }
            } while (nombre == null || nombre.trim().isEmpty());

            String apellido; // Validar apellido no vacío
            do {
                System.out.print(" Introduzca el apellido: ");
                apellido = System.console().readLine();
                if (apellido == null || apellido.trim().isEmpty()) {
                    System.out.println(" El apellido no puede estar vacío. Por favor, introduzca un apellido válido.");
                }
            } while (apellido == null || apellido.trim().isEmpty());

            Date fecha_nac; // Validar fecha de nacimiento
            do {
                System.out.print("Introduce la fecha de nacimiento (YYYY-MM-DD): ");
                String fechaStr = System.console().readLine();
                try {
                    fecha_nac = Date.valueOf(fechaStr);
                } catch (IllegalArgumentException e) {
                    System.out.println(
                            " La fecha de nacimiento no es válida. Por favor, introduzca una fecha en formato YYYY-MM-DD.");
                    fecha_nac = null;
                }
            } while (fecha_nac == null);

            Alumno al = new Alumno(0L, nombre, apellido, fecha_nac, null);
            Long nuevoid = alumnosSvc.create(al);
            al.setId(nuevoid);
            System.out.println(" Nuevo alumno creado correctamente ");
            System.out.println(al);

            espaciadoPantalla();
        } catch (

        SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void modificarAlumno() {
        try {
            // Validar que el ID existe para que no de error llamando a leerAlumnoExistente
            listarAlumnos();
            Alumno alumno = leerAlumnoExistente();

            System.out.println("Actualizarás el alumno con ID " + alumno.getId());
            System.out.println(alumno);

            System.out.print("Nuevo Nombre(pulse enter para mantener actual): ");
            String nombre = System.console().readLine();
            if (nombre.isBlank() || nombre == null)
                nombre = alumno.getNombre();

            System.out.print("Nuevos Apellidos(pulse enter para mantener actual): ");
            String apellidos = System.console().readLine();
            if (apellidos.isBlank() || apellidos == null)
                apellidos = alumno.getApellidos();

            // Validar fecha de nacimiento para que no de error
            Date fecha_nac = null;
            do {
                System.out.print("Introduce la fecha de nacimiento (YYYY-MM-DD) (pulse enter para mantener actual): ");
                String fechaStr = System.console().readLine();
                if (fechaStr == null || fechaStr.isBlank()) {
                    fecha_nac = alumno.getFecha_nac();
                    break;
                }

                try {
                    fecha_nac = Date.valueOf(fechaStr);
                } catch (IllegalArgumentException e) {
                    System.out.println(
                            " La fecha de nacimiento no es válida. Por favor, introduzca una fecha en formato YYYY-MM-DD o pulse enter para mantener la actual.");
                    fecha_nac = null;
                }
            } while (fecha_nac == null);

            System.out.println("Listado de Grupos disponibles:");
            listarGrupos();
            Long grupo = null;
            do {

                System.out.println(
                        "Pulse Enter para mantener el grupo actual, o introduzca el ID de un grupo disponible:");
                String grupoStr = System.console().readLine();

                if (grupoStr.isBlank() || grupoStr == null) {
                    grupo = alumno.getGrupo(); // mantener actual
                    break;
                }

                try {
                    Long grupoId = Long.parseLong(grupoStr);

                    Grupo grupoBD = gruposSvc.requestById(grupoId);
                    if (grupoBD != null) {
                        grupo = grupoId; // existe y salimos del bucle
                    } else {
                        System.out.println("El grupo con ID " + grupoId + " no existe. Inténtelo de nuevo.");
                        grupo = null; // repetir bucle por grupo no existente
                    }

                } catch (NumberFormatException e) {
                    System.out.println(" Entrada no válida. Introduzca un número o pulse Enter.");
                    grupo = null; // repetir bucle por error de formato
                }

            } while (grupo == null);

            alumno.setNombre(nombre);
            alumno.setApellidos(apellidos);
            alumno.setFecha_nac(fecha_nac);
            alumno.setGrupo(grupo);
            alumnosSvc.update(alumno);

            System.out.println("Alumno actualizado correctamente:");
            System.out.println(alumno);
            espaciadoPantalla();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void borrarAlumno() {
        try {
            listarAlumnos();
            Alumno alumno = leerAlumnoExistente();

            System.out.println("El alumno a borrar es: " + alumno);
            System.out.print("¿Está seguro? (S/N): ");
            String confirmacion = System.console().readLine().trim();

            if (confirmacion.equalsIgnoreCase("S")) {
                if (alumnosSvc.delete(alumno.getId())) {
                    System.out.println("El alumno ha sido eliminado.");
                } else {
                    System.out.println("No se pudo eliminar el alumno.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }

            espaciadoPantalla();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al borrar: " + e.getMessage());
        }
    }

    private void listarGrupos() {
        try {
            ArrayList<Grupo> grupos = gruposSvc.requestAll();
            for (Grupo gr : grupos) {
                System.out.println(gr);

            }

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
        espaciadoPantalla();
    }

    private void listarAlumnosGrupo() {
        try {
            System.out.println("Listado de grupos disponibles:");
            System.out.println("ID: 0 - Sin grupo"); // opción especial
            listarGrupos();

            Long grupoId = null;

            do {
                System.out.print("Introduzca el ID del grupo a listar (0 para alumnos sin grupo): ");
                String entrada = System.console().readLine();

                try {
                    grupoId = Long.parseLong(entrada);
                } catch (NumberFormatException e) {
                    System.out.println("El ID debe ser un número. Inténtelo de nuevo.");
                    grupoId = null;
                }
            } while (grupoId == null);

            ArrayList<Alumno> alumnos;

            if (grupoId == 0L) { // listar sin grupo
                alumnos = alumnosGrupoSvc.filtrarAlumnos(null);
                System.out.println("Alumnos sin grupo:");
            } else {
                Grupo gr = gruposSvc.requestById(grupoId);
                if (gr == null) {
                    System.out.println("No existe ningún grupo con el ID " + grupoId);
                    espaciadoPantalla();
                    return;
                }
                alumnos = alumnosGrupoSvc.filtrarAlumnos(grupoId);
                System.out.println("Alumnos del grupo: " + gr.getNombre());
            }

            if (alumnos.isEmpty()) {
                System.out.println("No hay alumnos en este grupo.");
            } else {
                for (Alumno al : alumnos) {
                    System.out.println(al);
                }
            }

            espaciadoPantalla();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al listar alumnos del grupo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void crearGrupo() {
        try {
            String nombre = null;
            do {
                System.out.print("Introduzca el nombre del grupo: ");
                nombre = System.console().readLine();
                if (nombre == null || nombre.trim().isEmpty()) {
                    System.out.println("El nombre del grupo no puede estar vacío. Inténtelo de nuevo.");
                }
            } while (nombre == null || nombre.trim().isEmpty());

            Grupo gr = new Grupo(0L, nombre, null); // tutor opcional, null por defecto

            Long nuevoid = gruposSvc.create(gr);
            gr.setId(nuevoid);

            System.out.println("Nuevo grupo creado correctamente:");
            System.out.println(gr);

            espaciadoPantalla();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al crear el grupo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void actualizarGrupo() {
        try {
            listarGrupos();
            Grupo grupo = leerGrupoExistente();
            Long id = grupo.getId();
            System.out.println("Actualizarás el grupo con ID " + id);
            System.out.println(grupo);

            // Actualizar nombre
            String nombre;
            do {
                System.out.print("Nombre del grupo (Enter para mantener actual): ");
                nombre = System.console().readLine();
                if (nombre == null || nombre.isBlank()) {
                    nombre = grupo.getNombre();
                    break;
                }
            } while (nombre.isBlank());

            // Actualizar tutor
            System.out.print("ID del tutor (Enter para mantener actual): ");
            String idTutorEntrada = System.console().readLine();
            String idTutor = idTutorEntrada.isBlank() ? grupo.getId_tutor() : idTutorEntrada;

            grupo.setNombre(nombre);
            grupo.setId_tutor(idTutor);
            gruposSvc.update(grupo);

            System.out.println("Grupo actualizado correctamente:");
            System.out.println(grupo);
            espaciadoPantalla();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void borrarGrupo() {
        try {

            listarGrupos();
            Grupo grupo = leerGrupoExistente();
            Long id = grupo.getId();

            System.out.println("El grupo a borrar es: " + grupo);
            System.out.println("¿Está seguro? (S/N)");
            String confirmacion = System.console().readLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                if (gruposSvc.delete(id))
                    System.out.println("El grupo ha sido eliminado");
                else
                    System.out.println("Ha ocurrido un error");
            } else {
                System.out.println("Operación cancelada");
            }
            espaciadoPantalla();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void matricularAlumno() {
        try {

            listarAlumnos();
            Alumno al = leerAlumnoExistente();

            listarGrupos();
            Grupo gr = leerGrupoExistente();

            System.out.println("Vas a matricular al alumno:");
            System.out.println(al);
            System.out.println("en el grupo:");
            System.out.println(gr);
            System.out.print("¿Estás seguro? (S/N): ");
            String confirmacion = System.console().readLine().trim();
            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Operación cancelada.");
                return;
            }
            // Asignación del grupo
            al.setGrupo(gr.getId());

            // Actualización
            if (alumnosSvc.update(al) > 0) {
                System.out.println("Alumno matriculado correctamente:");
                System.out.println(al);
            } else {
                System.out.println("Error: no se pudo matricular al alumno.");
            }

        } catch (SQLException e) {
            System.out.println("Ocurrió un error al matricular el alumno: " + e.getMessage());
        }
    }

    private int leerOpcion(String mensaje, int min, int max) { // método genérico para leer opción // validación
        int opcion = -1;
        do {
            System.out.print(mensaje);
            String entrada = System.console().readLine();
            try {
                opcion = Integer.parseInt(entrada);
                if (opcion < min || opcion > max) {
                    System.out.println("Número fuera de rango. Introduzca un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduzca un número entre " + min + " y " + max + ".");
            }
        } while (opcion < min || opcion > max);
        return opcion;
    }

    private void listarTutores() {
        try {
            ArrayList<Tutor> tutores = tutoresSvc.requestAll();
            for (Tutor t : tutores) {
                System.out.println(t.toString());
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
        espaciadoPantalla();
    }

    private void crearTutor() {
        try {

            String nombre; // Validar nombre no vacíos
            do {
                System.out.print(" Introduzca el nombre: ");
                nombre = System.console().readLine();
                if (nombre == null || nombre.trim().isEmpty()) {
                    System.out.println(" El nombre no puede estar vacío. Por favor, introduzca un nombre válido.");
                }
            } while (nombre == null || nombre.trim().isEmpty());

            String apellido; // Validar apellido no vacío
            do {
                System.out.print(" Introduzca el apellido: ");
                apellido = System.console().readLine();
                if (apellido == null || apellido.trim().isEmpty()) {
                    System.out.println(" El apellido no puede estar vacío. Por favor, introduzca un apellido válido.");
                }
            } while (apellido == null || apellido.trim().isEmpty());

            Tutor tut = new Tutor(0L, nombre, apellido);
            Long nuevoid = tutoresSvc.create(tut);
            tut.setId(nuevoid);
            System.out.println(" Nuevo tutor creado correctamente ");
            System.out.println(tut);

            espaciadoPantalla();
        } catch (

        SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void modificarTutor() {
        try {
            // Validar que el ID existe para que no de error llamando a leerTutorExistente
            listarTutores();
            Tutor tut = leerTutorExistente();

            System.out.println("Actualizarás el tutor con ID " + tut.getId());
            System.out.println(tut);

            System.out.print("Nuevo Nombre(pulse enter para mantener actual): ");
            String nombre = System.console().readLine();
            if (nombre.isBlank() || nombre == null)
                nombre = tut.getNombre();

            System.out.print("Nuevos Apellidos(pulse enter para mantener actual): ");
            String apellidos = System.console().readLine();
            if (apellidos.isBlank() || apellidos == null)
                apellidos = tut.getApellidos();

            tut.setNombre(nombre);
            tut.setApellidos(apellidos);
            tutoresSvc.update(tut);

            System.out.println("Tutor actualizado correctamente:");
            System.out.println(tut);
            espaciadoPantalla();

        } catch (SQLException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }

    }

    private void borrarTutor() {
        try {
            listarTutores();
            Tutor tut = leerTutorExistente();

            System.out.println("El tutor a borrar es: " + tut);
            System.out.print("¿Está seguro? (S/N): ");
            String confirmacion = System.console().readLine().trim();

            if (confirmacion.equalsIgnoreCase("S")) {
                if (tutoresSvc.delete(tut.getId())) {
                    System.out.println("El tutor ha sido eliminado.");
                } else {
                    System.out.println("No se pudo eliminar el tutor.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }

            espaciadoPantalla();
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al borrar: " + e.getMessage());
        }
    }

    private void espaciadoPantalla() {
        System.out.println("");
        System.out.println("");
        System.out.println("Pulsa intro para continuar...");
        System.out.println("");
        System.console().readLine();
    }

    private void espaciadoPantallaSimple() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

    }

    public void init() {
        boolean salir = false;
        boolean salirSubmenu = false;

        while (!salir) {
            mostrarMenu();
            int opcion1 = leerOpcion("Seleccione una opción: ", 1, 4);

            switch (opcion1) {
                case 1:
                    salirSubmenu = false;
                    while (!salirSubmenu) {
                        mostrarSubmenu(opcion1);
                        int opcion2 = leerOpcion("Seleccione una opción: ", 1, 5);

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
                                System.out.println("Opción incorrecta. Elija una opción del 1 al 5");
                        }
                    }

                    break;
                case 2:
                    salirSubmenu = false;
                    while (!salirSubmenu) {
                        mostrarSubmenu(opcion1);
                        int opcion2 = leerOpcion("Seleccione una opción: ", 1, 7);

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
                                System.out.println("Opción incorrecta. Elija una opción del 1 al 7");
                        }
                    }
                    break;
                case 3:

                    salirSubmenu = false;
                    while (!salirSubmenu) {
                        mostrarSubmenu(opcion1);
                        int opcion2 = leerOpcion("Seleccione una opción: ", 1, 5);

                        switch (opcion2) {
                            case 1:
                                listarTutores();
                                break;
                            case 2:
                                crearTutor();
                                break;
                            case 3:
                                modificarTutor();
                                break;
                            case 4:
                                borrarTutor();
                                break;
                            case 5:
                                salirSubmenu = true;
                                break;
                            default:
                                System.out.println("Opción incorrecta. Elija una opción del 1 al 5");
                        }
                    }
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción incorrecta. Elija una opción del 1 al 3.");
            }
        }

    }
}
