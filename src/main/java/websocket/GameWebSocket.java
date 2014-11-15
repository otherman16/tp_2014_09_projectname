package websocket;

import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

@WebSocket
public class GameWebSocket {
    private String gamerEmail;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String gamerEmail, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.gamerEmail = gamerEmail;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        System.out.append("Open_Socket");
        setSession(session);
        webSocketService.addSocket(this);
        gameMechanics.addGamer(gamerEmail);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //webSocketService.deleteSocket(this); // нельзя здесь удалять сокет, потому что он еще используется для отправки сообщения game_over
    }

    @OnWebSocketMessage
    public void onMessage(String data)  {
        try {
            System.out.append("onMessage").append(data);
            JSONObject jsonRequest = new JSONObject(data);
            int direction = jsonRequest.getInt("dir");
            gameMechanics.enemyStepAction(gamerEmail, direction);
        } catch (Exception e) {
            System.out.println("Exception in GameWebSocket.onMessage: " + e.getMessage());
        }
    }

    public String getMyName() {
        return gamerEmail;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void sendResponse(JSONObject jsonRequest) {
        try {
            session.getRemote().sendString(jsonRequest.toString());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
