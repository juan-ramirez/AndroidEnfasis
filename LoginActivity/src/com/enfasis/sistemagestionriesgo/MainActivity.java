package com.enfasis.sistemagestionriesgo;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	SQLiteDatabase database;
    private static String DB_NAME = "db_riesgos.s3db";

	Intent generalIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		inicializarObjetos();
		inicializarDatabase();
		crearMenu();

	}
	
	private void inicializarDatabase(){
		DataBaseHelper dbHelper = new DataBaseHelper(this);

		try {

			dbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			dbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
		
		database = dbHelper.myDataBase;

	}

	private void inicializarObjetos(){
       database = openOrCreateDatabase(DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);
	}
	
	private void crearMenu() {
		String[] opcion1 = { "Crear", "Creacion de formatos", "crear" };
		String[] opcion2 = { "Modificar", "Creacion de formatos", "modificar" };
		String[] opcion3 = { "Consultar", "Creacion de formatos", "consultar" };
		String[] opcion4 = { "Control", "Creacion de formatos", "control" };
		String[] opcion5 = { "Grafica", "Creacion de formatos", "grafico" };
		ArrayList<String[]> arrayListMenu = new ArrayList<String[]>();
		arrayListMenu.add(opcion1);
		arrayListMenu.add(opcion2);
		arrayListMenu.add(opcion3);
		arrayListMenu.add(opcion4);
		arrayListMenu.add(opcion5);
		ListView listViewMenu = (ListView) findViewById(R.id.listViewMenuPpal);
		MenuPpalAdapter adapter = new MenuPpalAdapter(this, arrayListMenu);
		listViewMenu.setAdapter(adapter);
		
		listViewMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					goCrear();
					break;
				case 1:
					goModificar();
					break;
				case 2:
					goConsultar();
					break;
				case 3:
					goControl();
					break;
				case 4:
					goGrafico();
					break;

					
				default:
					break;
				}
				
			}
		});

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

		final View layout_dialogo = inflater.inflate(
				R.layout.dialog_layout_consultar, null);

		builder.setView(layout_dialogo);

		builder.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				EditText editDialogo = (EditText) layout_dialogo
						.findViewById(R.id.etxtBusqueda);
				String id = editDialogo.getText().toString();
				if (esConsulta(id)) {
					generalIntent = new Intent(getApplicationContext(),
							CrearActivity.class);
					generalIntent.putExtra("isModificar", true);
					generalIntent.putExtra("id", id);
					startActivity(generalIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							"La consulta no arrojó ningún resultado",
							Toast.LENGTH_SHORT).show();
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

		final View layout_dialogo = inflater.inflate(
				R.layout.dialog_layout_consultar, null);

		builder.setView(layout_dialogo);

		builder.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				EditText editDialogo = (EditText) layout_dialogo
						.findViewById(R.id.etxtBusqueda);
				String id = editDialogo.getText().toString();
				if (esConsulta(id)) {
					generalIntent = new Intent(getApplicationContext(),
							ConsultarActivity.class);
					generalIntent.putExtra("id", id);
					startActivity(generalIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							"La consulta no arrojó ningún resultado",
							Toast.LENGTH_SHORT).show();
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

		final View layout_dialogo = inflater.inflate(
				R.layout.dialog_layout_consultar, null);

		builder.setView(layout_dialogo);

		builder.setPositiveButton("Aceptar", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				EditText editDialogo = (EditText) layout_dialogo
						.findViewById(R.id.etxtBusqueda);
				String id = editDialogo.getText().toString();
				if (esConsulta(id)) {
					generalIntent = new Intent(getApplicationContext(),
							ControlActivity.class);
					generalIntent.putExtra("id", id);
					startActivity(generalIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							"La consulta no arrojó ningún resultado",
							Toast.LENGTH_SHORT).show();
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
		Cursor c = database.rawQuery("select * from riesgos where id = " + id,
				null);

		c.moveToFirst();
		if (c.getCount() > 0) {
			return true;
		} else
			return false;
	}

}
