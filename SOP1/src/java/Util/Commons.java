package Util;

import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.zkoss.zk.ui.util.GenericForwardComposer;
//import org.zkoss.zk.ui.util.GenericForwardComposer;


@SuppressWarnings("rawtypes")
public class Commons extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;
    public static final String APPNAME = "Plantilla Base";
    public static final String USER = "USER";
    public static final String CONTRA = "";
    public static final String PATH_INICIAL = "menu.zul";
    
    
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose((org.zkoss.zk.ui.Component) comp);
                
	}
	
	
	private static final DateFormat fmtFec = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat fmtHr = new SimpleDateFormat("HH:mm:ss");
	private static final DateFormat fmtHrMin = new SimpleDateFormat("HH:mm"); 
        
	public String getStringOfDateTime(Date f, Date h){
		String result = fmtFec.format(f);
		String hr = fmtHr.format(h);
		return result+" "+hr;
	}
	

	
	public void salir(){
		desktop.getSession().invalidate();
		execution.sendRedirect("login.zul");
	}
	
	public String getUsuario(){
		if(desktop.getSession().getAttribute(USER) == null){
			salir();
			return "";
		}
		return desktop.getSession().getAttribute(USER).toString();
	}
        
        public String getContra(){
		if(desktop.getSession().getAttribute(CONTRA) == null){
			salir();
			return "";
		}
		return desktop.getSession().getAttribute(CONTRA).toString();
	}
	
	public boolean validarSesion(){
		if(desktop.getSession().getAttribute(USER) != null){
			return true;
		}
		return false;
	}
        
}
