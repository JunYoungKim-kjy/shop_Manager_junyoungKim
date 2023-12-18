package Controller;

import Utils.FileManager;
import Utils.InputManager;
import dao.CartDAO;
import dao.ItemDAO;
import dao.UserDAO;
import vo.User;

public class ShopController {
	UserDAO uDAO;
	ItemDAO iDAO;
	CartDAO cDAO;
	User log;
	FileManager fm;
	public ShopController(){
		cDAO = new CartDAO();
		uDAO = new UserDAO();
		iDAO = new ItemDAO();
		fm = new FileManager();
		fm.loadData(cDAO, uDAO, iDAO);
	}

	private void loginMenu() {
		while (true) {
			System.out.println("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
			System.out.printf("[ %s ]님 로그인\n",log.getName());
			int sel = InputManager.getValue("메뉴입력", 0, 2);
			if(sel == 0) {
				System.out.println("뒤로가기");
				return;
			}else if(sel == 1) { //쇼핑
				iDAO.shopping(log,cDAO);
			}else if(sel == 2) { //장바구니목룍
				myPage();
			}
		}
	}
	private void myPage() {
		while (true) {
			System.out.println("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");
			int sel = InputManager.getValue("메뉴입력", 0, 3);
			if (sel == 0) {
				System.out.println("뒤로가기");
				return;
			} else if (sel == 1) { // 내 장바구니
				cDAO.printMyList(log, iDAO);
			} else if (sel == 2) { // 삭제
				cDAO.deleteMyCartList(log);
			} else if (sel == 3) { // 구입
				cDAO.buyItem(log);
			}
		}
	}
	
	public void run() {
		while (true) {
			System.out.print("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]" + "\n[100.관리자] ");
//			System.out.println("자동 저장 완료");
			fm.saveData(cDAO, uDAO, iDAO);
			int sel = InputManager.getValue("메뉴입력", 0, 100);
			if (sel == 0) {// 종료
				System.out.println("종료");
				return;
			} else if (sel == 1) {// 가입
				uDAO.insertUser();
			} else if (sel == 2) {// 탈퇴
				uDAO.exitUser(cDAO);
			} else if (sel == 3) {// 로그인
				if(log != null)loginMenu();
				else {
					log = uDAO.login();
					if(log != null)loginMenu();
				}
			} else if (sel == 4) {// 로그아웃
				if(log==null) {
					System.out.println("로그인 상태가 아닙니다.");
					return;
				}
				log = null;
				return;
			} else if (sel == 100) {// 관리자
				if(uDAO.adminlogin()) {
					adminMenu();
				}
			} else {
				System.out.println("없는 메뉴입니다.");
				continue;
			}
		}
	}
	private void adminMenu() {
		while (true) {
			System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리] [0.뒤로가기] ");
			int sel = InputManager.getValue("[관리자메뉴]메뉴입력", 0, 4);
			if (sel == 0) {
				System.out.println("종료");
				return;
			} else if (sel == 1) {// 아이템관리
				itemManager();
			} else if (sel == 2) {// 카테고리관리
				categoriManager();
			} else if (sel == 3) {// 장바구니관리
				cDAO.printCartListUpToDate();
			} else if (sel == 4) {// 유저목록관리
				userListManager();
			}
		}
	}
	private void userListManager(){
		while (true) {
			System.out.println("[1.유저 목록] [2.유저 삭제] [0.뒤로가기]");
			int sel = InputManager.getValue("[유저 관리]메뉴입력", 0, 2);
			if (sel == 0) {
				System.out.println("종료");
				return;
			} else if (sel == 1) {// 유저 목록
				uDAO.printUserList();
			} else if (sel == 2) {// 유저 삭제
				uDAO.deleteUser(cDAO);
			}
		}
	}

	private void itemManager() {
		while (true) {
			System.out.println("[1.아이템 추가] [2. 아이템 삭제] [0.뒤로가기]");
			int sel = InputManager.getValue("[아이템 관리]메뉴입력", 0, 2);
			if (sel == 0) {
				System.out.println("종료");
				return;
			} else if (sel == 1) {// 아이템추가
				iDAO.insertItem();
			} else if (sel == 2) {// 아이템삭제ㅔ
				iDAO.deleteItem(cDAO);
			}
		}
	}
	private void categoriManager() {
		while (true) {
			System.out.println("[1.카테고리별 목록] [2.카테고리 삭제] [0.뒤로가기]");
			int sel = InputManager.getValue("[카테고리 관리]메뉴입력", 0, 2);
			if (sel == 0) {
				System.out.println("종료");
				return;
			} else if (sel == 1) {// 카테고리별 목록
				iDAO.printListByCategory();
			} else if (sel == 2) {// 카테고리 전체 삭제
				iDAO.deleteCategory(cDAO);
			}
		}
	}
	
	
	// 	System.out.println("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]" + "\n[100.관리자] [0.종료] ");  
	// 	System.out.println("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
	// 	System.out.println("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");	
	// 	System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리] [0.뒤로가기] ");
}