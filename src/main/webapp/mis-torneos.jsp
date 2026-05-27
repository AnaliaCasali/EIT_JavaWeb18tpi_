<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GamerMatch - Mis Torneos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/estilo.css'/>">
</head>
<body>

    <%-- INCLUSIÓN DE NAVBAR COMÚN --%>
    <jsp:include page="navbar.jsp" />
    <main class="container">
        <div class="row g-4">
            
            <section class="col-lg-4">
                <div class="card launcher-card p-4 text-center">
                    <div class="mb-3">
                        <div class="d-inline-flex p-3 rounded-circle bg-dark border border-info border-2 shadow-lg mb-2">
                            <i class="bi bi-person-bounding-box text-info display-5"></i>
                        </div>
                        <h4 class="text-white fw-bold m-0"><c:out value="${sessionScope.usuarioLogueado.nickname}"/></h4>
                        <small class="text-muted"><c:out value="${sessionScope.usuarioLogueado.email}"/></small>
                    </div>
                    
                    <div class="border-top border-secondary pt-3 text-start">
                        <p class="small text-secondary mb-2">ESTADÍSTICAS DE LA CUENTA</p>
                        <div class="d-flex justify-content-between mb-2">
                            <span>Rango de Competición:</span>
                            <span class="text-warning fw-bold"><c:out value="${sessionScope.usuarioLogueado.rango}"/></span>
                        </div>
                        <div class="d-flex justify-content-between">
                            <span>Torneos Activos:</span>
                            <span class="text-cyan fw-bold text-info">
                                <c:out value="${not empty requestScope.listaInscripciones ? requestScope.listaInscripciones.size() : 0}"/>
                            </span>
                        </div>
                    </div>
                </div>
            </section>

            <section class="col-lg-8">
                <div class="card tournament-card p-4 h-100">
                    <h3 class="fw-bold text-white mb-4">
                        <i class="bi bi-journal-check me-2 text-info"></i>Mis Squads y Torneos
                    </h3>

                    <c:choose>
                        <%-- Si el jugador tiene inscripciones registradas en la base de datos --%>
                        <c:when test="${not empty requestScope.listaInscripciones}">
                            <div class="table-responsive">
                                <table class="table table-dark table-hover align-middle border-secondary m-0">
                                    <thead>
                                        <tr class="text-secondary small">
                                            <th scope="col">TORNEO</th>
                                            <th scope="col">JUEGO / PLATAFORMA</th>
                                            <th scope="col">FECHA INSCRIPCIÓN</th>
                                            <th scope="col" class="text-end">ACCIONES</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="inscripcion" items="${requestScope.listaInscripciones}">
                                            <tr>
                                                <td>
                                                    <div class="fw-bold text-white"><c:out value="${inscripcion.torneo.nombreTorneo}"/></div>
                                                    <small class="text-muted">ID Inscripción: #<c:out value="${inscripcion.id}"/></small>
                                                </td>
                                                <td>
                                                    <span class="badge game-badge text-uppercase"><c:out value="${inscripcion.torneo.nombreJuego}"/></span>
                                                    <div class="small text-muted mt-1"><i class="bi bi-display me-1"></i><c:out value="${inscripcion.torneo.plataforma}"/></div>
                                                </td>
                                                <td class="small text-light">
                                                    <c:set var="fecha" value="${inscripcion.fechaInscripcion}"/>
                                                    <% 
                                                        java.time.LocalDateTime ldt = (java.time.LocalDateTime) pageContext.getAttribute("fecha");
                                                        if (ldt != null) {
                                                            out.print(ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                                                        }
                                                    %>
                                                </td>
                                                <td class="text-end">
                                                    <form action="<c:url value='/BajaInscripcionServlet'/>" 
                                                       method="POST" onsubmit="return confirm('¿Seguro que deseas abandonar este torneo? Perderás tu cupo reservado.');">
                                                        <input type="hidden" name="idInscripcion" value="${inscripcion.id}">
                                                        <button type="submit" class="btn btn-outline-danger btn-sm">
                                                            <i class="bi bi-trash3-fill"></i> Abandonar
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>

                        <%-- En caso de que no esté inscrito a ningún evento --%>
                        <c:otherwise>
                            <div class="text-center py-5">
                                <div class="alert alert-gamer d-inline-block p-4 rounded-3">
                                    <i class="bi bi-calendar-x display-5 d-block mb-3 text-secondary"></i>
                                    <h5 class="fw-bold">No estás anotado en ningún torneo todavía</h5>
                                    <p class="small text-muted mb-3">Explora la arena competitiva y asegura tu lugar en los próximos despliegues.</p>
                                    <a href="<c:url value='/DashboardServlet'/>" class="btn btn-gamer btn-sm px-4">
                                        Buscar Torneos
                                    </a>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
            
        </div>
    </main>
	<jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>