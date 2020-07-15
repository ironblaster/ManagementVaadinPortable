package net.ironblaster.Gestionale.Logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import net.ironblaster.Gestionale.Logica.Pojos.Prodotto;
import net.ironblaster.Gestionale.customException.PersonalException;


public class PersistenceLogic {
	
	
	private static String pathfile=System.getProperty("java.io.tmpdir")+"/db.prodotti";
	
	public static synchronized void SalvaOAggiungiProdotto(Prodotto doc) throws FileNotFoundException, IOException,PersonalException, ClassNotFoundException {
		
		//controllo se gia presente
		
		try {
			 Collection<Prodotto> obj = getAllProdotti();
			 
			 
			 
		try {
			
			
			
			
			Prodotto daRimuovere=null;
			for (Prodotto docum : obj) 
				if(docum.getCodice().equals(doc.getCodice())) {
					daRimuovere=docum;
				//	throw new PersonalException("Codice prodotto già presente");
				}
			
			try{obj.remove(daRimuovere);}catch (Exception e) {}
			
			obj.add(doc);
			ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(pathfile));
			objectOut.writeObject(obj);
			objectOut.close(); 
			
			
			
			
		}
		catch (IOException e) {
			throw e;
		}
		
 
		}
		catch (FileNotFoundException e) {
			//se il file non esiste
			 Collection<Prodotto> obj = new ArrayList<Prodotto>();
				obj.add(doc);
				ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(pathfile));
				objectOut.writeObject(obj);
				objectOut.close(); 
		}
		
	
		
		
		
		
		
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static synchronized Collection<Prodotto> getAllProdotti() throws FileNotFoundException, IOException, ClassNotFoundException {
		
	ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(pathfile));
		 Collection<Prodotto> obj = (Collection<Prodotto>) objectIn.readObject();
		 objectIn.close();
		 
		 return obj;
	}
	
	
	

	
	public static synchronized void rimuoviDaPersistenza (Prodotto daRimuovere) throws Exception{
		
		Collection<Prodotto> doc = getAllProdotti();
		Prodotto rim=null;
		for (Prodotto prod : doc) {
			if (prod.getCodice().equals(daRimuovere.getCodice()))
				rim=prod;
		}
		doc.remove(rim);
		ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(pathfile));
		objectOut.writeObject(doc);
		objectOut.close(); 
		
		
	}
	
	
	
	/*public  void ricercaInPiuDatabase() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		
		
		
		 File dir = new File("c:/bartolini");
		  File[] directoryListing = dir.listFiles();
		  
		    for (File child : directoryListing) {
		     
		   
		    
		    
		 	ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(child.getCanonicalFile()));
			
			Collection<Prodotto> obj = (Collection<Prodotto>) objectIn.readObject();
			 objectIn.close();
			 
		    
		    for (Prodotto doc : obj) {
		  //  	System.out.println(doc.getDoc().getDoc$()+";"+doc.getRiferimentoMittenteNumerico());
		    	
		    	
		    	
		    }
		    
		    
		    }
		
	}*/
	
	
	public static Prodotto ricercaDatabaseSingolo(String codice) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		 	ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(pathfile));
			
			@SuppressWarnings("unchecked")
			Collection<Prodotto> obj = (Collection<Prodotto>) objectIn.readObject();
			 objectIn.close();
			 
		    
		    for (Prodotto doc : obj) {
		
		    	if(doc.getCodice().contentEquals(codice))
		    		return doc;
		    	
		    	
		    }
		    
		    
		  return null;  
		
	}
	
	
	
	
	
	
	
	
}
