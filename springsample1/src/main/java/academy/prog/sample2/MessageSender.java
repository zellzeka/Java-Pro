package academy.prog.sample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MessageSender {
    private final Transport transport;

    //@Autowired
    public MessageSender(Transport transport) {
        this.transport = transport;
    }

    public void sendMessage(String text) {
        transport.sendMessage(text);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(getClass().getName() + " constructed");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(getClass().getName() + " is being destroyed");
    }
}
