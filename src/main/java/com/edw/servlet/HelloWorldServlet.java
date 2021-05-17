package com.edw.servlet;

import com.edw.helper.SingletonCounter;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *  Definition of the JMS destination (Queue) used
 */
@JMSDestinationDefinitions(
        value = {
                @JMSDestinationDefinition(
                        name = "java:/queue/testamq",
                        interfaceName = "javax.jms.Queue",
                        destinationName = "testamq"
                )
        }
)

/**
 * <pre>
 *     com.edw.servlet.HelloWorldServlet
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 17 Mei 2021 22:17
 */
@WebServlet("/hello-world")
public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 0;

    private int msgCount = 100;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/queue/testamq")
    private Queue queue;

    @Inject
    private SingletonCounter singletonCounter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {

            if(req.getParameter("count") != null) {
                msgCount = Integer.parseInt(req.getParameter("count"));
            }

            out.write("<h1>Sending messages to <em>" + queue + "</em></h1>");
            for (int i = 0; i < msgCount; i++) {
                String text = "This is message " + (i + 1);
                context.createProducer().send(queue, text);
                Thread.sleep(30);
            }
            out.write("<p><i>check JBoss EAP server console or server log to see the result of messages processing.</i></p>");
            out.write("<p><i>Total Message is "+ singletonCounter.getCounter() +"</i></p>");

            // clear the counter, but need to make sure jms queue are all taken first
            Thread.sleep(3000);
            singletonCounter.setCounter(0);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
