package alwaysontime;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
/**
 *
 * @author adina
 */
public class MCT{
    int rollout=0;
    Tour new_tour;
    Tour best_tour;
    private final int ALPHA = 1;
    private final int vehicleCapacity;
    double[][][]policy;
    double[][]globalPolicy;
    long elapsed;
    int progress;
    
     public MCT(int level, int N, int vehicleCapacity) {
        this.vehicleCapacity=vehicleCapacity;
        policy=new double[level][N][N];
        globalPolicy=new double[N][N];
        best_tour=new Tour(vehicleCapacity);
        elapsed = 0;
        progress = 0;
        //set policy
        for(int i = 0; i < level; i++){
            for(int j=0; j<N; j++){
                for(int k=0; k<N; k++){
                    policy[i][j][k] = 0;
                }
            }
        }
        //set global policy
        for(int j=0; j<N; j++){
            for(int k=0; k<N; k++){
                globalPolicy[j][k] = 0;
            }
        }
    }
    
    public long getElapsed(){
        return this.elapsed/1000;
    }
    
    public Tour search(int level,int iterations){
        if(progress==0){
            System.out.print("Progress: |");
        }
        progress++;
        if(progress % 30000 == 0){
            System.out.print("=");
        }
        best_tour.setTourCost(Double.POSITIVE_INFINITY);
        Instant start = Instant.now();
        if(level==0){
            rollout++;
           // System.out.println("Rollout: "+rollout);
            return rollout();
        }else{  
            policy[level-1]=globalPolicy;
            for(int i=0;i<iterations;i++){
               // System.out.println("SEARCH");
               // System.out.println("level: "+level);
               // System.out.println("I: "+i);
                new_tour=search(level-1,i);
                //System.out.println("Before, best tour: "+best_tour.getTourCost());
                if(new_tour.getTourCost()<best_tour.getTourCost()){
                    best_tour=new_tour;
                    //System.out.println("after, best tour: "+best_tour.getTourCost());
                    adapt(best_tour, level);
                    // if the searching time is far too long then directly 
                    //return the best tour we can search of after limited time
                }
                Instant end = Instant.now();
                    Duration timeElapsed = Duration.between(start,end);
                    elapsed = timeElapsed.toMillis();
                    if(elapsed >= 35*1000){
                        return best_tour;
                    }
            }
            globalPolicy=policy[level];
       }
        return best_tour;
    }
        
    public void adapt(Tour a_tour, int level){
        //System.out.println("-----ADAPTING-----");
        MyLinkedList<Customer>visited = new MyLinkedList<>();
        for(int i=0;i<a_tour.tourRoutes.getSize();i++){ //for every route in a_tour
            MyLinkedList<Customer>stops=a_tour.tourRoutes.get(i).stopLinkedList();
            //System.out.println("Route "+(i+1));
            for(int j=0;j<stops.getSize()-1;j++){ //for every stop in route 
                //System.out.print(stops.get(j)+" ");
//                if(visited.contains(stops.get(j))){
//                    System.out.print("(already visited) ");
//                }else{
//                    System.out.print("(visited) ");
//                }
                policy[level][stops.get(j).ID][stops.get(j+1).ID] += ALPHA;
                double z=0;
                MyLinkedList<Customer> possible_move = new MyLinkedList<>();
                possible_move=possible_move.clone(Tour.allCustomer);//create a list for customers other than the current stop
                possible_move.remove(stops.get(j));//remove current stop
                for(int n=0;n<possible_move.getSize();n++){
                    if(!visited.contains(possible_move.get(n)))
                        z+=Math.exp(globalPolicy[stops.get(j).ID][possible_move.get(n).ID]);
                }
                for(int n=0;n<possible_move.getSize();n++){
                    if(!visited.contains(possible_move.get(n)))
                        policy[level][stops.get(j).ID][possible_move.get(n).ID]-=ALPHA*(Math.exp(globalPolicy[stops.get(j).ID][possible_move.get(n).ID])/z);
                }
                visited.add(stops.get(j));//set stop as visited  
            }
           // System.out.println();
        }
       // System.out.println();
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
                    //new_tour.displayMCTS_Tour();//Display the tour
                    break;//rollout process is done
                }
                //System.out.println("New Route");
                new_tour.newRoute();//else add new vehicle, again start at depot with ID 0
                check.clear();//uncheck all unvisited customer for new route
                //skip to next loop to continue search a route for new vehicle
                continue;
            }
            Customer nextStop = select_next_move(currentStop, possible_successors);
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
    
    
    public Customer select_next_move(Customer currentStop, MyLinkedList<Customer>possible_successors){
        double[]probability=new double[possible_successors.getSize()];
        double sum=0;
        for(int i=0;i<possible_successors.getSize();i++){
            probability[i]=Math.exp(globalPolicy[currentStop.ID][possible_successors.get(i).ID]);
            sum+=probability[i];
        }
        double mrand=new Random().nextDouble()*sum;
        int i=0;
        sum=probability[0];
        while(sum<mrand){
            sum+=probability[++i];
        }
        return possible_successors.get(i);
    }
}
