package com.asistencia.sena.aprendiz.network;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.asistencia.sena.aprendiz.pojos.FichaPojo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by usuario on 18/11/2014.
 */
public class ServerConnection extends AsyncTask<String, Void, Object> {
    ProgressBar progressBar;
   public  ServerConnection(ProgressBar progressBar){
        this.progressBar=progressBar;
    }

    public ServerConnection(){}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Object doInBackground(String... strings) {
        Object resul=null;
        final String NAMESPACE = Constantes.NAMESPACE;
        final String URL=Constantes.URL;
        final String METHOD_NAME = strings[0];
        final String SOAP_ACTION =  Constantes.NAMESPACE+METHOD_NAME;;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        switch (METHOD_NAME){
            case Constantes.METHOD_NAME_VALIDAR_APRENDIZ:
                String identificacion=strings[1];
                String ficha=strings[2];
                request.addProperty("identificacion", identificacion);
                request.addProperty("fi_codigo", ficha);
                break;
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);



            switch (METHOD_NAME) {

                case Constantes.METHOD_NAME_LISTADO_FICHAS:
                    SoapObject resSoap =(SoapObject)envelope.getResponse();
                 FichaPojo[] listaFichas = new FichaPojo[resSoap.getPropertyCount()];
                    for (int i = 0; i < listaFichas.length; i++)
                    {
                        SoapObject ic = (SoapObject)resSoap.getProperty(i);
                        FichaPojo fi = new FichaPojo();
                        fi.fi_codigo = Integer.parseInt(ic.getProperty(0).toString());
                        fi.fi_programa = Integer.parseInt(ic.getProperty(1).toString());
                        fi.fi_fecha_inicio = ic.getProperty(2).toString();
                        fi.fi_fecha_fin = ic.getProperty(3).toString();
                        fi.fi_hora_inicio = Integer.parseInt(ic.getProperty(4).toString());
                        fi.fi_hora_fin = Integer.parseInt(ic.getProperty(5).toString());
                        fi.fi_estado_ficha = ic.getProperty(6).toString();
                        fi.fi_estado_listado = ic.getProperty(7).toString();
                        listaFichas[i] = fi;
                    }
                    resul=listaFichas;
                    break;
                case Constantes.METHOD_NAME_VALIDAR_APRENDIZ:
                    SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
                    String res = resultado_xml.toString();
                    resul=res;
                    break;
                }
            }
        catch (Exception e)
        {

        }

        return resul;
    }

    }

