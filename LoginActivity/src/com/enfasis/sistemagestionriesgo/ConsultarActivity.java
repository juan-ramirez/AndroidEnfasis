package com.enfasis.sistemagestionriesgo;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ConsultarActivity extends Activity {

	private static String DB_NAME = "db_riesgos.s3db";
	private SQLiteDatabase database;
	private Cursor cursorDataBase;

	private String id;
	private ListView lvResultadosConsulta;
	private ArrayList<String> arrayResultados;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar);

		inicializarObjetos();
		realizarQuery();
		mostrarResultados();

	}

	private void inicializarObjetos() {
		arrayResultados = new ArrayList<String>();
		database = openOrCreateDatabase(DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);
		id = getIntent().getExtras().getString("id");
		lvResultadosConsulta = (ListView) findViewById(R.id.lvResultadosConsulta);
	}

	private void realizarQuery() {
		String query = "select * from riesgos where id =" + id;
		cursorDataBase = database.rawQuery(query, null);
		cursorDataBase.moveToFirst();
	}

	private void mostrarResultados() {
		llenarArrayList();
		asignarAdapterListView();
	}

	private void llenarArrayList() {
		arrayResultados.add("Número del riesgo: " + cursorDataBase.getString(0));
		arrayResultados.add("Agente amenaza: " + cursorDataBase.getString(1));
		arrayResultados.add("Amenaza: " + cursorDataBase.getString(2));
		arrayResultados.add("Vulnerabilidad: " + cursorDataBase.getString(3));
		arrayResultados.add("Impacto: " + cursorDataBase.getString(4));
		arrayResultados.add("Confiabilidad: " + cursorDataBase.getString(5));
		arrayResultados.add("Integridad: " + cursorDataBase.getString(6));
		arrayResultados.add("Disponibilidad: " + cursorDataBase.getString(7));
		arrayResultados.add("Probabilidad: " + cursorDataBase.getString(8));
		arrayResultados.add("Accesibilidad: " + cursorDataBase.getString(9));
		arrayResultados.add("Habilidad: " + cursorDataBase.getString(10));
		arrayResultados.add("Recursos: " + cursorDataBase.getString(11));
		arrayResultados.add("Motivación: " + cursorDataBase.getString(12));
		arrayResultados.add("Riesgo inherente: " + cursorDataBase.getString(13));
		arrayResultados.add("Riesgo residual: " + cursorDataBase.getString(14));
		arrayResultados.add("efectividad: " + cursorDataBase.getString(15));
		arrayResultados.add("Control: " + cursorDataBase.getString(16));
		arrayResultados.add("Descripción: " + cursorDataBase.getString(17));
		arrayResultados.add("Estado: " + getEstado());
	}

	private String getEstado() {
		int estado = Integer.parseInt(cursorDataBase.getString(13));
		if (estado >= 0 && estado <= 40) {
			return "Bajo";
		} else if (estado > 40 && estado <= 80) {
			return "Medio";
		}
		if (estado > 80) {
			return "Alto";
		}
		return "ninguno";
	}

	private void asignarAdapterListView() {
		ArrayAdapter<String> adapter = new ConsultaAdapter(this, arrayResultados);
		lvResultadosConsulta.setAdapter(adapter);
	}

	@Override
	protected void onStop() {
		database.close();
		cursorDataBase.close();
		super.onStop();
	}

}
