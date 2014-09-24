package com.insider.kontrollkunde;

import java.util.Calendar;

import com.insider.kontrollkunde.Model.Customer;
import com.insider.kontrollkunde.Model.CustomerList;
import com.insider.kontrollkunde.Model.Globals;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
	private AutoCompleteTextView custSelect;
	public Customer cust;
	public String date;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.custList = new CustomerList();
        
        custSelect = (AutoCompleteTextView) findViewById(R.id.custselect);
        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1, Globals.custList.getList());
        custSelect.setAdapter(adapter);
        
        
        Button sendEmail = (Button) findViewById(R.id.regbutton);
        
        sendEmail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Mail mail = new Mail("email", "password");
				String[] toArr = {"mottaker"}; 
	            mail.setTo(toArr); 
	            mail.setFrom("from_email"); 
	            mail.setSubject("Halla balla."); 
	            mail.setBody("Ipsum sorem");
	            
	            new SendEmailTask(mail).execute();
			}
		});
    }
    public void register(View view){
    	Customer cust = getCustomer(custSelect.getText().toString());
    	//Setting customer to global var.
    	this.cust = cust;
    	Calendar c = Calendar.getInstance();
    	String date=c.get(Calendar.DATE)+"."+c.get(Calendar.MONTH)+"."+c.get(Calendar.YEAR)+" "
    			+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
    	this.date = date;
    	
    	//sendMail(cust, date);
    	//registrer jobb i database
    }
    
    private Customer getCustomer(String name){
    	for(Customer cust : Globals.custList.getList()){
    		if(name.equals(cust.getName()))
    			return cust;
    	}
    	return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    //Class to make a background thread sending the email.
    class SendEmailTask extends AsyncTask<Void, Void, Void>{
    	Mail mail;
    	public SendEmailTask(Mail mail) {
			// TODO Auto-generated constructor stub
    		this.mail = mail;
		}
    	
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				mail.send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
    }
}
