<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GamerMatch - Lobby de Torneos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/estilo.css'/>">
</head>
<body>

    <%-- INCLUSIÓN DE NAVBAR COMÚN --%>
    <jsp:include page="navbar.jsp" />
    
    <main class="container">
        
        <section class="row mb-4 align-items-center">
            <div class="col-md-4 mb-3 mb-md-0">
                <h3 class="m-0 fw-bold"><i class="bi bi-trophy me-2 text-warning"></i>Torneos Disponibles</h3>
            </div>
            <div class="col-md-8 text-md-end">
                <div class="btn-group flex-wrap" role="group" aria-label="Filtros de torneos">
                    <a href="<c:url value='/DashboardServlet?filtro=todos'/>" class="btn filter-btn ${param.filtro == 'todos' || empty param.filtro ? 'active' : ''}">Todos</a>
                    <a href="<c:url value='/DashboardServlet?filtro=gratis'/>" class="btn filter-btn ${param.filtro == 'gratis' ? 'active' : ''}">Solo Gratuitos</a>
                    <a href="<c:url value='/DashboardServlet?filtro=pc'/>" class="btn filter-btn ${param.filtro == 'pc' ? 'active' : ''}">PC</a>
                    <a href="<c:url value='/DashboardServlet?filtro=consola'/>" class="btn filter-btn ${param.filtro == 'consola' ? 'active' : ''}">Consola</a>
                </div>
            </div>
        </section>

        <section class="row g-4 mb-5">
            
            <c:choose>
                <c:when test="${not empty requestScope.listaTorneos}">
                    <c:forEach var="torneo" items="${requestScope.listaTorneos}">
                        <div class="col-12 col-md-6 col-lg-4 col-xl-3">
                            <div class="card tournament-card h-100 d-flex flex-column justify-content-between p-3">
                                <div>
                                    <div class="d-flex justify-content-between align-items-start mb-2">
                                        <span class="badge game-badge text-uppercase"><c:out value="${torneo.nombreJuego}"/></span>
                                        <small class="text-muted"><i class="bi bi-display me-1"></i><c:out value="${torneo.plataforma}"/></small>
                                    </div>
                                    
                                    <h5 class="card-title text-white fw-bold mb-3"><c:out value="${torneo.nombreTorneo}"/></h5>
                                    
                                    <div class="d-flex justify-content-between border-top border-secondary pt-3 mb-3 text-secondary small">
                                        <span><i class="bi bi-people-fill me-1"></i> Cupos: <strong class="text-light"><c:out value="${torneo.cupo}"/></strong></span>
                                        
                                        <span>
                                            <i class="bi bi-ticket-perforated-fill me-1"></i> Costo: 
                                            <c:choose>
                                                <c:when test="${torneo.precio == 0}">
                                                    <strong class="price-free">FREE</strong>
                                                </c:when>
                                                <c:otherwise>
                                                    <strong class="text-white">$<c:out value="${torneo.precio}"/></strong>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                </div>
                                
                                <div class="d-grid mt-2">
                                    <form action="<c:url value='/InscripcionServlet'/>" method="POST">
                                        <input type="hidden" name="idTorneo" value="${torneo.id}">
                                        <button type="submit" class="btn btn-gamer py-2 btn-sm">
                                            <i class="bi bi-plus-circle me-1"></i> Inscribirme
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                
                <c:otherwise>
                    <div class="col-12 text-center my-5">
                        <div class="alert alert-gamer p-5 d-inline-block rounded-3">
                            <i class="bi bi-search display-4 d-block mb-3"></i>
                            <h4 class="fw-bold">No se encontraron torneos.</h4>
                            <p class="m-0 small text-muted">Intenta cambiar los parámetros de búsqueda en el panel superior.</p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            
        </section>
    </main>
	<jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>