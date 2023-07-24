package academy.prog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetUsersSet {
    public void getUsersList() {
        try {
            URL  obj = new URL(Utils.getURL() + "/list");
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            InputStream is = conn.getInputStream();
            byte[] buffer = responseBodyToArray(is);
            String bufString = new String(buffer, StandardCharsets.UTF_8);
            if (bufString != null) System.out.println(bufString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }


}
