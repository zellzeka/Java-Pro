package academy.prog.sample3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    @Qualifier("httpTransport")
    private Transport transport;

    public void sendMessage(String text) {
        transport.sendMessage(text);
    }
}
