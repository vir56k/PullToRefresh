PullToRefresh
===========

*注：本项目使用Android Studio开发*

### 根据MarkMjw的PullToRefresh改造: ###
    1.减去了刷新时间的显示
    2.增加了XExpandableListView支持。
    3.触底加载，拖动到底部时加载更多数据

原PullToRefresh参考链接：https://github.com/MarkMjw/PullToRefresh

## **XListView使用示例** ##

设置XListView相关属性<br>
```java
mListView = (XListView) findViewById(R.id.list_view);
mListView.setPullRefreshEnable(true);
mListView.setPullLoadEnable(true);
mListView.setAutoLoadEnable(true);
mListView.setXListViewListener(this);
mListView.setRefreshTime(getTime());

mAdapter = new ArrayAdapter<String>(this, R.layout.vw_list_item, items);
mListView.setAdapter(mAdapter);
```

下拉刷新，上拉加载更多使用示例<br>
```java
@Override
public void onRefresh() {
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            mIndex = ++mRefreshIndex;
            items.clear();
            geneItems();
            mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.vw_list_item,
                    items);
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

