package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Komponente")
public class Komponente {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int komponenteId;
	@Column
	private String name;
	@Column
	private int fertigungsdauer;
	@Column
	private int anzahl;
	@Column
	private int fertigungsKosten;
	@ManyToMany(mappedBy="komponenten")
	Set<Angebot> angebote= new HashSet<Angebot>();
	
	public Komponente(){}
	
	public Komponente(String name,int anzahl,int dauer,int fertigungsKosten){
		this.name = name;
		this.anzahl = anzahl;
		this.fertigungsdauer = dauer;
		this.fertigungsKosten = fertigungsKosten;
	}
	
	public int getFertigungsdauer(){
		return fertigungsdauer;
	}
	
	public int getAnzahl(){
		return anzahl;
	}
	
	public int getFertigungskosten(){
		return fertigungsKosten;
	}
	
	public String toString() {
		return name;
	}
	
	public int getKomponenteID() {
		return komponenteId;
	}
}
