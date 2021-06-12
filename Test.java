
package alwaysontime;

public class Test {
    public static void main(String[] args) {
        MCT mct = new MCT(4,20,48);
        Greedy g = new Greedy(48);
        DFS d = new DFS(48);
        
        Customer depot = new Customer(11,52,0,0);
        Customer c1 = new Customer(187,57,0,1);
        Customer c2 = new Customer(29,33,28,2);
        Customer c3 = new Customer(165,169,36,3);
        Customer c4 = new Customer(198,11,26,4);
        Customer c5 = new Customer(28,44,22,5);
        Customer c6 = new Customer(99,78,48,6);
        Customer c7 = new Customer(8,18,45,7);
        Customer c8 = new Customer(32,86,38,8); 
        Customer c9 = new Customer(8,129,34,9);
        Customer c10 = new Customer(33,41,35,10);
        Customer c11 = new Customer(180,56,14,11);
        
        
        Tour.depot=depot;
        Tour.allCustomer.add(c1);
        Tour.allCustomer.add(c2);
        Tour.allCustomer.add(c3);
        Tour.allCustomer.add(c4);
        Tour.allCustomer.add(c5);
        Tour.allCustomer.add(c6);
        Tour.allCustomer.add(c7);
        Tour.allCustomer.add(c8);
        Tour.allCustomer.add(c9);
        Tour.allCustomer.add(c10);
        Tour.allCustomer.add(c11);
        
        mct.search(4,150).displayMCTS_Tour(mct);
        System.out.println("Depth First Search");
        d.rollout();
        System.out.println("Greedy Search");
        g.rollout();
    }
}
