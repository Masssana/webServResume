package ru.college.resumesserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.college.resumesserv.Controller.ResumeController;
import java.io.IOException;

/**
 * App class looks like that
 *
 */
@SpringBootApplication
public class App
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}

