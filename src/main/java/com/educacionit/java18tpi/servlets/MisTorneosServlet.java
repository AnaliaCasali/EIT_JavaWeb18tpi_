package com.educacionit.java18tpi.servlets;

 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.educacionit.java18tpi.dao.InscripcionImpl;
import com.educacionit.java18tpi.entidades.Inscripcion;
import com.educacionit.java18tpi.entidades.Jugador;

@WebServlet("/MisTorneosServlet")
public class MisTorneosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InscripcionImpl inscripcionDAO;

    @Override
    public void init() throws ServletException {
        // Inicializamos el DAO de inscripciones
        this.inscripcionDAO = new InscripcionImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Control de seguridad: Validar que el jugador tenga una sesión activa
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // 2. Extraer el objeto Jugador completo guardado en la sesión
        Jugador jugadorLogueado = (Jugador) session.getAttribute("usuarioLogueado");

        // 3. Recuperar la lista total de inscripciones desde la base de datos
        // Nota: Tu método getAll() en InscripcionImpl debe incluir los objetos relacionados (Torneo y Jugador) via INNER JOIN
        List<Inscripcion> todasLasInscripciones = inscripcionDAO.getAll();

        // 4. FILTRADO CON JAVA STREAMS: 
        // Dejamos pasar solo las inscripciones cuyo ID de jugador coincida con el de la sesión
        List<Inscripcion> misInscripcionesFiltradas = todasLasInscripciones.stream()
                .filter(i -> i.getJugador() != null && i.getJugador().getId() == jugadorLogueado.getId())
                .sorted() // Aplica el orden del compareTo (por fecha cronológica si lo definiste en tu Entidad)
                .toList(); // Standard Java 16+

        // 5. Inyectar la colección resultante en el RequestScope para el JSP
        request.setAttribute("listaInscripciones", misInscripcionesFiltradas);

        // 6. Despachar la vista final
        request.getRequestDispatcher("mis-torneos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigimos cualquier petición POST al método doGet para centralizar la lectura
        doGet(request, response);
    }
}