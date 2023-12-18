package vo;

public class Cart {
	private String userId; // 구입한 유저 id
	private String itemName; // 구입한 아이템
	public Cart(String userId, String itemName) {
		this.userId = userId;
		this.itemName = itemName;
	}
	public String getData() {
		return "%s/%s\n".formatted(userId,itemName);
	}
	public String getUserId() {
		return userId;
	}
	@Override
	public String toString() {
		return itemName;
	}
	public String getItemName() {
		return itemName;
	}
}
