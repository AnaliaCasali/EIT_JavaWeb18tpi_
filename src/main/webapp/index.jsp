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
    
    <div class="container d-flex justify-content-center p-3">
        <div class="launcher-card p-4 p-sm-5">
            
            <div class="text-center mb-4">
                <h2 class="brand-title m-0"><i class="bi bi-controller me-2"></i>GamerMatch</h2>
                <p class="text-muted small mt-1">CONECTA • COMPITE • DOMINA</p>
            </div>

            <c:if test="${not empty errorMsg}">
                <div class="alert alert-gamer d-flex align-items-center mb-4" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <div>
                        <c:out value="${errorMsg}"/>
                    </div>
                </div>
            </c:if>

            <form action="LoginControllerServlet" method="POST">
                
                <div class="mb-3">
                    <label for="username" class="form-label text-light small fw-semibold">NICKNAME</label>
                    <div class="input-group">
                        <span class="input-group-text bg-dark border-secondary text-muted"><i class="bi bi-person-fill"></i></span>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Ej: FakerJunior" required autocomplete="username">
                    </div>
                </div>

                <div class="mb-4">
                    <label for="password" class="form-label text-light small fw-semibold">CONTRASEÑA</label>
                    <div class="input-group">
                        <span class="input-group-text bg-dark border-secondary text-muted"><i class="bi bi-lock-fill"></i></span>
                        <input type="password" class="form-control" id="password" name="password" placeholder="••••••••" required autocomplete="current-password">
                    </div>
                </div>

                <div class="d-grid mb-4">
                    <button type="submit" class="btn btn-gamer py-2">
                        Entrar <i class="bi bi-chevron-right ms-1"></i>
                    </button>
                </div>
<!-- 
                <div class="d-flex justify-content-between flex-wrap gap-2 text-center">
                    <a href="#" class="gamer-link">¿Olvidaste tu contraseña?</a>
                    <a href="#" class="gamer-link fw-bold text-info">Crear Cuenta</a>
                </div>
      -->           
            </form>

        </div>
    </div>
<jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>