package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();

			Thread th = new Thread(new GetThread(login));
			th.setDaemon(true);
			th.start();

			System.out.println("Enter message recipient: @login message");
			while (true) {
				String textMessage = scanner.nextLine();
				if (textMessage.isEmpty()) break;
				String text = "";
				String to = "";
				if (textMessage.equals("/list")) {
					new GetUsersList().getUsersOnline();
				} else if (textMessage.startsWith("@")) {
					to = textMessage.substring(textMessage.indexOf("@") + 1, textMessage.indexOf(" "));
					text = textMessage.substring(textMessage.indexOf(" ") + 1);
				} else {
					text = textMessage;
				}


				Message m = new Message(login, to, text);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
