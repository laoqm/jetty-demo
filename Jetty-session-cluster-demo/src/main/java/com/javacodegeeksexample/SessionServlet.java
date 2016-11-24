package com.javacodegeeksexample;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionServlet
 */
@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SessionServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String sessionId = request.getSession().getId();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println("In POST - We will post the session id here - ");
		HttpSession session = request.getSession();
		if(request.getParameter("JSESSIONID") != null)
		{
			Cookie userCookie = new Cookie("JSESSIONID",request.getParameter("JSESSIONID"));
			response.addCookie(userCookie);
		}
		else
		{
			String sessionId = session.getId();
			Cookie userCookie = new Cookie("JSESSIONID",sessionId);
			response.addCookie(userCookie);
			
		}
		String currDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").format(new Date());
		response.getWriter().println(" Current Date = " + currDate + " session id = " + session.getId());
	}

}
