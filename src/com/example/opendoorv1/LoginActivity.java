package com.example.opendoorv1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
import com.parse.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.List;
import android.util.Log;

public class LoginActivity<login> extends Activity {

	private EditText mobileNumber = null;
	private EditText password = null;
	private Button loginBtn;
	private Button resetPwdBtn;
	public static final String MYPREFERENCES = "MYPREFERENCES";
	public static final String name = "name"; 
	public static final String pass = "pass"; 
	SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//--------+This is Opendoor's info+-----------
		Parse.initialize(this, "iRvjigqtqgGRZT7JZihLP9tsRHUEsuwlNR9uYqC3", "KBfojXYU68Oh4XoFoG3gKHDQYaM2zU6DjIBMT8MZ");
		//-------+This is my test app's info+--------
		//Parse.initialize(this, "DAMvJpwZmzYevsSW7JEQ1nf4eZMMuNS9u9zQkyIX", "VW60HdiH3V95qQWt47wZwykwkfNDuMWH3R32U5BP");
		
		mobileNumber = (EditText)findViewById(R.id.mobileNumber);
		password = (EditText)findViewById(R.id.password);
		loginBtn = (Button)findViewById(R.id.loginBtn);
	}

	protected void onResume() {
		sharedpreferences=getSharedPreferences(MYPREFERENCES, 
				Context.MODE_PRIVATE);
		if (sharedpreferences.contains(name))
		{
			if(sharedpreferences.contains(pass)){
				Intent i = new Intent(this,com.example.opendoorv1.WorkActivity.class);
				startActivity(i);
			}
		}
		super.onResume();
	}



	public void login(View view){
		ParseUser.logInInBackground(mobileNumber.getText().toString(), password.getText().toString(), new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				Intent in = new Intent(LoginActivity.this,com.example.opendoorv1.WorkActivity.class);
		
				if (user != null) {
					// Hooray! The user is logged in.
					Log.e("loginTest","Log in WORKS!");
					Toast.makeText(getApplicationContext(), "Redirecting...", 
							Toast.LENGTH_SHORT).show();
					Editor editor = sharedpreferences.edit();
					String mobile = mobileNumber.getText().toString();
					String pwd = password.getText().toString();
					editor.putString(name, mobile);
					editor.putString(pass, pwd);
					editor.commit();
					startActivity(in);
				} else {
					// Signup failed. Look at the ParseException to see what happened.
					Log.e("loginTest","Log in FAIL");
					Toast.makeText(getApplicationContext(), "Wrong Credentials",
							Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
