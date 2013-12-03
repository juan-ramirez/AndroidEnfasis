package com.enfasis.sistemagestionriesgo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuPpalAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<String[]> values;
    private static LayoutInflater inflater =null;
    public ImageSwitcher imageSwitcher; 
 
    public MenuPpalAdapter(Context context, ArrayList<String[]> values) {
    	this.values = values;
    	this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageSwitcher = new ImageSwitcher(context);
    }
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = inflater.inflate(R.layout.row_layout_adapter_menu, parent, false);
		TextView textViewOpcion = (TextView) rowView.findViewById(R.id.txtViewMenuPpalOpcion);
		TextView textViewDescripcion = (TextView) rowView.findViewById(R.id.txtViewMenuPpalDescripcion);
		ImageView icon = (ImageView) rowView.findViewById(R.id.imgListViewPpal);
		
		String[] data = values.get(position);
		
		// Setting all values in listview
		textViewOpcion.setText(data[0]);
		textViewDescripcion.setText(data[1]);
		int id = context.getResources().getIdentifier(data[2], "drawable", context.getPackageName());
		Drawable drawable = context.getResources().getDrawable(id);
		icon.setImageDrawable(drawable);  
		
		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


}
