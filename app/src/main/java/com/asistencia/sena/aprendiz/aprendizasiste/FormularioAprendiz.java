package com.asistencia.sena.aprendiz.aprendizasiste;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.asistencia.sena.aprendiz.network.Constantes;
import com.asistencia.sena.aprendiz.network.ServerConnection;
import com.asistencia.sena.aprendiz.pojos.FichaPojo;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.concurrent.ExecutionException;


public class FormularioAprendiz extends Activity {

    String nombre,ficha,id,tipoId;
    Spinner spFichas,spTipoId;
    ServerConnection connection;
    EditText etId;
    ProgressBar progressBar;
    boolean flagValidacion=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aprendiz);
        spFichas=(Spinner)findViewById(R.id.spFichas);
        spTipoId=(Spinner)findViewById(R.id.spTipoId);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constantes.fichas);
        spFichas.setAdapter(adapter);
        etId=(EditText)findViewById(R.id.etId);
        spFichas.setOnItemSelectedListener(spListener);
        spTipoId.setOnItemSelectedListener(spListener);
        progressBar=(ProgressBar)findViewById(R.id.progressForm);
        progressBar.setVisibility(View.INVISIBLE);

    }


AdapterView.OnItemSelectedListener spListener=new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv=(TextView)view;
        switch (adapterView.getId()){
            case R.id.spFichas:
                ficha=tv.getText().toString();
                break;
            case R.id.spTipoId:
                tipoId=tv.getText().toString();
                if(tipoId.equalsIgnoreCase("pasaporte")){
                    etId.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    etId.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
};

    public void crearQr(View v){
        id=etId.getText().toString();
        String msgAlert="";
        if(validarInformacion()) {
            SharedPreferences prefs =
                    getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constantes.NOMBRE, nombre);
            editor.putString(Constantes.FICHA, ficha);
            editor.putString(Constantes.ID, id);
            editor.putString(Constantes.TIPO_ID, tipoId);
            editor.commit();

            msgAlert=nombre+"\nAprendiz Valido!!!";
            flagValidacion=true;
        }else{
            msgAlert="Aprendiz no valido, verifique por favor los datos ingresados";
        }

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("VALIDACION DE APRENDIZ");
        alert.setMessage(msgAlert);
        alert.setPositiveButton("ACEPTAR",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                     if(flagValidacion){
                         new IntentIntegrator(FormularioAprendiz.this).shareText("ficha:" + ficha + Constantes.SEPARATOR
                                 + "tipoId:" + tipoId + Constantes.SEPARATOR
                                 + "Id:" + id + Constantes.SEPARATOR
                                 + "Nombre:" + nombre);
                     }
            }
        });
        alert.show();
        progressBar.setVisibility(View.INVISIBLE);
    }


    public boolean validarInformacion(){
        connection=new ServerConnection(progressBar);
        id=etId.getText().toString();
        String[] params={Constantes.METHOD_NAME_VALIDAR_APRENDIZ,id,ficha};
       String resul="false";
        try {
            resul = (String) connection.execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(resul.equals("false")){
            return false;
        }else{
            nombre=resul;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.formulario_aprendiz, menu);
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

    public void goMenu(){
        Intent goMenu=new Intent(this,MenuOpciones.class);
        startActivity(goMenu);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMenu();
    }
}
