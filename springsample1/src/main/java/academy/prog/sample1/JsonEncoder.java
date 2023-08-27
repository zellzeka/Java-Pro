package academy.prog.sample1;

public class JsonEncoder implements Encoder {
    public String encode(String text) {
        return "{ \"message\": \"" + text + "\" }";
    }
}
