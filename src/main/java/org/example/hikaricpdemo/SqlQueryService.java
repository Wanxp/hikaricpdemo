package org.example.hikaricpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
@Slf4j
public class SqlQueryService {

	private static final String sql = "select id, name, birthday, active_level from user";

	@Autowired
	private DataSource dataSource;

	@Scheduled(cron = "0/10 * * * * *")
	@Async
	public void query() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSource.getConnection();
			log.warn("thread:{},connection:{}", Thread.currentThread().getName(), dataSource);
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			int row = 0;
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				Date birthday = resultSet.getDate("birthday");
				Boolean active = resultSet.getBoolean("active_level");
				log.debug("row[{}]: id:{}, name:{}, birthday:{}, active:{}", row++, id, name, birthday, active);
			}
		} catch (SQLException throwables) {
			log.error("query failed", throwables);
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
