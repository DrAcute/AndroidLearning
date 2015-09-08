import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DrAcute on 2015/8/11.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;
    private boolean includeEdge;

    public SpacingItemDecoration(int spacing, boolean includeEdge) {
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            int position = parent.getChildAdapterPosition(view);
            int column = lookup.getSpanIndex(position, spanCount);
            int row = lookup.getSpanGroupIndex(position, spanCount);
            boolean isFirstColumn = column == 0;
            boolean isLastColumn = row != lookup.getSpanGroupIndex(position + 1, spanCount);

            if (includeEdge) {
                outRect.left = isFirstColumn ? spacing : (spacing / 2);
                outRect.right = isLastColumn ? spacing : (spacing / 2);

                if (row == 0) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = isFirstColumn ? 0 : (spacing / 2);
                outRect.right = isLastColumn ? 0 : (spacing / 2);
                if (row > 0) {
                    outRect.top = spacing;
                }
            }
            return;
        }

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
            boolean isFirstRow = manager.getPosition(view) == 0;
            boolean isVertical = manager.getOrientation() == LinearLayoutManager.VERTICAL;
            if (isVertical) {
                if (includeEdge) {
                    outRect.left = spacing;
                    outRect.right = spacing;
                    if (isFirstRow) {
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing;
                } else {
                    if (!isFirstRow) {
                        outRect.top = spacing;
                    }
                }

            } else {
                if (includeEdge) {
                    outRect.top = spacing;
                    outRect.bottom = spacing;
                    if (isFirstRow) {
                        outRect.left = spacing;
                    }
                    outRect.right = spacing;
                } else {
                    if (!isFirstRow) {
                        outRect.left = spacing;
                    }
                }

            }
        }
    }
}
