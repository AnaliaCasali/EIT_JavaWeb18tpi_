package com.educacionit.java18tpi.servlets;

 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import com.educacionit.java18tpi.dao.InscripcionImpl;

@WebServlet("/BajaInscripcionServlet")
public class BajaInscripcionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InscripcionImpl inscripcionDAO;

    @Override
    public void init() throws ServletException {
        // Inicialización del DAO de inscripciones
        this.inscripcionDAO = new InscripcionImpl();
    }

    /**
     * PROCESA LA BAJA DEL TORNEO
     * Se activa al enviar el formulario oculto de desvinculación desde mis-torneos.jsp
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Filtro de Seguridad: Evitar que usuarios no autenticados ejecuten bajas
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. Recuperar el ID de la inscripción que se quiere eliminar
        String idInscripcionParam = request.getParameter("idInscripcion");
        
        if (idInscripcionParam != null && !idInscripcionParam.isEmpty()) {
            try {
                int idInscripcion = Integer.parseInt(idInscripcionParam);
                
                // 3. Ejecutar la baja física del registro en la Base de Datos
                inscripcionDAO.delete(idInscripcion);
                
            } catch (NumberFormatException e) {
                // Registro de log o control de errores si el ID enviado está corrupto
                e.printStackTrace();
            }
        }

        // 4. Redirección PRG: Volver a cargar el listado actualizado del jugador
        // Al usar un redirect, el navegador actualiza la URL y evita la re-ejecución del borrado al presionar F5
        response.sendRedirect(request.getContextPath() + "/MisTorneosServlet");
    }

    /**
     * CONTROL DE NAVEGACIÓN ERRÓNEA
     * Si intentan entrar escribiendo la URL directamente en la barra de navegación (GET),
     * los mandamos de manera segura de vuelta al listado de sus torneos.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/MisTorneosServlet");
    }
}