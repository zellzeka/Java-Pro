package academy.prog.sample4;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Encoder jsonEncoder() { // optional
        return new JsonEncoder();
    }

    @Bean(name = "xdxx")
    public Transport emailTransport() {
        return new EmailTransport();
    }

    @Bean
    public Transport httpTransport() {
        return new HttpTransport();
    }

    @Bean(initMethod = "postConstruct", destroyMethod = "preDestroy")
    public MessageSender messageSender(@Qualifier("httpTransport") Transport transport) {
        return new MessageSender(transport);
    }
}
