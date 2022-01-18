package entidades;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.xpiration.ConnectionHelper;
import com.example.xpiration.MainActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lotes {
    private int LOTE_ID;
    private int PRODUCTO_ID;
    private String LOTE_NOMBRE;
    private Date LOTE_FECHA_INGRESO;
    private Date LOTE_FECHA_CADUCIDAD;
    private int LOTE_NOTIFICACION_NARANJA; //preventiva
    private int LOTE_NOTIFICACION_ROJA; //critica

    Connection con;

    public int getLOTE_ID() {
        return LOTE_ID;
    }

    public void setLOTE_ID(int LOTE_ID) {
        this.LOTE_ID = LOTE_ID;
    }

    public int getPRODUCTO_ID() {
        return PRODUCTO_ID;
    }

    public void setPRODUCTO_ID(int PRODUCTO_ID) {
        this.PRODUCTO_ID = PRODUCTO_ID;
    }

    public String getLOTE_NOMBRE() {
        return LOTE_NOMBRE;
    }

    public void setLOTE_NOMBRE(String LOTE_NOMBRE) {
        this.LOTE_NOMBRE = LOTE_NOMBRE;
    }

    public Date getLOTE_FECHA_INGRESO() {
        return LOTE_FECHA_INGRESO;
    }

    public void setLOTE_FECHA_INGRESO(Date LOTE_FECHA_INGRESO) {
        this.LOTE_FECHA_INGRESO = LOTE_FECHA_INGRESO;
    }

    public Date getLOTE_FECHA_CADUCIDAD() {
        return LOTE_FECHA_CADUCIDAD;
    }

    public void setLOTE_FECHA_CADUCIDAD(Date LOTE_FECHA_CADUCIDAD) {
        this.LOTE_FECHA_CADUCIDAD = LOTE_FECHA_CADUCIDAD;
    }

    public int getLOTE_NOTIFICACION_NARANJA() {
        return LOTE_NOTIFICACION_NARANJA;
    }

    public void setLOTE_NOTIFICACION_NARANJA(int LOTE_NOTIFICACION_NARANJA) {
        this.LOTE_NOTIFICACION_NARANJA = LOTE_NOTIFICACION_NARANJA;
    }

    public int getLOTE_NOTIFICACION_ROJA() {
        return LOTE_NOTIFICACION_ROJA;
    }

    public void setLOTE_NOTIFICACION_ROJA(int LOTE_NOTIFICACION_ROJA) {
        this.LOTE_NOTIFICACION_ROJA = LOTE_NOTIFICACION_ROJA;
    }

    public void insertar (Context context,String idproducto, String nombre, String fechaIngreso, String fechaCaducidad, String notiNaranja, String notiRoja){
//******************** Validaciones para query de insercion ****************************************
            DateFormat dateFormatYMD = new SimpleDateFormat("yyyy/MM/dd");
            Date nuevo = null;
            Date comparado = null;

            if(fechaCaducidad.isEmpty()){
                Toast.makeText(context,"Ingrese una fecha de vencimiento",Toast.LENGTH_SHORT).show();
            }else {
                try {
                    nuevo = dateFormatYMD.parse(fechaIngreso);
                    comparado = dateFormatYMD.parse(fechaCaducidad);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (comparado.compareTo(nuevo) <= 0) {
                    Toast.makeText(context, "El producto caduca hoy o ya ha caducado", Toast.LENGTH_SHORT).show();
                } else {
                    if (nombre.length() == 0) {
                        Toast.makeText(context, "Ingrese Nombre del producto", Toast.LENGTH_SHORT).show();
                    } else {
                        if((notiNaranja.isEmpty()) || (notiNaranja.compareToIgnoreCase("0") == 0)){
                            Toast.makeText(context, "Ingrese dias para notificacion Preventiva", Toast.LENGTH_SHORT).show();
                        }else{
                            if((notiRoja.isEmpty()) || (notiRoja.compareToIgnoreCase("0") == 0)){
                                Toast.makeText(context, "Ingrese dias para notificacion Critica", Toast.LENGTH_SHORT).show();
                            }else{
//************** inicializacion de conexion, construccion y envio de query *************************
                                try {
                                    String query = "INSERT PRODUCTO VALUES (" + idproducto + ",'" + nombre +"','"+ fechaIngreso + "','" + fechaCaducidad + "',"+notiNaranja+','+notiRoja+");";
                                    ConnectionHelper conexion = new ConnectionHelper();
                                    Connection con = conexion.connectionclass();

                                    PreparedStatement pst = con.prepareStatement(query);

                                    pst.executeUpdate();

                                    Toast.makeText(context, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);


                                } catch (SQLException e) {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    }
                }
            }
    }

}
