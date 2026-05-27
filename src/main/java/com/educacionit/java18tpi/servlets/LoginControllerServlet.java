package com.educacionit.java18tpi.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.educacionit.java18tpi.dao.JugadorImpl;
import com.educacionit.java18tpi.entidades.Jugador;


@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JugadorImpl jugadorDAO;

    @Override
    public void init() throws ServletException {
        this.jugadorDAO = new JugadorImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usernameInput = request.getParameter("username");
        String passwordInput = request.getParameter("password");

        // Obtenemos todos los jugadores (En producción usarías un método específico getByNicknameOrEmail)
        List<Jugador> jugadores = jugadorDAO.getAll();

        // Aplicamos un Stream para buscar si existe el jugador con esas credenciales
        Jugador jugadorAutenticado = jugadores.stream()
                .filter(j -> (j.getNickname().equalsIgnoreCase(usernameInput) || j.getEmail().equalsIgnoreCase(usernameInput)))
                .filter(j -> j.getPassword().equals(passwordInput)) // Nota: En producción las contraseñas van cifradas
                .findFirst()
                .orElse(null);

        if (jugadorAutenticado != null) {
            // Credenciales válidas: Creamos la sesión e inyectamos al usuario logueado
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", jugadorAutenticado);
            
            // Redirigimos al Dashboard para que cargue la lista de torneos
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
        } else {
            // Credenciales incorrectas: Volvemos al login con mensaje de error
            request.setAttribute("errorMsg", "Clanes denegados. Nickname/Email o contraseña incorrectos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Capturamos la sesión actual si existe (false evita que cree una nueva)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // 2. Removemos los atributos por seguridad (opcional pero buena práctica)
            session.removeAttribute("usuarioLogueado");
            
            // 3. Destruimos la sesión por completo en el contenedor (Tomcat)
            session.invalidate();
        }
        
        // 4. Redirigimos al usuario al Login usando el Context Path para evitar errores de rutas
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }    
}