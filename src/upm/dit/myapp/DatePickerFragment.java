package upm.dit.myapp;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import upm.dit.myapp.ScheduleClient;

public class DatePickerFragment extends Activity{
	
	private TextView tvdisplaydate;
	private DatePicker dpResult;
	private Button bntchangedate;
	// private Button bntenter;


	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 999;
	
	private String globalDate;
	
	// This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
	
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    
	    // setContentView(R.layout.date_picker);
	    setContentView(R.layout.date_picker_style);
	    setCurrentdateonView();
	    addListenerOnButton();
	    
	    // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

	     }
	
	public void setCurrentdateonView(){

		tvdisplaydate = (TextView)findViewById(R.id.tvdate);
		dpResult = (DatePicker) findViewById(R.id.dpResult);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) ;
		day = c.get(Calendar.DAY_OF_MONTH);
		// tvdisplaydate.setText(new StringBuffer().append(month+1).append("-").append(day).append("-").append(year).append(""));
		
		tvdisplaydate.setText(new StringBuffer().append(year).append("-").append(month+1).append("-").append(day).append(""));
				
		dpResult.init(year, month, day, null);
				
		globalDate = new String(year +"-"+ (month+1) +"-"+ day);
			
		}
	
	public void addListenerOnButton(){
		
		bntchangedate = (Button)findViewById(R.id.bntchangedate);
		
		bntchangedate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				showDialog(DATE_DIALOG_ID);
				}
			});
		
		Button bntenter = (Button)findViewById(R.id.btnenter);
		bntenter.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startAlarm();
				Intent data = new Intent();
				data.setData(Uri.parse(globalDate));
                setResult(RESULT_OK, data);
                finish();
				}
			});
		}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id){
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this,datePickerLisner,year,month,day);
				}
		return null;
		}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    // your stuff or nothing
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    // your stuff or nothing
	}

	
	private DatePickerDialog.OnDateSetListener datePickerLisner = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int Selectyear,int Selectmonth, int Selectday) {
			
			year= Selectyear;
			month= Selectmonth;
			day = Selectday;
			tvdisplaydate.setText(new StringBuilder().append(Selectyear).append("-").append(Selectmonth+1).append("-").append(Selectday).append(""));
			
			
			globalDate = new String(year +"-"+ (month+1) +"-"+ day);
			
			dpResult.init(year, month, day, null);
			}
		};

	
	private void startAlarm() {
			
			Log.i("","Crea la alarma");
			
	    	Calendar c = Calendar.getInstance();
	    	c.set(year, month, day);
	    	c.set(Calendar.HOUR_OF_DAY, 0);
	    	c.set(Calendar.MINUTE, 0);
	    	c.set(Calendar.SECOND, 0);
	    	
	    	// Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
	    	scheduleClient.setAlarmForNotification(c);
	    	
	    	// Notify the user what they just did
	    	Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
	    	
	    	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    
	}

}
