package com.gongza.views.gridView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

/**
 * 一个带头和尾的gridview
 * @author gongza
 *
 */
public class GridViewWithHeaderAndFooter extends GridView {
    public static boolean DEBUG = false;
    private int mNumColumns = -1;
    private View mViewForMeasureRowHeight = null;
    private int mRowHeight = -1;
    private static final String LOG_TAG = "grid-view-with-header-and-footer";
    private ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> mHeaderViewInfos = new ArrayList();
    private ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> mFooterViewInfos = new ArrayList();

    private void initHeaderGridView() {
    }

    public GridViewWithHeaderAndFooter(Context context) {
        super(context);
        this.initHeaderGridView();
    }

    public GridViewWithHeaderAndFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initHeaderGridView();
    }

    public GridViewWithHeaderAndFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initHeaderGridView();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter adapter = this.getAdapter();
        if(adapter != null && adapter instanceof GridViewWithHeaderAndFooter.HeaderViewGridAdapter) {
            ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)adapter).setNumColumns(this.getNumColumnsCompatible());
            ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)adapter).setRowHeight(this.getRowHeight());
        }

    }

    public void setClipChildren(boolean clipChildren) {
    }

    public void setClipChildrenSupper(boolean clipChildren) {
        super.setClipChildren(false);
    }

    public void addHeaderView(View v) {
        this.addHeaderView(v, (Object)null, true);
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        ListAdapter adapter = this.getAdapter();
        if(adapter != null && !(adapter instanceof GridViewWithHeaderAndFooter.HeaderViewGridAdapter)) {
            throw new IllegalStateException("Cannot add header view to grid -- setAdapter has already been called.");
        } else {
            LayoutParams lyp = (LayoutParams) v.getLayoutParams();
            GridViewWithHeaderAndFooter.FixedViewInfo info = new GridViewWithHeaderAndFooter.FixedViewInfo();
            GridViewWithHeaderAndFooter.FullWidthFixedViewLayout fl = new GridViewWithHeaderAndFooter.FullWidthFixedViewLayout(this.getContext());
            if(lyp != null) {
                v.setLayoutParams(new android.widget.FrameLayout.LayoutParams(lyp.width, lyp.height));
                fl.setLayoutParams(new android.widget.AbsListView.LayoutParams(lyp.width, lyp.height));
            }

            fl.addView(v);
            info.view = v;
            info.viewContainer = fl;
            info.data = data;
            info.isSelectable = isSelectable;
            this.mHeaderViewInfos.add(info);
            if(adapter != null) {
                ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)adapter).notifyDataSetChanged();
            }

        }
    }

    public void addFooterView(View v) {
        this.addFooterView(v, (Object)null, true);
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        ListAdapter mAdapter = this.getAdapter();
        if(mAdapter != null && !(mAdapter instanceof GridViewWithHeaderAndFooter.HeaderViewGridAdapter)) {
            throw new IllegalStateException("Cannot add header view to grid -- setAdapter has already been called.");
        } else {
            LayoutParams lyp = (LayoutParams) v.getLayoutParams();
            GridViewWithHeaderAndFooter.FixedViewInfo info = new GridViewWithHeaderAndFooter.FixedViewInfo();
            GridViewWithHeaderAndFooter.FullWidthFixedViewLayout fl = new GridViewWithHeaderAndFooter.FullWidthFixedViewLayout(this.getContext());
            if(lyp != null) {
                v.setLayoutParams(new android.widget.FrameLayout.LayoutParams(lyp.width, lyp.height));
                fl.setLayoutParams(new android.widget.AbsListView.LayoutParams(lyp.width, lyp.height));
            }

            fl.addView(v);
            info.view = v;
            info.viewContainer = fl;
            info.data = data;
            info.isSelectable = isSelectable;
            this.mFooterViewInfos.add(info);
            if(mAdapter != null) {
                ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)mAdapter).notifyDataSetChanged();
            }

        }
    }

    public int getHeaderViewCount() {
        return this.mHeaderViewInfos.size();
    }

    public int getFooterViewCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if(this.mHeaderViewInfos.size() > 0) {
            boolean result = false;
            ListAdapter adapter = this.getAdapter();
            if(adapter != null && ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)adapter).removeHeader(v)) {
                result = true;
            }

            this.removeFixedViewInfo(v, this.mHeaderViewInfos);
            return result;
        } else {
            return false;
        }
    }

    public boolean removeFooterView(View v) {
        if(this.mFooterViewInfos.size() > 0) {
            boolean result = false;
            ListAdapter adapter = this.getAdapter();
            if(adapter != null && ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)adapter).removeFooter(v)) {
                result = true;
            }

            this.removeFixedViewInfo(v, this.mFooterViewInfos);
            return result;
        } else {
            return false;
        }
    }

    private void removeFixedViewInfo(View v, ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> where) {
        int len = where.size();

        for(int i = 0; i < len; ++i) {
            GridViewWithHeaderAndFooter.FixedViewInfo info = (GridViewWithHeaderAndFooter.FixedViewInfo)where.get(i);
            if(info.view == v) {
                where.remove(i);
                break;
            }
        }

    }

    @TargetApi(11)
    private int getNumColumnsCompatible() {
        if(VERSION.SDK_INT >= 11) {
            return super.getNumColumns();
        } else {
            try {
                Field e = GridView.class.getDeclaredField("mNumColumns");
                e.setAccessible(true);
                return e.getInt(this);
            } catch (Exception var2) {
                if(this.mNumColumns != -1) {
                    return this.mNumColumns;
                } else {
                    throw new RuntimeException("Can not determine the mNumColumns for this API platform, please call setNumColumns to set it.");
                }
            }
        }
    }

    @TargetApi(16)
    private int getColumnWidthCompatible() {
        if(VERSION.SDK_INT >= 16) {
            return super.getColumnWidth();
        } else {
            try {
                Field e = GridView.class.getDeclaredField("mColumnWidth");
                e.setAccessible(true);
                return e.getInt(this);
            } catch (NoSuchFieldException var2) {
                throw new RuntimeException(var2);
            } catch (IllegalAccessException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mViewForMeasureRowHeight = null;
    }

    public void invalidateRowHeight() {
        this.mRowHeight = -1;
    }

    public int getHeaderHeight(int row) {
        return row >= 0?((GridViewWithHeaderAndFooter.FixedViewInfo)this.mHeaderViewInfos.get(row)).view.getMeasuredHeight():0;
    }

    @TargetApi(16)
    public int getVerticalSpacing() {
        int value = 0;

        try {
            int ex = VERSION.SDK_INT;
            if(ex < 16) {
                Field field = GridView.class.getDeclaredField("mVerticalSpacing");
                field.setAccessible(true);
                value = field.getInt(this);
            } else {
                value = super.getVerticalSpacing();
            }
        } catch (Exception var4) {
            ;
        }

        return value;
    }

    @TargetApi(16)
    public int getHorizontalSpacing() {
        int value = 0;

        try {
            int ex = VERSION.SDK_INT;
            if(ex < 16) {
                Field field = GridView.class.getDeclaredField("mHorizontalSpacing");
                field.setAccessible(true);
                value = field.getInt(this);
            } else {
                value = super.getHorizontalSpacing();
            }
        } catch (Exception var4) {
            ;
        }

        return value;
    }

    public int getRowHeight() {
        if(this.mRowHeight > 0) {
            return this.mRowHeight;
        } else {
            ListAdapter adapter = this.getAdapter();
            int numColumns = this.getNumColumnsCompatible();
            if(adapter != null && adapter.getCount() > numColumns * (this.mHeaderViewInfos.size() + this.mFooterViewInfos.size())) {
                int mColumnWidth = this.getColumnWidthCompatible();
                View view = this.getAdapter().getView(numColumns * this.mHeaderViewInfos.size(), this.mViewForMeasureRowHeight, this);
                android.widget.AbsListView.LayoutParams p = (android.widget.AbsListView.LayoutParams)view.getLayoutParams();
                if(p == null) {
                    p = new android.widget.AbsListView.LayoutParams(-1, -2, 0);
                    view.setLayoutParams(p);
                }

                int childHeightSpec = getChildMeasureSpec(MeasureSpec.makeMeasureSpec(0, 0), 0, p.height);
                int childWidthSpec = getChildMeasureSpec(MeasureSpec.makeMeasureSpec(mColumnWidth, 1073741824), 0, p.width);
                view.measure(childWidthSpec, childHeightSpec);
                this.mViewForMeasureRowHeight = view;
                this.mRowHeight = view.getMeasuredHeight();
                return this.mRowHeight;
            } else {
                return -1;
            }
        }
    }

    @TargetApi(11)
    public void tryToScrollToBottomSmoothly() {
        int lastPos = this.getAdapter().getCount() - 1;
        if(VERSION.SDK_INT >= 11) {
            this.smoothScrollToPositionFromTop(lastPos, 0);
        } else {
            this.setSelection(lastPos);
        }

    }

    @TargetApi(11)
    public void tryToScrollToBottomSmoothly(int duration) {
        int lastPos = this.getAdapter().getCount() - 1;
        if(VERSION.SDK_INT >= 11) {
            this.smoothScrollToPositionFromTop(lastPos, 0, duration);
        } else {
            this.setSelection(lastPos);
        }

    }

    public void setAdapter(ListAdapter adapter) {
        if(this.mHeaderViewInfos.size() <= 0 && this.mFooterViewInfos.size() <= 0) {
            super.setAdapter(adapter);
        } else {
            GridViewWithHeaderAndFooter.HeaderViewGridAdapter headerViewGridAdapter = new GridViewWithHeaderAndFooter.HeaderViewGridAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
            int numColumns = this.getNumColumnsCompatible();
            if(numColumns > 1) {
                headerViewGridAdapter.setNumColumns(numColumns);
            }

            headerViewGridAdapter.setRowHeight(this.getRowHeight());
            super.setAdapter(headerViewGridAdapter);
        }

    }

    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        this.mNumColumns = numColumns;
        ListAdapter adapter = this.getAdapter();
        if(adapter != null && adapter instanceof GridViewWithHeaderAndFooter.HeaderViewGridAdapter) {
            ((GridViewWithHeaderAndFooter.HeaderViewGridAdapter)adapter).setNumColumns(numColumns);
        }

    }

    private static class HeaderViewGridAdapter implements WrapperListAdapter, Filterable {
        private final DataSetObservable mDataSetObservable = new DataSetObservable();
        private final ListAdapter mAdapter;
        static final ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> EMPTY_INFO_LIST = new ArrayList();
        ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> mHeaderViewInfos;
        ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> mFooterViewInfos;
        private int mNumColumns = 1;
        private int mRowHeight = -1;
        boolean mAreAllFixedViewsSelectable;
        private final boolean mIsFilterable;
        private boolean mCachePlaceHoldView = true;
        private boolean mCacheFirstHeaderView = false;

        public HeaderViewGridAdapter(ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> headerViewInfos, ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> footViewInfos, ListAdapter adapter) {
            this.mAdapter = adapter;
            this.mIsFilterable = adapter instanceof Filterable;
            if(headerViewInfos == null) {
                this.mHeaderViewInfos = EMPTY_INFO_LIST;
            } else {
                this.mHeaderViewInfos = headerViewInfos;
            }

            if(footViewInfos == null) {
                this.mFooterViewInfos = EMPTY_INFO_LIST;
            } else {
                this.mFooterViewInfos = footViewInfos;
            }

            this.mAreAllFixedViewsSelectable = this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos);
        }

        public void setNumColumns(int numColumns) {
            if(numColumns >= 1) {
                if(this.mNumColumns != numColumns) {
                    this.mNumColumns = numColumns;
                    this.notifyDataSetChanged();
                }

            }
        }

        public void setRowHeight(int height) {
            this.mRowHeight = height;
        }

        public int getHeadersCount() {
            return this.mHeaderViewInfos.size();
        }

        public int getFootersCount() {
            return this.mFooterViewInfos.size();
        }

        public boolean isEmpty() {
            return (this.mAdapter == null || this.mAdapter.isEmpty()) && this.getHeadersCount() == 0 && this.getFootersCount() == 0;
        }

        private boolean areAllListInfosSelectable(ArrayList<GridViewWithHeaderAndFooter.FixedViewInfo> infos) {
            if(infos != null) {
                Iterator i$ = infos.iterator();

                while(i$.hasNext()) {
                    GridViewWithHeaderAndFooter.FixedViewInfo info = (GridViewWithHeaderAndFooter.FixedViewInfo)i$.next();
                    if(!info.isSelectable) {
                        return false;
                    }
                }
            }

            return true;
        }

        public boolean removeHeader(View v) {
            for(int i = 0; i < this.mHeaderViewInfos.size(); ++i) {
                GridViewWithHeaderAndFooter.FixedViewInfo info = (GridViewWithHeaderAndFooter.FixedViewInfo)this.mHeaderViewInfos.get(i);
                if(info.view == v) {
                    this.mHeaderViewInfos.remove(i);
                    this.mAreAllFixedViewsSelectable = this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos);
                    this.mDataSetObservable.notifyChanged();
                    return true;
                }
            }

            return false;
        }

        public boolean removeFooter(View v) {
            for(int i = 0; i < this.mFooterViewInfos.size(); ++i) {
                GridViewWithHeaderAndFooter.FixedViewInfo info = (GridViewWithHeaderAndFooter.FixedViewInfo)this.mFooterViewInfos.get(i);
                if(info.view == v) {
                    this.mFooterViewInfos.remove(i);
                    this.mAreAllFixedViewsSelectable = this.areAllListInfosSelectable(this.mHeaderViewInfos) && this.areAllListInfosSelectable(this.mFooterViewInfos);
                    this.mDataSetObservable.notifyChanged();
                    return true;
                }
            }

            return false;
        }

        public int getCount() {
            return this.mAdapter != null?(this.getFootersCount() + this.getHeadersCount()) * this.mNumColumns + this.getAdapterAndPlaceHolderCount():(this.getFootersCount() + this.getHeadersCount()) * this.mNumColumns;
        }

        public boolean areAllItemsEnabled() {
            return this.mAdapter == null?true:this.mAreAllFixedViewsSelectable && this.mAdapter.areAllItemsEnabled();
        }

        private int getAdapterAndPlaceHolderCount() {
            int adapterCount = (int)(Math.ceil((double)(1.0F * (float)this.mAdapter.getCount() / (float)this.mNumColumns)) * (double)this.mNumColumns);
            return adapterCount;
        }

        public boolean isEnabled(int position) {
            int numHeadersAndPlaceholders = this.getHeadersCount() * this.mNumColumns;
            if(position < numHeadersAndPlaceholders) {
                return position % this.mNumColumns == 0 && ((GridViewWithHeaderAndFooter.FixedViewInfo)this.mHeaderViewInfos.get(position / this.mNumColumns)).isSelectable;
            } else {
                int adjPosition = position - numHeadersAndPlaceholders;
                int adapterCount = 0;
                if(this.mAdapter != null) {
                    adapterCount = this.getAdapterAndPlaceHolderCount();
                    if(adjPosition < adapterCount) {
                        return adjPosition < this.mAdapter.getCount() && this.mAdapter.isEnabled(adjPosition);
                    }
                }

                int footerPosition = adjPosition - adapterCount;
                return footerPosition % this.mNumColumns == 0 && ((GridViewWithHeaderAndFooter.FixedViewInfo)this.mFooterViewInfos.get(footerPosition / this.mNumColumns)).isSelectable;
            }
        }

        public Object getItem(int position) {
            int numHeadersAndPlaceholders = this.getHeadersCount() * this.mNumColumns;
            if(position < numHeadersAndPlaceholders) {
                return position % this.mNumColumns == 0?((GridViewWithHeaderAndFooter.FixedViewInfo)this.mHeaderViewInfos.get(position / this.mNumColumns)).data:null;
            } else {
                int adjPosition = position - numHeadersAndPlaceholders;
                int adapterCount = 0;
                if(this.mAdapter != null) {
                    adapterCount = this.getAdapterAndPlaceHolderCount();
                    if(adjPosition < adapterCount) {
                        if(adjPosition < this.mAdapter.getCount()) {
                            return this.mAdapter.getItem(adjPosition);
                        }

                        return null;
                    }
                }

                int footerPosition = adjPosition - adapterCount;
                return footerPosition % this.mNumColumns == 0?((GridViewWithHeaderAndFooter.FixedViewInfo)this.mFooterViewInfos.get(footerPosition)).data:null;
            }
        }

        public long getItemId(int position) {
            int numHeadersAndPlaceholders = this.getHeadersCount() * this.mNumColumns;
            if(this.mAdapter != null && position >= numHeadersAndPlaceholders) {
                int adjPosition = position - numHeadersAndPlaceholders;
                int adapterCount = this.mAdapter.getCount();
                if(adjPosition < adapterCount) {
                    return this.mAdapter.getItemId(adjPosition);
                }
            }

            return -1L;
        }

        public boolean hasStableIds() {
            return this.mAdapter != null?this.mAdapter.hasStableIds():false;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if(GridViewWithHeaderAndFooter.DEBUG) {
                Log.d("grid-view-with-header-and-footer", String.format("getView: %s, reused: %s", new Object[]{Integer.valueOf(position), Boolean.valueOf(convertView == null)}));
            }

            int numHeadersAndPlaceholders = this.getHeadersCount() * this.mNumColumns;
            if(position < numHeadersAndPlaceholders) {
                ViewGroup adjPosition1 = ((GridViewWithHeaderAndFooter.FixedViewInfo)this.mHeaderViewInfos.get(position / this.mNumColumns)).viewContainer;
                if(position % this.mNumColumns == 0) {
                    return adjPosition1;
                } else {
                    if(convertView == null) {
                        convertView = new View(parent.getContext());
                    }

                    convertView.setVisibility(4);
                    convertView.setMinimumHeight(adjPosition1.getHeight());
                    return convertView;
                }
            } else {
                int adjPosition = position - numHeadersAndPlaceholders;
                int adapterCount = 0;
                if(this.mAdapter != null) {
                    adapterCount = this.getAdapterAndPlaceHolderCount();
                    if(adjPosition < adapterCount) {
                        if(adjPosition < this.mAdapter.getCount()) {
                            View footerPosition1 = this.mAdapter.getView(adjPosition, convertView, parent);
                            return footerPosition1;
                        }

                        if(convertView == null) {
                            convertView = new View(parent.getContext());
                        }

                        convertView.setVisibility(4);
                        convertView.setMinimumHeight(this.mRowHeight);
                        return convertView;
                    }
                }

                int footerPosition = adjPosition - adapterCount;
                if(footerPosition < this.getCount()) {
                    ViewGroup footViewContainer = ((GridViewWithHeaderAndFooter.FixedViewInfo)this.mFooterViewInfos.get(footerPosition / this.mNumColumns)).viewContainer;
                    if(position % this.mNumColumns == 0) {
                        return footViewContainer;
                    } else {
                        if(convertView == null) {
                            convertView = new View(parent.getContext());
                        }

                        convertView.setVisibility(4);
                        convertView.setMinimumHeight(footViewContainer.getHeight());
                        return convertView;
                    }
                } else {
                    throw new ArrayIndexOutOfBoundsException(position);
                }
            }
        }

        public int getItemViewType(int position) {
            int numHeadersAndPlaceholders = this.getHeadersCount() * this.mNumColumns;
            int adapterViewTypeStart = this.mAdapter == null?0:this.mAdapter.getViewTypeCount() - 1;
            int type = -2;
            if(this.mCachePlaceHoldView && position < numHeadersAndPlaceholders) {
                if(position == 0 && this.mCacheFirstHeaderView) {
                    type = adapterViewTypeStart + this.mHeaderViewInfos.size() + this.mFooterViewInfos.size() + 1 + 1;
                }

                if(position % this.mNumColumns != 0) {
                    type = adapterViewTypeStart + position / this.mNumColumns + 1;
                }
            }

            int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if(this.mAdapter != null) {
                adapterCount = this.getAdapterAndPlaceHolderCount();
                if(adjPosition >= 0 && adjPosition < adapterCount) {
                    if(adjPosition < this.mAdapter.getCount()) {
                        type = this.mAdapter.getItemViewType(adjPosition);
                    } else if(this.mCachePlaceHoldView) {
                        type = adapterViewTypeStart + this.mHeaderViewInfos.size() + 1;
                    }
                }
            }

            if(this.mCachePlaceHoldView) {
                int footerPosition = adjPosition - adapterCount;
                if(footerPosition >= 0 && footerPosition < this.getCount() && footerPosition % this.mNumColumns != 0) {
                    type = adapterViewTypeStart + this.mHeaderViewInfos.size() + 1 + footerPosition / this.mNumColumns + 1;
                }
            }

            if(GridViewWithHeaderAndFooter.DEBUG) {
                Log.d("grid-view-with-header-and-footer", String.format("getItemViewType: pos: %s, result: %s", new Object[]{Integer.valueOf(position), Integer.valueOf(type), Boolean.valueOf(this.mCachePlaceHoldView), Boolean.valueOf(this.mCacheFirstHeaderView)}));
            }

            return type;
        }

        public int getViewTypeCount() {
            int count = this.mAdapter == null?1:this.mAdapter.getViewTypeCount();
            if(this.mCachePlaceHoldView) {
                int offset = this.mHeaderViewInfos.size() + 1 + this.mFooterViewInfos.size();
                if(this.mCacheFirstHeaderView) {
                    ++offset;
                }

                count += offset;
            }

            if(GridViewWithHeaderAndFooter.DEBUG) {
                Log.d("grid-view-with-header-and-footer", String.format("getViewTypeCount: %s", new Object[]{Integer.valueOf(count)}));
            }

            return count;
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            this.mDataSetObservable.registerObserver(observer);
            if(this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(observer);
            }

        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            this.mDataSetObservable.unregisterObserver(observer);
            if(this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(observer);
            }

        }

        public Filter getFilter() {
            return this.mIsFilterable?((Filterable)this.mAdapter).getFilter():null;
        }

        public ListAdapter getWrappedAdapter() {
            return this.mAdapter;
        }

        public void notifyDataSetChanged() {
            this.mDataSetObservable.notifyChanged();
        }
    }

    private class FullWidthFixedViewLayout extends FrameLayout {
        public FullWidthFixedViewLayout(Context context) {
            super(context);
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int realLeft = GridViewWithHeaderAndFooter.this.getPaddingLeft() + this.getPaddingLeft();
            if(realLeft != left) {
                this.offsetLeftAndRight(realLeft - left);
            }

            super.onLayout(changed, left, top, right, bottom);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int targetWidth = GridViewWithHeaderAndFooter.this.getMeasuredWidth() - GridViewWithHeaderAndFooter.this.getPaddingLeft() - GridViewWithHeaderAndFooter.this.getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(targetWidth, MeasureSpec.getMode(widthMeasureSpec));
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private static class FixedViewInfo {
        public View view;
        public ViewGroup viewContainer;
        public Object data;
        public boolean isSelectable;

        private FixedViewInfo() {
        }
    }
}
