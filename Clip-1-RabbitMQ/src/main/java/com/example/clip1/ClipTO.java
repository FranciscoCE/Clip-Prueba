package com.example.clip1;

public class ClipTO {


	
	private final long hits;
	private final String key;
	
	public ClipTO(long hits, String key) {
		this.hits = hits;
		this.key = key;
	}

	public long getHits() {
		return hits;
	}

	public String getKey() {
		return key;
	}
	
}
