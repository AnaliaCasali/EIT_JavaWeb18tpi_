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

        try {
            // Forzamos a que intente traer los datos
            List<Jugador> jugadores = jugadorDAO.getAll();

            // Aplicamos un Stream para buscar si existe el jugador
            Jugador jugadorAutenticado = jugadores.stream()
                .filter(j -> (j.getNickname().equalsIgnoreCase(usernameInput) || j.getEmail().equalsIgnoreCase(usernameInput)))
                .filter(j -> j.getPassword().equals(passwordInput))
                .findFirst()
                .orElse(null);





            if (jugadorAutenticado != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado",
                    jugadorAutenticado);
                response.sendRedirect(request.getContextPath()
                    + "/DashboardServlet");
            } else {
                request.setAttribute("errorMsg", "Clanes denegados. Nickname/Email o contraseña incorrectos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }




        } catch (Exception e) {
            // SI ALGO FALLA, ESTO LO MUESTRA EN LA PANTALLA EN LUGAR DEL ERROR 500
            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(500);
            response.getWriter().println("--- ERROR DETECTADO EN EL SERVLET ---");
            e.printStackTrace(response.getWriter());
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
        response.sendRedirect(request.getContextPath() +
            "/index.jsp");
    }    
}


