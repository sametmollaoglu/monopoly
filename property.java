
public class property {
	
	private int id;
	private String name;
	private int cost;
	private int whoOwnThisProperty=0;
	private String whatKindProperty;
	
	public property(int id, String name, int cost, String whatKindProperty){
		this.id= id;
		this.name= name;
		this.cost= cost;
		this.whatKindProperty= whatKindProperty;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getWhoOwnThisProperty() {
		return whoOwnThisProperty;
	}

	public void setWhoOwnThisProperty(int whoOwnThisProperty) {
		this.whoOwnThisProperty = whoOwnThisProperty;
	}

	public String getWhatKindProperty() {
		return whatKindProperty;
	}

	public void setWhatKindProperty(String whatKindProperty) {
		this.whatKindProperty = whatKindProperty;
	}
	
}
