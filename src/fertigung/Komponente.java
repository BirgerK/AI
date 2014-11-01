package fertigung;

public class Komponente {
	String name;
	int fertigungsdauer;
	
	public Komponente(String name,int dauer){
		this.name = name;
		this.fertigungsdauer = dauer;
	}
	
	public int getFertigungsdauer(){
		return fertigungsdauer;
	}
}
