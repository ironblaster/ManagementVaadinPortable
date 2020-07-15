package net.ironblaster.Gestionale;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.contextmenu.GridContextMenu;
import com.vaadin.contextmenu.GridContextMenu.GridContextMenuOpenListener.GridContextMenuOpenEvent;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;

import net.ironblaster.Gestionale.Logica.PersistenceLogic;
import net.ironblaster.Gestionale.Logica.Pojos.Prodotto;
import net.ironblaster.Gestionale.customComponent.WindowProdotto;
import net.ironblaster.Gestionale.customComponent.WindowRicercaSeriale;

@Theme("mytheme")
public class Gestionale extends UI {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ListDataProvider<Prodotto> dataProvider;   
    Grid<Prodotto> griglia = new Grid<>();
    
    public void UpdateGrid() throws FileNotFoundException, ClassNotFoundException, IOException {
    	
    	dataProvider= new ListDataProvider<>(PersistenceLogic.getAllProdotti());
    	dataProvider.refreshAll();
    	griglia.setDataProvider(dataProvider);
    
    	
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	getPage().setTitle("Gestionale");
    	
    	
    	
    	
        final VerticalLayout layout = new VerticalLayout();
        

        
        
        
        
        
        
        
        Grid.Column<Prodotto,String> primaColonna = griglia.addColumn(Prodotto::getCodice).setCaption("Codice");
        Grid.Column<Prodotto,String> secondaColonna = griglia.addColumn(Prodotto::getDescrizione).setCaption("Descrizione");
        Grid.Column<Prodotto,String> terzaColonna = griglia.addColumn(Prodotto::getFornitore).setCaption("Fornitore");
        
        
        griglia.addItemClickListener(e->{
			if(e.getMouseEventDetails().isDoubleClick()) {
				getUI().addWindow(new WindowProdotto(e.getItem(),this));
				
			}
			
        });
        
        
        
        
       
        try {
        	dataProvider = new ListDataProvider<>(PersistenceLogic.getAllProdotti());
    		griglia.setDataProvider(dataProvider);
		} catch (Exception e) {
			Notification.show(e.getMessage(),Notification.Type.WARNING_MESSAGE);
		}
        
        
        HeaderRow ricerche = griglia.appendHeaderRow();
        
        
        
      //Primo Filtro
		
      		TextField primoFiltro = new TextField();
      		primoFiltro.addValueChangeListener(event ->{
      			
      			dataProvider.addFilter(prodottorecord -> StringUtils.containsIgnoreCase(prodottorecord.getCodice(),primoFiltro.getValue()));
      			});
      		primoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
      		ricerche.getCell(primaColonna).setComponent(primoFiltro);
      		primoFiltro.setSizeFull();
      		primoFiltro.setPlaceholder("Codice");
        
        
            //Secondo Filtro
    		
      		TextField secondoFiltro = new TextField();
      		secondoFiltro.addValueChangeListener(event ->{
      			
      			dataProvider.addFilter(prodottorecord -> StringUtils.containsIgnoreCase(prodottorecord.getDescrizione(),secondoFiltro.getValue()));
      			});
      		secondoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
      		ricerche.getCell(secondaColonna).setComponent(secondoFiltro);
      		secondoFiltro.setSizeFull();
      		secondoFiltro.setPlaceholder("Codice");
        
        
      		
            //Terzo Filtro
    		
      		TextField terzoFiltro = new TextField();
      		terzoFiltro.addValueChangeListener(event ->{
      			
      			dataProvider.addFilter(prodottorecord -> StringUtils.containsIgnoreCase(prodottorecord.getFornitore(),terzoFiltro.getValue()));
      			});
      		terzoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
      		ricerche.getCell(terzaColonna).setComponent(terzoFiltro);
      		terzoFiltro.setSizeFull();
      		terzoFiltro.setPlaceholder("Fornitore");
      		
      		
      		
      		
      		
      		
            Button addProdotto = new Button("Aggiungi Prodotto");
            addProdotto.setIcon(VaadinIcons.PLUS);
            
            addProdotto.addClickListener(e->{
            	getUI().addWindow(new WindowProdotto(new Prodotto(""),this));
            	
            	
            	
            });
      		
      		
      		
            Button ricercaSeriale = new Button("Ricerca Seriale");
            ricercaSeriale.setIcon(VaadinIcons.MAGNET);
            
            ricercaSeriale.addClickListener(e->{
            	
            	getUI().addWindow(new WindowRicercaSeriale(this));
            	
            });
      		
      		
      		
      		
      		
      		
      		
      		HorizontalLayout bottoni = new HorizontalLayout();
      		bottoni.addComponents(addProdotto,ricercaSeriale);
      		
      	layout.addComponents(bottoni);
        griglia.setSizeFull();
        layout.setSizeFull();
        layout.addComponents(griglia);
        layout.setExpandRatio(griglia, 1);
        
        
        
        GridContextMenu<Prodotto> gridMenu = new GridContextMenu<>(griglia);
        
        
        gridMenu.addGridBodyContextMenuListener(this::updateGridBodyMenu);
        
        
        
        
        
        
        
        setContent(layout);
        
        
        
        
        
        
    }
    private void updateGridBodyMenu(GridContextMenuOpenEvent<Prodotto> event) {
        event.getContextMenu().removeItems();
        if (event.getItem() != null) {
        if (!event.getItem().getCodice().isEmpty()) {
            event.getContextMenu().addItem("Cancella Prodotto", VaadinIcons.TRASH, selectedItem -> {
            	
            	try {
					PersistenceLogic.rimuoviDaPersistenza(event.getItem());
					this.UpdateGrid();
				} catch (Exception e) {
					Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
				}
            	
            });
            
        }
        
        }
    }
    
    
    

    @WebServlet(urlPatterns = "/*", name = "Gestionale", asyncSupported = true)
    @VaadinServletConfiguration(ui = Gestionale.class, productionMode = false)
    public static class GestionaleServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
    }
}
