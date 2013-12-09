package com.enfasis.sistemagestionriesgo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuPpalAdapter extends ArrayAdapter<String[]> {

	private Context context;
	private ArrayList<String[]> values;

	public MenuPpalAdapter(Context context, ArrayList<String[]> values) {
		super(context, R.layout.row_layout_adapter_menu, values);
		this.values = values;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout_adapter_menu,
				parent, false);

		ImageView icon = (ImageView) rowView.findViewById(R.id.imgListViewPpal);
		

		TextView textViewDescripcion = (TextView) rowView
				.findViewById(R.id.txtViewMenuPpalDescripcion);
		
		TextView textViewOpcion = (TextView) (rowView.findViewWithTag("opcion"));
		
		

		String[] data = values.get(position);
		Log.e("DATO: ", String.valueOf(textViewOpcion == null));

		// Setting all values in listview
		textViewOpcion.setText(data[0]);
		textViewDescripcion.setText(data[1]);
		int id = context.getResources().getIdentifier(data[2], "drawable",
				context.getPackageName());
		Log.e("DATO: ", String.valueOf(data[2]));
		Drawable drawable = context.getResources().getDrawable(id);
		icon.setImageDrawable(drawable);

		return rowView;
	}

}
