package net.ironblaster.Gestionale.customComponent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.vaadin.contextmenu.GridContextMenu;
import com.vaadin.contextmenu.GridContextMenu.GridContextMenuOpenListener.GridContextMenuOpenEvent;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;

import net.ironblaster.ConfirmDialog;
import net.ironblaster.Gestionale.Gestionale;
import net.ironblaster.Gestionale.Logica.PersistenceLogic;
import net.ironblaster.Gestionale.Logica.Pojos.Prodotto;
import net.ironblaster.Gestionale.Logica.Pojos.Seriale;
import net.ironblaster.Gestionale.util.StaticUtil;

public class WindowProdotto extends Window{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Prodotto prodotto;
    ListDataProvider<Seriale> dataProvider;   
    TextField codice = new TextField("Codice");
    boolean errore = false;
    Gestionale UIParent;
	public WindowProdotto(Prodotto prodotto,Gestionale UIParent) {
		errore=false;
		this.UIParent=UIParent;
		this.prodotto=prodotto;
		setCaption("Prodotto "+prodotto.getCodice());
		addAttachListener(this::onAttach);
		this.center();
		this.setModal(true);
		
	}
	
	
	
    public void UpdateGrid() throws FileNotFoundException, ClassNotFoundException, IOException {
    
    	dataProvider.refreshAll();
    	
    }
	
	
	
	
	private void onAttach(AttachEvent evt) {
	
		VerticalLayout lay = new VerticalLayout();
		
		HorizontalLayout contenuto = new HorizontalLayout();
		FormLayout datiForm = new FormLayout();
		
		
		
		
		codice.setValue(prodotto.getCodice());
		codice.addValueChangeListener(e->{
			prodotto.setCodice(e.getValue());
		});
		
		if(prodotto.getCodice().isEmpty())
			codice.focus();
		else
			codice.setEnabled(false);
	
		codice.addBlurListener(e->{
			ControlloCodice();
		});
		codice.setId(""+RandomUtils.nextLong());
		
		TextField descrizione = new TextField("Descrizione");
		descrizione.setValue(prodotto.getDescrizione());
		descrizione.addValueChangeListener(e->{
			prodotto.setDescrizione(e.getValue());
		});
		
		descrizione.setId(""+RandomUtils.nextLong());
		TextField fornitore = new TextField("Fornitore");
		fornitore.setValue(prodotto.getFornitore());
		fornitore.addValueChangeListener(e->{
			prodotto.setFornitore(e.getValue());
		});
		
		fornitore.setId(""+RandomUtils.nextLong());
	
		
		codice.setWidth("100%");
		descrizione.setWidth("100%");
		fornitore.setWidth("100%");
		datiForm.addComponents(codice,descrizione,fornitore);
		
		
		
		
		contenuto.addComponent(datiForm);
		contenuto.setWidth("80%");
		lay.addComponent(contenuto);
		
		
		 Grid<Seriale> griglia = new Grid<>();
	        
		 try {
	        	dataProvider = new ListDataProvider<>(prodotto.getSeriali());
	    		griglia.setDataProvider(dataProvider);
			} catch (Exception e) {
		//		Notification.show("seriali non presenti",Notification.Type.WARNING_MESSAGE);
			}
	        
	        
	        HeaderRow ricerche = griglia.appendHeaderRow();
	        
		 
		 
		 
	        
	        
	        
	        griglia.addItemClickListener(e->{
				if(e.getMouseEventDetails().isDoubleClick()) {
					getUI().addWindow(new WindowSeriale(e.getItem(),prodotto,this));
					
				}
				
	        });
	        
	        Grid.Column<Seriale,String> primaColonna = griglia.addColumn(Seriale::getSeriale).setCaption("Seriale");
	        Grid.Column<Seriale,String> secondaColonna = griglia.addColumn(Seriale::getDocumento).setCaption("Doc");
	        Grid.Column<Seriale,String> terzaColonna = griglia.addColumn(Seriale::getCliente).setCaption("Cliente");
		
		
		
      		TextField primoFiltro = new TextField();
      		primoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
      		ricerche.getCell(primaColonna).setComponent(primoFiltro);
      		primoFiltro.setSizeFull();
      		primoFiltro.setPlaceholder("Seriale");
        
        
      		TextField secondoFiltro = new TextField();
      		secondoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
      		ricerche.getCell(secondaColonna).setComponent(secondoFiltro);
      		secondoFiltro.setSizeFull();
      		secondoFiltro.setPlaceholder("Documento");
        
      		TextField terzoFiltro = new TextField();
      		terzoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
      		ricerche.getCell(terzaColonna).setComponent(terzoFiltro);
      		terzoFiltro.setSizeFull();
      		terzoFiltro.setPlaceholder("Cliente");

		
		
		contenuto.addComponent(griglia);
		contenuto.setExpandRatio(datiForm, 2);
		contenuto.setExpandRatio(griglia, 1);
		
		
		Button aggiungiSeriali = new Button("AggiungiSeriali");
		aggiungiSeriali.setIcon(VaadinIcons.PLUS);
		
		
		aggiungiSeriali.addClickListener(e->{
			UI.getCurrent().addWindow(new WindowAggiungiSeriale(prodotto, this));
		});
		
		
		Button salva = new Button("Salva");
		salva.setIcon(VaadinIcons.DISC);
		
		salva.addClickListener(e->{
			
			try {
				if(errore) {
					errore = false;
					return;
					}
				
				prodotto.save();
				UIParent.UpdateGrid();
				this.close();
			} catch (Exception e1) {
				Notification.show(e1.getMessage(),Notification.Type.ERROR_MESSAGE);
			}
		});
		
		   GridContextMenu<Seriale> gridMenu = new GridContextMenu<>(griglia);
	        
	        
	        gridMenu.addGridBodyContextMenuListener(this::updateGridBodyMenu);
		
		HorizontalLayout bottoni = new HorizontalLayout();
		bottoni.addComponents(aggiungiSeriali,salva);
		
		
		lay.addComponent(bottoni);
		
		lay.setComponentAlignment(bottoni, Alignment.MIDDLE_RIGHT);
		//lay.setSizeFull();
		
		this.setWidth("80%");
		this.setHeight(lay.getHeight(),lay.getHeightUnits());
		
		
		
		
		
		
		
		setContent(lay);
	}
	
	
	
	
	private void ControlloCodice() {
		try {
			for(Prodotto prod : PersistenceLogic.getAllProdotti()){
				if(prodotto.getCodice().equals(prod.getCodice())) {
					ConfirmDialog aprireCodice = new ConfirmDialog("codice prodotto già presente aprire prodotto?");
					aprireCodice.buttonNoClickListener(no->{
						errore =false;
						aprireCodice.close();
						codice.selectAll();
					//	codice.focus();
					});
					
					aprireCodice.buttonYesClickListener(yes->{
						getUI().addWindow(new WindowProdotto(prod,UIParent));
						this.close();
						aprireCodice.close();
						
					});
					
					getUI().addWindow(aprireCodice);
					errore = true;
					break;
					
				}
				
			}

		} catch (Exception e1) {
			Notification.show(e1.getMessage(),Notification.Type.ERROR_MESSAGE);
			errore = true;
		}
		errore = false;
	}
	
	
	
	
    private void updateGridBodyMenu(GridContextMenuOpenEvent<Seriale> event) {
        event.getContextMenu().removeItems();
        if (event.getItem() != null) {
        if (!event.getItem().getSeriale().isEmpty()) {
            event.getContextMenu().addItem("Cancella Prodotto", VaadinIcons.TRASH, selectedItem -> {
            	
            	try {
            		prodotto.getSeriali().remove(event.getItem());
            		prodotto.save();
					this.UpdateGrid();
				} catch (Exception e) {
					Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
				}
            	
            });
            
        }
        
        }
    }
	

}
