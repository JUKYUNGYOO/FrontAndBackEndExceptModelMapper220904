package com.example.app220901;

import com.example.app220901.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({ @ComponentScan("com.example.app220901.controller"),
        @ComponentScan("com.example.app220901.config")})
@EnableJpaRepositories("com.example.app220901.repository")
@EntityScan("com.example.app220901.model")
//@Configuration
public class appdemo {
    public static void main(String[] args) {
        SpringApplication.run(appdemo.class, args);
    }
//
//    @Bean
//    public AccountsRepository accountsRepository(){
//        return new AccountsRepository();
//    }
//    @Bean
//    public AccountTransactionsRepository accountTransactionsRepository(){
//        return new AccountTransactionsRepository();
//    }
//    @Bean
//    public CardsRepository accountTransactionsRepository(){
//        return new CardsRepository();
//    }
//    @Bean
//    public ContactRepository accountTransactionsRepository(){
//        return new ContactRepository();
//    }
//    @Bean
//    public CustomerRepository accountTransactionsRepository(){
//        return new CustomerRepository();
//
//    }
//    @Bean
//    public LoanRepository accountTransactionsRepository(){
//        return new LoanRepository();
//
//    }
//    @Bean
//    public NoticeRepository accountTransactionsRepository(){
//        return new NoticeRepository();
//
//    }

}
