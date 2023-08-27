package academy.prog.sample3;

import org.springframework.stereotype.Component;

@Component
public class HttpTransport extends Transport {
    @Override
    protected void DoSendMessage(String text) {
        System.out.println("Message sent via HTTP: " + text);
    }
}
