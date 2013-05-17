package upm.dit.myapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateDBQlite {

	 	public static final String KEY_TITLE = "title";
	 	public static final String KEY_NAME = "name";
	 	public static final String KEY_DATE = "date";
	    public static final String KEY_BODY = "body";
	    public static final String KEY_ROWID = "_id";

	    private static final String TAG = "EventsDbAdapter";
	    
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;

	    /**
	     * Database creation sql statement
	     */
	    private static final String DATABASE_CREATE = "create table events (_id integer primary key autoincrement, "
	        + "title text not null, name text not null, date text not null, body text not null );";

	    private static final String DATABASE_NAME = "data";
	    
	    private static final String DATABASE_TABLE = "events";
	    
	    private static final int DATABASE_VERSION = 10;

	    private final Context mCtx;

	    private static class DatabaseHelper extends SQLiteOpenHelper {

	        public DatabaseHelper(Context context) {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {

	            db.execSQL(DATABASE_CREATE);
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "+ newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS events");
	            onCreate(db);
	        }
	    }

	    /**
	     * Constructor - takes the context to allow the database to be
	     * opened/created
	     * 
	     * @param ctx the Context within which to work
	     */
	    public CreateDBQlite(Context ctx) {
	        this.mCtx = ctx;
	    }

	    /**
	     * Open the notes database. If it cannot be opened, try to create a new
	     * instance of the database. If it cannot be created, throw an exception to
	     * signal the failure
	     * 
	     * @return this (self reference, allowing this to be chained in an
	     *         initialization call)
	     * @throws SQLException if the database could be neither opened or created
	     */
	    
	    public CreateDBQlite open() throws SQLException {
	    	
	        mDbHelper = new DatabaseHelper(mCtx);
	        mDb = mDbHelper.getWritableDatabase();
	        // mDb.close();
	        return this;
	    }
	    
	    public void close() {
	       mDbHelper.close();
	    }
	    
	    /**
	     * Create a new note using the title and body provided. If the note is
	     * successfully created return the new rowId for that note, otherwise return
	     * a -1 to indicate failure.
	     * 
	     * @param title 
	     * @param body
	     * @return rowId or -1 if failed
	     */
	    public long createEvent(String title, String name, String date, String body) {
	    	
	        ContentValues initialValues = new ContentValues();
	        
	        initialValues.put(KEY_TITLE, title);
	        initialValues.put(KEY_NAME, name);
	        initialValues.put(KEY_DATE,date);
	        initialValues.put(KEY_BODY, body);
	       

	        return mDb.insert(DATABASE_TABLE, null, initialValues);
	    }

	    /**
	     * Delete the note with the given rowId
	     * 
	     * @param rowId id of event to delete
	     * @return true if deleted, false otherwise
	     */
	    public boolean deleteEvent(long rowId) {

	        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	    }

	    /**
	     * Return a Cursor over the list of all notes in the database
	     * query: first field: Name of the database table
	     * 		next: list of columns we want to return
	     * 		the: selection, selectionArgs, groupBy, having, orderBy
	     * @return Cursor over all events
	     */
	    public Cursor fetchAllEvents() {

	        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_NAME, 
	        		KEY_DATE, KEY_BODY}, null, null, null, null, null);
	    }

	    /**
	     * Return a Cursor positioned at the note that matches the given rowId
	     * 
	     * @param rowId id of event to retrieve
	     * @return Cursor positioned to matching event, if found
	     * @throws SQLException if event could not be found/retrieved
	     */
	    public Cursor fetchEvent(long rowId) throws SQLException {

	        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,KEY_TITLE, KEY_NAME, 
	        		KEY_DATE, KEY_BODY}, KEY_ROWID + "=" + rowId, null,
	                    null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;

	    }

	    /**
	     * Update the note using the details provided. The note to be updated is
	     * specified using the rowId, and it is altered to use the title and body
	     * values passed in
	     * 
	     * @param rowId id
	     * @param title 
	     * @param body value 
	     * @return true if it was successfully updated, false otherwise
	     */
	    public boolean updateEvent(long rowId, String title, String name, String date, String body) {
	        
	    	ContentValues args = new ContentValues();
	        
	        args.put(KEY_TITLE, title);
	        args.put(KEY_NAME, name);
	        args.put(KEY_DATE, date);
	        args.put(KEY_BODY, body);


	        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	    }
	}

