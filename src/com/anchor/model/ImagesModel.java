package com.anchor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ImagesModel {

	@SerializedName("launches")
	private List<LaunchesItem> launches;

	public void setLaunches(List<LaunchesItem> launches){
		this.launches = launches;
	}

	public List<LaunchesItem> getLaunches(){
		return launches;
	}

	@Override
 	public String toString(){
		return 
			"ImagesModel{" + 
			"launches = '" + launches + '\'' + 
			"}";
		}
}