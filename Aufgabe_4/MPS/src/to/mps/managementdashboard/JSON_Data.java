package to.mps.managementdashboard;

public class JSON_Data {
	private boolean istEinAuftrag = false;
	private boolean storniert = false;
	private boolean neuKunde = false;
	
	private long auftragsErteilung = 0;
	private long zahlungsEingang = 0;
	private long fertigStellung = 0;
	private long ausLieferung = 0;
	
	private long auftragserteilungZuZahlungseingang = zahlungsEingang - auftragsErteilung;
	private long fertigstellungZuAuslieferung = ausLieferung - fertigStellung;
	
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

	public void setIstEinAuftrag(boolean istEinAuftrag) {
		this.istEinAuftrag = istEinAuftrag;
	}

	public void setStorniert(boolean storniert) {
		this.storniert = storniert;
	}

	public void setNeuKunde(boolean neuKunde) {
		this.neuKunde = neuKunde;
	}
	
	public long getAuftragsErteilung() {
		return auftragsErteilung;
	}

	public long getZahlungsEingang() {
		return zahlungsEingang;
	}

	public long getFertigStellung() {
		return fertigStellung;
	}

	public long getAusLieferung() {
		return ausLieferung;
	}

	public void setAuftragsErteilung(long auftragsErteilung) {
		this.auftragsErteilung = auftragsErteilung;
		refreshRanges();
	}

	public void setZahlungsEingang(long zahlungsEingang) {
		this.zahlungsEingang = zahlungsEingang;
		refreshRanges();
	}

	public void setFertigStellung(long fertigStellung) {
		this.fertigStellung = fertigStellung;
		refreshRanges();
	}

	public void setAusLieferung(long ausLieferung) {
		this.ausLieferung = ausLieferung;
		refreshRanges();
	};

	public long getAuftragserteilungZuZahlungseingang() {
		return auftragserteilungZuZahlungseingang;
	}

	public long getFertigstellungZuAuslieferung() {
		return fertigstellungZuAuslieferung;
	}

	private void refreshRanges() {
		auftragserteilungZuZahlungseingang = zahlungsEingang - auftragsErteilung;
		fertigstellungZuAuslieferung = ausLieferung - fertigStellung;
	}
}
