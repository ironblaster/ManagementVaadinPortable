package net.ironblaster.Gestionale.Logica.Pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import net.ironblaster.Gestionale.Logica.PersistenceLogic;


public class Prodotto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	String codice="";
	String descrizione="";
	Collection <Seriale> seriali= new ArrayList<Seriale>();
	String fornitore="";
	
	
	
	
	public String getFornitore() {
		return fornitore;
	}


	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}


	public Prodotto(String codice) {
		this.codice=codice;

	}
	
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Collection<Seriale> getSeriali() {
		return seriali;
	}
	public void setSeriali(Collection<Seriale> seriali) {
		this.seriali = seriali;
	}
	
	
	
	
	public void save() throws Exception {
		PersistenceLogic.SalvaOAggiungiProdotto(this);
	}
	
	

}
