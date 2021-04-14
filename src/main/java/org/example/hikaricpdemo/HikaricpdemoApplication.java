package org.example.hikaricpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class HikaricpdemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HikaricpdemoApplication.class, args);
	}

	/**
	 * 	注入上下文datasource
	 */
	@Autowired
	private DataSource dataSource;

	static String sql = "select id, name, birthday, active_level from user";

	@Override
	public void run(String... args) throws Exception {
		log.warn("input is empty");
		Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		int row = 0;
		while (resultSet.next()) {
			Long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			Date birthday = resultSet.getDate("birthday");
			Boolean active = resultSet.getBoolean("active_level");
			log.warn("row[{}]: id:{}, name:{}, birthday:{}, active:{}", row++, id, name, birthday, active);
		}
	}
}
