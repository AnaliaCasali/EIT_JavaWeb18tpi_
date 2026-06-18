package com.educacionit.java18tpi.interfaces;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum AdmConexiones {
    INSTANCE;

    private  final Logger log =
            Logger.getLogger(AdmConexiones.class.getName());

    private final HikariDataSource dataSource;

    // El constructor del enum se ejecuta UNA sola vez, al cargar la clase.
    AdmConexiones() {
        HikariConfig config = new HikariConfig();

        // 1. Cargar database.properties desde el classpath
        Properties props = cargarProperties();

        // 2. Conexión: env vars tienen prioridad sobre el .properties
        config.setJdbcUrl(     resolver("DB_URL",  props, "db.url"));
        config.setUsername(    resolver("DB_USER", props, "db.user"));
        config.setPassword(    resolver("DB_PASS", props, "db.pass"));
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // 3. Pool: leído siempre desde el .properties
        config.setMaximumPoolSize(  intProp(props, "hikari.maximumPoolSize",  15));
        config.setMinimumIdle(      intProp(props, "hikari.minimumIdle",       2));
        config.setConnectionTimeout(intProp(props, "hikari.connectionTimeout",10000));
        config.setIdleTimeout(      intProp(props, "hikari.idleTimeout",    300000));
        config.setMaxLifetime(      intProp(props, "hikari.maxLifetime",    600000));

        dataSource = new HikariDataSource(config);
        log.info("[DB] Pool HikariCP inicializado.");
    }

    /** Devuelve una conexión del pool. Usar siempre en try-with-resources. */
    public Connection obtenerConexion() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "[DB] Error obteniendo conexión del pool", e);
            throw new RuntimeException("No se pudo obtener conexión", e);
        }
    }

    /** Llamar desde AppContextListener.contextDestroyed() */
    public void cerrarPool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            log.info("[DB] Pool cerrado correctamente.");
        }
    }

    // ── funciones privadas  ──────────────────────────────────────────────────

    private Properties cargarProperties() {
        Properties props = new Properties();
        try (InputStream is = AdmConexiones.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (is == null) {
                log.warning("[DB] database.properties no encontrado en classpath.");
                return props;
            }
            props.load(is);
        } catch (IOException e) {
            log.log(Level.SEVERE, "[DB] Error leyendo database.properties", e);
        }
        return props;
    }

    /** Env var tiene prioridad; si no existe, usa el .properties. */
    private String resolver(String envKey, Properties props, String propKey) {
        String env = System.getenv(envKey);
        return (env != null && !env.isBlank()) ? env : props.getProperty(propKey);
    }

    private int intProp(Properties props, String key, int defaultVal) {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch (NumberFormatException | NullPointerException e) {
            return defaultVal;
        }
    }
}