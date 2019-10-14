package distribution;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;

public class ItemDTO {
	private int unit;
	private String name, unitPrice;
	
	public ItemDTO(int unit, String price, String name) {
		super();
		this.unit = unit;
		this.unitPrice = price;
		this.name = name;
	}

	public static void toXML(ItemDTO itemDTO, OutputStream outputStream) {
		XMLEncoder encoder = new XMLEncoder(outputStream);
		encoder.writeObject(itemDTO);
		encoder.close();
	}
	
	public static ItemDTO fromXML(InputStream inputStream) {
		XMLDecoder decoder = new XMLDecoder(inputStream);
		ItemDTO result = (ItemDTO) decoder.readObject();
		decoder.close();
		return result;
	}
	
	
	//getters and setters
	public void setUnit(int unit) {
		this.unit = unit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getUnit() {
		return unit;
	}

	public String getName() {
		return name;
	}

	public String getUnitPrice() {
		return unitPrice;
	}
	
	
}
