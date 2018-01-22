package br.com.glima.popularmovies.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.BR;
import br.com.glima.popularmovies.business.Review;
import br.com.glima.popularmovies.databinding.ReviewLayoutBinding;

/**
 * Created by gustavo.lima on 21/01/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

	private List<Review> reviews = new ArrayList<>();

	public ReviewAdapter(List<Review> reviews) {
		this.reviews.addAll(reviews);
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new Holder(ReviewLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}
	@Override
	public void onBindViewHolder(Holder holder, int position) {
		holder.bind(reviews.get(position));
	}
	@Override
	public int getItemCount() {
		return reviews.size();
	}

	class Holder extends RecyclerView.ViewHolder {
		ReviewLayoutBinding binding;

		Holder(ReviewLayoutBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		public void bind(Review review) {
			binding.setVariable(BR.review, review);
			binding.executePendingBindings();
		}
	}
}
