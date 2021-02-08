package notifications.component;

import notifications.entity.Greeting;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.Map;

@Component
public class WebsocketEventListener {

    private SimpMessagingTemplate template;
    private Map<String, String> usersOnline;

    public WebsocketEventListener(SimpMessagingTemplate template, @Qualifier("onlineUsersSessions") Map<String, String> usersOnline) {
        this.template = template;
        this.usersOnline = usersOnline;
    }


    @EventListener
    public void handleWebsocketConnectionListener(SessionConnectedEvent event) {
        final SimpMessageHeaderAccessor simpConnectMessage = SimpMessageHeaderAccessor.wrap((Message<?>) event.getMessage().getHeaders().get("simpConnectMessage"));
        String user = simpConnectMessage.getFirstNativeHeader("userId");
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        usersOnline.put(sessionId, user);
        usersOnline.forEach((k, v) -> {
            System.out.println("haha");
            if (!k.equals(sessionId)) {
                System.out.println("hehe");
                template.convertAndSendToUser(k,"/topic/cancel", new Greeting("Cancel, some fuck is on line"), createHeaders(k));
            }
        });
    }

    private MessageHeaders createHeaders(String sessionId) {
        final SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        simpMessageHeaderAccessor.setSessionId(sessionId);
        simpMessageHeaderAccessor.setLeaveMutable(true);
        return simpMessageHeaderAccessor.getMessageHeaders();

    }

}
