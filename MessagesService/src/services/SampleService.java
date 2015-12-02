package services;

import domain.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;

@Path("/")
public class SampleService {
	
	private MessageStore msgStore = MessageStore.getInstance();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey!!";
	}
	
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public String sayXMLHello() {
	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	  }

	  // Test method
	  @GET
	  @Path("/{name}/{lname}")
	  @Produces(MediaType.TEXT_HTML)
	  public String sayHtmlHello(@PathParam("name") String name, @PathParam("lname") String lname) {
	    return "<html> " + "<title>" + "Hello: " + name + " " + lname + "</title>"
	        + "<body><h1>" + "Hello: " + name + " " + lname + "</body></h1>" + "</html> ";
	  }
	  
	  // chat message look up by id
	  @GET
	  @Path("/chat/{id}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String fetchMsg(@PathParam("id") int id) {
		  
		  
		  System.out.println("Current stored messages: " + (msgStore.getHot().size() + msgStore.getCold().size()));
		  
		  Iterator<Message> it = msgStore.getHot().iterator();
		  
		  while(it.hasNext()){
			  Message m = (Message)it.next();
			  if(m.getId() == id)
				  return m.toString();
		  }
		  
		  for(int i=0;i<msgStore.getCold().size();i++){
			  Message m = msgStore.getCold().get(i);
			  if(m.getId() == id)
				  return m.toString();
		  }
		  
		  return "{}";
	  }
	  
	  
	  // chat message generation
	  @POST
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  @Produces(MediaType.APPLICATION_JSON)
	  @Path("/chat")
	  public Response payloadPost(
			  @FormParam("username") String username,
			  @FormParam("text") String text,
			  @FormParam("timeout") int timeout
			  )throws IOException {
		ResponseBuilder rb = Response.ok();
		
	    try{
	    	if(username == null || username.length() ==0 || text == null || text.length() == 0){
	    		String error = "{\"Error\" :\"Required information missing\","
	    				+ "\"Description\":\"Username and text are required fields and cannot "
	    				+ "be left empty\"}";
	    		
	    		return Response.serverError().entity(error).build();
	    	}
	    	if(timeout<0){
	    		String error = "{\"Error\":\"Invalid time out\","
	    				+ "\"Description\":\"Time out should be a positive integer indicating the"
	    				+ "number of seconds the message remains live until time out. If left blank or"
	    				+ "with a value zero it will be set to time out in 60 seconds\"}";
	    		
	    		return Response.serverError().entity(error).build();
	    	}
	    	
	    	if(timeout == 0)
	    		timeout = 60;
	    	
	    	Message message = new Message(timeout);
	    	message.setText(text);
	    	message.setUsername(username);
	    	
	    	msgStore.addMsg(message);
	    	rb.status(201);
		    rb.entity("{\"id\": " + String.valueOf(message.getId()) + "}");
		    
	    } catch(Exception e){
	    	e.printStackTrace();
	    }

	    return rb.build();
	  }
	  
	  // chat message look up
	  @GET
	  @Path("/chats/{username}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String fetchMsgs(@PathParam("username") String username) {
		    if(username==null || username.length() == 0)
		    	return "[]";
		    StringBuilder sb = new StringBuilder();
		    
		    try{
				System.out.println("Current stored messages: " + (msgStore.getHot().size() + msgStore.getCold().size()));
				ArrayList<Message> li = new ArrayList<Message>();
				
				PriorityQueue<Message> hots = msgStore.getHot();
				ArrayList<Message> colds = msgStore.getCold();
				
			  	Iterator<Message> it = hots.iterator();
				  
			  	while(it.hasNext()){
			  		Message m = (Message)it.next();
			  		if(m.getUsername().equals(username)){
			  			li.add(m);
			  		}
			  	}

			  	sb.append("[");
			  	for(int i=0;i<li.size();i++){
			  		sb.append(li.get(i).toString());
			  		if(i<li.size()-1){
			  			sb.append(",");
			  		}
			  	}
				sb.append("]");
		    } catch(Exception e){
		    	e.printStackTrace();
		    }
			
		  	return sb.toString();
	  }
}
