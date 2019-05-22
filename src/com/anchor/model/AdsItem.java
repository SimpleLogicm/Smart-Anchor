package com.anchor.model;

import com.google.gson.annotations.SerializedName;


public class AdsItem {

	@SerializedName("date")
	private String date;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private Object id;

	@SerializedName("youtube_id")
	private String youtubeId;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	public void setYoutubeId(String youtubeId){
		this.youtubeId = youtubeId;
	}

	public String getYoutubeId(){
		return youtubeId;
	}

	@Override
 	public String toString(){
		return 
			"AdsItem{" + 
			"date = '" + date + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",youtube_id = '" + youtubeId + '\'' + 
			"}";
		}
}