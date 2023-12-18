package vo;

public class Item {
	 String name;
	 int price;
	 String category; // 카테고리 // 육류 , 과자 , 어류 , 과일 등등
	 
	 
	@Override
	public String toString() {
		return  name + ", " + price + "원, category=[" + category + "]";
	}
	public Item(String name, int price,String category) {
		 super();
		 this.name = name;
		 this.price = price;
		 this.category = category;
	 }
	public String getData() {
		return "%s/%d/%s\n".formatted(name,price,category);
	}
	public String getName() {
		 return name;
	 }
	public int getPrice() {
		return price;
	}
	public String getCate() {
		return category;
	}
}
