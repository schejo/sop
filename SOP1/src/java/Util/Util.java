
package Util;

//import parqueo.web.Conexion.conexion;
import Conexion.Conexion;
//import static groovy.sql.Sql.in;
import static java.io.FileDescriptor.in;
import java.io.UnsupportedEncodingException;
import static java.lang.System.in;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import static javax.management.Query.in;
import org.apache.commons.codec.binary.Base64;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;

public class Util {
    
    private int cambio;
    private char caracter;

	public  String getCurrentDateString(){
		
		Calendar fec = Calendar.getInstance();
		int day = fec.get(Calendar.DATE);
		int month = fec.get(Calendar.MONTH)+1;
		int year = fec.get(Calendar.YEAR);
		int hh = fec.get(Calendar.HOUR_OF_DAY);
		int mi = fec.get(Calendar.MINUTE);
		int ss = fec.get(Calendar.SECOND);
		
		String dd = ""+day;
		if (day >0 && day < 10)
			dd = "0"+dd;
		
		String mes = ""+month;
		if(month > 0 && month<10)
			mes = "0"+mes;
		
		String hr = ""+hh;
		if (hh >= 0 && hh < 10)
			hr = "0"+hr;
		
		String min = ""+mi;
		if(mi >= 0 && mi<10)
			min = "0"+min;
		
		String seg = ""+ss;
		if(ss >= 0 && ss<10)
			seg = "0"+seg;
		
		String fecha = dd+"/"+mes+"/"+year+" "+hr+":"+min+":"+seg;
		//System.out.println(fecha);
		return fecha;
		
	}
        
        
        public  String Encriptar(String texto) 
        {
            String secretKey = "carlos23*"; //llave para encriptar datos
            String base64EncryptedString = "";

            try {

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

                SecretKey key = new SecretKeySpec(keyBytes, "DESede");
                Cipher cipher = Cipher.getInstance("DESede");
                cipher.init(Cipher.ENCRYPT_MODE, key);

                byte[] plainTextBytes = texto.getBytes("utf-8");
                byte[] buf = cipher.doFinal(plainTextBytes);
                byte[] base64Bytes = Base64.encodeBase64(buf);
                base64EncryptedString = new String(base64Bytes);

            } catch (Exception ex) {
            }
            return base64EncryptedString;
        }   
        
        
        public  String Desencriptar(String textoEncriptado) throws Exception 
        {
            String secretKey = "carlos23*"; //llave para desenciptar datos
            String base64EncryptedString = "";

            try {
                byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
                byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
                SecretKey key = new SecretKeySpec(keyBytes, "DESede");

                Cipher decipher = Cipher.getInstance("DESede");
                decipher.init(Cipher.DECRYPT_MODE, key);

                byte[] plainText = decipher.doFinal(message);

                base64EncryptedString = new String(plainText, "UTF-8");

            } catch (Exception ex) {
            }
            return base64EncryptedString;
        }
        
        private void aAscii(char caracter) {
            cambio = (int)caracter;
    }
        
        private void aCaracter(int numero) {
        caracter = (char)numero;
    }
        
        public int getAscii(){
        return cambio;
    }
    public char getCaracter() {
        return caracter;
    }
        
        public String EncriptarClave(String sUsuario, String sClave) throws UnsupportedEncodingException
        {
        String sClaveCryot = "";
        sClave = sClave.trim().toLowerCase();
        String ls_password = "";
        
        //CambioConversion pCambio = new CambioConversion();
        Scanner miScanner = new Scanner(System.in);
        
        int pass = 0;
        for(int i = 0;  i < sClave.length(); i++ ){
        pass = pass + (sClave.codePointAt(i));
        }
        for(int j = 0;  j< sUsuario.length(); j++ ){
        pass = pass + (sUsuario.codePointAt(j));
        }
        String mula = "";
        
        do{
        mula = mula + "%";
        pass = pass - 250;
        }while(pass > 250);
        
      char caracter1 = (char)pass;
      
        
        mula = mula + caracter1;
        mula = new String(mula.getBytes("ISO-8859-1"), "UTF-8"); 
   
      sClave = mula.toLowerCase();
        return sClave;
  
        }
        
      
        public String EncriptarClave2(String sUsuario, String sClave) throws UnsupportedEncodingException
        {
        String sClaveCryot = "";
        sClave = sClave.trim().toLowerCase();
        String ls_password = "";
        
        Scanner miScanner = new Scanner(System.in);
        
        int pass = 0;
        for(int i = 0;  i < sClave.length(); i++ ){
        pass = pass + (sClave.codePointAt(i));
        }
        for(int j = 0;  j< sUsuario.length(); j++ ){
        pass = pass + (sUsuario.codePointAt(j));
        }
        String mula = "";
        
        do{
        mula = mula + "%";
        pass = pass - 250;
        }while(pass > 250);
        
      char caracter1 = (char)pass;
      
        
        mula = mula + caracter1;
        //mula = new String(mula.getBytes("ISO-8859-1"), "UTF-8"); 
              
 
      sClave = mula.toLowerCase();
      
        return sClave;

        }
        
        
        public int calculaTiempo(String fecha) throws ParseException{
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        int tiempo = 0;
        Date fechaInicial=dateFormat.parse(fecha); 
        tiempo =(int) ((fechaInicial.getTime())/1000);
        
        return tiempo;
       }
           
           public String tiempoVencimientoClave( String fechaF) throws ParseException{
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        String tiempo = "";
        //Date date = 
        Date fechaInicial= new Date();
        Date fechaFinal=dateFormat.parse(fechaF);
 
        int diferencia=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/1000);
 
        int dias=0;
        int horas=0;
        int minutos=0;
        if(diferencia>86400) {
            dias=(int)Math.floor(diferencia/86400);
            diferencia=diferencia-(dias*86400);
        }
        if(diferencia>3600) {
            horas=(int)Math.floor(diferencia/3600);
            diferencia=diferencia-(horas*3600);
        }
        if(diferencia>60) {
            minutos=(int)Math.floor(diferencia/60);
            diferencia=diferencia-(minutos*60);
        }
        //tiempo ="Su clave vencera en: "+ dias + "  Dias, "  + horas + " Horas,  " + minutos + " Minutos";
          tiempo ="Su clave vencera en: "+ dias + "  Dias  ";
        
        return tiempo;
   }
           
           
           
           public void cargaCombox(String paquete, Combobox co) throws SQLException{
            PreparedStatement smt = null;
            Connection conn;
            Conexion conex = new Conexion();
            conn = conex.getConnection();
            ResultSet rst=null;
	
            co.getItems().clear();	
            
            try {
            
                Comboitem item = new Comboitem();
		smt = conn.prepareStatement(paquete);
                rst = smt.executeQuery();
			
                while(rst.next()){
                        item = new Comboitem();
                        item.setLabel(rst.getString(2));
                        item.setValue(rst.getString(1));
                        item.setParent(co);
                }
                        
                co.setValue(null);    
                     
		}catch(SQLException e){
			e.printStackTrace();
                    }finally{
                        if(smt != null){
                              smt.close();
                        }
                        if (rst != null){
                            smt.close();
                        }
                        if(conn!=null){
                            conn.close();
                        }
                    }
        }
      		
}
