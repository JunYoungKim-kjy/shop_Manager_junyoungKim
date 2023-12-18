package dao;

import java.util.ArrayList;

import Utils.InputManager;
import vo.Cart;
import vo.User;

public class UserDAO {
	ArrayList<User> userList;
	public UserDAO(){
		userList = new ArrayList<User>();
	}
	public void insertUser() {
		System.out.println("=====[회원가입]=====");
		String id = InputManager.getValue("아이디 입력");
		if(getUserById(id)!= null) {
			System.out.println("이미 존재하는 아이디 입니다.");
			return;
		}
		String pw = InputManager.getValue("비밀번호 입력");
		String name = InputManager.getValue("이름 입력");
		User user = new User(id, pw, name);
		userList.add(user);
		System.out.println("회원가입 완료");
		System.out.println(user);
	}
	private User getUserById(String id) {
		for(User list : userList) {
			if(id.equals(list.getId())) {
				return list;
			}
		}
		return null;
	}
	public void exitUser(CartDAO cDAO) {
		if(hasData()) {
			System.out.println("데이터가 없습니다.");
			return;
		}
		System.out.println("=====[탈 퇴]=====");
		String id = InputManager.getValue("[탙퇴]아이디 입력:");
		User user = getUserById(id);
		if(user == null) {
			System.out.println("없는 아이디 입니다.");
			return;
		}
		String pw = InputManager.getValue("[탈퇴]비밀번호 입력:");
		if(!user.getPw().equals(pw)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		cDAO.deleteAllCartListByOneUser(user.getId());
		userList.remove(user);
		System.out.println(user.getName() +"님 회원탈퇴 완료");
		
	}
	private boolean hasData() {
		if(userList.size()==0) {
			return true;
		}
		return false;
	}
	public User login() {
		System.out.println("=====[로그인]=====");
		String id = InputManager.getValue("[로그인] 아이디입력  :");
		String pw = InputManager.getValue("[로그인] 비밀번호입력 :");
		User user = getUserById(id);
		if(user == userList.get(0)) {
			System.out.println("관리자메뉴로 접근해주세요");
			return null;
		}
		if(user == null || !user.getPw().equals(pw)) {
			System.out.println("회원정보가 일치하지않습니다.");
			return null;
		}
		return user;
	}
	public boolean adminlogin() {
		String id = InputManager.getValue("[관리자]아이디입력 :");
		String pw = InputManager.getValue("[관리자]비밀번호입력:");
		if(id.equals(userList.get(0).getId()) && pw.equals(userList.get(0).getPw())){
			return true;
		}
		System.out.println("정보가 일치하지 않습니다.");
		return false;
	}
	public void printUserList() {
		for(User list : userList) {
			System.out.println(list);
		}
	}
	public void deleteUser(CartDAO cDAO) {
		printUserList();
		String id = InputManager.getValue("[유저삭제]아이디입력:");
		User user = getUserById(id);
		cDAO.deleteAllCartListByOneUser(id);
		userList.remove(user);
		System.out.println("유저 삭제 완료");
	}
	public String getData() {
		String data = "";
		for(User list : userList) {
			data += list.getData();
		}
		return data;
	}
	public void loadDataFromFile(String data) {
		String temp[] = data.split("\n");
		for(int i=0; i < temp.length; i+=1) {
			String info[] = temp[i].split("/");
			User user = new User(info[0], info[1], info[2]);
			userList.add(user);
		}
	}
}

