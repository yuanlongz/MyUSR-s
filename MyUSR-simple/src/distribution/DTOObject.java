package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;

import domain.DomainObject;

public abstract class DTOObject implements DomainObject{
	
	public static void toXML(DTOObject DTO, OutputStream outputStream) {
		XMLEncoder encoder = new XMLEncoder(outputStream);
		encoder.writeObject(DTO);
		encoder.close();
	}
	
	public static DTOObject fromXML(InputStream inputStream) {
		XMLDecoder decoder = new XMLDecoder(inputStream);
		DTOObject result = (DTOObject) decoder.readObject();
		decoder.close();
		return result;
	}
}
