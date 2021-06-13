package alwaysontime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main3 {
public static void main(String[] args) {
        DFS dfs = new DFS(24);
	Greedy greedy = new Greedy(81);
        MCT mct2 = new MCT(4,50,81);
        
	String file = "C:\\Users\\user\\Documents\\WIA1002 DS\\sample_AlwaysOnTime.txt"; //path
	ArrayList<Customer> cust = new ArrayList<>();
	try {
		Scanner s = new Scanner(new FileInputStream(file));
		//FileInputStream f = new FileInputStream(file);
		while(s.hasNext()) {
			int x = s.nextInt();
			int y = s.nextInt();
			int demand = s.nextInt();
			int id = s.nextInt();
			cust.add(new Customer(x, y, demand, id));
			
		}
		s.close();
	}
	catch(FileNotFoundException e) {
		System.out.println("File not found");
	}
	Tour.depot = cust.get(0);
	for(int i = 1; i < cust.size() ; i++) {
		Tour.allCustomer.add(cust.get(i));
	}	
        
        mct2.search(4,150).displayMCTS_Tour(mct2);
        System.out.println("");
        System.out.println("Depth First Search");
        dfs.rollout();
        System.out.println("Greedy Search");
        greedy.rollout();
}
}
