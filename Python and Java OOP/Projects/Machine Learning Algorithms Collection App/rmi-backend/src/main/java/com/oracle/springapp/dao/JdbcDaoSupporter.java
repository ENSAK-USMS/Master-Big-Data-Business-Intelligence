package com.oracle.springapp.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class JdbcDaoSupporter extends JdbcDaoSupport {
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void initialize() {
		setDataSource(dataSource);

	}
}
