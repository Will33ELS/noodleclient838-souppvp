package fr.will33.souppvp.database;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.ISQLBridge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLLiteDatabase implements ISQLBridge {

    private final Path sqlFile;
    private Connection connection;

    public SQLLiteDatabase(String fileName){
        this.sqlFile = Paths.get(SoupPvPPlugin.getInstance().getDataFolder().toString(), fileName);

        try{
            if(!Files.exists(this.sqlFile))
                Files.createFile(this.sqlFile);
        }catch (IOException err){
            err.printStackTrace();
        }
        this.setup();
        this.onCreateTable();
    }

    /**
     * Connect to database
     */
    private void setup(){
        if(!this.isConnected()){
            try{
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + sqlFile.toAbsolutePath());
            }catch (SQLException err){
                err.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Connection getConnection() {
        if (!this.isConnected()) {
            try {
                this.shutdownDataSource();
            }catch (Exception err){
                err.printStackTrace();
            }
            this.setup();
            return this.connection;
        }

        return this.connection;
    }

    /**
     * Close connection
     * @throws Exception
     */
    private void shutdownDataSource() throws Exception{
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new Exception("Erreur: "+e.getMessage());
        }
    }

    /**
     * Check if database is valid
     * @return
     */
    private boolean isConnected() {
        try {
            return (this.connection != null) && (!this.connection.isClosed()) /*&& this.connection.isValid(1000)*/;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create all tables if not exists
     */
    public void onCreateTable(){
        try{
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS players (" +
                    "`uuid` VARCHAR(255) PRIMARY KEY," +
                    "`credit` INTEGER DEFAULT 0" +
                    ")");
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS kits_unlocked (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
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
