package be.nickoos.yamtmp;

import be.nickoos.yamtmp.config.ConfigManager;
import be.nickoos.yamtmp.database.Database;

import java.sql.SQLException;

import org.bstats.bungeecord.Metrics;
import net.md_5.bungee.api.plugin.Plugin;


public class yamtmp extends Plugin {
	public static Database db = new Database();
	
	public void onEnable(){
		Metrics metrics = new Metrics(this);
		
		
		ConfigManager config = new ConfigManager();
        config.create(this, null,"config.yml");
        config.create(this, null,"messages.yml");
        
        db.connect();
        
	}

	public void onDisable(){
        try {
            db.hikari.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }	
	}
}
