package academy.prog;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
	private static final MessageList msgList = new MessageList();

	private int countOfMessages;

	private final Gson gson;
	private final List<Message> list = new LinkedList<>();

	public static MessageList getInstance() {
		return msgList;
	}

	private MessageList() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	public synchronized void add(Message m) {
		list.add(m);
	}

	public synchronized String toJSON(int n, String receiver) {
		if (n >= list.size()) return null;
		countOfMessages= list.size();
		return gson.toJson(new JsonMessages(list, n,receiver,countOfMessages));
	}

	public synchronized String getOnlineUsersToJson() {
		return gson.toJson((new UsersList()).getOnlineUsers());
	}
}
