package uk.co.stircomp.emojemap.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class BloombergResponse {
	
	private LinkedList<BlSecurity> securities;
	
	public BloombergResponse() {
		
		securities = new LinkedList<BlSecurity>();
		
	}
	
	public void addSecurity(BlSecurity s) {
		
		securities.add(s);
		
	}
	
	public LinkedList<BlSecurity> getSecurities() {
		
		return this.securities;
		
	}
	
	public static BloombergResponse generateResponse(String data) {
		
		//System.out.println("<<<");
		//System.out.println(data);
		//System.out.println(">>>");
		
		// Create the response object.
		BloombergResponse response = new BloombergResponse();
		
		// Parse the JSON to a Java object.
		Object obj = JSONValue.parse(data);		
		JSONObject json =(JSONObject)obj;
		
		// Output the response message
		System.out.println("Response Code: " + json.get("message"));
		
		// Get the data blocks
		JSONArray responseData = (JSONArray)json.get("data");
		
		// For each of these, look for at their children
		for (int i = 0; i < responseData.size(); i++) {
			
			JSONObject selectedSecurityBlock = (JSONObject) responseData.get(i);
			
			// Get the security data field.
			JSONArray securities = (JSONArray)selectedSecurityBlock.get("securityData");
			for (int j = 0; j < securities.size(); j++) {
				
				JSONObject security = (JSONObject) securities.get(j);
				BlSecurity s = new BlSecurity(security.get("security").toString());
				
				JSONObject fields = (JSONObject) security.get("fieldData");
				
				Iterator iter = fields.entrySet().iterator();
				while(iter.hasNext()){
				      Map.Entry entry = (Map.Entry)iter.next();
				      s.addField(entry.getKey().toString(), entry.getValue().toString());
				}
				
				// Add security to respons
				response.addSecurity(s);
				
			}
						
		}		
		
		return response;		
		
	}

}
