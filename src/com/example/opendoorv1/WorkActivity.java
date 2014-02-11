package com.example.opendoorv1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class WorkActivity extends Activity implements EndlessListView.EndlessListener {
	
	private final static int ITEM_PER_REQUEST = 50;
	EndlessListView lv;
	
	int mult = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);
		lv = (EndlessListView) findViewById(R.id.el);

		EndlessAdapter adp = new EndlessAdapter(this, createItems(mult), R.layout.row_layout);
		lv.setLoadingView(R.layout.loading_layout);
		lv.setAdapter(adp);

		lv.setListener(this);
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

		for (int i=0; i < ITEM_PER_REQUEST; i++) {
			result.add("Item " + (i * mult));
		}

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
