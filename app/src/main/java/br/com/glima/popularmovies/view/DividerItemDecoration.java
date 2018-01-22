package br.com.glima.popularmovies.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.glima.popularmovies.R;

/**
 * Created by gustavo.lima on 22/01/18.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

	private final Context mContext;

	public DividerItemDecoration(Context context) {
		this.mContext = context;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.bottom = mContext.getResources().getDimensionPixelSize(R.dimen.list_default_separator);
		outRect.left = mContext.getResources().getDimensionPixelSize(R.dimen.list_default_separator);
		outRect.right = mContext.getResources().getDimensionPixelSize(R.dimen.list_default_separator);
		outRect.top = mContext.getResources().getDimensionPixelSize(R.dimen.list_default_separator);
	}
}

