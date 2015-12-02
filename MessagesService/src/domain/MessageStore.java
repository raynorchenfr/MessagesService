package domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MessageStore {	
	
	private static MessageStore instance;
	
	private PriorityQueue<Message> hot;//unexpired messages
	private ArrayList<Message> cold; //expired messages
	
	private HashSet<String> usernames;
	
	private int MID=1; //internal incremental message id
	
	
	private MessageStore(){
		hot = new PriorityQueue<Message>();
		cold = new ArrayList<Message>();
		usernames = new HashSet<String>();
	}
	
	public synchronized static MessageStore getInstance(){
		if(instance == null){
			instance = new MessageStore();
		}
		
		return instance;
	}
	
	public void addMsg(Message msg){
		check();
		if(!usernames.contains(msg.getUsername())){
			usernames.add(msg.getUsername());
		}
		msg.setId(MID++);
		this.hot.add(msg);
		System.out.println("Added message: "+msg.toString());
		System.out.println("Curr messages: "+(hot.size()+cold.size()));
	}
	
	private synchronized void check(){
		Calendar curr = Calendar.getInstance();
		while(!hot.isEmpty()){
			Calendar exp = hot.peek().getExpiration_date();
			int res = curr.compareTo(exp);
			
			if(res<0){
				break;
			} else {
				cold.add(hot.poll());
			}
		}
	}

	public PriorityQueue<Message> getHot() {
		check();
		return hot;
	}

	public void setHot(PriorityQueue<Message> hot) {
		this.hot = hot;
	}

	public ArrayList<Message> getCold() {
		check();
		return cold;
	}

	public void setCold(ArrayList<Message> cold) {
		this.cold = cold;
	}

	public HashSet<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(HashSet<String> usernames) {
		this.usernames = usernames;
	}

	public static void setInstance(MessageStore instance) {
		MessageStore.instance = instance;
	}
	
	
}
