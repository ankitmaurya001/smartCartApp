package com.example.smartcart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class endPage extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endpage);
		
		//to delete the data in the stream
	//	String uri = "http://10.196.64.81:8080/input/go0Kyy7Dxzh6dwArbqMbH0eenP0/clear?private_key=VO4oWW6pGQCMa8LEy5ByT4116O4";
	//	String uri = "http://192.168.1.107:8080/input/GV7BkPD80es8gooB2dGlIKZArMr/clear?private_key=mpLEaozr4GIwN224rPB1iWYd5w5";
		String uri = "http://10.77.133.24:8080/input/qdLPqJ1QmKHAG136Z1Alhle2bg3J/clear?private_key=g9qr05yJzMcqn18Yr1qgSRdkJn60";
		MyTask task = new MyTask();
		task.execute(uri);

	}

	public class MyTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {

			// pb.setVisibility(View.VISIBLE);

		}

		@Override
		protected String doInBackground(String... params) {

			String response = HttpManager.getData(params[0]);
			return response;

		}

		@Override
		protected void onPostExecute(String result) {

			// pb.setVisibility(View.INVISIBLE);

			// tvData.setText(result);

			if (result == null) {
				// Toast.makeText(MainActivity.this, "Can't connect to web",
				// Toast.LENGTH_SHORT).show();
				// return;

				// getData(etPollingIntervalValue,
				// "http://192.168.1.6:8080/output/RDpqP47p35Sw3x47zlgdFzZL70e.json");

				// getData(etPollingIntervalValue,
				// "http://10.196.64.81:8080/output/go0Kyy7Dxzh6dwArbqMbH0eenP0.json");

				// try {
				// Thread.sleep(2000);
				// } catch (InterruptedException e) {

				// e.printStackTrace();
				// }
				// startQuery();

			}

			// flowerList = FlowerJSONParser.parseFeed(result);
			// if (result != null) {

			// itemsList = sparkFunJSONParser.parseFeed(result);
			// updateDisplay();
		}

	}

}
