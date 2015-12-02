package domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Message implements Comparable<Message>{
	private int id;
	private String username;
	private String text;
	private Calendar expiration_date;
	
	public Message(){
		this.expiration_date = Calendar.getInstance();
		expiration_date.add(Calendar.SECOND, 60);
	}
	
	public Message(int timeout){
		this.expiration_date = Calendar.getInstance();
		expiration_date.add(Calendar.SECOND, timeout);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Calendar getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Calendar expiration_date) {
		this.expiration_date = expiration_date;
	}

	@Override
	public int compareTo(Message msg) {
		// TODO Auto-generated method stub
		return this.expiration_date.compareTo(msg.getExpiration_date());
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		
		//username
		sb.append("\"");
		sb.append("id");
		sb.append("\"");
		sb.append(":");
		sb.append("\"");
		sb.append(id);
		sb.append("\"");
		sb.append(",");
		
		//username
		sb.append("\"");
		sb.append("username");
		sb.append("\"");
		sb.append(":");
		sb.append("\"");
		sb.append(username);
		sb.append("\"");
		sb.append(",");
		
		//text
		sb.append("\"");
		sb.append("text");
		sb.append("\"");
		sb.append(":");
		sb.append("\"");
		sb.append(text);
		sb.append("\"");
		sb.append(",");
		
		//expiration date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = dateFormat.format(this.expiration_date.getTime());
		
		sb.append("\"");
		sb.append("expiration_date");
		sb.append("\"");
		sb.append(":");
		sb.append("\"");
		sb.append(date);
		sb.append("\"");
		
		sb.append("}");
		
		return sb.toString();
		
		
	}
	
	public String fetchNDitch(){
		Calendar exp = Calendar.getInstance();
		setExpiration_date(exp);
		
		StringBuilder sb = new StringBuilder();
		//username
		sb.append("{");
		sb.append("\"");
		sb.append("id");
		sb.append("\"");
		sb.append(":");
		sb.append(id);
		sb.append(",");
		
		//text
		sb.append("\"");
		sb.append("text");
		sb.append("\"");
		sb.append(":");
		sb.append("\"");
		sb.append(text);
		sb.append("\"");
		
		sb.append("}");
		
		return sb.toString();
	}
}
