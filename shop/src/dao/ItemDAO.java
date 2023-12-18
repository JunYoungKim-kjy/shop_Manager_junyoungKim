package dao;

import java.util.ArrayList;

import Utils.InputManager;
import vo.Cart;
import vo.Item;
import vo.User;

public class ItemDAO {
	ArrayList<Item> itemList;
	public ItemDAO(){
		itemList = new ArrayList<Item>();
	}
	private boolean hasData() {
		if(itemList.size() == 0){
			System.out.println("데이터가 없습니다.");
			return true;
		}
		return false;
	}
	private void printItemList() {
		int num = 1;
		for(Item list : itemList) {
			System.out.printf("%d %s \n",num++,list);
		}
	}
	public void shopping(User user, CartDAO cDAO) {
		printItemList();
		while (true) {
			int sel = InputManager.getValue("[쇼핑]번호선택", 0, itemList.size()) - 1;
			if (sel == -1) {
				return;
			}
			cDAO.addCartData(user.getId(), itemList.get(sel).getName());
			System.out.println(itemList.get(sel) + "장바구니 담기 완료");
		}
	}
	public String getData() {
		String data = "";
		for(Item list : itemList) {
			data += list.getData();
		}
		return data;
	}
	private Item getItemByName(String name) {
		for(Item list : itemList) {
			if(list.getName().equals(name)) {
				return list;
			}
		}
		return null;
	}
	public void insertItem() {
		printItemList();
		String name = InputManager.getValue("[아이템추가]이름 입력: ");
		Item item = getItemByName(name);
		if(item != null) {
			System.out.println("이미 존재하는 아이템 입니다.");
			return;
		}
		String category = InputManager.getValue("[아이템추가]카테고리 입력:");
		int price = InputManager.getValue("[아이템추가]가격 입력: ", 1, 99999);
		item = new Item(name, price, category);
		itemList.add(item);
		System.out.println("아이템 추가 완료");
	}
	public void loadDataFromFile(String data) {
		String temp[] = data.split("\n");
		for(int i=0; i < temp.length; i+=1) {
			String info[] = temp[i].split("/");
			Item item = new Item(info[0], Integer.parseInt(info[1]),info[2]);
			itemList.add(item);
		}
	}
	public void deleteItem(CartDAO cDAO) {
		if(hasData())return;
		String itemName = InputManager.getValue("[아이템삭제]아이템이름 :");
		Item item = getItemByName(itemName);
		if(item == null) {
			System.out.println("아이템이 존재하지 않습니다.");
			return;
		}
		cDAO.deleteAllCartListByItemName(itemName);
		itemList.remove(item);
		System.out.println("[아이템 삭제 완료]");
	}
	public void printListByCategory() {
		if(hasData())return;
		String cate = InputManager.getValue("[카테고리]이름 입력:");
		for(Item list :itemList) {
			if(list.getCate().equals(cate)) {
				System.out.println(list);
			}
		}
	}
	public void deleteCategory(CartDAO cDAO) {
		if(hasData())return;
		printItemList();
		String cate = InputManager.getValue("[카테고리삭제]이름 입력");
		for(int i=0; i < itemList.size() ; i++) {
			if(itemList.get(i).getCate().equals(cate)) {
				cDAO.deleteAllCartListByItemName(itemList.get(i).getName());
				itemList.remove(i);
				i-=1;
			}
		}
	}
}
