package zhangyf.vir56k.pulltorefresh;

/**
 * Implements this interface to get refresh/load more event.
 *
 * @author markmjw
 */
public interface IXListViewListener {
    public void onRefresh();

    public void onLoadMore();
}