package com.accountsservices;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;
import com.accountsservices.Service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class AccountsServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsServicesApplication.class, args);
    }
   /* @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {


        };
}*/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
