package pl.krzysztofgwizdz.ticketit;

import org.hibernate.event.internal.DefaultPersistEventListener;
import org.hibernate.event.spi.PersistEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketItApplication {

    public static void main(String[] args) {
        DefaultPersistEventListener logger = new DefaultPersistEventListener();
        logger.onPersist();
        SpringApplication.run(TicketItApplication.class, args);
    }

}
