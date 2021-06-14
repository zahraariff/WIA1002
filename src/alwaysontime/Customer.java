package alwaysontime;

//Each customer represents a location
public class Customer implements Comparable<Customer>{
    int demand,ID;
    double x,y;
    private boolean visited;

    public Customer(double x, double y, int demand, int ID) {
        this.x=x;
        this.y=y;
        this.demand = demand;
        this.ID = ID;
        visited=false;
    }
    
    //get x
    public double getX() {
        return x;
    }

    //get y
    public double getY() {
        return y;
    }
    
    //get iD
    public int getID() {
        return ID;
    }
    
    //get demand
    public int getDemand() {
        return demand;
    }
    
    //set a stop as visited
    public void setVisited() {
        visited=true;
    }
    
    //set stop as unvisited
    public void setUnvisited(){
        visited=false;
    }
    
    //check if customer is visited
    public boolean isVisited() {
        return visited;
    }
    
    
    @Override
    public int compareTo(Customer o) {
        if(this.ID>o.ID)
            return 1;
        else if(this.ID<o.ID)
            return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return ""+ID;
    }

    
    
}
