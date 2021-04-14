package org.example.hikaricpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class HikaricpdemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HikaricpdemoApplication.class, args);
	}

	/**
	 * 注入上下文datasource
	 */
	@Autowired
	private DataSource dataSource;

	static String sql = "select id, name, birthday, active_level from user";

	@Override
	public void run(String... args) throws Exception {
		query();
	}


	@Scheduled(cron = "0/10 * * * * *")
	public void query() throws SQLException {
		log.warn("query start");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			int row = 0;
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				Date birthday = resultSet.getDate("birthday");
				Boolean active = resultSet.getBoolean("active_level");
				log.warn("row[{}]: id:{}, name:{}, birthday:{}, active:{}", row++, id, name, birthday, active);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

}
