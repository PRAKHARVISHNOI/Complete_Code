package edu.simberbest.dcs.entity;

import java.util.concurrent.ConcurrentLinkedQueue;
@Deprecated
public class Queue {

	private ConcurrentLinkedQueue list;
	
	public Queue(){
		list= new ConcurrentLinkedQueue();
	}
	
	public boolean enqueue(Object item){
	   return list.add(item);
	}
	
	public boolean dequeue(){
		return list.remove(0);
	}
	
	public Object peek(){
		return list.peek();
	}
	
}
