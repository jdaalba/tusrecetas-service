package com.jdaalba.tusrecetasservice.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jdaalba.tusrecetasservice")
public class TusrecetasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TusrecetasServiceApplication.class, args);
	}

}
