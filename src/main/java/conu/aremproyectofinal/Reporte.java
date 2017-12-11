/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conu.aremproyectofinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
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
    
    
    public void insertar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
                String connectionString
                = "jdbc:sqlserver://proyectoconu.database.windows.net:1433;"
                + "database=BaseDatosBackend;"
                + "user=adminBD@proyectoconu;"
                + "password=Ab1234567890.;"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "hostNameInCertificate=*.database.windows.net;"
                + "loginTimeout=30;";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement prepsInsertProduct = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(connectionString);
        String insertSql = "INSERT INTO Reporte (fecha,errorDesarrollo,capacitacion,pais,configuracion,tipo,cliente,responsable) VALUES ('"+fecha+"','"+errorDesarrollo+"','"+capacitacion+"','"+pais+"','"+configuracion+"','"+tipo+"','"+cliente+"','"+responsable+"');";
//        String insertSql = "CREATE TABLE IF NOT EXISTS Reporte (fecha varchar(255),errorDesarrollo varchar(255),capacitacion varchar(255),pais varchar(255),configuracion varchar(255),tipo varchar(255) ,cliente varchar(255),responsable varchar(255));";
        
        prepsInsertProduct = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reporte (fecha varchar(255),errorDesarrollo varchar(255),capacitacion varchar(255),pais varchar(255),configuracion varchar(255),tipo varchar(255) ,cliente varchar(255),responsable varchar(255))");
//        stmt.executeUpdate("INSERT INTO Reporte (fecha,errorDesarrollo,capacitacion,pais,configuracion,tipo,cliente,responsable) VALUES ('"+fecha+"','"+errorDesarrollo+"','"+capacitacion+"','"+pais+"','"+configuracion+"','"+tipo+"','"+cliente+"','"+responsable+"')");
        prepsInsertProduct.execute();
        resultSet = prepsInsertProduct.getGeneratedKeys();
        while (resultSet.next()) {
            System.out.println("Generated: " + resultSet.getString(1));
        } 
        if (prepsInsertProduct != null) {
            prepsInsertProduct.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
                statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
    
    public Set<Reporte> getAll() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
            String connectionString
        = "jdbc:sqlserver://proyectoconu.database.windows.net:1433;"
        + "database=BaseDatosBackend;"
        + "user=adminBD@proyectoconu;"
        + "password=Ab1234567890.;"
        + "encrypt=false;"
        + "trustServerCertificate=false;"
        + "hostNameInCertificate=*.database.windows.net;"
        + "loginTimeout=30;";

        // Declare the JDBC objects.  
        Connection connection = null;  
        Statement statement = null;   
        ResultSet resultSet = null;  
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(connectionString);  

        // Create and execute a SELECT SQL statement.  

        String selectSql = "SELECT fecha,errorDesarrollo,capacitacion,pais,configuracion,tipo,cliente,responsable FROM Reporte";  
        statement = connection.createStatement();  
        resultSet = statement.executeQuery(selectSql);  

            // Print results from select statement  
        Set<Reporte> reportes = new HashSet<>();
        Reporte reporte;
        while (resultSet.next()){  
            reporte = new Reporte(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
            reportes.add(reporte);
        }  
            // Close the connections after the data has been handled.  
        if (resultSet != null)  { resultSet.close(); }  
        if (statement != null) { statement.close(); } 
        if (connection != null)  { connection.close(); }  
        
        return reportes;
    }   
    

    
}
