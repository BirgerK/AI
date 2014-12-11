package to.mps.common;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
	private int id;
	
	@Id
    @GeneratedValue(strategy = AUTO)
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public AbstractEntity(){
		
	}
}
