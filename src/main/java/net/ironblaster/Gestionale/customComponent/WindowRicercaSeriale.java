package net.ironblaster.Gestionale.customComponent;

import org.apache.commons.lang3.RandomUtils;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import net.ironblaster.Gestionale.Gestionale;
import net.ironblaster.Gestionale.Logica.PersistenceLogic;
import net.ironblaster.Gestionale.Logica.Pojos.Prodotto;
import net.ironblaster.Gestionale.Logica.Pojos.Seriale;

public class WindowRicercaSeriale extends Window{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gestionale UiParent;
	
	public WindowRicercaSeriale(Gestionale UiParent) {
		this.UiParent=UiParent;
		
		addAttachListener(this::onAttach);
		center();
	}
	
	
	
	
	private void onAttach(AttachEvent evt) {
		
		VerticalLayout lay = new VerticalLayout();
		
		
		TextField seriale = new TextField("Seriale");
		seriale.setId(""+RandomUtils.nextLong());
		seriale.focus();
		
		
		
		
		
		
		Button cerca = new Button("Cerca");
		cerca.setClickShortcut(KeyCode.ENTER);
		cerca.setIcon(VaadinIcons.SEARCH);
		
		
		cerca.addClickListener(e->{
			
			
			
			try {
				for(Prodotto prod : PersistenceLogic.getAllProdotti()) {
					for (Seriale ser : prod.getSeriali()) {
						if (ser.getSeriale().equals(seriale.getValue().trim())) {
							WindowProdotto windoProd = new WindowProdotto(prod, UiParent);
							UI.getCurrent().addWindow(windoProd);
							UI.getCurrent().addWindow(new WindowSeriale(ser, prod, windoProd));
							this.close();
							return;
						}
					}
					
					
				}
				
				
				Notification.show("SERIALE NON PRESENTE A PROGRAMMA",Notification.Type.ERROR_MESSAGE);
				seriale.selectAll();
				seriale.focus();
				
				
				
			} catch (Exception e1) {
				Notification.show(e1.getMessage(),Notification.Type.WARNING_MESSAGE);
			}
					
					
					
					
					
					
		});
		
		
		lay.addComponent(seriale);
		lay.addComponent(cerca);
		lay.setComponentAlignment(seriale, Alignment.MIDDLE_CENTER);
		lay.setComponentAlignment(cerca, Alignment.MIDDLE_CENTER);
		setContent(lay);
		
		
	
	}

}
