package com.javacodegeeks.snippets.enterprise.jettyjndi;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javacodegeeks.snippets.enterprise.jettyjndi.dbutils.DatabaseUtil;

public class JndiExampleServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<String> articleNames = DatabaseUtil.getArticleNames();
		for (String articleName : articleNames) {
			resp.getOutputStream().println(articleName);
		}
	}
}
