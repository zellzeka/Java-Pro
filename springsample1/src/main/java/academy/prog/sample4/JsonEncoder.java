package academy.prog.sample4;

public class JsonEncoder implements Encoder {
    public String encode(String text) {
        return "{ \"message\": \"" + text + "\" }";
    }
}
