package com.gongza.views.listviewtools;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gongza.novice.R;

/**
 * 
 * @ClassName: HehuaPullToRefreshView
 * @Description: 一个GridView和listView都可以使用的 下拉刷新上拉加载
 * @author gongza
 * @date 2015年6月19日 上午10:12:31
 *
 */
public class HehuaPullToRefreshView extends LinearLayout {
	private static final String TAG = "HehuaPullToRefreshView";
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;
	private boolean enablePullTorefresh = true;
	private boolean enablePullLoadMoreDataStatus = true;
	private int mLastMotionY;
	private boolean mLock;
	private View mHeaderView;
	private View mFooterView;
	private AdapterView<?> mAdapterView;
	private ScrollView mScrollView;
	private int mHeaderViewHeight;
	private int mFooterViewHeight;
	private ProgressBar mHeaderImageView;

	// private ImageView mFooterImageView;
	private TextView mHeaderTextView;
	private TextView mFooterTextView;
	private TextView mHeaderUpdateTextView;
	// private TextView mFooterUpdateTextView;
	private ProgressBar mHeaderProgressBar;
	private ProgressBar mFooterProgressBar;
	private LayoutInflater mInflater;
	private int mHeaderState;
	private int mFooterState;
	private int mPullState;
	private RotateAnimation mFlipAnimation;
	private RotateAnimation mReverseFlipAnimation;
	private OnFooterRefreshListener mOnFooterRefreshListener;
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	public HehuaPullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HehuaPullToRefreshView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(250);
		mFlipAnimation.setFillAfter(true);
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);

		mInflater = LayoutInflater.from(getContext());
		addHeaderView();
	}

	private void addHeaderView() {
		mHeaderView = mInflater.inflate(R.layout.hehua_refresh_header, this,
				false);

		mHeaderImageView = (ProgressBar) mHeaderView
				.findViewById(R.id.pull_to_refresh_image);

		mHeaderTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_text);
		mHeaderUpdateTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_updated_at);
		mHeaderProgressBar = (ProgressBar) mHeaderView
				.findViewById(R.id.pull_to_refresh_progress);
		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		params.topMargin = -(mHeaderViewHeight);
		addView(mHeaderView, params);

	}

	private void addFooterView() {
		mFooterView = mInflater.inflate(R.layout.hehua_refresh_footer, this,
				false);
		// mFooterImageView = (ImageView) mFooterView
		// .findViewById(R.id.pull_to_load_image);
		mFooterTextView = (TextView) mFooterView
				.findViewById(R.id.pull_to_load_text);
		mFooterProgressBar = (ProgressBar) mFooterView
				.findViewById(R.id.pull_to_load_progress);
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mFooterViewHeight);
		addView(mFooterView, params);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		addFooterView();
		initContentAdapterView();
	}

	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (isRefreshViewScroll(deltaY)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mLock) {
			return true;
		}
		int y = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (mPullState == PULL_DOWN_STATE) {
				Log.i(TAG, " pull down!parent view move!");
				headerPrepareToRefresh(deltaY);
			} else if (mPullState == PULL_UP_STATE) {
				Log.i(TAG, "pull up!parent view move!");
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					headerRefreshing();
				} else {
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight) {
					footerRefreshing();
				} else {
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private boolean isRefreshViewScroll(int deltaY) {
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			return false;
		}
		if (mAdapterView != null) {
			if (deltaY > 0) {
				if (!enablePullTorefresh) {
					return false;
				}
				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					return false;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 8) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				if (!enablePullLoadMoreDataStatus) {
					return false;
				}
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					return false;
				}
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
								.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		if (mScrollView != null) {
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY()) {
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			mHeaderTextView.setText("松开刷新");
			mHeaderUpdateTextView.setVisibility(View.GONE);
			// mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			// mHeaderImageView.clearAnimation();
			// mHeaderImageView.startAnimation(mFlipAnimation);
			mHeaderState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
			// mHeaderImageView.clearAnimation();
			// mHeaderImageView.startAnimation(mFlipAnimation);
			mHeaderTextView.setText("下拉刷新");
			mHeaderState = PULL_TO_REFRESH;
		}
	}

	private void footerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH) {
			mFooterTextView.setText("松开后加载");
			// mFooterImageView.clearAnimation();
			// mFooterImageView.startAnimation(mFlipAnimation);
			mFooterState = RELEASE_TO_REFRESH;
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			// mFooterImageView.clearAnimation();
			// mFooterImageView.startAnimation(mFlipAnimation);
			mFooterTextView.setText("上拉加载更多");
			mFooterState = PULL_TO_REFRESH;
		}
	}

	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	public void headerRefreshing() {
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		// mHeaderImageView.setVisibility(View.GONE);
		// mHeaderImageView.clearAnimation();
		// mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView.setText("正在努力加载中...");
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	private void footerRefreshing() {
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		// mFooterImageView.setVisibility(View.GONE);
		// mFooterImageView.clearAnimation();
		// mFooterImageView.setImageDrawable(null);
		mFooterProgressBar.setVisibility(View.VISIBLE);
		mFooterTextView.setText("正在努力加载中...");
		if (mOnFooterRefreshListener != null) {
			mOnFooterRefreshListener.onFooterRefresh(this);
		}
	}

	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	public void onHeaderRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderImageView.setVisibility(View.VISIBLE);
		// mHeaderImageView.setImageResource(R.drawable.hehua_default_ptr_flip);
		mHeaderTextView.setText("下拉刷新");
		// mHeaderProgressBar.setVisibility(View.GONE);
		mHeaderState = PULL_TO_REFRESH;
		setLastUpdated("最近更新:" + new Date().toLocaleString());
	}

	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onHeaderRefreshComplete();
	}

	public void onFooterRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		// mFooterImageView.setVisibility(View.VISIBLE);
		// mFooterImageView.setImageResource(R.drawable.hehua_default_ptr_flip);
		mFooterTextView.setText("上拉加载更多");
		mFooterProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mFooterState = PULL_TO_REFRESH;
	}

	public void onFooterRefreshComplete(int size) {
		if (size > 0) {
			mFooterView.setVisibility(View.VISIBLE);
		} else {
			mFooterView.setVisibility(View.GONE);
		}
		setHeaderTopMargin(-mHeaderViewHeight);
		// mFooterImageView.setVisibility(View.VISIBLE);
		// mFooterImageView.setImageResource(R.drawable.hehua_default_ptr_flip);
		mFooterTextView.setText("上拉加载更多");
		mFooterProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mFooterState = PULL_TO_REFRESH;
	}

	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			// mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderUpdateTextView.setVisibility(View.GONE);
			mHeaderUpdateTextView.setText(lastUpdated);
		} else {
			mHeaderUpdateTextView.setVisibility(View.GONE);
		}
	}

	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	// /**
	// * lock
	// *
	// * @description Envn
	// */
	// private void lock() {
	// mLock = true;
	// }
	//
	// /**
	// * unlock
	// *
	// * @description Envn
	// */
	// private void unlock() {
	// mLock = false;
	// }

	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener) {
		mOnFooterRefreshListener = footerRefreshListener;
	}

	public interface OnFooterRefreshListener {
		public void onFooterRefresh(HehuaPullToRefreshView view);
	}

	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(HehuaPullToRefreshView view);
	}

	public boolean isEnablePullTorefresh() {
		return enablePullTorefresh;
	}

	public void setEnablePullTorefresh(boolean enablePullTorefresh) {
		this.enablePullTorefresh = enablePullTorefresh;
	}

	public boolean isEnablePullLoadMoreDataStatus() {
		return enablePullLoadMoreDataStatus;
	}

	public void setEnablePullLoadMoreDataStatus(
			boolean enablePullLoadMoreDataStatus) {
		this.enablePullLoadMoreDataStatus = enablePullLoadMoreDataStatus;
	}

	public void setLoadOff() {
		enablePullLoadMoreDataStatus = false;
	}

	public void setLoadOn() {
		enablePullLoadMoreDataStatus = true;
	}
}
