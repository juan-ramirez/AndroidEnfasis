package com.enfasis.sistemagestionriesgo;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CrearActivity extends Activity {

	private TextView txtvNumeroRiesgo;
	private EditText etxtAgenteAmenaza;
	private EditText etxtAmenaza;
	private EditText etxtVulnerabilidad;
	private Spinner spConfidencialidad;
	private Spinner spIntegridad;
	private Spinner spDisponibilidad;
	private Spinner spAccesibilidad;
	private Spinner spHabilidad;
	private Spinner spRecursos;
	private Spinner spMotivacion;
	private ImageButton imgbtnCrear;

	private int numeroRiesgo;
	private String agenteAmenaza;
	private String amenaza;
	private String vulnerabilidad;
	private int confidencialidad;
	private int integridad;
	private int disponibilidad;
	private int accesibilidad;
	private int habilidad;
	private int recursos;
	private int motivacion;

	private boolean isModificar;

	ArrayAdapter<String> spinnerArrayAdapter;

	ArrayList<String> spinnerArray = new ArrayList<String>();

	private static String DB_NAME = "db_riesgos.s3db";
	SQLiteDatabase database;

	Cursor generalCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crear);

		obtenerProcedencia();
		inicializarObjetos();
		mostrarNumeroRiesgo();
		asignarAdapterSpinners();
		if (isModificar) {
			imgbtnCrear.setImageResource(R.drawable.modificar_mini);
			cargarDatosPrevios();
		}

	}

	private void obtenerProcedencia() {
		isModificar = getIntent().getExtras().getBoolean("isModificar");
	}

	private void inicializarObjetos() {
		txtvNumeroRiesgo = (TextView) findViewById(R.id.txtvNumeroRiesgo);
		etxtAgenteAmenaza = (EditText) findViewById(R.id.etxtAgenteAmenaza);
		etxtAmenaza = (EditText) findViewById(R.id.etxtAmenaza);
		etxtVulnerabilidad = (EditText) findViewById(R.id.etxtVulnerabilidad);
		spConfidencialidad = (Spinner) findViewById(R.id.spConfidencialidad);
		spIntegridad = (Spinner) findViewById(R.id.spIntegridad);
		spDisponibilidad = (Spinner) findViewById(R.id.spDisponibilidad);
		spAccesibilidad = (Spinner) findViewById(R.id.spAccesibilidad);
		spHabilidad = (Spinner) findViewById(R.id.spHabilidad);
		spRecursos = (Spinner) findViewById(R.id.spRecursos);
		spMotivacion = (Spinner) findViewById(R.id.spMotivacion);

		imgbtnCrear = (ImageButton) findViewById(R.id.imgbtnCrear);

		database = openOrCreateDatabase(DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);

		spinnerArray.add("1");
		spinnerArray.add("2");
		spinnerArray.add("3");

		spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
	}

	private void mostrarNumeroRiesgo() {
		numeroRiesgo = obtenerNumero();
		String text = txtvNumeroRiesgo.getText() + " " + String.valueOf(numeroRiesgo);
		txtvNumeroRiesgo.setText(text);

	}

	private int obtenerNumero() {
		if (isModificar) {
			return Integer.parseInt(getIntent().getExtras().getString("id"));
		} else {
			Cursor c = database.rawQuery("select max(id) from riesgos", null);

			c.moveToFirst();
			String data = c.getString(0);
			if (data == null) {
				return 1;
			} else {
				return c.getInt(0) + 1;

			}
		}
	}

	private void asignarAdapterSpinners() {
		spConfidencialidad.setAdapter(spinnerArrayAdapter);
		spIntegridad.setAdapter(spinnerArrayAdapter);
		spDisponibilidad.setAdapter(spinnerArrayAdapter);
		spAccesibilidad.setAdapter(spinnerArrayAdapter);
		spHabilidad.setAdapter(spinnerArrayAdapter);
		spRecursos.setAdapter(spinnerArrayAdapter);
		spMotivacion.setAdapter(spinnerArrayAdapter);

	}

	public void crearRiesgo(View v) {
		obtenerDatos();
		if (verificarVacios()) {
			Toast.makeText(this, "Alguno de los campos está vacío, por favor ingréselo", Toast.LENGTH_SHORT).show();
		} else {
			if (isModificar) {
				actualizarDatos();
			} else {
				almacenarDatos();
			}
			finish();
		}
	}

	private boolean verificarVacios() {
		return agenteAmenaza.equals("") || amenaza.equals("") || vulnerabilidad.equals("");
	}

	private void obtenerDatos() {
		agenteAmenaza = etxtAgenteAmenaza.getText().toString();
		amenaza = etxtAmenaza.getText().toString();
		vulnerabilidad = etxtVulnerabilidad.getText().toString();
		confidencialidad = Integer.parseInt(spConfidencialidad.getSelectedItem().toString());
		integridad = Integer.parseInt(spIntegridad.getSelectedItem().toString());
		disponibilidad = Integer.parseInt(spDisponibilidad.getSelectedItem().toString());
		accesibilidad = Integer.parseInt(spAccesibilidad.getSelectedItem().toString());
		habilidad = Integer.parseInt(spHabilidad.getSelectedItem().toString());
		recursos = Integer.parseInt(spRecursos.getSelectedItem().toString());
		motivacion = Integer.parseInt(spMotivacion.getSelectedItem().toString());

	}

	private void almacenarDatos() {
		int impacto = confidencialidad + integridad + disponibilidad;
		int probabilidad = accesibilidad + habilidad + recursos + motivacion;
		int riesgoInherente = impacto * probabilidad;
		String query = "insert into riesgos (agente,amenaza,vulnerabilidad,impacto" + ",confidencialidad,integridad,disponibilidad,probabilidad,accesibilidad,habilidad,recursos,motivacion,"
				+ "riesgoinherente,riesgoresidual) values ('"
				+ agenteAmenaza
				+ "','"
				+ amenaza
				+ "','"
				+ vulnerabilidad
				+ "',"
				+ impacto
				+ ","
				+ confidencialidad
				+ ","
				+ integridad
				+ ","
				+ disponibilidad
				+ ","
				+ probabilidad
				+ ","
				+ accesibilidad + "," + habilidad + "," + recursos + "," + motivacion + "," + riesgoInherente + "," + 0 + ")";

		database.execSQL(query);

	}

	private void cargarDatosPrevios() {
		String query = "select * from riesgos where id =" + numeroRiesgo;
		generalCursor = database.rawQuery(query, null);
		generalCursor.moveToFirst();
		mostrarDatosPrevios();

	}

	private void mostrarDatosPrevios() {
		etxtAgenteAmenaza.setText(generalCursor.getString(1));
		etxtAmenaza.setText(generalCursor.getString(2));
		etxtVulnerabilidad.setText(generalCursor.getString(3));
		spConfidencialidad.setSelection(generalCursor.getInt(5) - 1);
		spIntegridad.setSelection(generalCursor.getInt(6) - 1);
		spDisponibilidad.setSelection(generalCursor.getInt(7) - 1);
		spAccesibilidad.setSelection(generalCursor.getInt(9) - 1);
		spHabilidad.setSelection(generalCursor.getInt(10) - 1);
		spRecursos.setSelection(generalCursor.getInt(11) - 1);
		spMotivacion.setSelection(generalCursor.getInt(12) - 1);
	}

	private void actualizarDatos() {
		int impacto = confidencialidad + integridad + disponibilidad;
		int probabilidad = accesibilidad + habilidad + recursos + motivacion;
		int riesgoInherente = impacto * probabilidad;

		String query = "update riesgos set agente = '" + agenteAmenaza + "', amenaza = '" + amenaza + "', vulnerabilidad = '" + vulnerabilidad + "', impacto = " + impacto + ", confidencialidad = "
				+ confidencialidad + ", integridad = " + integridad + ", disponibilidad = " + disponibilidad + ", probabilidad = " + probabilidad + ", accesibilidad = " + accesibilidad
				+ ", habilidad = " + habilidad + ", recursos = " + recursos + ", motivacion = " + motivacion + ", riesgoinherente = " + riesgoInherente + " where id = " + numeroRiesgo;
		database.execSQL(query);
	}

	@Override
	protected void onStop() {
		database.close();
		if (generalCursor != null) {
			generalCursor.close();
		}
		super.onStop();
	}

}
