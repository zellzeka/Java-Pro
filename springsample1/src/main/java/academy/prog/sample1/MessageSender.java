package academy.prog.sample1;

public class MessageSender {
    private final Transport transport;

    public MessageSender(Transport transport) {
        this.transport = transport;
    }

    public void sendMessage(String text) {
        transport.sendMessage(text);
    }
}
