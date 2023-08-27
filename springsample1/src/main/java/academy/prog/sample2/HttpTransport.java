package academy.prog.sample2;

import org.springframework.stereotype.Component;

@Component
public class HttpTransport extends Transport {
    public HttpTransport(Encoder encoder) {
        super(encoder);
    }

    @Override
    protected void DoSendMessage(String text) {
        System.out.println("Message sent via HTTP: " + text);
    }
}
