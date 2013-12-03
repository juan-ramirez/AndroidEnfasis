package com.enfasis.sistemagestionriesgo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuPpalAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater =null;
    public ImageSwitcher imageSwitcher; 
 
    public MenuPpalAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageSwitcher = new ImageSwitcher(activity.getApplicationContext());
    }
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = inflater.inflate(R.layout.row_layout_adapter_menu, parent, false);
		TextView textViewOpcion = (TextView) rowView.findViewById(R.id.txtViewMenuPpalOpcion);
		TextView textViewDescripcion = (TextView) rowView.findViewById(R.id.txtViewMenuPpalDescripcion);
		ImageView icon = (ImageView) rowView.findViewById(R.id.imgListViewPpal);
		
		HashMap<String, String> opcion = new HashMap<String, String>();
		opcion = data.get(position);
		
		// Setting all values in listview
		textViewOpcion.setText(opcion.get(MainActivity.));
		textViewDescripcion.setText(opcion.get(MainActivity.));
        imageSwitcher.DisplayImage(opcion.get(MainActivity.), icon);       
		
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
