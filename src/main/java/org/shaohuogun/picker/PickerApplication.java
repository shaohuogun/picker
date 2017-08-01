package org.shaohuogun.picker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PickerApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PickerApplication.class);
		springApplication.addListeners(new PickerStartup());
		springApplication.run(args);
	}
}
