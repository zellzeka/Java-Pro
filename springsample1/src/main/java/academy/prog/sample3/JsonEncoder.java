package academy.prog.sample3;

import org.springframework.stereotype.Component;

@Component
public class JsonEncoder implements Encoder {
    public String encode(String text) {
        return "{ \"message\": \"" + text + "\" }";
    }
}
