package com.rbkmoney.schedulatorproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SchedulatorProxyApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulatorProxyApplication.class, args);
    }

}
