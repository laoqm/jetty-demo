package com.javacodegeeks.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

public class SimpleSupendResumeServlet extends HttpServlet{

	private static final long serialVersionUID = 2429884261212882858L;
	private MyAsyncHandler myAsyncHandler;
	
	@Override
	public void init() throws ServletException {
		myAsyncHandler = new MyAsyncHandler() {
			@Override
			public void register(final MyHandler myHandler) {
			  	new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(10000);
							myHandler.onMyEvent("complete");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();;
			}
		};
		
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		final PrintWriter writer = response.getWriter();
		final Continuation continuation = ContinuationSupport.getContinuation(request);
		if(continuation.isInitial()){
			sendMyFirstResponse(response, null);
			continuation.suspend();
			
			myAsyncHandler.register(new MyHandler() {
				
				@Override
				public void onMyEvent(Object result) {
					continuation.setAttribute("result",result );
					continuation.resume();
				}
			});
			return;
		}
		if(continuation.isExpired()){
			sendMyTimeoutResponse(response);
			return;
		}
		Object results = request.getAttribute("results");
		if(results == null){
			response.getWriter().write("why reach here??");
			continuation.resume();
			return;
		}
		sendMyResultResponse(response, results);
	}
	
	private interface MyAsyncHandler{
	    public void register(MyHandler myHandler);
	}
	
	public interface MyHandler{
	    public void onMyEvent(Object obj);
	}
	
	private void sendMyFirstResponse(HttpServletResponse response,Object results) throws IOException{
          response.setContentType("text/html");
          response.getWriter().write("start");
          response.getWriter().flush();
	}
	
	private void sendMyResultResponse(HttpServletResponse response,Object results) throws IOException{
        //response.setContentType("text/html");
        response.getWriter().write("result: "+results);
        response.getWriter().flush();
	}
	
	private void sendMyTimeoutResponse(HttpServletResponse response) throws IOException{
		response.getWriter().write("timeout");
	}
}
