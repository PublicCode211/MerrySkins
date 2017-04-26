package me.mrmaurice.merryskin.cache;

import me.mrmaurice.merryskin.HeadEnum;

public class SkinCache {

	private boolean enabled = true;
	private String value = "";
	private String signature = "";
	private String texture = "";
	private HeadEnum head = HeadEnum.SANTA_HAT;
	
	public SkinCache(String value, String signature, String texture, HeadEnum head){
		this.value = value;
		this.signature = signature;
		this.texture = texture;
		this.head = head;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public void setEnabled(boolean newEnabled){
		enabled = newEnabled;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String newValue){
		value = newValue;
	}
	
	public String getSignature(){
		return signature;
	}
	
	public void setSignature(String newSignature){
		signature = newSignature;
	}
	
	public String getTexture(){
		return texture;
	}
	
	public void setTexture(String newTexture){
		texture = newTexture;
	}
	
	public HeadEnum getType(){
		return head;
	}
	
	public void setType(HeadEnum newHead){
		head = newHead;
	}
	
}
