package com.gongza.novice.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gongza.novice.R;
import com.gongza.novice.adapter.FruitAdapter;
import com.gongza.novice.bean.Fruit;
import com.gongza.views.waterfalllistview.AdapterView;
import com.gongza.views.waterfalllistview.ListView;

/**
 * 
  * @ClassName: ActivityWaterfallList
  * @Description: 瀑布流listview
  * @author gongza
  * @date 2015年10月8日 下午5:25:49
  *
 */
public class ActivityWaterfallList extends Activity{
	private List<Fruit> fruitList = new ArrayList<Fruit>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waterfall_list);
		
		initFruits();
		FruitAdapter adapter = new FruitAdapter(ActivityWaterfallList.this,
				R.layout.fruit_item, fruitList);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Fruit fruit = fruitList.get(position);
//				Toast.makeText(MainActivity.this, fruit.getName(),
//						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initFruits() {
		for (int i = 0; i < 50; i++) {
			StringBuilder text = new StringBuilder("Position[" + i + "]:\n");
			int loopCount = new Random().nextInt(15) + 1;
			for (int j = 0; j < loopCount; j++) {
				text.append("瀑布流瀑布流瀑布流");
			}
			fruitList.add(new Fruit(text.toString()));
		}
	}
	
}
