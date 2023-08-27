package academy.prog.sample1;

public class HttpTransport extends Transport {
    public HttpTransport(Encoder encoder) {
        super(encoder);
    }

    @Override
    protected void DoSendMessage(String text) {
        System.out.println("Message sent via HTTP: " + text);
    }
}
