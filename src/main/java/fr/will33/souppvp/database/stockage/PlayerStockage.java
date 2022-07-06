package fr.will33.souppvp.database.stockage;

import fr.will33.souppvp.SoupPvPPlugin;
import fr.will33.souppvp.api.AbstractKit;
import fr.will33.souppvp.api.ISQLBridge;
import fr.will33.souppvp.database.MySQLDatabase;
import fr.will33.souppvp.model.PvpPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerStockage {

    private final ISQLBridge sqlBridge;
    private final String prefixTable;

    public PlayerStockage(){
        this.sqlBridge = SoupPvPPlugin.getInstance().getSqlBridge();
        if(this.sqlBridge instanceof MySQLDatabase) {
            MySQLDatabase mySQLDatabase = (MySQLDatabase) this.sqlBridge;
            this.prefixTable = mySQLDatabase.getPrefixTable();
        }else
            this.prefixTable = "";
    }

    /**
     * Load player data
     * @param pvpPlayer Instance of the player
     */
    public void loadPlayer(PvpPlayer pvpPlayer){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("SELECT * FROM " + this.prefixTable + "players WHERE uuid = ?");
            preparedStatement.setString(1, pvpPlayer.getPlayer().getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                pvpPlayer.setCredit(resultSet.getInt("credit"));
            } else {
                this.insertPlayer(pvpPlayer.getPlayer().getUniqueId());
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Insert player in to database
     * @param uuid Unique ID of the player
     */
    public void insertPlayer(UUID uuid){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("INSERT INTO " + this.prefixTable + "players (uuid) VALUES (?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Update credit balance
     * @param player Instance of the player
     */
    public void updateCredit(PvpPlayer player){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("UPDATE " + this.prefixTable + "players SET credit = ? WHERE uuid = ?");
            preparedStatement.setInt(1, player.getCredit());
            preparedStatement.setString(2, player.getPlayer().getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Get all kit unlocked
     * @param uuid Unique ID of the player
     * @return
     */
    public List<AbstractKit> getKitUnlocked(UUID uuid){
        List<AbstractKit> kits = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("SELECT kit_name FROM " + this.prefixTable + "kits_unlocked WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                kits.add(AbstractKit.getKit(resultSet.getString("kit_name")));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException err){
            err.printStackTrace();
        }
        return kits;
    }

    /**
     * Add kit
     * @param uuid Unique ID of the player
     * @param kit Instance of the kit
     */
    public void addKit(UUID uuid, AbstractKit kit){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("INSERT INTO " + this.prefixTable + "kits_unlocked (uuid, kit_name) VALUES (?, ?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, kit.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

}
