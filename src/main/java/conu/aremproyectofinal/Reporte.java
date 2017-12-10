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
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author JuanArevaloMerchan
 */


public class Reporte {
    private String fecha;
    private String errorDesarrollo;
    private String capacitacion;
    private String pais;
    private String configuracion;
    private String tipo;
    private String cliente;
    private String responsable;
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Autowired
    private DataSource dataSource;

    public Reporte(){
        
    }
    
    public Reporte(String fecha, String errorDesarrollo, String capacitacion, String pais, String configuracion, String tipo, String cliente, String responsable){
        this.fecha=fecha;
        this.errorDesarrollo=errorDesarrollo;
        this.capacitacion=capacitacion;
        this.pais=pais;
        this.configuracion=configuracion;
        this.tipo=tipo;
        this.cliente=cliente;
        this.responsable=responsable;
    }
    
    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the errorDesarrollo
     */
    public String getErrorDesarrollo() {
        return errorDesarrollo;
    }

    /**
     * @param errorDesarrollo the errorDesarrollo to set
     */
    public void setErrorDesarrollo(String errorDesarrollo) {
        this.errorDesarrollo = errorDesarrollo;
    }

    /**
     * @return the capacitacion
     */
    public String getCapacitacion() {
        return capacitacion;
    }

    /**
     * @param capacitacion the capacitacion to set
     */
    public void setCapacitacion(String capacitacion) {
        this.capacitacion = capacitacion;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the configuracion
     */
    public String getConfiguracion() {
        return configuracion;
    }

    /**
     * @param configuracion the configuracion to set
     */
    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    
    public void insertar(){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();    
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reporte (fecha varchar(255),errorDesarrollo varchar(255),capacitacion varchar(255),pais varchar(255),configuracion varchar(255),tipo varchar(255) ,cliente varchar(255),responsable varchar(255))");
            stmt.executeUpdate("INSERT INTO Reporte (fecha,errorDesarrollo,capacitacion,pais,configuracion,tipo,cliente,responsable) VALUES ('"+fecha+"','"+errorDesarrollo+"','"+capacitacion+"','"+pais+"','"+configuracion+"','"+tipo+"','"+cliente+"','"+responsable+"')");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    public Set<Reporte> getAll() throws SQLException{
        Set<Reporte> arreglo = new HashSet<>();
        Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fecha,errorDesarrollo,capacitacion,pais,configuracion,tipo,cliente,responsable FROM Reporte");
        ArrayList<String> output = new ArrayList<>();
        while (rs.next()) {
            arreglo.add(new Reporte(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return arreglo;
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
