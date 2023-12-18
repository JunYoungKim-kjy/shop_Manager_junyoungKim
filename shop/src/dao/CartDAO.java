package dao;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.InputManager;
import vo.Cart;
import vo.Item;
import vo.User;

public class CartDAO {
	private ArrayList<Cart> cartList;
	public CartDAO(){
		cartList = new ArrayList<Cart>();
	}
	public void deleteAllCartListByOneUser(String id) {
		for(int i=0; i < cartList.size(); i+=1) {
			if(cartList.get(i).getUserId().equals(id)) {
				cartList.remove(i);
				i-=1;
			}
		}
	}
	public void addCartData(String id,String name) {
		Cart c = new Cart(id,name);
		cartList.add(c);
	}
	private ArrayList<Cart> getMyCartList(User user) {
		ArrayList<Cart> myList = new ArrayList<Cart>();
		for(Cart list :cartList) {
			if(list.getUserId().equals(user.getId())) {
				myList.add(list);
			}
		}
		return myList;
	}
	private void myCartList(ArrayList<Cart> myList, String name) {
		System.out.println(name + "님의 장바구니 목록");
		int num =1;
		for(Cart list :myList) {
			System.out.print(num++ +" ");
			System.out.println(list);
		}
		System.out.println("=============================");
	}
	public void printMyList(User user , ItemDAO iDAO) {
		ArrayList<Cart> myList = getMyCartList(user);
//		myCartList(myList, user.getName());
		int itemCnt[] = new int[iDAO.itemList.size()];
		int cnt = 0;
		int total = 0;
		for(Cart list : myList) {
			int idx = 0;
			for(Item item : iDAO.itemList) {
				if(list.getItemName().equals(item.getName())) {
					itemCnt[idx] +=1;
					cnt +=1;
					total += item.getPrice(); 
				}
				idx++;
			}
		}
//		System.out.println(Arrays.toString(itemCnt));
		System.out.printf("=======%s 님의 장바구니 ========\n",user.getName());
		int num = 1;
		int idx = 0;
		for(Item item : iDAO.itemList) {
			if(itemCnt[idx]!=0) {
				System.out.printf("%d) %s \t%d원  %d개\n",num++,item.getName(),item.getPrice()*itemCnt[idx],itemCnt[idx]);
			}
			idx++;
		}
		System.out.printf("======= 총 갯수 %d 총 금액 %d원=======\n",cnt,total);
	}
	public String getData() {
		String data = "";
		for(Cart list : cartList) {
			data += list.getData();
		}
		return data;
	}
	public void buyItem(User user) {
		ArrayList<Cart> myList = getMyCartList(user);
		if(myList.size() == 0) {
			System.out.println("장바구니가 비어있습니다.");
		}
		myCartList(myList, user.getName());
		int sel = InputManager.getValue("[구입]번호입력:", 0, myList.size())-1;
		if(sel == -1 )return;
		cartList.remove(myList.get(sel));
		System.out.println(myList.get(sel).getItemName() + "구매 완료");
	}
	public void deleteMyCartList(User user) {
		ArrayList<Cart> myList = getMyCartList(user);
		if(myList.size() == 0) {
			System.out.println("장바구니가 비어있습니다.");
		}
		int num = 1;
		for(Cart ml : myList) {
			System.out.print(num++ + " ");
			System.out.println(ml);
		}
		int sel = InputManager.getValue("[삭제]번호입력:", 0, myList.size())-1;
		if(sel == -1) {
			return;
		}
		cartList.remove(myList.get(sel));
		System.out.println(myList.get(sel).getItemName() + "  삭제 완료"); 
		
	}
	public void loadDataFromFile(String data) {
		String temp[] = data.split("\n");
		for(int i=0; i < temp.length; i+=1) {
			String info[] = temp[i].split("/");
			Cart cart = new Cart(info[0], info[1]);
			cartList.add(cart);
		}
	}
	public void deleteAllCartListByItemName(String name) {
		for(int i=0; i < cartList.size(); i+=1) {
			if(cartList.get(i).getUserId().equals(name)) {
				cartList.remove(i);
				i-=1;
			}
		}
	}
	public void printCartListUpToDate() {
		if (cartList.size() == 0) {
			System.out.println("주문 정보가 없습니다");
			return;
		}
		System.out.printf("%s %s %n", "회원아이디", "상품 이름");
		for (int i = cartList.size() - 1; i >= 0; i -= 1) {
			System.out.printf("(%d) %s %s %n", cartList.size() - 1 - i, cartList.get(i).getUserId(), cartList.get(i).getItemName());
		}

	}
}
