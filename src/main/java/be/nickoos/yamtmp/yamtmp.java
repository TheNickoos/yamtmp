package be.nickoos.yamtmp;

import be.nickoos.yamtmp.config.ConfigManager;
import be.nickoos.yamtmp.database.Database;
import be.nickoos.yamtmp.announce.Announce;
import be.nickoos.yamtmp.commands.Commands;

import java.sql.SQLException;

import org.bstats.bungeecord.Metrics;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;


public class yamtmp extends Plugin implements Listener {
	public static Database db = new Database();
	private static yamtmp instance;
	private static Plugin yamtmp;
	
	public void onEnable(){
		yamtmp = this;
		Metrics metrics = new Metrics(this);
		
		getLogger().info("Yup, we enable the plugin");
		
		ConfigManager config = new ConfigManager();
        config.create(this, null,"config.yml");
        config.create(this, null,"messages.yml");
        
        getLogger().info("Connecting to the database ...");
        db.connect();
        db.createTable("announce", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, server VARCHAR(36), message TEXT NOT NULL, global INT(1));");
        Announce Announce = new Announce();
        Announce.Start();
        
        getProxy().getPluginManager().registerCommand(this, new Commands());
	}

	public void onDisable(){
		getLogger().info("[YAMTMP] Yup, we disable the plugin");
        try {
            db.hikari.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        	Announce.Stop();
        } catch (Exception e) {
            
        }
        	
	}
	public static ProxyServer getProxyServer() {
		return instance.getProxy();
	}
	
	public static Plugin getInstance() {
		return yamtmp;
	}
	public static boolean reload() {
		 return false;
	}
}
