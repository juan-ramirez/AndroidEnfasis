package com.enfasis.sistemagestionriesgo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class PicAdapter extends BaseAdapter {

	int defaultItemBackground;
	private Context galleryContext;
	private Bitmap[] imageBitmaps;

	public PicAdapter(Context galleryContext, Bitmap[] imageBitmaps) {
		this.galleryContext = galleryContext;
		this.imageBitmaps = imageBitmaps;
		TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
		defaultItemBackground = styleAttrs.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0);
		styleAttrs.recycle();
	}

	// return number of data items i.e. bitmap images
	public int getCount() {
		return imageBitmaps.length;
	}

	// return item at specified position
	public Object getItem(int position) {
		return position;
	}

	// return item ID at specified position
	public long getItemId(int position) {
		return position;
	}

	// get view specifies layout and display options for each thumbnail in the
	// gallery
	public View getView(int position, View convertView, ViewGroup parent) {

		// create the view
		ImageView imageView = new ImageView(galleryContext);
		// specify the bitmap at this position in the array
		imageView.setImageBitmap(imageBitmaps[position]);
		// set layout options
		imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
		// scale type within view area
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		// set default gallery item background
		imageView.setBackgroundResource(defaultItemBackground);
		// return the view
		return imageView;
	}

}
