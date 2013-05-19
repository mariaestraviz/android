package upm.dit.myapp;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditEvent extends Activity {
	
	private Long mRowId;
	
	private AutoCompleteTextView mTitleText;
	private EditText mNameText;
	// private EditText mDateText;
	private TextView mDateText;
	private EditText mBodyText; 
	private CreateDBQlite mDbHelper;
	
	
	int request_code = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		
		mDbHelper = new CreateDBQlite (this);
        mDbHelper.open();
		
		setContentView(R.layout.edit_event);
		setTitle(R.string.edit);
		
		
		mTitleText = (AutoCompleteTextView) findViewById(R.id.title);
		
		// Get the string array
		String[] auto = getResources().getStringArray(R.array.autocomplete_array);
		
		// Create the adapter and set it to the AutoCompleteTextView 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, auto);
		mTitleText.setAdapter(adapter);
	    
		mNameText = (EditText) findViewById(R.id.name);
		mDateText = (TextView)findViewById(R.id.tvTexto);
		mBodyText = (EditText) findViewById(R.id.body);
		
		
		Button confirmButton = (Button) findViewById(R.id.confirm);
		Button datebutton = (Button) findViewById(R.id.buttonDate);
		
		datebutton.setOnClickListener(new View.OnClickListener() {
	        	
	            public void onClick(View view) {
	            	
	            	//AÑADIDO
	                Intent myIntent = new Intent(view.getContext(), DatePickerFragment.class);
	                //ejecutamos la actividad y esperamos resultado
	                startActivityForResult(myIntent, request_code);
	               
	            }
	        });
		
		mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(CreateDBQlite.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(CreateDBQlite.KEY_ROWID)
									: null;
		}

		populateFields();
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			
		    public void onClick(View view) {
		    	
		    	setResult(RESULT_OK);
		    	
		    	//notification Toast
	        	LayoutInflater inflater = getLayoutInflater();
	        	View layout = inflater.inflate(R.layout.notify_toast, (ViewGroup) findViewById(R.id.toast_root));

	        	TextView text = (TextView) layout.findViewById(R.id.TextToast);
	        	
	        	text.setText("A new event has been stored");
	        	
	        	Toast toast = new Toast(getApplicationContext());
	        	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	        	toast.setDuration(Toast.LENGTH_LONG);
	        	toast.setView(layout);
	        	toast.show();
	        	
                finish();
		    }
		});
	}
	
	//NUEVO
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){

        	mDateText.setText(String.valueOf(data.getDataString()));
        	
        	Typeface face=Typeface.createFromAsset(getAssets(),"fonts/HandmadeTypewriter.ttf");
        	mDateText.setTypeface(face);
        
        }
    }
	
	private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchEvent(mRowId);
            startManagingCursor(note);
            
            mTitleText.setText(note.getString(
                        note.getColumnIndexOrThrow(CreateDBQlite.KEY_TITLE)));
            
            Typeface face=Typeface.createFromAsset(getAssets(),
                    "fonts/MgOpenCosmeticaBold.ttf");
            mTitleText.setTypeface(face);
                     
            mNameText.setText(note.getString(
                    note.getColumnIndexOrThrow(CreateDBQlite.KEY_NAME)));
            
            mNameText.setTypeface(face);
            //mDateText.setText(note.getString(note.getColumnIndexOrThrow(CreateDBQlite.KEY_DATE)));
            mBodyText.setText(note.getString(
                    note.getColumnIndexOrThrow(CreateDBQlite.KEY_BODY)));
            mBodyText.setTypeface(face);
            
           note.close();

        }
    }
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(CreateDBQlite.KEY_ROWID, mRowId);
    }
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    // your stuff or nothing
	}

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
        
    }
    
    @Override
    public void onDestroy(){
    	mDbHelper.close();
    	super.onDestroy();
    	
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
    	
    	String title = mTitleText.getText().toString();
        String name = mNameText.getText().toString();
        String date = mDateText.getText().toString();
        String body = mBodyText.getText().toString();
   

        if (mRowId == null) {
        	long id = mDbHelper.createEvent(title, name, date, body);
            if (id > 0) {
                mRowId = id;
            }
        } else {
        	mDbHelper.updateEvent(mRowId, title, name, date, body);
        }
        
    }
	

}
