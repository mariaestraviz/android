package upm.dit.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendEmail extends Activity{
	
	Button buttonSend;
	//EditText textTo;
	TextView textTo;
	EditText textSubject;
	EditText textMessage;
	
	String to;
	
	
	 public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.send_email);

		    buttonSend = (Button) findViewById(R.id.send);
		    // textTo = (EditText) findViewById(R.id.nameUser);
		    textTo = (TextView) findViewById(R.id.nameUser);
		    to = "maria.estraviz.pardo@gmail.com";
		    textTo.setText(to);
		    textSubject = (EditText) findViewById(R.id.textSubject);
		    textMessage = (EditText) findViewById(R.id.textMessage);

		    buttonSend.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {

		            // String to = textTo.getText().toString();
		            String subject = textSubject.getText().toString();
		            String message = textMessage.getText().toString();

		            Intent email = new Intent(Intent.ACTION_SEND);
		            email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		            // email.putExtra(Intent.EXTRA_CC, new String[]{ to});
		            // email.putExtra(Intent.EXTRA_BCC, new String[]{to});
		            email.putExtra(Intent.EXTRA_SUBJECT, subject);
		            email.putExtra(Intent.EXTRA_TEXT, message);

		            // need this to prompts email client only
		            email.setType("message/rfc822");

		            startActivity(Intent.createChooser(email, "Choose an Email client :"));
		            
		        }
		    });
		}

}
