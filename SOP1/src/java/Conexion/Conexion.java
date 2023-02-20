package Conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Conexion { 
   Connection conn = null;
    
    public Connection Conexion()
    {
        Properties prop = new Properties();
        InputStream is = Conexion.class.getResourceAsStream("config.properties");

        String driver = "";
        String db = "";
        String port = "";
        String sid = "";
        String user = "";
        String pwd = "";
        
         try {
                prop.load(is);
                
                driver = prop.getProperty("driver");
                db = prop.getProperty("ipdb");
                port = prop.getProperty("port");
                sid = prop.getProperty("sid");
                user = prop.getProperty("user");
                pwd = prop.getProperty("pwd");

                Class.forName(driver);
                
                String url = "jdbc:oracle:thin:@";
                        url += db;
                        url += ":"+port;
                        url += ":"+sid;

                conn = DriverManager.getConnection(url, user, pwd);
                return conn;
                
        } catch (IOException  e) {
           return null;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
      
    public Connection getConnection(){
	return Conexion();
    }
	
    public Connection desconectar() throws SQLException{
        conn.close();   
        conn=null;
        return conn;     
    }
    
}

