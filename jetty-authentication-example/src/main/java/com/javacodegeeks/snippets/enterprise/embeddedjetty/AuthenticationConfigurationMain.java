package com.javacodegeeks.snippets.enterprise.embeddedjetty;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class AuthenticationConfigurationMain {

	public static void main(String[] args) throws Exception {

		
		//1. Creating the server on port 8080
		Server server = new Server(8080);

		//2. Creating the WebAppContext for the created content
		WebAppContext ctx = new WebAppContext();
		ctx.setResourceBase("src/main/webapp");

		//3. Creating the LoginService for the realm
		HashLoginService loginService = new HashLoginService("JCGRealm");
		
		//4. Setting the realm configuration there the users, passwords and roles reside
		loginService.setConfig("jcgrealm.txt");

		//5. Appending the loginService to the Server
		server.addBean(loginService);
		
		//6. Setting the handler
		server.setHandler(ctx);

		//7. Starting the Server
		server.start();
		server.join();

	}
}
