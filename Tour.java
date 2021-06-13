/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime;

import java.util.Stack;

public class Tour{
    MyLinkedList<RouteGraph<Customer, Double>> tourRoutes; //Linked list of routes in a tour
    public static MyLinkedList<Customer> allCustomer = new MyLinkedList<>(); // Linked List for all customer
    Stack<Customer> stack = new Stack<>();
    Stack<Customer> visited = new Stack<>();
    public static Customer depot; //A static depot variable(the depot is the same for all routes/tours
    private double tourCost; //Each tour has a cost (Sum of all route cost)
    private int vehicleID; //To identify each vehicle in a tour
    private int vehicleCapacity; //To set the capacity of each vehicle
    
    //Default constructor 
    public Tour(int vehicleCapacity) {
        this.vehicleCapacity=vehicleCapacity;
        //Iniatilize vehicleID 
        vehicleID=0;
        
        //create a tour route list when tour object is created
        tourRoutes= new MyLinkedList<>();
        //Unvisit all the customer for new tour
        for(int i=0;i<allCustomer.getSize();i++){
            allCustomer.get(i).setUnvisited();
        }
    }
    
    
        //create a tour route list when tour object is created
    public void newRouteMCTS(){
        //Increment vehicleID everytime a new vehicle is used for a new route
        vehicleID++;
        //Declare a new route and assign a new vehicle as a parameter
        RouteGraph<Customer,Double>newRoute= new RouteGraph<>(new Vehicle(vehicleCapacity,vehicleID));
         //add the depot into the route as the first stop
        newRoute.addVertex(depot);
        //add new route to the tourRoutes list
        tourRoutes.add(newRoute);
        //stack.push(depot);
    }
    
//    public void newRouteDFS(){
//	vehicleID++;
//	//Declare a new route and assign a new vehicle as a parameter
//	RouteGraph<Customer,Double> newRoute= new RouteGraph<>(new Vehicle(vehicleCapacity, vehicleID));
//	//add the depot into the route as the first stop
//	//stack.push(depot); // tour.stack.currentRoute.push
//    }
    
    //Constructor to set tour with a complete route list
    public Tour(MyLinkedList<RouteGraph<Customer,Double>> tourRoutes) {
        this.tourRoutes = tourRoutes;
    }
    
    //To get the depot
    public void setDepot(Customer depot){
        Tour.depot = depot;
    }
    
    //method to get route cost by distance using euclidean formula
    public static double getStopCost(Customer source, Customer destination){
        double result=Math.sqrt(Math.pow(source.x - destination.x, 2) +
                Math.pow(source.y - destination.y, 2) * 1.0);
        return result;
    }
    
    //set tour cost
    public void setTourCost(double tourCost) {
        this.tourCost = tourCost;
    }
    
    //To calculate the tour cost
    public double calculateTourCost(){
        tourCost=0;
        for(int i=0;i<tourRoutes.getSize();i++){
            tourCost+=tourRoutes.get(i).getRouteCost();
        }
        return tourCost;
    }
    
    //method to get the tour cost
    public double getTourCost(){
        return tourCost;
    }
    
    //To get all customers which are NOT visited yet
    public MyLinkedList<Customer>unvisitedCustomers(){
        MyLinkedList<Customer> list = new MyLinkedList<>();
        for(int i=0;i<allCustomer.getSize();i++){
            if(!allCustomer.get(i).isVisited())
                list.add(allCustomer.get(i));
        }
        return list;
    }
 
    //To get all visited customers
    public MyLinkedList<Customer>visitedCustomers(){
        MyLinkedList<Customer> list = new MyLinkedList<>();
        for(int i=0;i<allCustomer.getSize();i++){
            if(allCustomer.get(i).isVisited())
                list.add(allCustomer.get(i));
        }
        return list;
    }
    
    //Display the resultant tour from MCTS
    public void displayMCTS_Tour(MCT m){
        System.out.println("\nMCTS Simulation\nTour\nTour Cost: "+getTourCost());
        for(int i=0;i<tourRoutes.getSize();i++){
            System.out.println("Vehicle "+tourRoutes.get(i).vehicle.getVehicleID());
            if(tourRoutes.get(i).getSize() != 2){
                System.out.println(tourRoutes.get(i).printRoute());
            }
            System.out.println("Capacity: "+tourRoutes.get(i).vehicle.getSize());
        }
        System.out.println("** Time elapsed: " + m.getElapsed() + " seconds **");
    }
    
    public void displayDFS(DFS dfs) {
	System.out.println("\nBasic Simulation \nTour\nTour Cost: "+getTourCost());
            for(int i = 0; i<tourRoutes.getSize(); i++) {
                //Only prints out non-empty routes
                //Empty routes have two stops which is 0->0
                if(tourRoutes.get(i).getSize() > 2){
                System.out.println();
		System.out.println("Vehicle "+tourRoutes.get(i).vehicle.getVehicleID());
                    System.out.println(tourRoutes.get(i).printRoute());
                    System.out.println("Capacity: "+tourRoutes.get(i).vehicle.getSize());
                    System.out.println("Cost: "+tourRoutes.get(i).getRouteCost());
                }
            }
	System.out.println("** Time elapsed: " + dfs.getElapsed() + "seconds **\n");
    }
	    
    public void displayGreedy(Greedy g) {
        System.out.println("Greedy \nTour\nTour Cost: "+getTourCost());
	for(int i = 0; i<tourRoutes.getSize(); i++) {
            System.out.println();
            System.out.println("Vehicle "+tourRoutes.get(i).vehicle.getVehicleID());
            System.out.println(tourRoutes.get(i).printRoute());
            System.out.println("Capacity: "+tourRoutes.get(i).vehicle.getSize());
            System.out.println("Cost: "+tourRoutes.get(i).getRouteCost());
	}
        System.out.println("** Time elapsed: " + g.getElapsed() + "seconds **\n");
    }
     
}