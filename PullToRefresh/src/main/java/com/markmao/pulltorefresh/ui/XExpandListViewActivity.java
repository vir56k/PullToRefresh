package com.markmao.pulltorefresh.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.markmao.pulltorefresh.R;
import com.markmao.pulltorefresh.widget.IXListViewListener;
import com.markmao.pulltorefresh.widget.XExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * XListView demo
 *
 * @author markmjw
 * @date 2013-10-08
 */
public class XExpandListViewActivity extends Activity implements IXListViewListener {
    private XExpandableListView mListView;

    private MyExpandableListViewAdapter mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int mIndex = 0;
    private int mRefreshIndex = 0;

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, XExpandListViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_expand_list_view);

        geneItems();
        initView();
    }

    private void initView() {
        mHandler = new Handler();

        mListView = (XExpandableListView) findViewById(R.id.list_view);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);

        mAdapter = new MyExpandableListViewAdapter();
        mListView.setAdapter(mAdapter);
    }

    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
        LayoutInflater layoutInflater;

        public MyExpandableListViewAdapter() {
            layoutInflater = LayoutInflater.from(XExpandListViewActivity.this);
        }

        @Override
        public int getGroupCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public String getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition) + "的 子节点" + childPosition;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            View v = convertView = layoutInflater.inflate(R.layout.vw_list_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.list_item_text);
            textView.setPadding(textView.getPaddingLeft() + 30, textView.getPaddingTop(), textView.getPaddingRight(), textView.getPaddingBottom());
            textView.setText(getGroup(groupPosition));
            convertView = v;

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View v = convertView = layoutInflater.inflate(R.layout.vw_list_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.list_item_text);
            textView.setPadding(textView.getPaddingLeft() + 60, textView.getPaddingTop(), textView.getPaddingRight(), textView.getPaddingBottom());
            textView.setText(getChild(groupPosition, childPosition));
            convertView = v;
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        if (hasFocus) {
//            mListView.autoRefresh();
//        }
//    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIndex = ++mRefreshIndex;
                items.clear();
                geneItems();
                mAdapter = new MyExpandableListViewAdapter();
                mListView.setAdapter(mAdapter);
                onLoadComplete();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoadComplete();
            }
        }, 2500);
    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("Test XListView item " + (++mIndex));
        }
    }

    private void onLoadComplete() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
