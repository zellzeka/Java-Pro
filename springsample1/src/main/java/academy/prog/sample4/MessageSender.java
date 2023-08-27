package academy.prog.sample4;

public class MessageSender {
    private final Transport transport;

    public MessageSender(Transport transport) {
        this.transport = transport;
    }

    public void sendMessage(String text) {
        transport.sendMessage(text);
    }

    public void postConstruct() {
        System.out.println(getClass().getName() + " constructed");
    }

    public void preDestroy() {
        System.out.println(getClass().getName() + " is being destroyed");
    }
}
