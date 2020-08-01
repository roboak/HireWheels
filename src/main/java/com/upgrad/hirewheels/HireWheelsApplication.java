package com.upgrad.hirewheels;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.entities.*;
import com.upgrad.hirewheels.service.InitService;
import com.upgrad.hirewheels.utils.DTOEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class HireWheelsApplication implements CommandLineRunner {

	@Autowired
	InitService initService;


	public static void main(String[] args) {
		SpringApplication.run(HireWheelsApplication.class, args);
	}

	@Override
	public void run(String... arg0){
		//initService.start();

	}
}
