package com.abc.asms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface Service<T> {

	T parse(HttpServletRequest request);

	T parse(ResultSet rs) throws SQLException;

	void registCheck(T t);

	void updateCheck(T t);

	T findAsKey(T key);

	public List<T> find(T key);

	public void insert(T bean);

	public void update(T bean);

	public void delete(T bean);
}
