package com.pichangetheworld.venmotest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.pichangetheworld.venmotest.VenmoLibrary.VenmoResponse;

public class MainActivity extends Activity {
	
	private static final String __APP_SECRET__ = "venmotest_pichangetheworld";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    switch(requestCode) {
	        case 1: { //1 is the requestCode we picked for Venmo earlier when we called startActivityForResult
	            if(resultCode == RESULT_OK) {
	                String signedrequest = data.getStringExtra("signedrequest");
	                if(signedrequest != null) {
	                    VenmoResponse response = 
	                    		(new VenmoLibrary()).validateVenmoPaymentResponse(signedrequest, __APP_SECRET__);
	                    if(response.getSuccess().equals("1")) {
	                        //Payment successful.  Use data from response object to display a success message
	                        String note = response.getNote();
	                        String amount = response.getAmount();
	                        
	                        System.out.println("Note: " + note + " amount:" + amount);
	                    }
	                }
	                else {
	                    String error_message = data.getStringExtra("error_message");
	                    //An error occurred.  Make sure to display the error_message to the user
	                    
	                    System.err.println("Error:" + error_message);
	                }                               
	            }
	            else if(resultCode == RESULT_CANCELED) {
	                //The user cancelled the payment
	            }
	        break;
	        }           
	    }
	}
}
