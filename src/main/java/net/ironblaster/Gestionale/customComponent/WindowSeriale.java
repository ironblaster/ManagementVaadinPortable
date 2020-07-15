package net.ironblaster.Gestionale.customComponent;

import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import net.ironblaster.Gestionale.Logica.Pojos.Prodotto;
import net.ironblaster.Gestionale.Logica.Pojos.Seriale;

public class WindowSeriale extends Window{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Prodotto prodotto;
	Seriale seriale;
	
	WindowProdotto uiParent;
	public WindowSeriale(Seriale seriale,Prodotto prodotto,WindowProdotto uiParent) {
		this.setModal(true);
		this.uiParent=uiParent;
		this.prodotto=prodotto;
		this.seriale=seriale;
		this.center();
		
		addAttachListener(this::onAttach);
	}
	
	
	
	
	
	
	
	private void onAttach(AttachEvent evt) {
		
		VerticalLayout lay = new VerticalLayout();
		
		
		
		HorizontalLayout hory = new HorizontalLayout();
		
		
		 TextField serialeText = new TextField("Seriale");
		 serialeText.setId(""+RandomUtils.nextLong());
		 serialeText.setEnabled(false);
		 serialeText.setValue(seriale.getSeriale());
		 serialeText.addValueChangeListener(e->{
			seriale.setSeriale(e.getValue()); 
		 });
		 
		 
		 TextField cliente = new TextField("Cliente");
		 cliente.setValue(seriale.getCliente());
		 
		 cliente.addValueChangeListener(e->{
			seriale.setCliente(e.getValue()); 
		 });
		 
		 cliente.setId(""+RandomUtils.nextLong());
		 
		 DateField data = new DateField("Data");
		 data.setValue(seriale.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		 
		 data.addValueChangeListener(e->{
			seriale.setData(Date.from(data.getValue().atStartOfDay()
				      .atZone(ZoneId.systemDefault())
				      .toInstant())); 
		 });
		
		hory.addComponents(serialeText,cliente);
		lay.addComponent(hory);
		
		
		 TextField documento = new TextField("Documento");
		 documento.setValue(seriale.getDocumento());
		 documento.setId(""+RandomUtils.nextLong());
		 documento.addValueChangeListener(e->{
				seriale.setDocumento(e.getValue()); 
			 });
		
		HorizontalLayout secondaLinea = new HorizontalLayout();
		secondaLinea.addComponents(data,documento);
		lay.addComponent(secondaLinea);
		lay.setComponentAlignment(secondaLinea, Alignment.MIDDLE_CENTER);
		
		
		
		Button salva = new Button ("Salva");
		salva.setClickShortcut(KeyCode.ENTER);
		 salva.setIcon(VaadinIcons.DISC);
		 
		 salva.addClickListener(e->{
			 try {
				prodotto.save();
				uiParent.UpdateGrid();
				this.close();
			} catch (Exception e1) {
				Notification.show(e1.getMessage(),Notification.Type.WARNING_MESSAGE);
			}
			 
		 });
		 
		 
		 
		
		lay.addComponent(salva);
		lay.setComponentAlignment(salva, Alignment.MIDDLE_RIGHT);
		
		setContent(lay);
		
		//salvataggio salvare già in memoria
	}
	
	
	

}
