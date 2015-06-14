package com.gongza.novice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.gongza.novice.R;
import com.gongza.novice.ptrdemo.PtrMainActivity;


public class MainActivity extends Activity implements OnClickListener{
	private LinearLayout layout_ptrpull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
    	layout_ptrpull=(LinearLayout) findViewById(R.id.layout_ptrpull);
    	layout_ptrpull.setOnClickListener(this);
	}
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_ptrpull:
			startActivity(new Intent(this, PtrMainActivity.class));
			break;

		default:
			break;
		}
		
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
