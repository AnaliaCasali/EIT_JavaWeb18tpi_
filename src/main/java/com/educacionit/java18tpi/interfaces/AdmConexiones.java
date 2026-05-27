package com.educacionit.java18tpi.interfaces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public interface AdmConexiones {

	default Connection ObtenerConexion() {

		final String DRIVER;
		final String DB_CADENA_CONEXION;
		final String DB_USUARIO;
		final String DB_PASSWORD;

		Connection conn = null;
		try {
			// 1. Intentar leer desde variables de entorno (Railway, CI, producción)
			String envUrl  = System.getenv("DB_URL");
			String envUser = System.getenv("DB_USER");
			String envPass = System.getenv("DB_PASS");

			if (envUrl != null && envUser != null && envPass != null) {
				// Variables de entorno encontradas — usamos estas
				DRIVER             = "com.mysql.cj.jdbc.Driver";
				DB_CADENA_CONEXION = envUrl;
				DB_USUARIO         = envUser;
				DB_PASSWORD        = envPass;
			} else {
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
			}

			Class.forName(DRIVER);
			conn = DriverManager.getConnection(DB_CADENA_CONEXION, DB_USUARIO, DB_PASSWORD);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("No se pudo conectar a MySQL: " + e.getMessage() + e.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}
}