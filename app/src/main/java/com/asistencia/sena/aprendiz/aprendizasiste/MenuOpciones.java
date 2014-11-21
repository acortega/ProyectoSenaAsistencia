package com.asistencia.sena.aprendiz.aprendizasiste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asistencia.sena.aprendiz.network.Constantes;
import com.asistencia.sena.aprendiz.network.ServerConnection;
import com.asistencia.sena.aprendiz.pojos.FichaPojo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.concurrent.ExecutionException;


public class MenuOpciones extends Activity {
    ServerConnection connection;
    String[] datos;
    String paramsFichas[]={Constantes.METHOD_NAME_LISTADO_FICHAS};
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opciones);
        progressBar=(ProgressBar)findViewById(R.id.progress);

    }

    public void crearQr(View v){

        goForm();

    }

    public void goForm(){

       connection=new ServerConnection(progressBar);
        obtenerDatos();
        Intent goForm=new Intent(this,FormularioAprendiz.class);

        startActivity(goForm);
        finish();
    }
    public void obtenerDatos(){
        try {
            Object object= connection.execute(paramsFichas).get();

            if(object!=null) {

                FichaPojo listaFichas[] = (FichaPojo[]) object;
                datos = new String[listaFichas.length];
                int i = 0;
                for (FichaPojo dato : listaFichas) {
                    datos[i] = "" + dato.fi_codigo;
                    i++;
                }
            }else{
                datos= new String[]{"464946", "119811"};
            }

            Constantes.fichas=new String[datos.length];
            Constantes.fichas=datos;
            Log.e("cantidad fichas:","cantidad fichas: "+datos.length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void VerQR(View v){
        SharedPreferences prefs =
                getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);

        String id = prefs.getString(Constantes.ID, "null");
        String nombre = prefs.getString(Constantes.NOMBRE,"null");
        String ficha = prefs.getString(Constantes.FICHA,"null");
        String tipoId=prefs.getString(Constantes.TIPO_ID,"null");

        if(id.equals("null")){
            Toast.makeText(getApplicationContext(),"No hay QR creados",Toast.LENGTH_SHORT).show();
            goForm();
        }else{
            new IntentIntegrator(this).shareText(Constantes.FICHA+":"+ficha+Constantes.SEPARATOR
                    +Constantes.TIPO_ID+":"+tipoId+Constantes.SEPARATOR
                    +Constantes.ID+":"+id+Constantes.SEPARATOR
                    +Constantes.NOMBRE+":"+nombre);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
