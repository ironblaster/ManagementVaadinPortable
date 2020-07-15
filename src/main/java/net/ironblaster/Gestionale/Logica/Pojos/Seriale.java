package net.ironblaster.Gestionale.Logica.Pojos;

import java.io.Serializable;
import java.util.Date;

public class Seriale implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String seriale="";
	Date data;
	String cliente="";
	String documento="";
	
	
	
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getSeriale() {
		return seriale;
	}
	public void setSeriale(String seriale) {
		this.seriale = seriale;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
	

}
