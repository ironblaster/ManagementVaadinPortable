package net.ironblaster.Gestionale.customComponent;

import java.time.ZoneId;
import java.util.Date;
import org.apache.commons.lang3.RandomUtils;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import net.ironblaster.Gestionale.Logica.Pojos.Prodotto;
import net.ironblaster.Gestionale.Logica.Pojos.Seriale;

public class WindowAggiungiSeriale extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	WindowProdotto uiParent;
	
	Prodotto prodotto;
	public WindowAggiungiSeriale(Prodotto prodotto,WindowProdotto uiParent) {
		this.prodotto=prodotto;
		this.uiParent=uiParent;
		this.setDraggable(true);
		this.setCaption("Aggiungi Seriale prodotto "+prodotto.getCodice());
		addAttachListener(this::onAttach);
	}

	
	
	
	
	 private void onAttach(AttachEvent evt) {
		 
		 VerticalLayout lay = new VerticalLayout();
		 
		 FormLayout form = new FormLayout();
		 
		 
		 TextField seriale = new TextField("Inserisci Seriale");
		 seriale.setId(""+RandomUtils.nextLong());
		 seriale.focus();
		 
		 TextArea documento = new TextArea("Documento");
		 documento.setId(""+RandomUtils.nextLong());
		 
		 TextField cliente = new TextField("Cliente");
		 cliente.setId(""+RandomUtils.nextLong());
		 DateField data = new DateField();
		 data.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		 
		 
		 Button salva = new Button("Salva");
		 salva.setClickShortcut(KeyCode.ENTER);
		 salva.addClickListener(e->{
			 
			 
			for (Seriale seria : prodotto.getSeriali()) {
				if (seria.getSeriale().equals(seriale.getValue().trim())) {
					Notification.show("Seriale Già presente del Prodotto",Notification.Type.WARNING_MESSAGE);
					seriale.selectAll();
					seriale.focus();
					return;
				}
			}
			 
			 
			 
			 Seriale ser = new Seriale();
			 ser.setSeriale(seriale.getValue().trim());
			 ser.setData(Date.from(data.getValue().atStartOfDay()
				      .atZone(ZoneId.systemDefault())
				      .toInstant()));
			 ser.setCliente(cliente.getValue());
			 ser.setDocumento(documento.getValue().trim());
			 prodotto.getSeriali().add(ser);
			 try {
				prodotto.save();
			} catch (Exception e2) {
				Notification.show(e2.getMessage(),Notification.Type.ERROR_MESSAGE);
			}
			 
			 try {
				uiParent.UpdateGrid();
			} catch (Exception e1) {
				Notification.show(e1.getMessage(),Notification.Type.ERROR_MESSAGE);
			}
			 seriale.setValue("");
			 cliente.setValue("");
			 data.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			 seriale.focus();

			 
		 });
		 
		 
		 
		 
		 
		 form.addComponent(seriale);
		 form.addComponent(documento);
		 form.addComponent(cliente);
		 form.addComponent(data);
		 form.addComponent(salva);
		 
		 this.setWidth("30%");
		 lay.addComponent(form);
		 
		 setContent(lay);
		 
	 }
	
	
	
	
	
	
}