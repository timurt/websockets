package kz.tim.samples.websocket;

import kz.tim.samples.handler.SessionHandler;
import kz.tim.samples.utils.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Documentation for {@code WebSocketServer}.
 *
 * @author Timur Tibeyev.
 */

@ApplicationScoped
@ServerEndpoint("/ws")
public class WebSocketServer {

    @Inject
    private SessionHandler sessionHandler;

    /**
     * Invokes when new connection open.
     * @param session {@code WebSocket} session
     */
    @OnOpen
    public void open(Session session) {
        sessionHandler.initSession(session);
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.INFO, "Session open");
    }

    /**
     * Invokes when connection close.
     * @param session {@code WebSocket} session
     */
    @OnClose
    public void close(Session session) {
        try {
            sessionHandler.closeSession(session);
        } catch (IOException e) {
            Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, e);
        }
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.INFO, "Session close");
    }

    /**
     * Invokes when error occurred.
     * @param error error
     */
    @OnError
    public void error(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    /**
     * Invokes when new message receive. In our case message is JSON object with three fields:
     * action, var1 and var2. Action determines what type of calculation required: summation,
     * subtraction, multiplication, division. var1 and var2 are two operands.
     * @param message message
     */
    @OnMessage
    public void message(String message) throws IOException {
        Logger.getLogger(WebSocketServer.class.getName()).log(Level.INFO, message);

        String action = "undefined";

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            action = jsonMessage.getString("action");

            final int var1 = Integer.parseInt(jsonMessage.getString("var1"));
            final int var2 = Integer.parseInt(jsonMessage.getString("var2"));

            switch (action) {
                case "add" : sessionHandler.add(var1, var2); break;
                case "subtract" : sessionHandler.subtract(var1, var2); break;
                case "multiply" : sessionHandler.multiply(var1, var2); break;
                case "divide" : sessionHandler.divide(var1, var2); break;
                default:sessionHandler.sendErrorMessage(action,
                        Constants.WS_UNKNOWN_ACTION_ERROR_CODE,
                        Constants.WS_UNKNOWN_ACTION_ERROR_MESSAGE);
            }

        } catch (Exception e) {
            Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, e);
            sessionHandler.sendErrorMessage(action, Constants.WS_INTERNAL_ERROR_CODE,
                    Constants.WS_INTERNAL_ERROR_MESSAGE);
        }

    }
}
