<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GamerMatch - Error</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="<c:url value='/css/estilo.css'/>">
</head>
<body class="d-flex flex-column min-height-vh-100">

    <jsp:include page="navbar.jsp" />

    <main class="container my-auto d-flex justify-content-center align-items-center flex-grow-1 py-5">
        <div class="row justify-content-center w-100">
            <div class="col-12 col-md-8 col-lg-6 text-center">
                
                <div class="d-inline-flex p-4 rounded-circle bg-dark border border-danger border-2 shadow-lg mb-4 text-danger animate-pulse">
                    <i class="bi bi-exclamation-octagon display-1"></i>
                </div>
                
                <h1 class="display-5 fw-bold text-white mb-2">ERROR EN LA WEB</h1>
                <h4 class="text-danger font-monospace mb-4">CRITICAL_SERVER_EXCEPTION</h4>
                
                <div class="card launcher-card p-4 mb-4 border border-secondary bg-opacity-10">
                    <p class="text-light m-0">
                        El servidor experimentó una anomalía inesperada al procesar los datos de tu escuadra. El despliegue de esta solicitud ha sido cancelado para proteger la estabilidad de la base de datos.
                    </p>
                    
					  <%-- Bloque opcional para desarrollo: Muestra el mensaje técnico real si existe --%>
					<c:if test="${not empty exception}">
					    <div class="mt-3 pt-3 border-top border-secondary text-start">
					        <small class="text-muted text-uppercase d-block mb-1 font-monospace small">Detalle del error:</small>
					        <code class="text-warning small word-break"><c:out value="${exception.message}"/></code>
					    </div>
					</c:if>
                </div>

                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <a href="<c:url value='/DashboardServlet'/>" class="btn btn-gamer btn-lg px-4 gap-3 py-2">
                        <i class="bi bi-house-door-fill me-2"></i>Volver
                    </a>
                </div>
                
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>