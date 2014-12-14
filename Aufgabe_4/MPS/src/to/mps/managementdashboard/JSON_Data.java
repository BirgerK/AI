package to.mps.managementdashboard;

public class JSON_Data {
	private boolean istEinAuftrag = false;
	private boolean storniert = false;
	private boolean neuKunde = false;
	private Timestamps timestamps = new Timestamps();
	
	public JSON_Data(){}

	public boolean isIstEinAuftrag() {
		return istEinAuftrag;
	}

	public boolean isStorniert() {
		return storniert;
	}

	public boolean isNeuKunde() {
		return neuKunde;
	}

	public Timestamps getTimestamps() {
		return timestamps;
	}

	public void setIstEinAuftrag(boolean istEinAuftrag) {
		this.istEinAuftrag = istEinAuftrag;
	}

	public void setStorniert(boolean storniert) {
		this.storniert = storniert;
	}

	public void setNeuKunde(boolean neuKunde) {
		this.neuKunde = neuKunde;
	}

	public void setAuftragsErteilung(long auftragsErteilung) {
		this.timestamps.setAuftragsErteilung(auftragsErteilung);
	}

	public void setZahlungsEingang(long zahlungsEingang) {
		this.timestamps.setZahlungsEingang(zahlungsEingang);
	}

	public void setFertigStellung(long fertigStellung) {
		this.timestamps.setFertigStellung(fertigStellung);
	}

	public void setAusLieferung(long ausLieferung) {
		this.timestamps.setAusLieferung(ausLieferung);
	};
	
	

}
