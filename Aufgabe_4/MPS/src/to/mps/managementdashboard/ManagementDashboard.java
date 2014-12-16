package to.mps.managementdashboard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;

import com.google.gson.Gson;

public final class ManagementDashboard{
	 
	private static Map<Angebot, JSON_Data> dataArray = new HashMap<Angebot, JSON_Data>();
	private static int jsonCounter = 1;
	private static Gson gson = new Gson();
	//private static ESearchWrapper esearch = new ESearchWrapper("localhost", 9200, "mps", "data", gson.toJson(new JSON_Data()));
	
	
	private ManagementDashboard(){};
	
	/**
	 * Erstellt ein fertiges Angebot. </br>
	 * Das uebergebene Angebot <code>angebot</code> muss fertig erstellt sein.
	 * Dabei wird ein Timestamp <code>Erstellungszeit</code> gesetzt.
	 * 
	 * @param angebot <code>Angebot</code> Zu erstellendes Angebot.
	 */
	public static void newAngebot(Angebot angebot) {
		dataArray.put(angebot, new JSON_Data());
	}

	
	/**
	 * Ein bereits erstelltes Angebot wurde vom Kunden akzeptiert
	 * und wird damit zum Auftrag
	 * @param angebot Das Angebot, welches zum Auftrag wird
	 */
	public static void angebotWirdAuftrag(Angebot angebot) {
		dataArray.get(angebot).setIstEinAuftrag(true);
		dataArray.get(angebot).setAuftragsErteilung(System.currentTimeMillis());
	}
	
	
	/**
	 * Der Zahlungseingang wird registriert. Dabei wird ein neuer Timestamp 
	 * <code>Zahlungszeit</code> gesetzt.
	 * 
	 * @param auftrag <code>Auftrag</code> Auftrag fuer den ein Zahlungseingang
	 * registriert wurde.
	 */
	public static void zahlungseingangFuer(Auftrag auftrag) {
		dataArray.get(auftrag.getAngebot()).setZahlungsEingang(System.currentTimeMillis());
	}

	/**
	 * Auftrag wird auf fertiggestellt gesetzt. Dabei wird ein neuer Timestamp
	 * <code>Fertigstellungszeit</code> gesetzt.
	 * 
	 * @param auftrag <code>Auftrag</code> Auftrag der in der Produktion 
	 * fertiggestellt wurde. 
	 */
	public static void fertiggestellterAuftrag(Auftrag auftrag) {
		dataArray.get(auftrag.getAngebot()).setFertigStellung(System.currentTimeMillis());
		try {
			FileWriter writer = new FileWriter("jsonFile" + jsonCounter + ".json");
			jsonCounter = jsonCounter + 1;
			String json = gson.toJson((dataArray.get(auftrag.getAngebot())));
	//		esearch.addEntry(json);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Auftrag wird auf ausgeliefert gesetzt. Dabei wird ein neuer Timestamp
	 * <code>Auflieferungszeit</code> gesetzt.
	 * 
	 * @param auftrag <code>Auftrag</code> Auftrag der ausgeliefert wurde.
	 */
	public static void ausgelieferterAuftrag(Auftrag auftrag) {
		dataArray.get(auftrag.getAngebot()).setAusLieferung(System.currentTimeMillis());
	}

	/**
	 * Setzt einen erstellten Auftrag <code>auftrag</code> auf storniert.
	 * 
	 * @param auftrag <code>Auftrag</code> Zu cancelnder Auftrag.
	 */
	public static void cancelAuftrag(Auftrag auftrag) {
		dataArray.get(auftrag.getAngebot()).setStorniert(true);
		try {
			FileWriter writer = new FileWriter("jsonFile" + jsonCounter + ".json");
			jsonCounter = jsonCounter + 1;
			String json = gson.toJson((dataArray.get(auftrag.getAngebot())));
	//		esearch.addEntry(json);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean exists(Auftrag auftrag) {
		return dataArray.containsKey(auftrag.getAngebot());
	}


}
