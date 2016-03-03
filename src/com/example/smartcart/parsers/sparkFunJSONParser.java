package com.example.smartcart.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smartcart.R;
import com.example.smartcart.model.sparkFun;

public class sparkFunJSONParser {

	public static List<sparkFun> parseFeed(String content) {

		try {
			JSONArray ar = new JSONArray(content);
			List<sparkFun> itemsList = new ArrayList<>();

			for (int i = 0; i < ar.length(); i++) {

				JSONObject obj = ar.getJSONObject(i);

				sparkFun SparkFun = new sparkFun();

				SparkFun.setPhonenumber(obj.getString("phonenumber"));
				SparkFun.setProductname(obj.getString("productname"));
				SparkFun.setProductprice(obj.getString("productprice"));
				SparkFun.setKaand(obj.getString("kaand"));
				SparkFun.setTimestamp(obj.getString("timestamp"));
				SparkFun.setNoOfItems(ar.length());

				String product = obj.getString("productname").toLowerCase();
				
				
//				switch (product) {
//				case  product.contains("oreo"):

//				}
				
	            //doing juggad for the app again , don't know why the fucking changes in the app are not working.
				// My mapping is
				// Levi's Jeans - oreo
				// Wrogn shirt(Puma cap) -  jam
				// Arrow shirt(Nike Tshirt) - sauce
				// Leee shirt  - milk	
				// changing the pics and doing jugaad. 
				// like changing oreo image to levi's jeans.
				
				if (product.contains("oreo")){
					SparkFun.setIconID(R.drawable.oreo);
				}
				if (product.contains("jam")){
					SparkFun.setIconID(R.drawable.jam);
				}
				if (product.contains("sauce")){
					SparkFun.setIconID(R.drawable.sauce);
				}
				if (product.contains("milk")){
					SparkFun.setIconID(R.drawable.milk);
				}
				



                  
				itemsList.add(SparkFun);

			}
			
			
			

			return itemsList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}
}
