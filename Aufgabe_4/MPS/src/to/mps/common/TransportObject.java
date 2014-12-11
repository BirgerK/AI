package to.mps.common;

import java.io.Serializable;

public interface TransportObject<M> extends Serializable{
	public M toEntity();
}
