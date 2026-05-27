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

		Properties dbProperties = new Properties();
		Connection conn = null;
		try {
			// cargamos el archivo utilizando la ruta relativa donde esta el proyecto
			dbProperties
					.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));

			// leemos las propiedades
			DRIVER = dbProperties.getProperty("db.driver");
			DB_CADENA_CONEXION = dbProperties.getProperty("db.url");
			DB_USUARIO = dbProperties.getProperty("db.user", "root");
			DB_PASSWORD = dbProperties.getProperty("db.pass");

			Class.forName(DRIVER);

			// establesco la conexión
			conn = DriverManager.getConnection(DB_CADENA_CONEXION, DB_USUARIO, DB_PASSWORD);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (SQLException e) {
			System.out.println("No se pudo conectar a MySQL" + e.getMessage() + e.toString());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}