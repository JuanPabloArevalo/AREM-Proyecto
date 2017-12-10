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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    
        public static void main(String[] args){
            
            try {
                
                SpringApplication.run(Controller.class, args);
                Suscribe suscribe = new Suscribe();
                suscribe.create("publisher-multipleconsumers", "publishsubscribe.t");
                try {
                    while(true){
                        String greeting1 = suscribe.getGreeting(1000);
                    }
                } catch (JMSException e) {
                    
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
        
        
}
