package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import dao.*;

public class FileManager {
  // cart.txt
	// user.txt
	// item.txt
	String CUR_PATH = System.getProperty("user.dir") + "\\src\\"+ getClass().getPackageName()+"\\"; 
	File file;
	public void loadData(CartDAO cDAO,UserDAO uDAO,ItemDAO iDAO) {
		String cartData = loadFromFile("cart.txt");
		String userData = loadFromFile("user.txt");
		String itemData = loadFromFile("item.txt");
		if(cartData != null) {
			cDAO.loadDataFromFile(cartData);
		}
		if(userData != null) {
			uDAO.loadDataFromFile(userData);
		}
		if(itemData != null) {
			iDAO.loadDataFromFile(itemData);
		}
	}
	public void saveData(CartDAO cDAO,UserDAO uDAO,ItemDAO iDAO) {
		String cartData = cDAO.getData();
		String userData = uDAO.getData();
		String itemData = iDAO.getData();
//		System.out.println(cartData);
//		System.out.println(userData);
//		System.out.println(itemData);
		saveDataToFile("cart.txt", cartData);
		saveDataToFile("user.txt", userData);
		saveDataToFile("item.txt", itemData);
	}
	private void saveDataToFile(String fileName,String data) {
		try (FileWriter fw = new FileWriter(CUR_PATH + fileName)){
			fw.write(data);
//			System.out.println("저장성공");
		} catch (IOException e) {
			System.out.println("저장실패");
		}
	}
	private String loadFromFile(String fileName) {
		String data = "";
		try {
			FileReader fr = new FileReader(CUR_PATH + fileName);
			BufferedReader br = new BufferedReader(fr);
			while(true) {
				String str = br.readLine();
				if(str == null) {
					break;
				}
				data += str + "\n";
			}
			System.out.println(fileName + "로드 성공"); 
		} catch (IOException e) {
			System.out.println(fileName + "로드 실패");
		}
		if(data.isEmpty()) {
			return null;
		}
		data = data.substring(0,data.length()-1);
		return data;
			
		
			
			
	}
	public FileManager() {
		init("cart.txt");
		init("user.txt");
		init("item.txt");
	}
	private void init(String fileName) {
		file = new File(CUR_PATH + fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(fileName+"파일 생성 성공");
			} catch (IOException e) {
				System.out.println(fileName+"파일 생성 성공");
			}
		}
			
	}
}
