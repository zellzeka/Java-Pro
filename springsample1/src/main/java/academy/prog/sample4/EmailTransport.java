package academy.prog.sample4;

public class EmailTransport extends Transport {
    @Override
    protected void DoSendMessage(String text) {
        System.out.println("Message sent via e-mail: " + text);
    }
}
