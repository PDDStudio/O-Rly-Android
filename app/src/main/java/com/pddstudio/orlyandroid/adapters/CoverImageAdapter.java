package com.pddstudio.orlyandroid.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pddstudio.orly.book.generator.utils.BookUtils;
import com.pddstudio.orlyandroid.R;

import java.util.List;

/**
 * Created by pddstudio on 15/08/16.
 */
public class CoverImageAdapter extends RecyclerView.Adapter<CoverImageAdapter.ViewHolder> {

	private final List<Bitmap>    itemData;
	private final OnClickListener onClickListener;

	public interface OnClickListener {
		void onItemSelected(int itemPosition);
	}

	public CoverImageAdapter(Context context, OnClickListener onClickListener) {
		this.itemData = BookUtils.getImageResources(context);
		this.onClickListener = onClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Bitmap img = itemData.get(position);
		holder.imageView.setImageBitmap(img);
	}

	@Override
	public int getItemCount() {
		return itemData.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private ImageView imageView;

		public ViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.item_image);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			onClickListener.onItemSelected(getAdapterPosition());
		}
	}
}
