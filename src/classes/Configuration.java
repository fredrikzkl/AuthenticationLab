package classes;

import java.io.Serializable;

public class Configuration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -170307896967207625L;
	
	private String paramter;
	private String value;
	
	public Configuration(String parameter, String value){
		this.paramter = parameter;
		this.value = value;
	}
	
	public String getParamter() {
		return paramter;
	}
	public void setParamter(String paramter) {
		this.paramter = paramter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return "" + paramter +" : " + value; 
	}
	
}
