package model;
import javax.persistence.*;

@Entity
@Table(name = "Fertigungen")
public class Fertigungsauftrag {
	@Column(name = "info")
	private String info;
	@Id
	private int id;
	public Fertigungsauftrag(String name){
		this.info = name;
	}
}
