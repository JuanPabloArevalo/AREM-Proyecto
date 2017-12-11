/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conu.aremproyectofinal;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
/**
 *
 * @author JuanArevaloMerchan
 */
public class Main {
    public static void main(String[] args){
        try {
            System.err.println("ENTRO ACA!!!!!!!!!!");
            Suscribe suscribe = new Suscribe();
            suscribe.create("publisher-multipleconsumers4", "publishsubscribe.t");
            while(true){
                String greeting1 = suscribe.getGreeting(1000);
            }
        } catch (JMSException | ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


        
        
}
