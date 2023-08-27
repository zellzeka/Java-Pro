package academy.prog.sample2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            var sender = context.getBean(MessageSender.class);
            sender.sendMessage("Hello world");
        }
    }
}
