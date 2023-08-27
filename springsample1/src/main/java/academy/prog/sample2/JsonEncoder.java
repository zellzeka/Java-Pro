package academy.prog.sample2;

import org.springframework.stereotype.Component;

@Component
public class JsonEncoder implements Encoder {
    public String encode(String text) {
        return "{ \"message\": \"" + text + "\" }";
    }
}
