package com.javacodegeeks.snippets.enterprise.jettyjndi;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.h2.jdbcx.JdbcDataSource;

public class JettyJndiExampleMain {

	public static void main(String[] args) {
		try {
			// 1.We are creating the service
			Server server = new Server(8080);

			// 2.We are enabling Jetty-plus configuration
			org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
			classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");

			// 3.We are creating the web-application context
			WebAppContext ctx = new WebAppContext();
			ctx.setResourceBase("src/main/webapp");
			ctx.setContextPath("/jetty-jndi-example");

			// 4.We are creating the data-source here
			JdbcDataSource dataSource = new JdbcDataSource();
			dataSource.setUrl("jdbc:h2:tcp://localhost/~/jcgdb");
			dataSource.setUser("sa");

			// 5.Here we are registring the datasource for our server
			new org.eclipse.jetty.plus.jndi.Resource(server, "jdbc/jcgDS", dataSource);

			// 6.Handler setting and application registration code
			server.setHandler(ctx);
			server.start();
			server.join();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}
}
