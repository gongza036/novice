package com.gongza.novice.fragment;

import java.util.ArrayList;
import java.util.List;

import com.gongza.novice.R;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author gongza
 *
 */
public class Tab01Fragment extends Fragment implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<android.support.v4.app.Fragment> mDatas;

	private TextView mChatTextView;
	private TextView mFriendTextView;
	private TextView mContactTextView;
	private LinearLayout id_ll_chat, id_ll_friend, id_ll_contact;

	private ImageView mTabline;
	private int mScreen1_3;
	private int mCurrentPageIndex;
	private FragmentActivity mainAct;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainAct = (FragmentActivity) getActivity();
		View view = inflater.inflate(R.layout.tab_1, container, false);
		initView(view, inflater);
		return view;
	}

	private void initView(View view, LayoutInflater inflater) {
		initTabLine(view);
		setViewPager(view);
		mViewPager.setCurrentItem(1);
	}

	private void initTabLine(View view) {
		mTabline = (ImageView) view.findViewById(R.id.id_iv_tabline);
		Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 3;
		LayoutParams lp = mTabline.getLayoutParams();
		lp.width = mScreen1_3;
		mTabline.setLayoutParams(lp);
	}

	private void setViewPager(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
		mChatTextView = (TextView) view.findViewById(R.id.id_tv_chat);
		mFriendTextView = (TextView) view.findViewById(R.id.id_tv_friend);
		mContactTextView = (TextView) view.findViewById(R.id.id_tv_contact);
		id_ll_chat = (LinearLayout) view.findViewById(R.id.id_ll_chat);
		id_ll_friend = (LinearLayout) view.findViewById(R.id.id_ll_friend);
		id_ll_contact = (LinearLayout) view.findViewById(R.id.id_ll_contact);
		id_ll_chat.setOnClickListener(this);
		id_ll_friend.setOnClickListener(this);
		id_ll_contact.setOnClickListener(this);

		mDatas = new ArrayList<>();

		TabViewPager1Fragment tab01 = new TabViewPager1Fragment();
		TabViewPager1Fragment tab02 = new TabViewPager1Fragment();
		TabViewPager1Fragment tab03 = new TabViewPager1Fragment();

		mDatas.add(tab01);
		mDatas.add(tab02);
		mDatas.add(tab03);

		mAdapter = new FragmentPagerAdapter(mainAct.getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mDatas.size();
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {

				return mDatas.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					mChatTextView.setTextColor(Color.parseColor("#008000"));
					break;
				case 1:
					mFriendTextView.setTextColor(Color.parseColor("#008000"));
					break;
				case 2:
					mContactTextView.setTextColor(Color.parseColor("#008000"));
					break;

				default:
					break;
				}
				mCurrentPageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
				Log.e("TAG", position + " , " + positionOffset + " , " + positionOffsetPx);
				//mCurrentPageIndex * 1/3  + positionOffset * 1/3
				LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabline.getLayoutParams();
				if (mCurrentPageIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex * mScreen1_3);
				} else if (mCurrentPageIndex == 1 && position == 0)// 1->0
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1) * mScreen1_3);
				} else if (mCurrentPageIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + positionOffset * mScreen1_3);
				} else if (mCurrentPageIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1) * mScreen1_3);
				}
				mTabline.setLayoutParams(lp);

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_ll_chat:
			resetTextView();
			mChatTextView.setTextColor(Color.parseColor("#008000"));
			mViewPager.setCurrentItem(0);
			break;
		case R.id.id_ll_friend:
			resetTextView();
			mFriendTextView.setTextColor(Color.parseColor("#008000"));
			mViewPager.setCurrentItem(1);
			break;
		case R.id.id_ll_contact:
			resetTextView();
			mContactTextView.setTextColor(Color.parseColor("#008000"));
			mViewPager.setCurrentItem(2);
			break;

		default:
			break;
		}
	}

	protected void resetTextView() {
		mChatTextView.setTextColor(Color.BLACK);
		mFriendTextView.setTextColor(Color.BLACK);
		mContactTextView.setTextColor(Color.BLACK);
	}

}
