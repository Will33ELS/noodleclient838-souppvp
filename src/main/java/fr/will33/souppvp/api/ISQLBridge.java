package fr.will33.souppvp.api;

import java.sql.Connection;

public interface ISQLBridge {

    /**
     * Get database connection
     * @return
     */
    Connection getConnection();
}
