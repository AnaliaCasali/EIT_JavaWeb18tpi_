<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-gamer sticky-top mb-4">
    <div class="container">
        <a class="navbar-brand brand-title fs-4" href="<c:url value='/DashboardServlet'/>">
            <i class="bi bi-controller me-2"></i>GamerMatch
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarGeneral">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarGeneral">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                <li class="nav-item">
                    <%-- Activo si NO hay lista de inscripciones (estamos en el Dashboard general) --%>
                    <a class="nav-link ${empty listaInscripciones ? 'active text-info fw-bold' : 'text-secondary'}" 
                       href="<c:url value='/DashboardServlet'/>">
                       <i class="bi bi-trophy me-1"></i>Torneos
                    </a>
                </li>
                <li class="nav-item">
                    <%-- Activo si viene la lista de inscripciones filtrada por el servlet MisTorneos --%>
                    <a class="nav-link ${not empty listaInscripciones ? 'active text-info fw-bold' : 'text-secondary'}" 
                       href="<c:url value='/MisTorneosServlet'/>">
                       <i class="bi bi-person-badge me-1"></i>Mis Inscripciones
                    </a>
                </li>
            </ul>

       		<c:if test="${not empty sessionScope.usuarioLogueado}">
			    <div class="d-flex align-items-center flex-wrap gap-3 mt-3 mt-lg-0">
			        <span class="text-light fw-medium">
			             <span class="text-info fw-bold">
			            <c:out value="${sessionScope.usuarioLogueado.nickname}"/></span> 
			            🎮 <span class="badge bg-secondary text-uppercase small">
			            <c:out value="${sessionScope.usuarioLogueado.rango}"/></span>
			        </span>
			        
			        <a href="<c:url value='/LoginControllerServlet'/>" class="btn btn-outline-danger btn-sm px-3">
			            <i class="bi bi-box-arrow-right me-1"></i> Salir
			        </a>
			    </div>
			</c:if>
        </div>
    </div>
</nav>