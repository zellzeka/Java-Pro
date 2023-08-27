package academy.prog.sample1;

enum TransportType {HTTP, Email};

public class Main {

    // change me
    private static TransportType transportType = TransportType.HTTP;

    public static void main(String[] args) {
        Encoder encoder = new JsonEncoder();

        Transport transport;
        if (transportType == TransportType.Email)
            transport = new EmailTransport(encoder);
        else
            transport = new HttpTransport(encoder);

        MessageSender sender = new MessageSender(transport);
        sender.sendMessage("Hello world");
    }
}
