package com.gongza.novice.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gongza.novice.R;
import com.gongza.novice.bean.Fruit;

/**
 * 
  * @ClassName: FruitAdapter
  * @Description: ActivityWaterfallList 瀑布流demo
  * @author gongza
  * @date 2015年10月8日 下午5:19:07
  *
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {

	private int resourceId;

	public FruitAdapter(Context context, int textViewResourceId,
			List<Fruit> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.d("TAG", "get view " + position + " convertView is " + convertView);
		Fruit fruit = getItem(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else {
			view = convertView;
		}
		TextView text = (TextView) view.findViewById(R.id.text);
		text.setText(fruit.getName());
		return view;
	}

}
