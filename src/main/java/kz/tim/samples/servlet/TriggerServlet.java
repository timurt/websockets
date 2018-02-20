package kz.tim.samples.servlet;

import kz.tim.samples.handler.SessionHandler;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class responsible only for one case, triggering server to send first message to the client.
 * Logic of the class is pretty obvious.
 *
 * @author Timur Tibeyev.
 */
@WebServlet("/trigger")
public class TriggerServlet extends HttpServlet {

    @Inject
    private SessionHandler sessionHandler;

    private void triggerRequest() throws IOException {
        sessionHandler.triggerRequest();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        triggerRequest();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        triggerRequest();
    }
}
