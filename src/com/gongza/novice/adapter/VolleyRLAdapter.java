package com.gongza.novice.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.gongza.novice.ApplicationNovice;
import com.gongza.novice.R;
import com.gongza.novice.bean.group.GeekGroupBeanN;
import com.gongza.novice.volleydemo.volleyrequest.BitmapCache;

public class VolleyRLAdapter extends RecyclerView.Adapter<ViewHolder> {
	private LayoutInflater inflater;
	private Context context;
	// protected List<String> mDatas;
	protected ArrayList<GeekGroupBeanN> mDatas;
	private RequestQueue mQueue;
	private ImageLoader imageLoader;

	private static final int TYPE_0 = 0;
	private static final int TYPE_1 = 1;

	public interface OnItemClickListener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int positon);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;
	}

	public VolleyRLAdapter(Context context, ArrayList<GeekGroupBeanN> datas) {
		this.context = context;
		this.mDatas = datas;
		inflater = LayoutInflater.from(context);
		mQueue = ApplicationNovice.getHttpQueue();
		imageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	// @Override
	// public int getItemViewType(int position) {
	// if (mDatas.get(position).getType() == 0) {
	// return TYPE_0;
	// } else if (mDatas.get(position).getType() == 1) {
	// return TYPE_1;
	// } else {
	// return 100;
	// }
	// }

	public void addData(int pos) {
		// mDatas.add(pos, "加了一条");
		notifyItemInserted(pos);
	}

	public void delData(int pos) {
		mDatas.remove(pos);
		notifyItemRemoved(pos);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int pos) {
		// 绑定ViewHolder里的数据
		// holder.tv_item.setText(mDatas.get(pos));
//		if (mDatas.get(pos).getType() == 0) {
//			// holder.tv_item.setText(mDatas.get(pos).getGeek().getGeek_name());
//			itemShowGroup(holder, mDatas.get(pos), pos);
//		}
		 if (mDatas.get(pos).getType() == 0&&holder instanceof VolleyRlHolder) {
			 itemShowGroup((VolleyRlHolder)holder, mDatas.get(pos), pos);
//	            ((VolleyRlHolder) holder).mTextView.setText(mTitles[position]);
	        } else if (mDatas.get(pos).getType() == 1&&holder instanceof VolleyAdHolder) {
//	            ((VolleyAdHolder) holder).mTextView.setText(mTitles[position]);
	        }
		
//		 setUpItemEvent(holder);
	}

	protected void setUpItemEvent(final VolleyRlHolder holder) {
		if (mOnItemClickListener != null) {

			holder.itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int layoutPosition = holder.getLayoutPosition();
					mOnItemClickListener.onItemClick(holder.itemView,
							layoutPosition);
				}
			});

			holder.itemView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					int layoutPosition = holder.getLayoutPosition();
					mOnItemClickListener.onItemLongClick(holder.itemView,
							layoutPosition);
					return true;
				}
			});
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == 0) {
			// 创建ViewHolder
			View viewGroup = inflater.inflate(R.layout.item_volleyrl_group,
					parent, false);
			VolleyRlHolder mGzViewHolder = new VolleyRlHolder(viewGroup);
			return mGzViewHolder;
		} else {
			View viewAd = inflater.inflate(R.layout.item_volleyrl_ad, parent,
					false);
			VolleyAdHolder mVolleyAdHolder = new VolleyAdHolder(viewAd);
			return mVolleyAdHolder;
		}

	}

	private void itemShowGroup(VolleyRlHolder parentView, final GeekGroupBeanN gb,
			final int position) {

		displayImg(parentView.iv_picture, gb.getGroup().getPicture());
		displayImg(parentView.iv_head, gb.getGeek().getGeek_logo());

		parentView.tv_geek_name.setText(gb.getGeek().getGeek_name());

		parentView.tv_region_name.setText(gb.getGeek().getRegion_name());

		if (gb.getGroup().getIs_shipping() == 0) {
			parentView.tv_group_name.setText(gb.getGroup().getGroup_name());
		} else {
			parentView.tv_group_name.setText("[包邮] "
					+ gb.getGroup().getGroup_name());
		}

		parentView.tv_group_price.setText("￥" + gb.getGroup().getGroup_price()
				+ "");

		parentView.tv_orginal_price.setText("￥"
				+ gb.getGroup().getOrginal_price());
		parentView.tv_orginal_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG);

		parentView.tv_lave_time.setText("" + gb.getGroup().getLave_time());

		if (gb.getGroup().getLave_time() <= 0) {
			parentView.tv_lave_time.setVisibility(View.GONE);
			parentView.iv_soldout.setVisibility(View.VISIBLE);
			parentView.iv_soldout.setImageResource(R.drawable.ic_launcher);
		} else {
			parentView.tv_lave_time.setVisibility(View.VISIBLE);
			// iv_soldout.setVisibility(View.GONE);
			// iv_soldout.setImageResource(R.drawable.hehua_end);
			if (gb.getGroup().getSold_out() == 0) {
				parentView.iv_soldout.setVisibility(View.GONE);
				parentView.iv_soldout.setImageResource(R.drawable.ic_launcher);
			} else {
				parentView.iv_soldout.setVisibility(View.VISIBLE);
				parentView.iv_soldout.setImageResource(R.drawable.ic_launcher);
			}

		}

		if (TextUtils.isEmpty(gb.getGroup().getContent())) {
			parentView.tv_group_desc.setText("");
		} else {
			parentView.tv_group_desc.setText("" + gb.getGroup().getContent());
		}

		parentView.tv_share_num.setText("已团 " + gb.getGroup().getSold_num());

		parentView.layout_sell_num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		if (gb.getGeek().getGeekInfoList() != null
				&& gb.getGeek().getGeekInfoList().size() > 0) {
			parentView.tv_g_member.setText(""
					+ gb.getGeek().getGeekInfoList().get(0).getG_member() + "");
		}

		parentView.tv_praise_num.setText("" + gb.getGroup().getPraise_num()
				+ "");
		if (gb.getGroup().getIs_like() == 0) {
		} else {
			parentView.iv_praise_num
					.setBackgroundResource(R.drawable.ic_launcher);
			// 已喜欢的不让取消
			// parentView.layout_praise_num.setTag(iv_praise_num);
		}

	}

	private void displayImg(ImageView imageView, String url) {
		ImageListener listener = ImageLoader.getImageListener(imageView,
				R.drawable.ic_launcher, R.drawable.ic_launcher);
		imageLoader.get(url, listener);
		// 指定图片允许的最大宽度和高度
		// imageLoader.get("http://developer.android.com/images/home/aw_dac.png",listener,
		// 200, 200);
	}

}

class VolleyRlHolder extends ViewHolder {
	TextView tv_item, tv_geek_name, tv_region_name, tv_group_name,
			tv_group_price, tv_orginal_price, tv_lave_time, tv_group_desc,
			tv_share_num, tv_g_member, tv_praise_num;
	RelativeLayout head_layout;
	ImageView iv_head, iv_picture, iv_soldout, iv_praise_num;
	LinearLayout layout_sell_num, layout_g_member, layout_praise_num,
			layou_item;

	public VolleyRlHolder(View itemView) {
		super(itemView);

		tv_item = (TextView) itemView.findViewById(R.id.tv_geek_name);
		head_layout = (RelativeLayout) itemView.findViewById(R.id.head_layout);
		iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
		tv_geek_name = (TextView) itemView.findViewById(R.id.tv_geek_name);
		tv_region_name = (TextView) itemView.findViewById(R.id.tv_region_name);
		iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
		tv_group_name = (TextView) itemView.findViewById(R.id.tv_group_name);
		tv_group_price = (TextView) itemView.findViewById(R.id.tv_group_price);
		tv_orginal_price = (TextView) itemView
				.findViewById(R.id.tv_orginal_price);
		iv_soldout = (ImageView) itemView.findViewById(R.id.iv_soldout);
		tv_lave_time = (TextView) itemView.findViewById(R.id.tv_lave_time);
		tv_group_desc = (TextView) itemView.findViewById(R.id.tv_group_desc);
		tv_share_num = (TextView) itemView.findViewById(R.id.tv_share_num);
		layout_sell_num = (LinearLayout) itemView
				.findViewById(R.id.layout_sell_num);
		layout_g_member = (LinearLayout) itemView
				.findViewById(R.id.layout_g_member);
		tv_g_member = (TextView) itemView.findViewById(R.id.tv_g_member);
		layout_praise_num = (LinearLayout) itemView
				.findViewById(R.id.layout_praise_num);
		iv_praise_num = (ImageView) itemView.findViewById(R.id.iv_praise_num);
		tv_praise_num = (TextView) itemView.findViewById(R.id.tv_praise_num);
		layou_item = (LinearLayout) itemView.findViewById(R.id.layou_item);
	}

}

class VolleyAdHolder extends ViewHolder {
	ImageView iv_itemad;

	public VolleyAdHolder(View itemView) {
		super(itemView);

		iv_itemad = (ImageView) itemView.findViewById(R.id.iv_itemad);
	}

}