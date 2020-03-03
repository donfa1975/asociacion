package converter;

import modelo.TblAreas;
//import facade.TblAreasFacade;
import controller.util.JsfUtil;
//import facade.TblAreasFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
//import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import modelo.TblCantones;

@FacesConverter(value = "TblCantonesConverter")
public class TblCantonesConverter implements Converter {

	EntityManager em;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
			return null;
		}
	return	em.find(TblCantones.class, getKey(value));
                
              //  return this.getEjbFacade().find(getKey(value));
	}

	java.lang.Integer getKey(String value) {
		java.lang.Integer key;
		key = Integer.valueOf(value);
		return key;
	}

	String getStringKey(java.lang.Integer value) {
		StringBuffer sb = new StringBuffer();
		sb.append(value);
		return sb.toString();
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		if (object == null
				|| (object instanceof String && ((String) object).length() == 0)) {
			return null;
		}
		if (object instanceof TblAreas) {
			TblAreas o = (TblAreas) object;
			return getStringKey(o.getIdArea());
		} else {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TblAreas.class.getName()});
			return null;
		}
	}

	/*private TblAreasFacade getEjbFacade() {
		this.ejbFacade = CDI.current().select(TblAreasFacade.class).get();
		return this.ejbFacade;
	}*/
}
