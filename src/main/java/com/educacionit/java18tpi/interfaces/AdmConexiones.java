package com.educacionit.java18tpi.interfaces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
// Importamos las clases nativas de logging de Java
import java.util.logging.Logger;
import java.util.logging.Level;

public interface AdmConexiones {

	// Inicializamos el logger estático para la interfaz
	Logger log = Logger.getLogger(AdmConexiones.class.getName());

	default Connection ObtenerConexion() {

		final String DRIVER;
		final String DB_CADENA_CONEXION;
		final String DB_USUARIO;
		final String DB_PASSWORD;

		Connection conn = null;
		try {
			log.info("[DB-LOG] Iniciando intento de conexión a la base de datos...");

			// 1. Intentar leer desde variables de entorno (Railway, CI, producción)
			String envUrl  = System.getenv("DB_URL");
			String envUser = System.getenv("DB_USER");
			String envPass = System.getenv("DB_PASS");

			if (envUrl != null && envUser != null && envPass != null) {
				log.info("[DB-LOG] -> Modo: PRODUCCIÓN (Variables de entorno encontradas)");
				log.info("[DB-LOG] -> Conectando a URL: " + envUrl);
				log.info("[DB-LOG] -> Usuario: " + envUser);

				DRIVER             = "com.mysql.cj.jdbc.Driver";
				DB_CADENA_CONEXION = envUrl;
				DB_USUARIO         = envUser;
				DB_PASSWORD        = envPass;
			} else {
				log.warning("[DB-LOG] -> Modo: DESARROLLO (Faltan variables de entorno, usando database.properties)");

				// 2. Fallback: leer del archivo database.properties (desarrollo local)
				Properties dbProperties = new Properties();
				dbProperties.load(
						Thread.currentThread()
								.getContextClassLoader()
								.getResourceAsStream("database.properties")
				);
				DRIVER             = dbProperties.getProperty("db.driver");
				DB_CADENA_CONEXION = dbProperties.getProperty("db.url");
				DB_USUARIO         = dbProperties.getProperty("db.user", "root");
				DB_PASSWORD        = dbProperties.getProperty("db.pass");

				log.info("[DB-LOG] -> URL local: " + DB_CADENA_CONEXION);
			}

			log.info("[DB-LOG] Cargando Driver: " + DRIVER);
			Class.forName(DRIVER);

			log.info("[DB-LOG] Solicitando conexión al DriverManager...");
			conn = DriverManager.getConnection(DB_CADENA_CONEXION, DB_USUARIO, DB_PASSWORD);

			if (conn != null && !conn.isClosed()) {
				log.info("[DB-LOG] ¡ÉXITO! Conexión establecida correctamente con MySQL.");
			}

		} catch (IOException e) {
			log.log(Level.SEVERE, "[DB-LOG] [ERROR IO] Fallo al leer el archivo database.properties", e);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "[DB-LOG] [ERROR SQL] Error crítico al conectar a MySQL. Mensaje: " + e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, "[DB-LOG] [ERROR DRIVER] No se encontró la clase del Driver de MySQL", e);
		}

		// Alerta crítica si el objeto se va vacío
		if (conn == null) {
			log.severe("[DB-LOG] [ALERTA] ObtenerConexion() va a retornar NULL. Tu DAO va a fallar con NullPointerException.");
		}

		return conn;
	}
}