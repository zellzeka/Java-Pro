package academy.prog.sample4;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class Transport {
    @Autowired(required = false)
    private Encoder encoder;

    public void sendMessage(String text) {
        if (encoder != null)
            text = encoder.encode(text);

        DoSendMessage(text);
    }

    protected abstract void DoSendMessage(String text);
}
