package academy.prog.sample4;

public class HttpTransport extends Transport {
    @Override
    protected void DoSendMessage(String text) {
        System.out.println("Message sent via HTTP: " + text);
    }
}
