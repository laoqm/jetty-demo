package com.javacodegeeks.example;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

/**
 * Servlet implementation class ContinuationsExampleServlet
 */
//@WebServlet("/ContinuationsExampleServlet")
public class ContinuationsExampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ContinuationsExampleServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestId = request.getParameter("id");
		
		Continuation cont = ContinuationSupport.getContinuation(request);
		
		response.setContentType("text/plain");
		response.getWriter().println("Request id is : " + requestId + " start: " + new Date().getTime()+ Thread.currentThread().getName());
		
		cont.setTimeout(3000);
		cont.suspend();
		
		response.getWriter().println("Request id is : " + requestId + " end: " + new Date().getTime() +Thread.currentThread().getName());
		if(cont.isInitial() != true)
		{
			cont.complete();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
