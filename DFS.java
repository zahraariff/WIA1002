
package alwaysontime;

import java.time.Duration;
import java.time.Instant;

public class DFS {
    long elapsed;
    int progress;
    
    Tour new_tour;
	private final int vehicleCapacity;
    //Tour best_tour = new Tour();
    
    public DFS(int vehicleCapacity) {
    	this.vehicleCapacity=vehicleCapacity;	
        progress=0;
    }

    public Tour rollout(){
        Instant start = Instant.now();
         if(progress==0){
            System.out.print("Progress: |");
        }
        
        //declare check list
        MyLinkedList<Customer>check = new MyLinkedList();
        //initialize new_tour with first route with first stop at 0
        new_tour= new Tour(vehicleCapacity);
        new_tour.newRoute();
        while(true){
            progress++;
            if(progress % 500000 == 0){
            System.out.print("=");
            }
            //declare possible_successors
            MyLinkedList<Customer>possible_successors=new MyLinkedList();
            Customer currentStop = new_tour.tourRoutes.tail.element.getLastVertex();
            //System.out.println("\ncurrent stop  : "+ currentStop);
            RouteGraph<Customer,Double> currentRoute = new_tour.tourRoutes.getLast();
            //find every possible successors that is not yet checked for the currentStop
            for(int n=0;n<new_tour.unvisitedCustomers().getSize();n++){
                if(!check.contains(new_tour.unvisitedCustomers().get(n))){
                    possible_successors.add(new_tour.unvisitedCustomers().get(n));
                    //System.out.println("Possible successor: "+new_tour.unvisitedCustomers().get(n));
                }
            }     
            //System.out.println("Possible successors : " +possible_successors.toString() );
            if(possible_successors.isEmpty()){
                //currentRoute is completed and should return to depot
                currentRoute.endRoute(currentStop, Tour.depot, Tour.getStopCost(currentStop, Tour.depot));
                if(new_tour.unvisitedCustomers().isEmpty()){
                    new_tour.setTourCost(new_tour.calculateTourCost());//set tour cost
                    new_tour.displayDFS(this);//Display the tour
                    return new_tour;//rollout process is done
                }
                //System.out.println("New Route");
                new_tour.newRoute();//else add new vehicle, again start at depot with ID 0
                check.clear();//uncheck all unvisited customer for new route
                //skip to next loop to continue search a route for new vehicle
                continue;
            }
            //System.out.println("test: " +possible_successors.getFirst());
            //System.out.println(possible_successors.toString());
            Customer nextStop = getNearest(currentStop, possible_successors,currentRoute) ; // fix
            if(nextStop.demand<=currentRoute.vehicle.remainingCapacity()){
                currentRoute.addStop(currentStop, nextStop, Tour.getStopCost(currentStop, nextStop));//add nextStop to currentRoute
                currentRoute.vehicle.addDemand(nextStop.demand);
                nextStop.setVisited();//set nextStop as visited
                //System.out.println("Visited: "+nextStop);
            }else{
                //System.out.println("Checked: "+nextStop);
                //check.add(nextStop);//set nextStop as checked
            	currentRoute.endRoute(currentStop, Tour.depot, Tour.getStopCost(currentStop, Tour.depot));
                if(new_tour.unvisitedCustomers().isEmpty()){
                    new_tour.setTourCost(new_tour.calculateTourCost());//set tour cost
                    new_tour.displayDFS(this);//Display the tour
                    return new_tour;//rollout process is done
                }
                //System.out.println("New Route");
                new_tour.newRoute();//else add new vehicle, again start at depot with ID 0
                check.clear();//uncheck all unvisited customer for new route
                //skip to next loop to continue search a route for new vehicle
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start,end);
                elapsed = timeElapsed.toMillis();
                if(elapsed >= 35*1000){
                    new_tour.displayDFS(this);
                    return new_tour;
                }
                continue;
            }
            
            //System.out.println("Current route : "+ currentRoute.printRoute());
            }
        }
   
    
    public long getElapsed(){
        return this.elapsed/1000;
    }
	 
 // - - - - - - - - - - - - - - - - - - - METHOD - - - - - - - - - - - - - - - - - - - - - - - - - 
    
     public static Customer getNearest(Customer currentStop, MyLinkedList<Customer> list, RouteGraph<Customer,Double> currentRoute) {
    	 int index = 0;
    	 //double tempDist = Integer.MAX_VALUE;
    	 
    	 MyLinkedList<Customer> tempList = new MyLinkedList<>(); // just added
    	 for(int i = 0; i < list.getSize() ; i++) {
    		 if(currentStop.ID == list.get(i).ID) {
    			continue;
    		 }
			 double cost = Tour.getStopCost(list.get(i), currentStop);
			 //System.out.println("cost id : "+ currentStop.ID+" to "+ list.get(i).ID+" : "+ cost);
                         
                         //what this line supposed to do?
                         //if((list.get(i).size < currentRoute.vehicle.remainingCapacity()))
                         //is it the list size? how many customer more?
			 if((list.get(i).demand < currentRoute.vehicle.remainingCapacity())) {
				 //System.out.println("id: " +list.get(i).ID + ", size : "+list.get(i).demand+", remaining: "+currentRoute.vehicle.remainingCapacity());
				 tempList.add(list.get(i));
				 index = i;
    		 } 
    	 }

    	 if(!tempList.isEmpty()) {
    		 return bigCap(currentStop, tempList);
    	 }
    	 return list.get(index);
    	 
     }
     
     // 5 2 6 3 0
     public static Customer bigCap(Customer currentStop, MyLinkedList<Customer> list){
    	 int index = 0;
    	 Customer returnCust = list.getFirst(); 
    	 double tempDist = Tour.getStopCost(list.getFirst(), currentStop);
    	 for(int k = 1; k < list.getSize() ; k++) {
    		 // test 1 - true but inverted order
//    		 if((Tour.getStopCost(list.get(k-1), currentStop) < Tour.getStopCost(list.get(k), currentStop))) {
//			 index = k;
//			 returnCust = list.get(index);
//    		 }
    		 
    		 
    		 //test 2 - true but not in the right order
//    		 if(list.get(k-1).size > list.get(k).size ) {
//    			 returnCust = list.get(k-1);
//    			 continue;
//    		 }

        		 if(list.get(k-1).demand > list.get(k).demand ) {
        			 returnCust = list.get(k-1);
        			 continue;
        		 }
        		 returnCust = list.get(k);
    	 }
    	 return returnCust;
     }
    
	 public static Customer getLow(Customer currentStop, MyLinkedList<Customer> list) {
			double min = 0;
			int index = 0;
			double tempDist = Integer.MAX_VALUE;
			for(int i = 0; i<list.getSize(); i++) {
				double cost = Tour.getStopCost(list.get(i), currentStop);
				if( cost < tempDist) {
					 min = cost;
					 cost = tempDist;
					 tempDist = min;
					 index = i;
				}
			}
			return list.get(index);
	 }
	 
	public static Customer getBigCap(MyLinkedList<Customer> list) {
	 Customer bigCap = list.get(0);	
	 int big = list.get(0).demand;
		for(int i = 1 ; i<list.getSize() ; i++) {
			if(list.get(i).demand > big) {
				bigCap = list.get(i);
			}
		}
		//System.out.println("bigcap "+ bigCap.id +" "+ bigCap.demand);
		return bigCap;
	}
}
        

