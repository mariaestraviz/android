package upm.dit.myapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ShowEvent extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.show_event);
		
		TextView txtName = (TextView) findViewById(R.id.eventName);
        TextView txtDate = (TextView) findViewById(R.id.eventDate);
        TextView txtbody = (TextView) findViewById(R.id.eventBody);
        
         
      //Recuperamos la información pasada en el intent
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("NAME");
        String date = bundle.getString("DATE");
      //Construimos el mensaje a mostrar
        txtName.setText(name);
        txtDate.setText(date);
        txtbody.setText(bundle.getString("BODY"));
        
        Typeface face=Typeface.createFromAsset(getAssets(),
        		"fonts/HandmadeTypewriter.ttf");
        txtName.setTypeface(face);
        txtDate.setTypeface(face);
        txtbody.setTypeface(face);
      
        
	}
}
