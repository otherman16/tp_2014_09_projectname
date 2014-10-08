package main;

import servlets.*;
import account_service.*;
import db_service.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import javax.servlet.http.HttpServlet;

/**
 * Created by Алексей on 23.09.2014.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.append("Starting at port: ").append(portString).append('\n');

        AccountService service = new AccountService();

        HttpServlet loginServlet = new LoginServlet(service);
        HttpServlet registrationServlet = new RegistrationServlet(service);
        HttpServlet getUserServlet = new GetUserServlet(service);
        HttpServlet logoutUserServlet = new LogoutServlet(service);
        HttpServlet adminServlet = new AdminServlet(service);
        HttpServlet getScoreServlet = new GetScoresServlet(service);
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getScoreServlet), "/get_scores");
        context.addServlet(new ServletHolder(getUserServlet), "/get_user");
        context.addServlet(new ServletHolder(logoutUserServlet), "/logout");
        context.addServlet(new ServletHolder(adminServlet), "/admin");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(registrationServlet), "/registration");
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
