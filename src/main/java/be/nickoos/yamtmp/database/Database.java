package be.nickoos.yamtmp.database;

import be.nickoos.yamtmp.config.ConfigManager;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    public HikariDataSource hikari;
    public void connect() {
        try {
            net.md_5.bungee.config.Configuration config = new ConfigManager().load(null,"config.yml");
            hikari = new HikariDataSource();
            hikari.setMaximumPoolSize(10);
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", config.getString("database.address"));
            hikari.addDataSourceProperty("port", config.getInt("database.port"));
            hikari.addDataSourceProperty("databaseName", config.getString("database.name"));
            hikari.addDataSourceProperty("user", config.getString("database.username"));
            hikari.addDataSourceProperty("password", config.getString("database.password"));
        } catch (Exception e) {
        	e.printStackTrace();
        }

    }

    public void createTable(String name, String SQL) {
        Connection connection = null;
        PreparedStatement statement = null;
        String tablePrefix = new ConfigManager().load(null,"config.yml").getString("database.table-prefix");
        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tablePrefix + name + " " + SQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}