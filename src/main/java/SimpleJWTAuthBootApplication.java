import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("com.test.bootapp.domain.entities")
@ComponentScan("com.test.bootapp")
@EnableJpaRepositories
public class SimpleJWTAuthBootApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SimpleJWTAuthBootApplication.class, args);
    }

}
