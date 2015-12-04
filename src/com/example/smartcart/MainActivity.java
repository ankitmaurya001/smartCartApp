package com.example.smartcart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcart.model.Flowers;
import com.example.smartcart.model.sparkFun;
import com.example.smartcart.parsers.sparkFunJSONParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//Array of options -- > ArrayAdapter --> ListView

//List view : {views : da_items.xml}

public class MainActivity extends ActionBarActivity {

	public TextView grandTotalTv;
	public TextView grandTotalValueTv;

	public boolean query = true;
	public String[] simpleArray;
	public TextView myCartTv;

	public Button checkoutButton;
	public String kaand = "green";
	public double total = 0;

	List<Flowers> flowerList;

	List<sparkFun> itemsList = new ArrayList<sparkFun>();
	List<sparkFun> itemsList1;
	public int noOfProducts = 0;

	public double etPollingIntervalValue;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitle("iCart");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get references

		// tvData = (TextView) findViewById(R.id.tvDataID);
		// pb = (ProgressBar) findViewById(R.id.progressBar1);
		// pb.setVisibility(View.INVISIBLE);
		// etPollingInterval = (EditText)
		// findViewById(R.id.etPollingIntervalID);

		myCartTv = (TextView) findViewById(R.id.myCart);

		grandTotalTv = (TextView) findViewById(R.id.grandTotal);
		grandTotalValueTv = (TextView) findViewById(R.id.grandTotalValue);
		grandTotalTv.setText("");
		grandTotalValueTv.setText("");

		checkoutButton = (Button) findViewById(R.id.checkoutButtonId);
		Log.d("tagname", "starting query");

		startQuery();


		// showMessage();

		// showList();

		// tvData.setText("");

		// bStop = (Button) findViewById(R.id.bStopID);
		// bStart = (Button) findViewById(R.id.bStartID);

		// if(etPollingInterval.requestFocus())
		// ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED,
		// InputMethodManager.HIDE_IMPLICIT_ONLY);

		checkoutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				stopQuery();
				Intent intent = new Intent(v.getContext(), endPage.class);
				startActivityForResult(intent, 0);

			}
		});

	}



	private String showNumber() {

		Bundle bundle = getIntent().getExtras();
		String message = bundle.getString("send_data");
		return message;

	}

	private void showList() {
		// Create list of items

		List<String> where = new ArrayList<String>();

		where.add(0, "11");
		where.add(1, "21");

		String[] simpleArray = new String[where.size()];
		where.toArray(simpleArray);

		String[] myItems = { "blue", "green", "red", "blue", "green", "red",
				"blue", "green", "red", "blue", "green", "red", "blue",
				"green", "red", "blue", "green", "red" };

		// Build Adapter

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.da_items, simpleArray);

		// Configure the list view.

		ListView list = (ListView) findViewById(R.id.listViewMain);
		list.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// getMenuInflater().inflate(R.menu., menu);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.mmain_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.startShopping_id:
			// startQuery();
			return true;
		case R.id.stopShopping_id:
			stopQuery();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private void stopQuery() {
		query = false;
		showStopToast();

	}

	private void startQuery() {
		Log.d("tagname", "in start query");
		query = true;
		Toast.makeText(MainActivity.this, "Starting query", Toast.LENGTH_SHORT)
				.show();
		// getData(etPollingIntervalValue,
		// "http://data.sparkfun.com/output/VGYDordaJJf7VGxaMyYK.json?page=1");

		// getData(etPollingIntervalValue,
		// "http://192.168.1.6:8080/output/RDpqP47p35Sw3x47zlgdFzZL70e.json");

		getData(etPollingIntervalValue,
				"http://192.168.1.100:8080/output/G2nxk3PzQVU8go2ALXWxSxp2bzK1.json");

		// getData(etPollingIntervalValue,
		// "http://10.196.64.81:8080/output/go0Kyy7Dxzh6dwArbqMbH0eenP0.json");

	}

	public void getData(Double pollingTime, String uri) {

		// if (pollingTime == 0) {
		// Toast.makeText(this, "Dont crash me:(", Toast.LENGTH_SHORT).show();
		// }

		if (isOnline()) {
			MyTask task = new MyTask();
			task.execute(uri);
		} else {
			Toast.makeText(this, "net connect kar le bhai", Toast.LENGTH_LONG)
					.show();

		}

	}

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateDisplay() {

		// sparkFun SparkFun = null;

		// noOfProducts = SparkFun.getNoOfItems();

		// update list view only when no of products have increased

		// if (SparkFun.getNoOfItems() > noOfProducts) {
		// populateListView();
		// }

		kaand = "green";
		total = 0;
		int i = 0;
		// sparkFun SparkFun = new sparkFun();

		// SparkFun.getNoOfItems();
		// Log.d("tagname", SparkFun.getProductname());
		if (itemsList != null) {
			// Log.d("tagname", "in total");
			for (sparkFun SparkFun : itemsList) {
				// tvData.append(SparkFun.getTest1() + "\n");
				if (SparkFun.getKaand().equals("green")) {
					i++;
					total = total
							+ Double.parseDouble(SparkFun.getProductprice());
					populateListView();
				}
				// grandTotalValueTv.setText("Rs ");
				// grandTotalValueTv.append("" + total);
				// grandTotalValueTv.setText(total);
				if (SparkFun.getKaand().equals("red")) {
					kaand = "red";
					query = false;
					checkoutButton.setEnabled(false);
					checkoutButton.setBackgroundColor(Color.RED);
					Toast.makeText(
							MainActivity.this,
							"Something is wrong, please proceed to manual checkout",
							5000).show();
				}
			}
		}
		Log.d("tagname", "in update display");
		 //if(query){
		// populateListView();
		// }

		// tvData.setText("");
		/*
		 * List<String> where = new ArrayList<String>(); int i = 0; String data;
		 * double total = 0;
		 * 
		 * if (itemsList != null) { for (sparkFun SparkFun : itemsList) { if
		 * (SparkFun.getPhonenumber().equals(showNumber())) { data =
		 * SparkFun.getProductname() + "               " +
		 * SparkFun.getProductprice(); total = total +
		 * Double.parseDouble(SparkFun.getProductprice()); where.add(i, data);
		 * kaand = SparkFun.getKaand(); if (kaand.equals("red")) {
		 * Toast.makeText( MainActivity.this,
		 * "Something is wrong, please proceed to manual checkout",
		 * 10000).show(); checkoutButton.setEnabled(false);
		 * checkoutButton.setBackgroundColor(Color.RED); query = false; }
		 * 
		 * i++; }
		 * 
		 * // where.add(i, SparkFun.getProductname());
		 * 
		 * // tvData.append(SparkFun.getTest1() + "\n"); } }
		 * 
		 * String[] simpleArray = new String[where.size()];
		 * where.toArray(simpleArray);
		 * 
		 * // Build Adapter
		 * 
		 * // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, //
		 * R.layout.da_items, simpleArray);
		 * 
		 * ArrayAdapter<String> adapter1 = new MyListAdapter();
		 * 
		 * // Configure the list view.
		 * 
		 * ListView list = (ListView) findViewById(R.id.listViewMain); if
		 * (!query) { list.setBackgroundColor(Color.RED); //
		 * list.setBackground(Color.RED);
		 * 
		 * } // list.setAdapter(adapter);
		 * 
		 * grandTotalTv.setText("Grand Total: ");
		 * 
		 * DecimalFormat df = new DecimalFormat("#,###.##");
		 * 
		 * // to display principalInvested //
		 * principalInvestedTextViewDisplay.setText
		 * (""+df.format(principalInvested)); grandTotalValueTv.setText("Rs ");
		 * grandTotalValueTv.append("" + df.format(total)); //
		 */
		// if(kaand.equals("green")){ checkoutButton.setVisibility(0);
		// }

		myCartTv.setText("My Cart");
		myCartTv.append("(" + i + ")");
		grandTotalTv.setText("Grand Total: ");
		checkoutButton.setVisibility(0);
		DecimalFormat df = new DecimalFormat("#,###.##");
		grandTotalValueTv.setText("Rs ");
		grandTotalValueTv.append("" + df.format(total));
		Log.d("tagname", "finisehd printing");

		if (query) {
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// Do something after 10s = 10000ms
					// Toast.makeText(MainActivity.this, "Starting query",
					// Toast.LENGTH_SHORT).show();

					// getData(etPollingIntervalValue,
					// "http://data.sparkfun.com/output/VGYDordaJJf7VGxaMyYK.json?page=1");
					Log.d("tagname", "in handler");
					// getData(etPollingIntervalValue,
					// "http://192.168.1.6:8080/output/RDpqP47p35Sw3x47zlgdFzZL70e.json");
					// getData(etPollingIntervalValue,
					// "http://10.196.64.81:8080/output/go0Kyy7Dxzh6dwArbqMbH0eenP0.json");

					getData(etPollingIntervalValue,
							"http://192.168.1.100:8080/output/G2nxk3PzQVU8go2ALXWxSxp2bzK1.json");

				}
			}, 100);
		}

		// getData(etPollingIntervalValue,
		// "http://data.sparkfun.com/output/VGYDordaJJf7VGxaMyYK.json?page=1");

	}

	public void populateListView() {
		Log.d("tagname", "in populate listview");
		// total = 0;
		ArrayAdapter<sparkFun> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.listViewMain);
		// list.setBackgroundColor(Color.RED);
		list.setAdapter(adapter);

	}

	public class MyListAdapter extends ArrayAdapter<sparkFun> {

		public MyListAdapter() {
			super(MainActivity.this, R.layout.da_items, itemsList);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View oldView ;

			// Make sure we habe a view to work with(may have been given false)
			View itemView = convertView;

			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.da_items,
						parent, false);
			}

			// find the product to work with

			sparkFun currentProduct = itemsList.get(position);
			Log.d("tagname", "in list view");
			
		//	if(currentProduct.getKaand().equals("green")){

			// Fill the view

			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.item_icon);
			imageView.setImageResource(currentProduct.getIconID());

			// ProductName :
			TextView productNameText = (TextView) itemView
					.findViewById(R.id.item_Nametv);

			productNameText.setText(" " + currentProduct.getProductname());

			// ProductPrice :
			TextView productPriceText = (TextView) itemView
					.findViewById(R.id.item_Pricetv);
			productPriceText.setText("Rs." + currentProduct.getProductprice());

			// ProductWeight :
			// TextView productWeightText = (TextView)
			// findViewById(R.id.item_Nametv);
			// productWeightText.setText(""+currentProduct.getProductname());

			// ProductName :
			// TextView productKaandText = (TextView)
			// findViewById(R.id.item_Weighttv);
			// productKaandText.setText(currentProduct.getKaand());
			// total = total
			// + Double.parseDouble(currentProduct.getProductprice());

			// myCartTv.setText("My Cart");
			// myCartTv.append("(" + currentProduct.getNoOfItems() + ")");
			noOfProducts = currentProduct.getNoOfItems();
			
			
			return itemView;
	
		}

	}

	private void showStopToast() {
		Toast.makeText(this, "stopped", Toast.LENGTH_LONG).show();

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

				getData(etPollingIntervalValue,
						"http://192.168.1.100:8080/output/G2nxk3PzQVU8go2ALXWxSxp2bzK1.json");

				// try {
				// Thread.sleep(2000);
				// } catch (InterruptedException e) {

				// e.printStackTrace();
				// }
				// startQuery();

			}

			// flowerList = FlowerJSONParser.parseFeed(result);
			if (result != null) {

				itemsList = sparkFunJSONParser.parseFeed(result);
				updateDisplay();
			}

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// tvData.append(values[0]);
			// tvData.append("\n");
		}

	}

}
