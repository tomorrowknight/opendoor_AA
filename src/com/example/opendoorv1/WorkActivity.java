package com.example.opendoorv1;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;


import java.util.ArrayList;
import java.util.List;


import com.parse.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class WorkActivity extends Activity implements EndlessListView.EndlessListener {

	private final static int ITEM_PER_REQUEST = 50;
	private List<ParseObject>taskActiveList = new ArrayList<ParseObject>();
	EndlessListView lv;
	SharedPreferences sharedpreferences;
	String mobile = "";
	int mult = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);
		sharedpreferences = getSharedPreferences(LoginActivity.MYPREFERENCES, 
				Context.MODE_PRIVATE);
		String mobile = sharedpreferences.getString("name", "");
		lv = (EndlessListView) findViewById(R.id.el);
		EndlessAdapter adp = new EndlessAdapter(this, createItems(mult), R.layout.row_layout);
		lv.setLoadingView(R.layout.loading_layout);
		lv.setAdapter(adp);
		lv.setListener(this);
		Parse.initialize(this, "iRvjigqtqgGRZT7JZihLP9tsRHUEsuwlNR9uYqC3", "KBfojXYU68Oh4XoFoG3gKHDQYaM2zU6DjIBMT8MZ");

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> taskList, ParseException e) {

				long currentDate = System.currentTimeMillis() / 1000L;
				if (e == null) {
					for(int i=0;i<taskList.size();i++){
						boolean isActive = taskList.get(i).getBoolean("is_active");
						boolean expiry = taskList.get(i).getLong("expiry_date")> currentDate;
						if(isActive && expiry){
							taskActiveList.add(taskList.get(i));
						}
					}
					Log.d("Is Active", "Retrieved " + taskActiveList.get(10).get("title") );

				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.work, menu);
		return true;
	}

	private class FakeNetLoader extends AsyncTask<String, Void, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {	
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			return createItems(mult);
		}

		@Override
		protected void onPostExecute(List<String> result) {			
			super.onPostExecute(result);
			lv.addNewData(result);
		}



	}




	private List<String> createItems(int mult) {
		List<String> result = new ArrayList<String>();
		if(ITEM_PER_REQUEST<taskActiveList.size()){
			for(int i=0;i<ITEM_PER_REQUEST;i++){
				result.add((String) taskActiveList.get(i*mult).get("title"));
				Log.e("Task" + i +": " , "WORKS");
			}
		}else{
			for(int i=0;i<taskActiveList.size();i++){
				result.add((String) taskActiveList.get(i*mult).get("title"));
				Log.e("Task" + i +": " , "WORKS");
			}
		}

		Log.e("RESULT",String.valueOf(result.size()));
		return result;
	}

	public void loadData() {
		System.out.println("Load data");
		mult += 10;
		// We load more data here
		FakeNetLoader fl = new FakeNetLoader();
		fl.execute(new String[]{});

	}


}
