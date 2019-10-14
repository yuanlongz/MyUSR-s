package distribution;

public class ItemDTO extends DTOObject{
	private int unit;
	private String name, unitPrice;
	
	public ItemDTO(int unit, String price, String name) {
		super();
		this.unit = unit;
		this.unitPrice = price;
		this.name = name;
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


	@Override
	public String getId() {
		return name;
	}
	
	
}
