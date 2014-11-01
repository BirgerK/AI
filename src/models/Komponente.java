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
	@ManyToMany(mappedBy="komponenten")
	Set<Angebot> angebote= new HashSet<Angebot>();
	
	public Komponente(String name,int dauer){
		this.name = name;
		this.fertigungsdauer = dauer;
	}
	
	public int getFertigungsdauer(){
		return fertigungsdauer;
	}
}
