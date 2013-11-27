package com.enfasis.sistemagestionriesgo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuPpalAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;
	
	
	public MenuPpalAdapter(Context context, ArrayList<String> values) {
		super(context, R.layout.row_layout_adapter_menu, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout_adapter_menu, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.);
		textView.setText(values.get(position));
		if (position == values.size() - 1) {
			if ((values.get(position)).equals("Estado: Bajo")) {
				textView.setBackgroundColor(Color.GREEN);
			} else if ((values.get(position)).equals("Estado: Medio")) {
				textView.setBackgroundColor(Color.YELLOW);
			} else if ((values.get(position)).equals("Estado: Alto")) {
				textView.setBackgroundColor(Color.RED);
			}

		}

		return rowView;
	}


}
