package com.enfasis.sistemagestionriesgo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText etxtUsuario;
	private EditText etxtPassword;
	private String usuario;
	private String password;
	private String perfil;

	private static String DB_PATH = "/data/data/com.enfasis.sistemagestionriesgo/databases/";
	private static String DB_NAME = "db_riesgos.s3db";
	SQLiteDatabase database;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

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

		inicializarObjetos();

	}

	private void inicializarObjetos() {
		etxtUsuario = (EditText) findViewById(R.id.etxtUsuario);
		etxtPassword = (EditText) findViewById(R.id.etxtPassword);
	}

	private void validarUsuario() {
		verificarVacios();
		obtenerCredenciales();
		if (validarCredenciales()) {
			Intent generalIntent = new Intent(getApplicationContext(), MainActivity.class);
			generalIntent.putExtra("perfil", perfil);
			startActivity(generalIntent);
			database.close();
			finish();
		} else {
			etxtUsuario.setText("");
			etxtPassword.setText("");
			Toast.makeText(this, "Usuario o contraseña erróneos", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean verificarVacios() {
		return etxtUsuario.equals("") || etxtPassword.equals("");
	}

	private void obtenerCredenciales() {
		usuario = etxtUsuario.getText().toString();
		password = etxtPassword.getText().toString();
	}

	private boolean validarCredenciales() {
		String query = "select * from usuarios where nombre = '" + usuario + "' and password = '" + password + "'";
		Cursor c = database.rawQuery(query, null);
		c.moveToFirst();
		if (c.getCount() > 0) {
			perfil = c.getString(c.getColumnIndex("perfil"));
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public void doLogin(View v) {
		validarUsuario();
	}

}
