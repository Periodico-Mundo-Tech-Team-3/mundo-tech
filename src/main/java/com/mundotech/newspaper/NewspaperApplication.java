package com.mundotech.newspaper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import me.paulschwarz.springdotenv.DotenvConfig;
import me.paulschwarz.springdotenv.DotenvPropertySource;

@SpringBootApplication
public class NewspaperApplication {

  public static void main(String[] args) {

    new SpringApplicationBuilder(NewspaperApplication.class)
        .initializers(
            (ApplicationContextInitializer<ConfigurableApplicationContext>) ctx -> DotenvPropertySource
                .addToEnvironment(ctx.getEnvironment(), DotenvConfig.defaults()))
        .run(args);
    
    

  }

}
