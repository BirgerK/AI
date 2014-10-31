package fertigung;

import java.util.Random;

public class ID {

	double re;
	double im;
	int id;

	public ID() {
		Random r=new Random();
		re = r.nextDouble();
		im = r.nextDouble();
		id = hashCode();
	}

	@Override
	public int hashCode() {
		int result = 17 + hashDouble(re);
		result = 31 * result + hashDouble(im);
		return result;
	}

	private int hashDouble(double val) {
		long longBits = Double.doubleToLongBits(re);
		return (int) (longBits ^ (longBits >>> 32));
	}

	@Override
	public String toString() {
		return "(" + re + " + " + im + "i)";
	}
	
	public int getID(){
		return id;
	}

	
}
