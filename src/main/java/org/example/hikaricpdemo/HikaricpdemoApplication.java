package org.example.hikaricpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAsync
public class HikaricpdemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.setProperty("com.zaxxer.hikari.housekeeping.periodMs", String.valueOf(10 * 1000));
		SpringApplication.run(HikaricpdemoApplication.class, args);
	}

	@Autowired
	private SqlQueryService sqlQueryService;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 10;i > 0;i--) {
			sqlQueryService.query();
		}
	}




}
