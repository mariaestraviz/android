package upm.dit.myapp;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddUsers extends ListActivity{
	
	private CreateDBQlite mDbHelper;
	
	// private Cursor mEventsCursor;
	
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
	
	public static final int INSERT_ID = Menu.FIRST;
	

    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int SHOW_ID = Menu.FIRST + 2;
    private static final int SHARE_ID = Menu.FIRST + 3;
    
    private static final int HELLO_ID = 0;
    

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.list);  
	    
	    TextView tv=(TextView)findViewById(R.id.txtHeader);
	    Typeface face=Typeface.createFromAsset(getAssets(),"fonts/HandmadeTypewriter.ttf");
	    tv.setTypeface(face);
	    
	    mDbHelper = new CreateDBQlite(this);
	    mDbHelper.open();
		fillData();
		
		registerForContextMenu(getListView());   
	
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

	 
	private void fillData() {
		    
		// Get all of the events from the database and create the item list
	    Cursor mEventsCursor =  mDbHelper.fetchAllEvents();
	    startManagingCursor(mEventsCursor);

	    // Create an array to specify the fields we want to display in the list (only TITLE)
	    String[] from = new String[] { CreateDBQlite.KEY_TITLE, CreateDBQlite.KEY_NAME, CreateDBQlite.KEY_DATE, CreateDBQlite.KEY_BODY };
	    
	    // and an array of the fields we want to bind those fields to (in this case just text1)
	    int[] to = new int[] { R.id.Txt1, R.id.nameChild, R.id.duration, R.id.invisibleBody};
	        
	   
	    // Now create a simple cursor adapter and set it to display
	    SimpleCursorAdapter events =  new SimpleCursorAdapter(this, R.layout.event_row_style, mEventsCursor, from, to);
	
	    setListAdapter(events);    

	    }
	
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		 
		 super.onCreateOptionsMenu(menu);

	     menu.add(0, INSERT_ID,0, R.string.menu_insert);
	     return true;   
	    }
	 
	 
	 @Override
	 public boolean onMenuItemSelected(int featureId, MenuItem item) {
	     
		 switch(item.getItemId()) {
	     	case INSERT_ID:
	     		createEvent();
	     		return true;
	         }
		 return super.onMenuItemSelected(featureId, item);
	    }
	    
	
	 @Override
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		 
			super.onCreateContextMenu(menu, v, menuInfo);
			// We add a menu item to delete an event
			menu.add(0, DELETE_ID, 0, R.string.menu_delete)
				.setIcon(R.drawable.trash);
			
			menu.add(0, SHOW_ID, 0, R.string.show_events);
			
			menu.add(0, SHARE_ID, 0, R.string.share);
	                     
		}
	 //  Cuando pulsamos la opción del menu
	 public boolean onContextItemSelected(MenuItem item) {
		 
		 final MenuItem myItem = item;
		 if(myItem.getItemId() == SHOW_ID){
			 
			 AdapterContextMenuInfo info = (AdapterContextMenuInfo) myItem.getMenuInfo();
			 Cursor c = (Cursor) mDbHelper.fetchEvent(info.id);
			 
			 String name =  c.getString(
				        c.getColumnIndexOrThrow(CreateDBQlite.KEY_NAME));
			 String date = c.getString(
				        c.getColumnIndexOrThrow(CreateDBQlite.KEY_DATE));
			 String body =  c.getString(
				        c.getColumnIndexOrThrow(CreateDBQlite.KEY_BODY));
			 
			//añadido
			 stopManagingCursor(c);
			 c.close();
			 
			 Intent myIntent = new Intent(AddUsers.this, ShowEvent.class);
             //creamos la información a pasar
			 myIntent.putExtra("NAME",name);
			 myIntent.putExtra("DATE", date );
			 myIntent.putExtra("BODY", body);
             
             startActivity(myIntent);
             
			 
		 }else if((myItem.getItemId()== SHARE_ID)){
			 
			 AdapterContextMenuInfo info = (AdapterContextMenuInfo) myItem.getMenuInfo();
			 Cursor c=(Cursor) mDbHelper.fetchEvent(info.id);
			 String name= c.getString(c.getColumnIndexOrThrow(CreateDBQlite.KEY_NAME));
			 String date = c.getString(
				        c.getColumnIndexOrThrow(CreateDBQlite.KEY_DATE));
			 String body =  c.getString(
				        c.getColumnIndexOrThrow(CreateDBQlite.KEY_BODY));
			 stopManagingCursor(c);
			 c.close();
			 Intent sendIntent = new Intent();
			 sendIntent.setAction(Intent.ACTION_SEND);
			 sendIntent.putExtra(Intent.EXTRA_TEXT,"This is my event to send.\n" +
			 		"Name: "+ name + "\n"+
					"Date: "+date+"\n"+
			 		"Body: "+body);
			 sendIntent.setType("text/plain");
			 startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
			 startActivity(sendIntent);
			 
		 }else{
		 
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setMessage(R.string.dialog_text)
		 
		 .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		 public void onClick(DialogInterface dialog, int id){
			 
			 if(myItem.getItemId() == DELETE_ID){
				 
				 AdapterContextMenuInfo info = (AdapterContextMenuInfo) myItem.getMenuInfo();
				 mDbHelper.deleteEvent(info.id);
				 fillData();
				 
				//notification Toast
				 LayoutInflater inflater = getLayoutInflater();
				 View layout = inflater.inflate(R.layout.notify_toast, (ViewGroup) findViewById(R.id.toast_root));
				 TextView text = (TextView) layout.findViewById(R.id.TextToast);
				 text.setText("An event has been deleted");
				 
				 Toast toast = new Toast(getApplicationContext());
				 toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				 toast.setDuration(Toast.LENGTH_LONG);
				 toast.setView(layout);
				 toast.show();
			 }
	            	
	            }
	        })
	     
	     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	     public void onClick(DialogInterface dialog, int id) {
	                // User cancelled the dialog
	            }
	        }).show();
		 }
		 
		 return super.onContextItemSelected(item);
		 
		}
		 
	 // Create a new event
	 private void createEvent (){ 	 

		 // We create a new Intent to create an event, using the EditEvent class
		 Intent i = new Intent(this, EditEvent.class);
		 startActivityForResult(i, ACTIVITY_CREATE);
	 }
	
	 
	
	 @Override
	 // When the user selects an item from the list
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 
		 super.onListItemClick(l, v, position, id);
		 
		 // we pull out event´s details from our query cursor.
		 // mEventsCursor.moveToPosition(position);
		 Intent i = new Intent(this, EditEvent.class);
		 
		 i.putExtra(CreateDBQlite.KEY_ROWID, id);

		 startActivityForResult(i, ACTIVITY_EDIT);
	    }
	 
	 // overridden method which will be called when an Activity returns with a result
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		 
		 // requestCode is the original request code specified in the Intent invocation
		 // either ACTIVITY_CREATE or ACTIVITY_EDIT
		 super.onActivityResult(requestCode, resultCode, intent); 
		 fillData();
		 
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	        	 
	        	//Agregando el icono, texto y momento para lanzar la notificación
	        	int icon = R.drawable.tick;
	        	CharSequence tickerText = "New event succesfully stored";
	        	long when = System.currentTimeMillis();
	        	 
	 			Notification notification = new Notification(icon, tickerText, when);
	        	 
	        	Context context = getApplicationContext();
	        	CharSequence contentTitle = "New event";
	        	CharSequence contentText = " A new event have been succesfully stored";
	        	 
	        	//Agregando sonido
	        	notification.defaults |= Notification.DEFAULT_SOUND;
	        	//Agregando vibración
	        	notification.defaults |= Notification.DEFAULT_VIBRATE;
	        	 
	        	Intent notificationIntent = new Intent(this, MainActivity.class);
	        	
	        	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
	        	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        	 
	        	mNotificationManager.notify(HELLO_ID, notification);
	}
	 
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 

		 switch (item.getItemId()) {
	        case INSERT_ID:
	            createEvent();
	            return true;
	        }
	       
	        return super.onOptionsItemSelected(item);
	    
	 
	 }
	 
	 
}
