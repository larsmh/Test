package com.insider.kontrollkunde;

import java.util.Calendar;

import com.insider.kontrollkunde.Model.Customer;
import com.insider.kontrollkunde.Model.CustomerList;
import com.insider.kontrollkunde.Model.Globals;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class MainActivity extends ActionBarActivity {
	private AutoCompleteTextView custSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.custList = new CustomerList();
        
        custSelect = (AutoCompleteTextView) findViewById(R.id.custselect);
        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1, Globals.custList.getList());
        custSelect.setAdapter(adapter);
    }
    public void register(View view){
    	Customer cust = getCustomer(custSelect.getText().toString());
    	Calendar c = Calendar.getInstance();
    	String date=c.get(Calendar.DATE)+"."+c.get(Calendar.MONTH)+"."+c.get(Calendar.YEAR)+" "
    			+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
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
}
