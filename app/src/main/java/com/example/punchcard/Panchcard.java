package com.example.punchcard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;;


/**
 * @author kasparof
 *
 */



public class Panchcard extends Activity {

	
	SQLiteDatabase mydb;
    public static String DBNAME = "INOUTLOG.db";    // THIS IS THE SQLITE DATABASE FILE NAME.
    public static String TABLE = "MY_TABLE";       // THIS IS THE TABLE NAME
	EditText dateEditText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panchcard);
		dateEditText = (EditText) findViewById(R.id.editText1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panchcard, menu);
        return true;
    }
 
    public void getdate (View v){
        
    	mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
        mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, CUR_DATE TEXT, TIME_1 TEXT, TIME_2 TEXT);");
        Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
        String idtouse="";
        Boolean foundDate = false;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
    	SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
    	String curDate = sdf.format(new Date());
    	String curTime = sdftime.format(new Date());
    	setContentView(R.layout.activity_panchcard);
        TextView tv = (TextView)findViewById(R.id.textView1);
        if(allrows.moveToFirst())
        {
        	do 
        	{
        		String check = allrows.getString(1);
                if (curDate.equals(check)) 
                	{
                	foundDate=true;
                	idtouse = allrows.getString(0);
                	}
        	}
        	while(!(foundDate)&&(allrows.moveToNext()));
        }
        if (foundDate)
        {
        	allrows.close();
        	Cursor rowtouse = mydb.rawQuery("SELECT * FROM "+ TABLE +" WHERE ID = '" + idtouse + "' ;", null);
        	rowtouse.moveToFirst();
        	if (!(rowtouse.getString(3)==null))
        	{
        		tv.setText(rowtouse.getString(1)+" , "+rowtouse.getString(2)+" , "+rowtouse.getString(3));
        		rowtouse.close();
        	}
        	else
        	{
        		tv.setText(rowtouse.getString(1)+" , "+rowtouse.getString(2)+" , "+curTime);
        		mydb.execSQL("UPDATE " + TABLE + " SET TIME_2 = '" + curTime + "' WHERE ID = "+idtouse);
        		rowtouse.close();
        	}
        }
        else
        {
        	tv.setText(curDate+" , "+curTime);
        	mydb.execSQL("INSERT INTO " + TABLE + " (CUR_DATE , TIME_1) VALUES('"+curDate+"','"+curTime+"')");
        }
        mydb.close();
    }

public void updateexit (View v){
    
	mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
    mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, CUR_DATE TEXT, TIME_1 TEXT, TIME_2 TEXT);");
    Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
    String idtouse="";
    Boolean foundDate = false;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
	SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
	String curDate = sdf.format(new Date());
	String curTime = sdftime.format(new Date());
	setContentView(R.layout.activity_panchcard);
    TextView tv = (TextView)findViewById(R.id.textView1);
    if(allrows.moveToFirst())
    {
    	do 
    	{
    		String check = allrows.getString(1);
            if (curDate.equals(check)) 
            	{
            	foundDate=true;
            	idtouse = allrows.getString(0);
            	}
    	}
    	while(!(foundDate)&&(allrows.moveToNext()));
    }
    if (foundDate)
    {
    	allrows.close();
    	Cursor rowtouse = mydb.rawQuery("SELECT * FROM "+ TABLE +" WHERE ID = '" + idtouse + "' ;", null);
    	rowtouse.moveToFirst();
    	tv.setText(rowtouse.getString(1)+" , "+rowtouse.getString(2)+" , "+curTime);
    	mydb.execSQL("UPDATE " + TABLE + " SET TIME_2 = '" + curTime + "' WHERE ID = "+idtouse);
    	rowtouse.close();
    }
    else
    {
    	tv.setText("Day not Started Yet!");
    }
    mydb.close();
}

public void deleteday (View v){
    
	mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
    mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, CUR_DATE TEXT, TIME_1 TEXT, TIME_2 TEXT);");
    Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
    String idtouse="";
    Boolean foundDate = false;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
	String curDate = sdf.format(new Date());
	setContentView(R.layout.activity_panchcard);
    TextView tv = (TextView)findViewById(R.id.textView1);
    if(allrows.moveToFirst())
    {
    	do 
    	{
    		String check = allrows.getString(1);
            if (curDate.equals(check)) 
            	{
            	foundDate=true;
            	idtouse = allrows.getString(0);
            	}
    	}
    	while(!(foundDate)&&(allrows.moveToNext()));
    }
    if (foundDate)
    {
    	allrows.close();
    	Cursor rowtouse = mydb.rawQuery("SELECT * FROM "+ TABLE +" WHERE ID = '" + idtouse + "' ;", null);
    	rowtouse.moveToFirst();
    	tv.setText("Current Day Deleted!");
    	mydb.execSQL("DELETE FROM " + TABLE + " WHERE ID = "+idtouse);
    	rowtouse.close();
    }
    else
    {
    	tv.setText("Day Does not Exist Yet!");
    }
    mydb.close();
}

public void exit_func (View v){
finish();
System.exit(0);
}
public void calculate_time (View v) throws ParseException {
    mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
    mydb.execSQL("CREATE TABLE IF  NOT EXISTS " + TABLE + " (ID INTEGER PRIMARY KEY, CUR_DATE TEXT, TIME_1 TEXT, TIME_2 TEXT);");
    Cursor allrows = mydb.rawQuery("SELECT * FROM " + TABLE, null);
    String timestamp = "";
    SimpleDateFormat MonthCur = new SimpleDateFormat("M");
    SimpleDateFormat YearCur = new SimpleDateFormat("y");
    String curMonth = MonthCur.format(new Date());
    String curYear = YearCur.format(new Date());
    Long leptadelay = Long.valueOf(0);
    Long leptaover = Long.valueOf(0);
    SimpleDateFormat clockDelay = new SimpleDateFormat("HH:mm");
    setContentView(R.layout.activity_panchcard);
    TextView tv = (TextView) findViewById(R.id.textView1);
    if (allrows.moveToFirst()) {
        do {
            String checkdate[] = allrows.getString(1).toString().split("/");
            Date startDayUpper, startDayLower, endDayLower, endDayUpper, loggedEnter, loggedExit;
            SimpleDateFormat parserDate = new SimpleDateFormat("HH:mm");
            startDayLower = parserDate.parse("09:00");
            startDayUpper = parserDate.parse("10:00");
            endDayLower = parserDate.parse("17:00");
            endDayUpper = parserDate.parse("18:00");
            String exitt;

            if (allrows.getString(3) == null)
                exitt = "17:00";
            else
                exitt = allrows.getString(3).toString();

            String enter = allrows.getString(2).toString();
            loggedEnter = parserDate.parse(enter);
            loggedExit = parserDate.parse(exitt);

            if (checkdate[0].equals(curYear))
                if (checkdate[1].equals(curMonth)) {
                    //loggedEnter = startDayLower;
                    long workTimeMin = (loggedExit.getTime() - loggedEnter.getTime()) / 1000 / 60;
                    if (workTimeMin < (8 * 60)) {
                        leptadelay += (8 * 60) - workTimeMin;
                    } else if (workTimeMin > (8 * 60)) {
                        leptaover += workTimeMin - (8 * 60);
                    }
                }
        }
        while (allrows.moveToNext());
    }

    timestamp = "Overtime : " + String.format("%02d:%02d", TimeUnit.MINUTES.toHours(leptaover),
            TimeUnit.MINUTES.toMinutes(leptaover)-
                    TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(leptaover))) +
             " Delay : " + String.format("%02d:%02d", TimeUnit.MINUTES.toHours(leptadelay),
                    TimeUnit.MINUTES.toMinutes(leptadelay) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(leptadelay)));
    	allrows.close();
    	tv.setText(timestamp);
    mydb.close();
}

public void showdate (View v){
    
	mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
    mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, CUR_DATE TEXT, TIME_1 TEXT, TIME_2 TEXT);");
    Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
    String idtouse="";
    Boolean foundDate = false;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
	SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
	String curDate = sdf.format(new Date());
	String curTime = sdftime.format(new Date());
	setContentView(R.layout.activity_panchcard);
	TextView tv = (TextView)findViewById(R.id.textView1);
    SimpleDateFormat datefromtextfield = new SimpleDateFormat("yyyy/M/d");
	String datetoshow = dateEditText.getText().toString();
	dateEditText.setText(null);
	if (datetoshow.length()==0)
    	{
    	datetoshow=curDate;
    	}
      if(allrows.moveToFirst())
    {
    	do 
    	{
    		String check = allrows.getString(1);
            if (datetoshow.equals(check)) 
            	{
            	foundDate=true;
            	idtouse = allrows.getString(0);
            	}
    	}
    	while(!(foundDate)&&(allrows.moveToNext()));
    }
    if (foundDate)
    {
    	allrows.close();
    	Cursor rowtouse = mydb.rawQuery("SELECT * FROM "+ TABLE +" WHERE ID = '" + idtouse + "' ;", null);
    	rowtouse.moveToFirst();
    	tv.setText(rowtouse.getString(1)+" , "+rowtouse.getString(2)+" , "+rowtouse.getString(3));
    	rowtouse.close();
    }
    else
    {
    	tv.setText("Day does not exist!");
    }
    mydb.close();
}


}


