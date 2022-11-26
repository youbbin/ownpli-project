package dbproject.ownpli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"dbproject.ownpli.domain"})
public class OwnPliApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnPliApplication.class, args);
    }

}
