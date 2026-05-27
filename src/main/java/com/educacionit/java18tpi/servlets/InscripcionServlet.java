package com.educacionit.java18tpi.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.educacionit.java18tpi.dao.InscripcionImpl;
import com.educacionit.java18tpi.entidades.Inscripcion;
import com.educacionit.java18tpi.entidades.Jugador;
import com.educacionit.java18tpi.entidades.Torneo;

@WebServlet("/InscripcionServlet")
public class InscripcionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InscripcionImpl inscripcionDAO;

    @Override
    public void init() throws ServletException {
        // Inicialización segura del Objeto de Acceso a Datos
        this.inscripcionDAO = new InscripcionImpl();
    }

    /**
     * PROCESA LA INSCRIPCIÓN (Acción invisible en la sombra)
     * Se ejecuta cuando el jugador presiona el botón "Inscribirme" en las Cards del Dashboard.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Control de seguridad: Verificar sesión activa
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // 2. Extraer el Jugador logueado de la sesión y el ID del torneo del formulario
        Jugador jugadorLogueado = (Jugador) session.getAttribute("usuarioLogueado");
        
        String idTorneoParam = request.getParameter("idTorneo");
        if (idTorneoParam == null || idTorneoParam.isEmpty()) {
            // Si por alguna razón el parámetro llega vacío, se cancela y vuelve al Lobby
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            return;
        }
        
        int idTorneo = Integer.parseInt(idTorneoParam);

        // 3. Recuperar el histórico de inscripciones desde la base de datos
        List<Inscripcion> todasInscripciones = inscripcionDAO.getAll();
        
        // 4. USO DE STREAMS: Validar de forma segura que no exista una inscripción idéntica
        boolean yaInscrito = todasInscripciones.stream()
                .filter(i -> i.getJugador() != null && i.getTorneo() != null) // Limpieza preventiva de valores nulos
                .anyMatch(i -> i.getJugador().getId() == jugadorLogueado.getId() && i.getTorneo().getId() == idTorneo);

        // 5. Si el jugador es apto y no está registrado en el torneo, se realiza el alta
        if (!yaInscrito) {
            // Creamos los objetos relacionales mínimos requeridos para las llaves foráneas (FK)
            Torneo torneoMapeado = new Torneo();
            torneoMapeado.setId(idTorneo);

            Inscripcion nuevaInscripcion = new Inscripcion();
            nuevaInscripcion.setJugador(jugadorLogueado);
            nuevaInscripcion.setTorneo(torneoMapeado);
            nuevaInscripcion.setFechaInscripcion(LocalDateTime.now()); // Marca de tiempo actual e inmutable

            // Persistencia en la Base de Datos
            inscripcionDAO.insert(nuevaInscripcion);
        }

        // 6. ¡CORREGIDO!: Redirección limpia hacia el controlador encargado de leer y renderizar la vista
        // Esto evita duplicaciones accidentales en la base de datos si el usuario refresca la pantalla (F5)
        response.sendRedirect(request.getContextPath() + "/MisTorneosServlet");
    }

    /**
     * CONTROL DE NAVEGACIÓN INDIRECTA
     * Si un usuario intenta forzar la URL tecleando de forma directa "/InscripcionServlet" en el navegador,
     * este método captura la petición GET y lo devuelve al Lobby principal de manera elegante.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/DashboardServlet");
    }
}