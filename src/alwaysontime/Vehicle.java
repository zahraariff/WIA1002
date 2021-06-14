package alwaysontime;

/**
 *
 * @author adina
 */
public class Vehicle{
    private final int capacity;//for max capacity of all vehicles
    private int size;//current capacity of the vehicle
    private final int vehicleID; //To identify each vehicle

    //default constructor for vehicle with capacity 10
    public Vehicle(int vehicleID){
        this.vehicleID=vehicleID;
        capacity=10;
    }
    
    //constructor to set vehicle with a different capacity
    public Vehicle(int capacity,int vehicleID) {
        this.vehicleID=vehicleID;
        this.capacity = capacity;
    }

    public int getVehicleID() {
        return vehicleID;
    }
    
    //method to return the remaining capacity
    public int remainingCapacity(){
        return capacity-size;
    }
    
    //Increase the size of demand
    public void addDemand(int demand){
        size+=demand;
    }
    
    //Decrease the size of demand
    public void subDemand(int demand){
        size-=demand;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Vehicle: "+vehicleID+" Capacity: "+size;
    }
    
    
}
