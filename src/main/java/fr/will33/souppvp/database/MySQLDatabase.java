package fr.will33.souppvp.database;

import fr.will33.souppvp.api.ISQLBridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDatabase implements ISQLBridge {

    private final String host,
            database,
            username,
            password,
            prefixTable;

    private Connection connection;

    public MySQLDatabase(String host, String database, String username, String password, String prefixTable){
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.prefixTable = prefixTable;
        this.onCreateTable();
    }

    /**
     * Connect to database
     */
    private void setup() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?autoreconnect=true&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false", username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() {
        if (!isConnected()) {
            try {
                shutdownDataSource();
            }catch (Exception err){
                err.printStackTrace();
            }
            setup();
        }
        return connection;
    }

    /**
     * Close connection
     * @throws Exception
     */
    public void shutdownDataSource() throws Exception{
        if(connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            throw new Exception("Erreur: "+e.getMessage());
        }
    }

    /**
     * Check if connection is valid
     * @return
     */
    private boolean isConnected() {
        try {
            return (connection != null) && (!connection.isClosed()) && connection.isValid(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get prefix table
     * @return
     */
    public String getPrefixTable() {
        return prefixTable;
    }

    /**
     * Create all tables if not exists
     */
    public void onCreateTable(){
        try{
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + this.prefixTable + "players (" +
                    "`uuid` VARCHAR(255) PRIMARY KEY," +
                    "`credit` INTEGER DEFAULT 0" +
                    ")");
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + this.prefixTable + "kits_unlocked (" +
                    "`id` INT PRIMARY KEY AUTO_INCREMENT," +
                    "`uuid` VARCHAR(255) NOT NULL," +
                    "`kit_name` VARCHAR(255) NOT NULL" +
                    ")");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException err){
            err.printStackTrace();
        }
    }
}
