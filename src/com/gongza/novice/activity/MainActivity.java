package com.gongza.novice.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongza.novice.R;
import com.gongza.novice.fragment.Tab01Fragment;
import com.gongza.novice.fragment.Tab02Fragment;
import com.gongza.novice.fragment.Tab03Fragment;
import com.gongza.novice.fragment.Tab04Fragment;

/**
 * 主界面
 * 
 * @author gongza
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	private LinearLayout layout_main;
	private TextView tv_tab1, tv_tab2, tv_tab3, tv_tab4;
	private FragmentManager fragmentManager;

	private Fragment mTab01;
	private Tab02Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		layout_main = (LinearLayout) findViewById(R.id.layout_main);
		tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
		tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
		tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
		tv_tab4 = (TextView) findViewById(R.id.tv_tab4);
		tv_tab1.setOnClickListener(this);
		tv_tab2.setOnClickListener(this);
		tv_tab3.setOnClickListener(this);
		tv_tab4.setOnClickListener(this);

		fragmentManager = getFragmentManager();
		setTabSelection(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_tab1:
			setTabSelection(0);
			break;
		case R.id.tv_tab2:
			setTabSelection(1);
			break;
		case R.id.tv_tab3:
			setTabSelection(2);
			break;
		case R.id.tv_tab4:
			setTabSelection(3);
			break;

		default:
			break;
		}

	}

	private void setTabSelection(int index) {
		resetBtn();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			tv_tab1.setTextColor(0xffee5555);
			if (mTab01 == null) {
				mTab01 = new Tab01Fragment();
				transaction.add(R.id.layout_main, mTab01);
			} else {
				transaction.show(mTab01);
			}
			break;
		case 1:
			tv_tab2.setTextColor(0xffee5555);
			if (mTab02 == null) {
				mTab02 = new Tab02Fragment();
				transaction.add(R.id.layout_main, mTab02);
			} else {
				transaction.show(mTab02);
			}
			break;
		case 2:
			tv_tab3.setTextColor(0xffee5555);
			if (mTab03 == null) {
				mTab03 = new Tab03Fragment();
				transaction.add(R.id.layout_main, mTab03);
			} else {
				transaction.show(mTab03);
			}
			break;
		case 3:
			tv_tab4.setTextColor(0xffee5555);
			if (mTab04 == null) {
				mTab04 = new Tab04Fragment();
				transaction.add(R.id.layout_main, mTab04);
			} else {
				transaction.show(mTab04);
			}
			break;
		}
		transaction.commit();

	}

	private void hideFragments(FragmentTransaction transaction) {
		if (mTab01 != null) {
			transaction.hide(mTab01);
		}
		if (mTab02 != null) {
			transaction.hide(mTab02);
		}
		if (mTab03 != null) {
			transaction.hide(mTab03);
		}
		if (mTab04 != null) {
			transaction.hide(mTab04);
		}

	}

	private void resetBtn() {
		tv_tab1.setTextColor(0xff000000);
		tv_tab2.setTextColor(0xff000000);
		tv_tab3.setTextColor(0xff000000);
		tv_tab4.setTextColor(0xff000000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_gridview:
			mTab02.actionGridView();
			break;
		case R.id.action_listview:
			mTab02.actionListView();
			break;
		case R.id.action_staggered:
			mTab02.actionStaggered();
			break;
		case R.id.action_hor_gridview:
			mTab02.actionHorizontalGridView();
			break;
		case R.id.action_add:
			mTab02.actionAdd();
			break;
		case R.id.action_delete:
			mTab02.actionDelete();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
