package br.com.glima.popularmovies.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.glima.popularmovies.BR;
import br.com.glima.popularmovies.business.Video;
import br.com.glima.popularmovies.databinding.VideoLayoutBinding;

/**
 * Created by gustavo.lima on 22/01/18.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoHolder> {

	private List<Video> videos = new ArrayList<>();
	private OnVideoClickListener listener;

	public VideosAdapter(List<Video> videos, OnVideoClickListener listener) {
		this.listener = listener;
		this.videos.addAll(videos);
	}

	@Override
	public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new VideosAdapter.VideoHolder(VideoLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(VideoHolder holder, int position) {
		holder.bind(videos.get(position));

	}

	@Override
	public int getItemCount() {
		return videos.size();
	}

	public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		VideoLayoutBinding binding;

		VideoHolder(VideoLayoutBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
			itemView.setOnClickListener(this);
		}

		void bind(Video video) {

			binding.setVariable(BR.video, video);
		}

		@Override
		public void onClick(View view) {
			listener.onVideoClicked(binding.getVideo().getKey());
		}
	}
}
