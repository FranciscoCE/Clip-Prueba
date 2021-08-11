package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Registro")
public class Registro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  long hits;
	
	private  String key;
	
	
	public long getHits() {
		return hits;
	}
	public void setHits(long hits) {
		this.hits = hits;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "[hits=" + hits + ", key=" + key + "]";
	}
	
	
	
}
