/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime;

public class Main {
    public static void main(String[] args) {
        
//        MCT mct1 = new MCT(3,5,10);//Level,N,C
//        Customer depot = new Customer(86,22,0,0);
//        Customer c1 = new Customer(29,17,1,1);
//        Customer c2 = new Customer(4,50,8,2);
//        Customer c3 = new Customer(25,13,6,3);
//        Customer c4 = new Customer(67,37,5,4);
//        Tour.depot=depot;
//        Tour.allCustomer.add(c1);
//        Tour.allCustomer.add(c2);
//        Tour.allCustomer.add(c3);
//        Tour.allCustomer.add(c4);
//        mct1.search(4,150).displayMCTS_Tour();
        
        
        
        MCT mct2 = new MCT(4,10,43);
        Customer depot = new Customer(22,190,0,0);
        Customer c1 = new Customer(23,101,14,1);
        Customer c2 = new Customer(54,34,15,2);
        Customer c3 = new Customer(55,62,5,3);
        Customer c4 = new Customer(28,101,32,4);
        Customer c5 = new Customer(137,8,27,5);
        Customer c6 = new Customer(148,152,42,6);
        Customer c7 = new Customer(113,131,24,7);
        Customer c8 = new Customer(175,178,39,8); 
        Customer c9 = new Customer(144,22,34,9);
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
        mct2.search(4,150).displayMCTS_Tour(mct2);
        
        
        
        /*
        MCT mct3 = new MCT(4,11,52);
        Customer depot = new Customer(185,57,0,0);
        Customer c1 = new Customer(29,33,28,1);
        Customer c2 = new Customer(165,169,36,2);
        Customer c3 = new Customer(198,11,26,3);
        Customer c4 = new Customer(28,44,22,4);
        Customer c5 = new Customer(99,78,48,5);
        Customer c6 = new Customer(8,18,45,6);
        Customer c7 = new Customer(32,86,38,7);
        Customer c8 = new Customer(8,129,34,8); 
        Customer c9 = new Customer(33,41,35,9);
        Customer c10 = new Customer(180,56,14,10);
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
        mct3.search(4,150).displayMCTS_Tour();
        */
        
    }
}

