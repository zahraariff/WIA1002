
package alwaysontime;

public class Greedy {
    Tour new_tour;
    private final int vehicleCapacity;
    long elapsed;
    //Tour best_tour = new Tour();
    
    public Greedy(int vehicleCapacity) {
    	this.vehicleCapacity=vehicleCapacity;	
    }

    public Tour rollout(){
    //declare check list
    MyLinkedList<Customer>check = new MyLinkedList();
    //initialize new_tour with first route with first stop at 0
    new_tour= new Tour(vehicleCapacity);
    new_tour.newRoute();
    while(true){
            //declare possible_successors
        MyLinkedList<Customer>possible_successors=new MyLinkedList();
        Customer currentStop = new_tour.tourRoutes.tail.element.getLastVertex();
        RouteGraph<Customer,Double> currentRoute = new_tour.tourRoutes.getLast();
        //find every possible successors that is not yet checked for the currentStop
        for(int n=0;n<new_tour.unvisitedCustomers().getSize();n++){
            if(!check.contains(new_tour.unvisitedCustomers().get(n))){
                possible_successors.add(new_tour.unvisitedCustomers().get(n));
                //System.out.println("Possible successor: "+new_tour.unvisitedCustomers().get(n));
            }
        }        
        if(possible_successors.isEmpty()){
        //currentRoute is completed and should return to depot
            currentRoute.endRoute(currentStop, Tour.depot, Tour.getStopCost(currentStop, Tour.depot));
                if(new_tour.unvisitedCustomers().isEmpty()){
                    new_tour.setTourCost(new_tour.calculateTourCost());//set tour cost
                    new_tour.displayGreedy(this);//Display the tour // add displayGreedy() method in Tour class
                    break;//rollout process is done
                }
                //System.out.println("New Route");
                new_tour.newRoute();//else add new vehicle, again start at depot with ID 0
                check.clear();//uncheck all unvisited customer for new route
                //skip to next loop to continue search a route for new vehicle
                continue;
            }
            Customer nextStop = getLow(currentStop, possible_successors);
            if(nextStop.demand<=currentRoute.vehicle.remainingCapacity()){
                currentRoute.addStop(currentStop, nextStop, Tour.getStopCost(currentStop, nextStop));//add nextStop to currentRoute
                currentRoute.vehicle.addDemand(nextStop.demand);
                nextStop.setVisited();//set nextStop as visited
                //System.out.println("Visited: "+nextStop);
            }else{
                //System.out.println("Checked: "+nextStop);
                check.add(nextStop);//set nextStop as checked
            }
        }
        return new_tour;
    }
        
        public long getElapsed(){
        return this.elapsed/1000;
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
}

