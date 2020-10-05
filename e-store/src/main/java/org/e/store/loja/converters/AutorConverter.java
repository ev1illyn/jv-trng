package org.e.store.loja.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.e.store.loja.models.Autor;

@FacesConverter("autorConverter")
public class AutorConverter implements Converter{

	// recuperará o Autor como objeto
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.trim().isEmpty()) {
			return null;	
		}
		Autor autor = new Autor();
		autor.setId(Integer.valueOf(id));
		return autor;
	}

	// recuperará o Autor no formato String
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object autorObject) {
		if (autorObject == null) {
			return null;
		}
		Autor autor = (Autor) autorObject;
		return autor.getId().toString();
	}

}