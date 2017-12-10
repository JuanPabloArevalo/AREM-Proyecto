/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conu.aremproyectofinal;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Administrador
 */
public class Suscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(Suscribe.class);

    private static final String NO_GREETING = "no greeting";

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;

    public void create(String clientId, String topicName) throws JMSException {
        this.clientId = clientId;
        // create a Connection Factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://54.186.68.167:61616?jms.useAsyncSend=true");
        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        // create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // create the Topic from which messages will be received
        Topic topic = session.createTopic(topicName);
        // create a MessageConsumer for receiving messages
        messageConsumer = session.createConsumer(topic);
        // start the connection in order to receive messages
        connection.start();
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public String getGreeting(int timeout) throws JMSException {
        String greeting = NO_GREETING;
        // read a message from the topic destination
        Message message = messageConsumer.receive();
        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;
            // retrieve the message content
            String text = textMessage.getText();
            // create greeting
            greeting = "Hello " + text + "!";
            JSONObject objetoJson = new JSONObject(text);
            
            String fecha = objetoJson.getString("fecha");
            String errorDesarrollo = objetoJson.getString("errorDesarrollo");
            if(errorDesarrollo==null || errorDesarrollo.equals("null") || errorDesarrollo.equals("false")){
                errorDesarrollo = "No";
            }
            else{
                errorDesarrollo = "Si";
            }
            String capacitacion = objetoJson.getString("capacitacion");
            if(capacitacion==null || capacitacion.equals("null") || capacitacion.equals("false")){
                capacitacion = "No";
            }
            else{
                capacitacion = "Si";
            }
            String pais = objetoJson.getString("pais");
            String configuracion = objetoJson.getString("configuracion");
            if(configuracion==null || configuracion.equals("null") || configuracion.equals("false")){
                configuracion = "No";
            }
            else{
                configuracion = "Si";
            }
            String tipo = objetoJson.getString("tipo");
            String cliente = objetoJson.getString("cliente");
            String responsable = objetoJson.getString("responsable");
            
            Reporte reporte = new Reporte(fecha, errorDesarrollo, capacitacion, pais, configuracion, tipo, cliente, responsable);
            reporte.insertar();
        } else {
        }
        System.out.println("Mensaje recibido: "+greeting);
        return greeting;
    }
}
