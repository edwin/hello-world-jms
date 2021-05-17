package com.edw.mdb;

import com.edw.helper.SingletonCounter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * <pre>
 *     com.edw.mdb.HelloWorldQueueMdb
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 17 Mei 2021 22:18
 */
@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testamq"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "200"),
        @ActivationConfigProperty(propertyName = "minSession", propertyValue = "100"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class HelloWorldQueueMdb implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldQueueMdb.class.toString());

    @Inject
    private SingletonCounter singletonCounter;

    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;

        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOGGER.info("Received Message from queue: " + msg.getText());
            } else {
                LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }

            // add plus one
            singletonCounter.increment();

            // print it
            Thread.sleep(100);
            LOGGER.info("Total Message: " + singletonCounter.getCounter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
