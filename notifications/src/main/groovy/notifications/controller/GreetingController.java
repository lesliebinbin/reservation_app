package notifications.controller;

import notifications.entity.Greeting;
import notifications.entity.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

@Controller
public class GreetingController {

    private SimpMessagingTemplate template;
    @Autowired
    @Qualifier("onlineUsersSessions")
    private Map<String, String> onlineUserLists;

    public GreetingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/new")
    public void newAppointment(HelloMessage message) throws Exception {
        System.out.println("here new");
        Thread.sleep(1000); // simulated delay
        template.convertAndSend("/topic/create", String.format("Create, %s!", HtmlUtils.htmlEscape(message.getName())));
    }

    @MessageMapping("/cancel")
//    @SendToUser("/topic/cancel")
    public void cancelAppointment(MessageHeaders messageHeaders, @Payload HelloMessage message, @Header(name = "simpSessionId") String sessionId) throws Exception {
        System.out.println(messageHeaders);
        template.convertAndSendToUser(sessionId, "/topic/cancel", new Greeting("Cancel, " + HtmlUtils.htmlEscape(message.getName()) + "!"), messageHeaders);
    }

    @MessageMapping("/update")
    @SendTo("/topic/update")
    public Greeting updateAppoinment(HelloMessage message) throws Exception {
        System.out.println("here update");
        Thread.sleep(1000); // simulated delay
        return new Greeting("Update, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    //TODO figure out a way to map online username to sessionId
    //TODO when a user related to an appointment is not online, the notification should be saved into database

}
