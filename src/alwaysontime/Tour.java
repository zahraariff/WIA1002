package alwaysontime;

/**
 *
 * @author adina
 */

public class Tour{
    MyLinkedList<RouteGraph<Customer, Double>> tourRoutes; //Linked list of routes in a tour
    public static MyLinkedList<Customer> allCustomer = new MyLinkedList<>(); // Linked List for all custome
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
    
    //Get the tour routes
    public MyLinkedList<RouteGraph<Customer, Double>> getTourRoutes() {
        return tourRoutes;
    }
    
    //Constructor to set tour with a complete route list
    public Tour(MyLinkedList<RouteGraph<Customer,Double>> tourRoutes) {
        this.tourRoutes = tourRoutes;
    }
    
    //To set the depot
    public void setDepot(Customer depot){
        Tour.depot = depot;
    }
    
    //method to get route cost by distance using euclidean formula
    public static double getStopCost(Customer source, Customer destination){
        double result=Math.sqrt(Math.pow(source.getX() - destination.getX(), 2) +Math.pow(source.getY() - destination.getY(), 2));
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
    
    //create new Route
    public void newRoute(){
        //Increment vehicleID everytime a new vehicle is used for a new route
        vehicleID++; 
        //Declare a new route and assign a new vehicle as a parameter
        RouteGraph<Customer,Double>newRoute= new RouteGraph<>(new Vehicle(vehicleCapacity,vehicleID));
        //add the depot into the route as the first stop
        newRoute.addVertex(depot);
        //add new route to the tourRoutes list
        tourRoutes.add(newRoute);
    }
    
    //To get all locations which are NOT visited yet 
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
    public String displayMCTS(MCT m){
       String output="";
       output+="MCTS Simulation\nTour\nTour Cost: "+getTourCost()+"\n";
        for(int i=0;i<tourRoutes.getSize();i++){
            output+="Vehicle "+tourRoutes.get(i).vehicle.getVehicleID()+"\n";
            output+=tourRoutes.get(i).printRoute()+"\n";
            output+="Capacity: "+tourRoutes.get(i).vehicle.getSize()+"\n";
            output+="Cost: "+tourRoutes.get(i).getRouteCost()+"\n";
        }
        output+="- Time elapsed: " + m.getElapsed() + " seconds -\n";
        return output;
    }
    
    //Display the resultant tour from DFS
    public String displayDFS(DFS dfs) {
        String output="";
	output+="Basic Simulation \nTour\nTour Cost: "+getTourCost()+"\n";
            for(int i = 0; i<tourRoutes.getSize(); i++) {
                //Only prints out non-empty routes
                //Empty routes have two stops which is 0->0
                //if(tourRoutes.get(i).getSize() > 2){
		output+="Vehicle "+tourRoutes.get(i).vehicle.getVehicleID()+"\n";
                output+=tourRoutes.get(i).printRoute()+"\n";
                output+="Capacity: "+tourRoutes.get(i).vehicle.getSize()+"\n";
                output+="Cost: "+tourRoutes.get(i).getRouteCost()+"\n";
                //}
            }
	output+="- Time elapsed: " + dfs.getElapsed() + " seconds -\n";
        return output;
    }
	  
    //Display the resultant tour from Greedy
    public String displayGreedy(Greedy g) {
        String output="";
        output+="Greedy Simulation\nTour\nTour Cost: "+getTourCost()+"\n";
	for(int i = 0; i<tourRoutes.getSize(); i++) {
            output+="Vehicle "+tourRoutes.get(i).vehicle.getVehicleID()+"\n";
            output+=tourRoutes.get(i).printRoute()+"\n";
            output+="Capacity: "+tourRoutes.get(i).vehicle.getSize()+"\n";
            output+="Cost: "+tourRoutes.get(i).getRouteCost()+"\n";
	}
        output+="- Time elapsed: " + g.getElapsed() + " seconds -\n";
        return output;
    }
    
    
    
    
}
