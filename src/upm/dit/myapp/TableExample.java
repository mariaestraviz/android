package upm.dit.myapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TableExample extends Activity{
	
	private static final int MNU_OPC1 = 1;
	
	private static final int SMNU_OPC1 = 1;
	private static final int SMNU_OPC2 = 2;
	private static final int SMNU_OPC3 = 3;
	
	 /** Called when the activity is first created. */

	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
       
        //setContentView(R.layout.tab_events);
        
        setContentView(R.layout.tab_events_style);
        
        TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabSpec spec1=tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("School", getResources().getDrawable(R.drawable.briefcase));
        
        ImageView img = (ImageView) findViewById(R.id.callImage);
        img.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	TextView textNumber = (TextView)findViewById(R.id.txtNumber);
            	String call_string= textNumber.getText().toString();

                try {
    		        Intent callIntent = new Intent(Intent.ACTION_CALL);
    		        callIntent.setData(Uri.parse("tel:"+ call_string));
    		        startActivity(callIntent);
    		    } catch (ActivityNotFoundException activityException) {
    		         Log.e("Android dialing", "Call failed", activityException );
    		    }
            }
        });
        
        ImageView imgSend = (ImageView) findViewById(R.id.sendImage);
        imgSend.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	//TextView textNumber = (TextView)findViewById(R.id.txtNumber);
            	//String call_string= textNumber.getText().toString();
            	Intent myIntent = new Intent(TableExample.this, SendEmail.class);
            	startActivity(myIntent);
            }
        });

        TabSpec spec2=tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("Medical",
        		getResources().getDrawable(R.drawable.help));
        spec2.setContent(R.id.tab2);
        
        ImageView img2 = (ImageView) findViewById(R.id.callImage2);
        img2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	TextView textNumber = (TextView)findViewById(R.id.txtNumber2);
            	String call_string= textNumber.getText().toString();

                try {
    		        Intent callIntent = new Intent(Intent.ACTION_CALL);
    		        callIntent.setData(Uri.parse("tel:"+ call_string));
    		        startActivity(callIntent);
    		    } catch (ActivityNotFoundException activityException) {
    		         Log.e("Android dialing", "Call failed", activityException );
    		    }
            }
        });
        
        ImageView img4 = (ImageView) findViewById(R.id.callImage4);
        img4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	TextView textNumber = (TextView)findViewById(R.id.txtNumber4);
            	String call_string= textNumber.getText().toString();

                try {
    		        Intent callIntent = new Intent(Intent.ACTION_CALL);
    		        callIntent.setData(Uri.parse("tel:"+ call_string));
    		        startActivity(callIntent);
    		    } catch (ActivityNotFoundException activityException) {
    		         Log.e("Android dialing", "Call failed", activityException );
    		    }
            }
        });

        TabSpec spec3=tabHost.newTabSpec("Tab 3");
        spec3.setIndicator("Others",
        		getResources().getDrawable(R.drawable.pencil));
        spec3.setContent(R.id.tab3);
        
        ImageView img3 = (ImageView) findViewById(R.id.callImage3);
        img3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	TextView textNumber = (TextView)findViewById(R.id.txtNumber3);
            	String call_string= textNumber.getText().toString();

                try {
    		        Intent callIntent = new Intent(Intent.ACTION_CALL);
    		        callIntent.setData(Uri.parse("tel:"+ call_string));
    		        startActivity(callIntent);
    		    } catch (ActivityNotFoundException activityException) {
    		         Log.e("Android dialing", "Call failed", activityException );
    		    }
            }
        });
        
        ImageView img5 = (ImageView) findViewById(R.id.callImage5);
        img5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	TextView textNumber = (TextView)findViewById(R.id.txtNumber5);
            	String call_string= textNumber.getText().toString();

                try {
    		        Intent callIntent = new Intent(Intent.ACTION_CALL);
    		        callIntent.setData(Uri.parse("tel:"+ call_string));
    		        startActivity(callIntent);
    		    } catch (ActivityNotFoundException activityException) {
    		         Log.e("Android dialing", "Call failed", activityException );
    		    }
            }
        });
        
        ImageView img6 = (ImageView) findViewById(R.id.callImage6);
        img6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	TextView textNumber = (TextView)findViewById(R.id.txtNumber6);
            	String call_string= textNumber.getText().toString();

                try {
    		        Intent callIntent = new Intent(Intent.ACTION_CALL);
    		        callIntent.setData(Uri.parse("tel:"+ call_string));
    		        startActivity(callIntent);
    		    } catch (ActivityNotFoundException activityException) {
    		         Log.e("Android dialing", "Call failed", activityException );
    		    }
            }
        });
       
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.setCurrentTab(0);

    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

	    //menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Add one")
	     //       .setIcon(R.drawable.plus);
	    
	    SubMenu smnu = menu.
	    	    addSubMenu(Menu.NONE, MNU_OPC1, Menu.NONE, "Add")
	    	    .setIcon(R.drawable.plus);
	    	smnu.add(Menu.NONE, SMNU_OPC1, Menu.NONE, "School");
	    	smnu.add(Menu.NONE, SMNU_OPC2, Menu.NONE, "Medical");
	    	smnu.add(Menu.NONE, SMNU_OPC3, Menu.NONE, "Others");

	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case SMNU_OPC1:
	            return true;
	        case SMNU_OPC2:
	        	return true;
	        case SMNU_OPC3:
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
