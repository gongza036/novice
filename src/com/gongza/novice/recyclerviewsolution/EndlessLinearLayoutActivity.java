package com.gongza.novice.recyclerviewsolution;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

import com.gongza.novice.R;
import com.gongza.novice.recyclerviewsolution.utils.NetworkUtils;
import com.gongza.novice.recyclerviewsolution.utils.RecyclerViewStateUtils;
import com.gongza.novice.recyclerviewsolution.weight.LoadingFooter;
import com.gongza.novice.recyclerviewsolution.weight.SampleHeader;
import com.gongza.views.cube.ptr.PtrDefaultHandler;
import com.gongza.views.cube.ptr.PtrFrameLayout;
import com.gongza.views.cube.ptr.PtrGongzFrameLayout;
import com.gongza.views.cube.ptr.PtrHandler;
import com.gongza.views.recyclerviewsolution.EndlessRecyclerOnScrollListener;
import com.gongza.views.recyclerviewsolution.HeaderAndFooterRecyclerViewAdapter;
import com.gongza.views.recyclerviewsolution.RecyclerViewUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cundong on 2015/10/29.
 *
 * 带HeaderView的分页加载LinearLayout RecyclerView
 */
public class EndlessLinearLayoutActivity extends Activity {
	private PtrGongzFrameLayout mPtrFrame;
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 64;

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private int mCurrentCounter = 0;

    private RecyclerView mRecyclerView = null;

    private DataAdapter mDataAdapter = null;

    private PreviewHandler mHandler = new PreviewHandler(this);
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_solution_activity_endlesslinear);
        initView();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        //init data
        ArrayList<ItemModel> dataList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            ItemModel item = new ItemModel();
            item.id = i;
            item.title = "item" + i;
            dataList.add(item);
        }

        mCurrentCounter = dataList.size();

        mDataAdapter = new DataAdapter(this);
        mDataAdapter.addItems(dataList);

        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerViewUtils.setHeaderView(mRecyclerView, new SampleHeader(this));

        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }
    
    private void initView() {
		mPtrFrame = (PtrGongzFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				 updateData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		// the following are default settings
		mPtrFrame.setResistance(2.3f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.0f);
		mPtrFrame.setDurationToClose(200);
		mPtrFrame.setDurationToCloseHeader(800);
		// default is false
		mPtrFrame.setPullToRefresh(false);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
		//立即下拉刷新，不等UIReset
		mPtrFrame.setEnabledNextPtrAtOnce(true);
		mPtrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				// mPtrFrame.autoRefresh();
			}
		}, 100);
		// updateData();
		setupViews(mPtrFrame);
	}

    protected void setupViews(final PtrGongzFrameLayout ptrFrame) {
		ptrFrame.setLoadingMinTime(1000);
		// setHeaderTitle(R.string.ptr_demo_block_auto_fresh);
		ptrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				ptrFrame.autoRefresh(true);
			}
		}, 500);
	}
	
	protected void updateData() {
		mPtrFrame.refreshComplete();
    }
    

    private void notifyDataSetChanged() {
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<ItemModel> list) {

        mDataAdapter.addItems(list);
        mCurrentCounter += list.size();
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if(state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }

            if (mCurrentCounter < TOTAL_COUNTER) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(EndlessLinearLayoutActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(EndlessLinearLayoutActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private static class PreviewHandler extends Handler {

        private WeakReference<EndlessLinearLayoutActivity> ref;

        PreviewHandler(EndlessLinearLayoutActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final EndlessLinearLayoutActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case -1:
                    int currentSize = activity.mDataAdapter.getItemCount();

                    //模拟组装10个数据
                    ArrayList<ItemModel> newList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }

                        ItemModel item = new ItemModel();
                        item.id = currentSize + i;
                        item.title = "item" + (item.id);

                        newList.add(item);
                    }

                    activity.addItems(newList);
                    RecyclerViewStateUtils.setFooterViewState(activity.mRecyclerView, LoadingFooter.State.Normal);
                    break;
                case -2:
                    activity.notifyDataSetChanged();
                    break;
                case -3:
                    RecyclerViewStateUtils.setFooterViewState(activity, activity.mRecyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, activity.mFooterClick);
                    break;
            }
        }
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(EndlessLinearLayoutActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    /**
     * 模拟请求网络
     */
    private void requestData() {

        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if(NetworkUtils.isNetAvailable(EndlessLinearLayoutActivity.this)) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }

    private class DataAdapter extends RecyclerView.Adapter {

        private LayoutInflater mLayoutInflater;
        private SortedList<ItemModel> mSortedList;

        public DataAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            mSortedList = new SortedList<>(ItemModel.class, new SortedList.Callback<ItemModel>() {

                /**
                 * 返回一个负整数（第一个参数小于第二个）、零（相等）或正整数（第一个参数大于第二个）
                 */
                @Override
                public int compare(ItemModel o1, ItemModel o2) {

                    if (o1.id < o2.id) {
                        return -1;
                    } else if (o1.id > o2.id) {
                        return 1;
                    }

                    return 0;
                }

                @Override
                public boolean areContentsTheSame(ItemModel oldItem, ItemModel newItem) {
                    return oldItem.title.equals(newItem.title);
                }

                @Override
                public boolean areItemsTheSame(ItemModel item1, ItemModel item2) {
                    return item1.id == item2.id;
                }

                @Override
                public void onInserted(int position, int count) {
                    notifyItemRangeInserted(position, count);
                }

                @Override
                public void onRemoved(int position, int count) {
                    notifyItemRangeRemoved(position, count);
                }

                @Override
                public void onMoved(int fromPosition, int toPosition) {
                    notifyItemMoved(fromPosition, toPosition);
                }

                @Override
                public void onChanged(int position, int count) {
                    notifyItemRangeChanged(position, count);
                }
            });
        }

        public void addItems(ArrayList<ItemModel> list) {
            mSortedList.beginBatchedUpdates();

            for(ItemModel itemModel : list) {
                mSortedList.add(itemModel);
            }

            mSortedList.endBatchedUpdates();
        }

        public void deleteItems(ArrayList<ItemModel> items) {
            mSortedList.beginBatchedUpdates();
            for (ItemModel item : items) {
                mSortedList.remove(item);
            }
            mSortedList.endBatchedUpdates();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.rv_solution_item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ItemModel item = mSortedList.get(position);

            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textView.setText(item.title);
        }

        @Override
        public int getItemCount() {
            return mSortedList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.info_text);

                textView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemModel item = mSortedList.get(RecyclerViewUtils.getAdapterPosition(mRecyclerView, ViewHolder.this));
                        Toast.makeText(EndlessLinearLayoutActivity.this, item.title, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}