package com.educacionit.java18tpi.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import com.educacionit.java18tpi.dao.TorneoImpl;
import com.educacionit.java18tpi.entidades.Torneo;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TorneoImpl torneoDAO;

    @Override
    public void init() throws ServletException {
        this.torneoDAO = new TorneoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Control de seguridad: Validar si el usuario está logueado en la sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // 1. Obtener la lista desde la Base de Datos
        List<Torneo> todosLosTorneos = torneoDAO.getAll();
        
        // 2. Capturar el parámetro del filtro
        String filtro = request.getParameter("filtro");
        if (filtro == null) {
            filtro = "todos"; 
        }

        // 3. FIX: Definir el Predicado (la condición) antes del Stream para evitar el switch repetitivo
        Predicate<Torneo> condicionFiltro;
        
        switch (filtro.toLowerCase()) {
            case "gratis":
                condicionFiltro = t -> t.getPrecio() != null && t.getPrecio().doubleValue() == 0.0;
                break;
            case "pc":
                condicionFiltro = t -> t.getPlataforma() != null && t.getPlataforma().equalsIgnoreCase("PC");
                break;
            case "consola":
                // Evitamos NullPointerException validando que no sea nulo antes del upperCase
                condicionFiltro = t -> t.getPlataforma() != null && !t.getPlataforma().toUpperCase().contains("PC");
                break;
            case "todos":
            default:
                condicionFiltro = t -> true; // Deja pasar todos los elementos
                break;
        }

        // 4. PROCESAR CON STREAMS (Limpio, rápido y seguro)
        List<Torneo> listaFiltrada = todosLosTorneos.stream()
                .filter(condicionFiltro) // Aplicamos la condición ya definida
                .sorted()                // Orden natural por Comparable (Precio)
                .toList();               // Java 16+

        // 5. Enviar al RequestScope y despachar al JSP
        request.setAttribute("listaTorneos", listaFiltrada);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}