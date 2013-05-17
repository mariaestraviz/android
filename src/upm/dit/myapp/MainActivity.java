package upm.dit.myapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.main_activity_style);
		
		//we cancel notification bar
		String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(ns);
        mNotificationManager.cancelAll();
	
        // esto podriamos meterlo en un metodo setHeader(String title, layout??)
        ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
        View inflated = stub.inflate();

        TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
        //Typeface face=Typeface.createFromAsset(getAssets(),"fonts/HandmadeTypewriter.ttf");
        Typeface face=Typeface.createFromAsset(getAssets(),
                "fonts/MgOpenCosmeticaBold.ttf");
        txtTitle.setTypeface(face);
        txtTitle.setText("START PLANNING !");

        
		//listener para el evento onclick ABOUt
        Button about = (Button) findViewById(R.id.Button01);
        about.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View view) {
                //Intent myIntent = new Intent(view.getContext(), About.class);
                Intent myIntent = new Intent(view.getContext(), SwipeImage.class);
                //ejecutamos la actividad y esperamos resultado
                //startActivityForResult(myIntent, 0);
                startActivity(myIntent);
            }
        });
        
      //listener para el evento onclick HELP
        Button help = (Button) findViewById(R.id.Button02);
        help.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View view) {
            	//Intent myIntent = new Intent(view.getContext(), LayoutChangesActivity.class);
            	Intent myIntent = new Intent(view.getContext(), TableExample.class);
            	//Intent myIntent = new Intent(view.getContext(), TabPhones.class);
                startActivity(myIntent);
            }
        });
        
  
        
      //listener para el evento onclick. CREACIÓN DE EVENTOS
        
        Button button = (Button) findViewById(R.id.Button03);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddUsers.class);
                
                //ejecutamos la actividad y esperamos resultado
                // startActivityForResult(myIntent, 0);
                startActivity(myIntent);
            }
        });
        
      //listener para el evento onclick FOOD
        Button food = (Button) findViewById(R.id.Button04);
        
        food.setOnClickListener(new View.OnClickListener() {     	
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ZoomActivity.class);
                //ejecutamos la actividad y esperamos resultado
                startActivity(myIntent);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
