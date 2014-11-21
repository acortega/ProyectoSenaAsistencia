package com.asistencia.sena.aprendiz.pojos;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by usuario on 17/11/2014.
 */
public class FichaPojo implements KvmSerializable {
    public int fi_codigo;
    public int fi_programa;
    public String fi_fecha_inicio;
    public String fi_fecha_fin;
    public int fi_hora_inicio;
    public int fi_hora_fin;
    public String fi_estado_ficha;
    public String fi_estado_listado;

    public FichaPojo() {
        fi_codigo = 0;
        fi_programa = 0;
        fi_fecha_inicio = "";
        fi_fecha_fin = "";
        fi_hora_inicio = 0;
        fi_hora_fin = 0;
        fi_estado_ficha = "";
        fi_estado_listado = "";

    }


    public FichaPojo(int fi_codigo, int fi_programa, String fi_fecha_inicio,
                     String fi_fecha_fin, int fi_hora_inicio, int fi_hora_fin,
                     String fi_estado_ficha, String fi_estado_listado) {
        super();
        this.fi_codigo = fi_codigo;
        this.fi_programa = fi_programa;
        this.fi_fecha_inicio = fi_fecha_inicio;
        this.fi_fecha_fin = fi_fecha_fin;
        this.fi_hora_inicio = fi_hora_inicio;
        this.fi_hora_fin = fi_hora_fin;
        this.fi_estado_ficha = fi_estado_ficha;
        this.fi_estado_listado = fi_estado_listado;
    }


    @Override
    public Object getProperty(int posicion) {
        // TODO Auto-generated method stub

        switch (posicion) {
            case 0:
                return fi_codigo;
            case 1:
                return fi_programa;
            case 2:
                return fi_fecha_inicio;
            case 3:
                return fi_fecha_fin;
            case 4:
                return fi_hora_inicio;
            case 5:
                return fi_hora_fin;
            case 6:
                return fi_estado_ficha;
            case 7:
                return fi_estado_listado;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 8;
    }

    @Override
    public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
        // TODO Auto-generated method stub
        switch (ind) {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "fi_codigo";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "fi_programa";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fi_fecha_inicio";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fi_fecha_fin";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "fi_hora_inicio";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "fi_hora_fin";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fi_estado_ficha";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "fi_estado_listado";
                break;

            default:
                break;
        }
    }

    @Override
    public void setProperty(int ind, Object val) {
        // TODO Auto-generated method stub
        switch (ind) {
            case 0:
                fi_codigo = Integer.parseInt(val.toString());
                break;
            case 1:
                fi_programa = Integer.parseInt(val.toString());
                break;
            case 2:
                fi_fecha_inicio = val.toString();
                break;
            case 3:
                fi_fecha_fin = val.toString();
                break;
            case 4:
                fi_hora_inicio = Integer.parseInt(val.toString());
                break;
            case 5:
                fi_hora_fin = Integer.parseInt(val.toString());
                break;
            case 6:
                fi_estado_ficha = val.toString();
                break;
            case 7:
                fi_estado_listado = val.toString();
                break;
            default:

                break;
        }
    }
}
