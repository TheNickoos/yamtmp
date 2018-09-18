package be.nickoos.yamtmp.announce;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.concurrent.TimeUnit;

import be.nickoos.yamtmp.yamtmp;
import be.nickoos.yamtmp.config.ConfigManager;
import be.nickoos.yamtmp.database.Database;


public class Announce extends Plugin {
	
	Connection connection = null;
	PreparedStatement statement = null;
	Database db = yamtmp.db;
	ConfigManager config = new ConfigManager();
    String tablePrefix = config.load(null, "config.yml").getString("database.table-prefix");
    
	private boolean random;
	private int delay;
	private String prefix;
	private int totalmsg;
	private String messages;
	private int nummessage;
	private String message;
	private long TotalMessagess;
	
	private static ScheduledTask task = null;
	

    public void Start() {
		net.md_5.bungee.config.Configuration config = new ConfigManager().load(null,"config.yml");
		random = config.getBoolean("random-order");
		delay = config.getInt("message-delay");
		prefix = ChatColor.translateAlternateColorCodes('&', config.getString("message-prefix"));
		totalmsg = getTotalMessages();
		if (totalmsg >= 1) {
			for (int i = 1; i <= totalmsg; i++) {
				messages = getMessages(i);
			}	
		
			task = ProxyServer.getInstance().getScheduler().schedule(yamtmp.getInstance(), new Runnable() {
				public void run() {
					System.out.println("8");
					ProxyServer.getInstance().broadcast(prefix + " " +  messages);
				}
			}, 0, delay, TimeUnit.SECONDS);
		} else {
			System.out.println("[YAMTMP]: You dont have set any announce ! Disabling announcer.");
		}
    }
    
    public static void Stop() {
    	ProxyServer.getInstance().getScheduler().cancel(task);
    }
    
	public Announce() {
	}
	
	public Announce(String message, int nummessage) {
        this.message = message;
        this.nummessage = nummessage;
	}
	
	public long ReturnTotalMsg(long value) {
        return value;
    }


	public int getTotalMessages() {
		Announce TotalMessages = new Announce();
        ResultSet resultSet = null;

        try {
            connection = yamtmp.db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT COUNT(message) as NumberOfMessage FROM " + tablePrefix + "announce WHERE global = 1");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	TotalMessagess = TotalMessages.ReturnTotalMsg(resultSet.getLong("NumberOfMessage"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return (int) TotalMessagess;
	}
	
	public String getMessages(int nummessage) {
		Announce annmsg = new Announce();
        ResultSet resultSet = null;

        String value = null;
		try {
            connection = yamtmp.db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + tablePrefix + "announce WHERE id = ? AND global = 1");
            statement.setString(1, String.valueOf(nummessage));
            System.out.println("nummessage : " + nummessage);
            System.out.println("statement : " + statement);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	value = resultSet.getString("message");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
	}



}
