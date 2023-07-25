package academy.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.*;

/*

	0 - m / from=0
	1 - m
	2 - m
	....
	100 - m / from=101
	....


 */

public class GetListServlet extends HttpServlet {

	private MessageList msgList = MessageList.getInstance();

	private UsersList onlineUsers = new UsersList();

	private String receiver;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromStr = req.getParameter("from");
		receiver = req.getParameter("receiver");
		int from = 0;
		try {
			from = Integer.parseInt(fromStr);
			if (from < 0) from = 0;
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		onlineUsers.getRequestFromUsers(receiver);

		resp.setContentType("application/json");

		String json = msgList.toJSON(from,receiver);
		if (json != null) {
			OutputStream os = resp.getOutputStream();
			byte[] buf = json.getBytes(StandardCharsets.UTF_8);
			os.write(buf);
		}
	}
}
