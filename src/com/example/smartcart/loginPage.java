package com.example.smartcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginPage extends ActionBarActivity {
	
	public Button myCart;
	public EditText phoneNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("iCart");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		
		phoneNo = (EditText) findViewById(R.id.editText1);
		
		myCart = (Button) findViewById(R.id.myCartButtonId);
		
		myCart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String message = phoneNo.getText().toString();
				Intent i = new Intent("second");
				Bundle bundle = new Bundle();
				bundle.putString("send_data", message);
				i.putExtras(bundle);
				startActivity(i);
				
			}
		});
		
		
	}

}
