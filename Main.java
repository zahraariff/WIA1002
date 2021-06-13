
package alwaysontime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        //Create MCT, DFS and Greedy objects
        MCT mct2 = new MCT(4,10,43);
        DFS dfs = new DFS(42);
	Greedy greedy = new Greedy(43);
        
        //Read input from text file
        String file = "C:\\Users\\user\\Documents\\WIA1002 DS\\sample_AlwaysOnTime1.txt"; //path
	ArrayList<Customer> cust = new ArrayList<>(); //Declare an ArrayList
	try {
		Scanner s = new Scanner(new FileInputStream(file));
		//FileInputStream f = new FileInputStream(file);
		while(s.hasNext()) {
                        // Input format: x y demand ID
			int x = s.nextInt();
			int y = s.nextInt();
			int demand = s.nextInt();
			int id = s.nextInt();
                        //Add customer to ArrayList
			cust.add(new Customer(x, y, demand, id));
			
		}
		s.close();
	}
	catch(FileNotFoundException e) {
		System.out.println("File not found");
	}
        //First customer is the depot
	Tour.depot = cust.get(0);
        //All the other customers are added
	for(int i = 1; i < cust.size() ; i++) {
		Tour.allCustomer.add(cust.get(i));
	}
        
        
        mct2.search(4,150).displayMCTS_Tour(mct2);
        System.out.println("");
        System.out.println("Depth First Search");
        dfs.rollout();
        System.out.println("Greedy Search\n");
        greedy.rollout();
        
 
        
    }
}

