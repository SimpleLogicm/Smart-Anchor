package com.anchor.model;

public class Scheme {

	int scheme_id;
	String scheme_desc,scheme_code,scheme_status;
	
	
	public Scheme() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Scheme(int scheme_id, String scheme_desc, String scheme_code,
			String scheme_status) {
		super();
		this.scheme_id = scheme_id;
		this.scheme_desc = scheme_desc;
		this.scheme_code = scheme_code;
		this.scheme_status = scheme_status;
	}


	public int getScheme_id() {
		return scheme_id;
	}


	public void setScheme_id(int scheme_id) {
		this.scheme_id = scheme_id;
	}


	public String getScheme_desc() {
		return scheme_desc;
	}


	public void setScheme_desc(String scheme_desc) {
		this.scheme_desc = scheme_desc;
	}


	public String getScheme_code() {
		return scheme_code;
	}


	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}


	public String getScheme_status() {
		return scheme_status;
	}


	public void setScheme_status(String scheme_status) {
		this.scheme_status = scheme_status;
	}


	@Override
	public String toString() {
		return "Scheme [scheme_id=" + scheme_id + ", scheme_desc="
				+ scheme_desc + ", scheme_code=" + scheme_code
				+ ", scheme_status=" + scheme_status + "]";
	}
	
	
	
}
