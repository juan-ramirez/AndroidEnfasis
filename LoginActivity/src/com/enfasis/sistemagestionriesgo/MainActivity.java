package com.enfasis.sistemagestionriesgo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {


	SQLiteDatabase database;
	

	Intent generalIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inicializarObjetos();
		crearAdapter();
		

	}

	private void inicializarObjetos() {
		ListView listViewMenu =  (ListView) findViewById(R.id.listViewMenuPpal);
		

	}

	

	private void crearAdapter() {
		
		
		
		
	}

	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void goCrear() {
		generalIntent = new Intent(this, CrearActivity.class);
		generalIntent.putExtra("isModificar", false);
		startActivity(generalIntent);
	}

	public void goModificar() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Consultar");

		LayoutInflater inflater = this.getLayoutInflater();

		final View layout_dialogo = inflater.inflate(R.layout.dialog_layout_consultar, null);

		builder.setView(layout_dialogo);

		builder.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				EditText editDialogo = (EditText) layout_dialogo.findViewById(R.id.etxtBusqueda);
				String id = editDialogo.getText().toString();
				if (esConsulta(id)) {
					generalIntent = new Intent(getApplicationContext(), CrearActivity.class);
					generalIntent.putExtra("isModificar", true);
					generalIntent.putExtra("id", id);
					startActivity(generalIntent);
				} else {
					Toast.makeText(getApplicationContext(), "La consulta no arrojó ningún resultado", Toast.LENGTH_SHORT).show();
				}

			}
		});

		builder.setNegativeButton("Cancelar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog dialog = builder.create();

		dialog.show();
	}

	protected void goConsultar() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Consultar");

		LayoutInflater inflater = this.getLayoutInflater();

		final View layout_dialogo = inflater.inflate(R.layout.dialog_layout_consultar, null);

		builder.setView(layout_dialogo);

		builder.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				EditText editDialogo = (EditText) layout_dialogo.findViewById(R.id.etxtBusqueda);
				String id = editDialogo.getText().toString();
				if (esConsulta(id)) {
					generalIntent = new Intent(getApplicationContext(), ConsultarActivity.class);
					generalIntent.putExtra("id", id);
					startActivity(generalIntent);
				} else {
					Toast.makeText(getApplicationContext(), "La consulta no arrojó ningún resultado", Toast.LENGTH_SHORT).show();
				}

			}
		});

		builder.setNegativeButton("Cancelar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog dialog = builder.create();

		dialog.show();

	}

	protected void goGrafico() {
		generalIntent = new Intent(this, GraficoActivity.class);
		startActivity(generalIntent);
	}

	protected void goControl() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Consultar");

		LayoutInflater inflater = this.getLayoutInflater();

		final View layout_dialogo = inflater.inflate(R.layout.dialog_layout_consultar, null);

		builder.setView(layout_dialogo);

		builder.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				EditText editDialogo = (EditText) layout_dialogo.findViewById(R.id.etxtBusqueda);
				String id = editDialogo.getText().toString();
				if (esConsulta(id)) {
					generalIntent = new Intent(getApplicationContext(), ControlActivity.class);
					generalIntent.putExtra("id", id);
					startActivity(generalIntent);
				} else {
					Toast.makeText(getApplicationContext(), "La consulta no arrojó ningún resultado", Toast.LENGTH_SHORT).show();
				}

			}
		});

		builder.setNegativeButton("Cancelar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog dialog = builder.create();

		dialog.show();
	}

	private boolean esConsulta(String id) {
		Cursor c = database.rawQuery("select * from riesgos where id = " + id, null);

		c.moveToFirst();
		if (c.getCount() > 0) {
			return true;
		} else
			return false;
	}

}
