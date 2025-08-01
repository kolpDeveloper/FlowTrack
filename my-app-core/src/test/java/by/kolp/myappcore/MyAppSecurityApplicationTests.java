package by.kolp.myappcore;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        //"by.kolp.myappweb",
        //"by.kolp.myappservice",
        "by.kolp.myappcore"
       // "by.kolp.myappsecurity"
})
public class MyAppSecurityApplicationTests {

    @Test
    void contextLoads() {
    }

}
