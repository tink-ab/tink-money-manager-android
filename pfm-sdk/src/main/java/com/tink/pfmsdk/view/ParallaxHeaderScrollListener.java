package com.tink.pfmsdk.view;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import java.util.List;

public class ParallaxHeaderScrollListener extends OnScrollListener {

    private List<View> headers;
    private float headerHeight;
    private int scrolledY;

    public ParallaxHeaderScrollListener(List<View> headers, float headerHeight) {
        this.headers = headers;

        this.headerHeight = headerHeight;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy == 0) { // Layout event happened, recalculate
            scrolledY = recyclerView.computeVerticalScrollOffset();
        }

        scrolledY += dy;
        float alpha = ((headerHeight - scrolledY * 1.66f) / (headerHeight));
        int topMargin = -scrolledY / 8;

        for (View header : headers) {
            header.setTranslationY(topMargin);
            header.setAlpha(alpha);
        }
    }
}
