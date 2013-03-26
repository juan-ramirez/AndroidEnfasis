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
import android.widget.Toast;

public class MainActivity extends Activity {

	private String perfil;

	SQLiteDatabase database;
	private static String DB_NAME = "db_riesgos.s3db";

	private PicAdapter imgAdapt;
	private Gallery picGallery;
	private Bitmap[] imageBitmaps;
	private Resources resources;

	Intent generalIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inicializarObjetos();
		crearAdapter();
		asignarAdapterALaGaleria();
		asignarOnItemClickGaleria();

	}

	private void inicializarObjetos() {

		perfil = getIntent().getExtras().getString("perfil");
		picGallery = (Gallery) findViewById(R.id.gallery);
		resources = getResources();
		llenarVectorGaleria();
		database = openOrCreateDatabase(DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);

	}

	private void llenarVectorGaleria() {
		if (perfil.equals("consultor")) {
			imageBitmaps = new Bitmap[2];
			imageBitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.consultar);
			imageBitmaps[1] = BitmapFactory.decodeResource(resources, R.drawable.grafico);
		} else if (perfil.equals("creador")) {
			imageBitmaps = new Bitmap[4];
			imageBitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.crear);
			imageBitmaps[1] = BitmapFactory.decodeResource(resources, R.drawable.modificar);
			imageBitmaps[2] = BitmapFactory.decodeResource(resources, R.drawable.consultar);
			imageBitmaps[3] = BitmapFactory.decodeResource(resources, R.drawable.grafico);
		} else if (perfil.equals("control")) {
			imageBitmaps = new Bitmap[3];
			imageBitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.control);
			imageBitmaps[1] = BitmapFactory.decodeResource(resources, R.drawable.consultar);
			imageBitmaps[2] = BitmapFactory.decodeResource(resources, R.drawable.grafico);
		} else if (perfil.equals("administrador")) {
			imageBitmaps = new Bitmap[5];
			imageBitmaps[0] = BitmapFactory.decodeResource(resources, R.drawable.crear);
			imageBitmaps[1] = BitmapFactory.decodeResource(resources, R.drawable.modificar);
			imageBitmaps[2] = BitmapFactory.decodeResource(resources, R.drawable.consultar);
			imageBitmaps[3] = BitmapFactory.decodeResource(resources, R.drawable.control);
			imageBitmaps[4] = BitmapFactory.decodeResource(resources, R.drawable.grafico);
		}

	}

	private void crearAdapter() {
		imgAdapt = new PicAdapter(this, imageBitmaps);
	}

	private void asignarAdapterALaGaleria() {
		picGallery.setAdapter(imgAdapt);
	}

	private void asignarOnItemClickGaleria() {
		picGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (perfil.equals("consultor")) {
					switch (arg2) {
					case 0:
						goConsultar();
						break;
					case 1:
						goGrafico();
						break;
					default:
						break;
					}
				} else if (perfil.equals("creador")) {
					switch (arg2) {
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
						goGrafico();
						break;
					default:
						break;
					}
				} else if (perfil.equals("control")) {
					switch (arg2) {
					case 0:
						goControl();
					case 1:
						goConsultar();
						break;
					case 2:
						goGrafico();
						break;
					default:
						break;
					}
				} else if (perfil.equals("administrador")) {
					switch (arg2) {
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
			}
		});
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
