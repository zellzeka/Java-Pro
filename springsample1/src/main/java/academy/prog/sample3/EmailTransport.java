package academy.prog.sample3;

import org.springframework.stereotype.Component;

@Component
public class EmailTransport extends Transport {
    @Override
    protected void DoSendMessage(String text) {
        System.out.println("Message sent via e-mail: " + text);
    }
}
