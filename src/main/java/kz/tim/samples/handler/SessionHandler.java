package kz.tim.samples.handler;

import kz.tim.samples.utils.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class stores {@code WebSocket} session and responsible for performing logic
 * and sending responses.
 *
 * @author Timur Tibeyev.
 */

@ApplicationScoped
public class SessionHandler {

    /** WebSocket session. **/
    private Session session = null;

    /**
     * Stores WebSocket session inside class field.
     * @param session the session.
     */
    public void initSession(Session session) {
        this.session = session;
    }

    /**
     * Closes session.
     * @param session the session.
     */
    public void closeSession(Session session) throws IOException {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    /**
     * Construct {@code JsonObject} instance based on given variables.
     * @param action one of the supported operators (sum, subtract, multiply, divide).
     * @param result result of the calculation.
     * @throws IOException
     */
    private void constructJsonObject(String action, int result) throws IOException {
        JsonProvider provider = JsonProvider.provider();

        JsonObject statusObject = provider.createObjectBuilder()
                .add("code", Constants.WS_SUCCESS_CODE)
                .add("message", Constants.WS_SUCCESS_MESSAGE)
                .build();

        JsonObject responseObject = provider.createObjectBuilder()
                .add("action", action)
                .add("result", result)
                .add("status", statusObject)
                .build();

        sendToSession(responseObject);
    }

    /**
     * Performs summation.
     * @param var1 first operand.
     * @param var2 second operand.
     * @throws IOException
     */
    public void add(int var1, int var2) throws IOException {
        int result = var1 + var2;
        constructJsonObject("add", result);
    }

    /**
     * Performs subtraction.
     * @param var1 first operand.
     * @param var2 second operand.
     * @throws IOException
     */
    public void subtract(int var1, int var2) throws IOException {
        int result = var1 - var2;
        constructJsonObject("subtract", result);
    }

    /**
     * Performs multiplication.
     * @param var1 first operand.
     * @param var2 second operand.
     * @throws IOException
     */
    public void multiply(int var1, int var2) throws IOException {
        int result = var1 * var2;
        constructJsonObject("multiply", result);
    }

    /**
     * Performs division.
     * @param var1 first operand.
     * @param var2 second operand.
     * @throws IOException
     */
    public void divide(int var1, int var2) throws IOException {
        if (var2 == 0) {
            sendErrorMessage("divide",
                    Constants.WS_DIVISION_BY_ZERO_ERROR_CODE,
                    Constants.WS_DIVISION_BY_ZERO_ERROR_MESSAGE);
        } else {
            int result = var1 / var2;
            constructJsonObject("divide", result);
        }
    }

    /**
     * Construct json object. Method required for showing case when first message comes from
     * server to client.
     * @throws IOException
     */
    public void triggerRequest() throws IOException {
        JsonProvider provider = JsonProvider.provider();

        JsonObject statusObject = provider.createObjectBuilder()
                .add("code", Constants.WS_SUCCESS_CODE)
                .add("message", Constants.WS_SUCCESS_MESSAGE)
                .build();

        JsonObject responseObject = provider.createObjectBuilder()
                .add("action", "messageFromServer")
                .add("status", statusObject)
                .build();

        sendToSession(responseObject);
    }

    /**
     * Constructs error message.
     * @throws IOException
     */
    public void sendErrorMessage(String action, int statusCode, String statusMessage)
            throws IOException {
        JsonProvider provider = JsonProvider.provider();
        JsonObject statusObject = provider.createObjectBuilder()
                .add("code", statusCode)
                .add("message", statusMessage)
                .build();

        JsonObject responseObject = provider.createObjectBuilder()
                .add("action", action)
                .add("status", statusObject)
                .build();

        sendToSession(responseObject);
    }

    /**
     * Sends message to the client.
     * @throws IOException
     */
    private void sendToSession(JsonObject message) throws IOException {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            closeSession(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
