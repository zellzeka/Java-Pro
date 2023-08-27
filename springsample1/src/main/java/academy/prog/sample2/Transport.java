package academy.prog.sample2;

public abstract class Transport {
    private final Encoder encoder;

    //@Autowired
    public Transport(Encoder encoder) {
        this.encoder = encoder;
    }

    public void sendMessage(String text) {
        if (encoder != null)
            text = encoder.encode(text);

        DoSendMessage(text);
    }

    protected abstract void DoSendMessage(String text);
}
