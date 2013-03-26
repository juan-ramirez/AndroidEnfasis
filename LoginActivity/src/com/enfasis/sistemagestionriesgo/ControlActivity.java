package com.enfasis.sistemagestionriesgo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ControlActivity extends Activity {

	private EditText etxtEfectividadControl;
	private EditText etxtDescripcion;
	private TextView txtvImpacto;
	private TextView txtvProbabilidad;
	private TextView txtvRiesgo;
	private TextView txtvControl;
	private TextView txtvRiesgoResidual;

	private static String DB_NAME = "db_riesgos.s3db";
	SQLiteDatabase database;

	private Cursor generalCursor;
	private String id;

	private String efectividad;
	private String descripcion;
	private String impacto;
	private String probabilidad;
	private String riesgo;
	private String control;
	private String riesgo_residual;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		inicializarObjetos();
		queryRiesgo();
		obtenerDatosPrevios();
		mostrarDatosPrevios();
	}

	private void inicializarObjetos() {
		database = openOrCreateDatabase(DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);
		id = getIntent().getExtras().getString("id");

		etxtEfectividadControl = (EditText) findViewById(R.id.etxtEfectividadControl);
		etxtDescripcion = (EditText) findViewById(R.id.etxtDescripcion);
		txtvImpacto = (TextView) findViewById(R.id.txtvImpacto);
		txtvProbabilidad = (TextView) findViewById(R.id.txtvProbabilidad);
		txtvRiesgo = (TextView) findViewById(R.id.txtvRiesgo);
		txtvControl = (TextView) findViewById(R.id.txtvControl);
		txtvRiesgoResidual = (TextView) findViewById(R.id.txtvRiesgoResidual);
	}

	private void queryRiesgo() {
		String query = "select * from riesgos where id =" + id;
		generalCursor = database.rawQuery(query, null);
		generalCursor.moveToFirst();

	}

	private void obtenerDatosPrevios() {
		efectividad = generalCursor.getString(generalCursor.getColumnIndex("efectividad"));
		descripcion = generalCursor.getString(generalCursor.getColumnIndex("descripcion"));
		impacto = generalCursor.getString(generalCursor.getColumnIndex("impacto"));
		probabilidad = generalCursor.getString(generalCursor.getColumnIndex("probabilidad"));
		riesgo = generalCursor.getString(generalCursor.getColumnIndex("riesgoinherente"));
		control = generalCursor.getString(generalCursor.getColumnIndex("control"));
		riesgo_residual = generalCursor.getString(generalCursor.getColumnIndex("riesgoresidual"));
	}

	private void mostrarDatosPrevios() {
		txtvImpacto.setText(txtvImpacto.getText() + " " + impacto);
		txtvProbabilidad.setText(txtvProbabilidad.getText() + " " + probabilidad);
		txtvRiesgo.setText(txtvRiesgo.getText() + " " + riesgo);		
		double riesgoResidual = Double.parseDouble(riesgo) - Double.parseDouble(efectividad);
		double control = 100 - ((riesgoResidual / Double.valueOf(riesgo)) * 100);
		txtvControl.setText(txtvControl.getText() + " " + control + "%");
		txtvRiesgoResidual.setText(txtvRiesgoResidual.getText() + " " + riesgo_residual);
		etxtEfectividadControl.setText(efectividad);
		etxtDescripcion.setText(descripcion);
	}

	public void controlarRiesgo(View v) {

		if (validarEfectividad()) {
			efectividad = etxtEfectividadControl.getText().toString();
			descripcion = etxtDescripcion.getText().toString();
			double riesgoResidual = Double.parseDouble(riesgo) - Double.parseDouble(efectividad);
			double control = 100 - ((riesgoResidual / Double.valueOf(riesgo)) * 100);

			String query = "update riesgos set efectividad = " + efectividad + ", control = " + control + ", descripcion = '" + descripcion + "', riesgoresidual = " + riesgoResidual + " where id = "
					+ id;
			database.execSQL(query);
			refrescarDatos(riesgoResidual, control);
		} else {
			Toast.makeText(this, "La efectividad está fuera del rango", Toast.LENGTH_SHORT).show();
		}

	}

	private boolean validarEfectividad() {
		double _efectividad = Double.parseDouble(etxtEfectividadControl.getText().toString());
		double _riesgo = Double.parseDouble(riesgo);

		if (_efectividad >= 0 && _efectividad <= _riesgo) {
			return true;
		} else {
			return false;
		}
	}

	private void refrescarDatos(double riesgoResidual, double control2) {
		this.riesgo_residual = String.valueOf(riesgoResidual);
		this.control = String.valueOf(control2);
		txtvRiesgoResidual.setText("Riesgo residual " + riesgo_residual);
		txtvControl.setText("Control " + control + "%");
	}

}
