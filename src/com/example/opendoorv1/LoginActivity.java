package com.example.opendoorv1;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText mobileNumber = null;
	private EditText password = null;
	private Button loginBtn;
	private Button resetPwdBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mobileNumber = (EditText)findViewById(R.id.mobileNumber);
		password = (EditText)findViewById(R.id.password);
		loginBtn = (Button)findViewById(R.id.loginBtn);
	}

	public void login(View view){
		if(mobileNumber.getText().toString().equals("91559541") && 
				password.getText().toString().equals("admin")){
			Toast.makeText(getApplicationContext(), "Redirecting...", 
					Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "Wrong Credentials",
					Toast.LENGTH_SHORT).show();
		}
	}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.login, menu);
			return true;
		}

	}
