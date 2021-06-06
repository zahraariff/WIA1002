/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alwaysontime;

public class Customer implements Comparable<Customer>{
    int demand,ID;
    int x,y;
    private boolean visited;

    public Customer(int x, int y, int demand, int ID) {
        this.x=x;
        this.y=y;
        this.demand = demand;
        this.ID = ID;
        visited=false;
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
