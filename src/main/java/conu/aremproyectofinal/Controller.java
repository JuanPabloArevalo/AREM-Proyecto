/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conu.aremproyectofinal;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author JuanArevaloMerchan
 */
@org.springframework.stereotype.Controller
@SpringBootApplication
@RequestMapping(value = "/")
public class Controller {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Autowired
    private DataSource dataSource;
    public static void main(String[] args){

        try {

            SpringApplication.run(Controller.class, args);
            Suscribe suscribe = new Suscribe();
            suscribe.create("publisher-multipleconsumers4", "publishsubscribe.t");
            try {
                while(true){
                    String greeting1 = suscribe.getGreeting(1000);
                }
            } catch (JMSException e) {
Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
            }

        } catch (JMSException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @RequestMapping(path = "/registros", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        try {
            System.out.println("Entro ACAAAAA");
            return new ResponseEntity<>(new Reporte().getAll(), HttpStatus.ACCEPTED);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error con la base de datos", HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping("/db")
    String db2(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reporte (fecha varchar(255),errorDesarrollo varchar(255),capacitacion varchar(255),pais varchar(255),configuracion varchar(255),tipo varchar(255) ,cliente varchar(255),responsable varchar(255))");
            stmt.executeUpdate("INSERT INTO Reporte (fecha,errorDesarrollo,capacitacion,pais,configuracion,tipo,cliente,responsable) VALUES ('1','2','3','4','5','6','7','8')");
        } catch (Exception e) {
            model.put("message", e.getMessage());
            return "error";
        }
                    
        try (Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Reporte");
        ArrayList<String> output = new ArrayList<String>();
        
        
        while (rs.next()) {
            output.add("fecha:"+rs.getString("fecha")+",errorDesarrollo:"+rs.getString("errorDesarrollo")+",capacitacion:"+rs.getString("capacitacion")+",pais:"+rs.getString("pais")+",configuracion:"+rs.getString("configuracion")+",tipo:"+rs.getString("tipo")+",cliente:"+rs.getString("cliente")+",responsable:"+rs.getString("responsable")+"");
        }
        model.put("records", output);
        return "db";
        } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
            }
    }
    
    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
        
        
}
