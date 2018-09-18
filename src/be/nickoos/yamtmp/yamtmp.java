package be.nickoos.yamtmp;

import be.nickoos.yamtmp.config.Configuration;
import org.bstats.bungeecord.Metrics;
import net.md_5.bungee.api.plugin.Plugin;


public class yamtmp extends Plugin {
	
	public void onEnable(){
		Metrics metrics = new Metrics(this);
        Configuration config = new Configuration();
        config.create(this, null,"config.yml");
	}

	public void onDisable(){
		
	}
}
