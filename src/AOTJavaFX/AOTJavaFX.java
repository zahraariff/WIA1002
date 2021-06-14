/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AOTJavaFX;

import alwaysontime.Customer;
import alwaysontime.DFS;
import alwaysontime.Greedy;
import alwaysontime.MCT;
import alwaysontime.MyLinkedList;
import alwaysontime.RouteGraph;
import alwaysontime.Tour;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author adina
 */
public class AOTJavaFX extends Application {
    int CustomerID=-1, NoOfLocationsLimit, NoOfLocationsSet=0, customerDemand=0;
    Boolean demandSet=true;
    TextField capacityLimit_TextField;
    Text invalidDemand_Text=null;
    Text locationsText=null;
    String basicDisplay, greedyDisplay, mctsDisplay;
    Text[]resultTexts = new Text[3];
    
    //BRIEF EXPLANATION OF GUI 
    /* Each scene represents each screen change in the gui
    Scene1: Program start screen page, user inputs the the number of 
            locations/customer here
    Scene2: User inputs the coordinates of each location/customer using mouse.
            When a double-mouse left click is detected, a location/customer
            is added.
    Scene3: Represents the result page, using a TabPane, there are 4 tabs:
            *Search Results - Displays the overall seacrh results of each algorithm.
                              Lists tour cost, routes etc.
            *Basic Search, Greedy Search, - Displays the graph of the tour result 
             MCTS Search.                   with delivery simulation using JavaFX animation.    
    */
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
            
        //For the first scene 
        Group group1 = new Group();
        Scene scene1 = new Scene(group1, 500, 350);
        scene1.setFill(Color.web("#e6e6ff"));
        
        //For the second scene
        Group group2 = new Group();
        Scene scene2 = new Scene(group2, 824, 568);
        scene2.setFill(Color.web("#e6e6ff"));
        
        //rectangle to set graph bounds
        Rectangle rect1 = new Rectangle();  
        rect1.setX(25); 
        rect1.setY(25); 
        rect1.setWidth(775); 
        rect1.setHeight(450); 
        rect1.setArcWidth(30.0); 
        rect1.setArcHeight(20.0);  
        rect1.setFill(Color.WHITE);
        group2.getChildren().add(rect1);
        
        //Set vehicle image to be displayed in scene1(Main Scene)
        Image vehicleImage = new Image(new FileInputStream("C:\\Users\\adina\\OneDrive\\Desktop\\UM Assignments\\Year 1 Sem 2\\WIA1002 Data Structure\\AOT_Truck.png"));
        ImageView vehicleImageView = new ImageView(vehicleImage);
        vehicleImageView.setX(200); 
        vehicleImageView.setY(35);
        vehicleImageView.setFitWidth(110); 
        vehicleImageView.setFitHeight(80); 
        vehicleImageView.setPreserveRatio(true);
        
        primaryStage.setTitle("AlwaysOnTime");
        primaryStage.setScene(scene1);
        
        //Text for "Never On Time "
        Text AlwaysOnTimeText = new Text(140,125,"Always On Time");
        AlwaysOnTimeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25)); 
        AlwaysOnTimeText.setFill(Color.web("#884dff"));
        
        //Text to set number of locations 
        Text NoOfLocationsText = new Text(170,230,"Set Number of Locations to start  \n              (include depot)");
        NoOfLocationsText.setFill(Color.web("#a366ff"));
        
        //Button to set number of locations limit
        Button NoOfLocationsLimitBttn = new Button("Set");
        NoOfLocationsLimitBttn.setStyle("-fx-background-color: #d1b3ff; ");
        NoOfLocationsLimitBttn.setLayoutX(267);
        NoOfLocationsLimitBttn.setLayoutY(170);
        
        //TextField to set number of locations limit
        TextField NoOfLocationsTextField = new TextField();
        NoOfLocationsTextField.setLayoutX(207);
        NoOfLocationsTextField.setLayoutY(170);
        NoOfLocationsTextField.setPrefWidth(50);
        
        //Text for invalid number or locations input
        Text invalidLocationsText = new Text(170,232,"Ïnvalid number of locations");
        invalidLocationsText.setFill(Color.RED);
        
        group1.getChildren().addAll(AlwaysOnTimeText,NoOfLocationsText,NoOfLocationsLimitBttn,NoOfLocationsTextField,vehicleImageView); //Add nodes to the scene
        
        //Set no of locations action handler
        EventHandler<ActionEvent> setNoOfLocationsEvent = (ActionEvent e) -> {
            if(Integer.parseInt(NoOfLocationsTextField.getText())>0){
                NoOfLocationsLimit = Integer.parseInt(NoOfLocationsTextField.getText());
                System.out.println("NUMBER OF LOCATION IS SET");
                System.out.println("Number of locations: "+NoOfLocationsTextField.getText());
                primaryStage.setScene(scene2);
            }else{
                group1.getChildren().remove(invalidLocationsText);
                group1.getChildren().add(invalidLocationsText);
            }
        };
        
        //when number of locations button is pressed
        NoOfLocationsLimitBttn.setOnAction(setNoOfLocationsEvent);
        
        //Text for input instruction
        Text instructionText = new Text("Left double-click on screen to add customer locations\n"
                + "                         (Depot is added first)");
        instructionText.setFill(Color.web("#a366ff"));
        instructionText.setLayoutX(270);
        instructionText.setLayoutY(430);
        group2.getChildren().add(instructionText);

        //Event handler to add locations(Customers) using mouse
        scene2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            group2.getChildren().remove(invalidDemand_Text);
            if(NoOfLocationsLimit>0&&NoOfLocationsSet!=NoOfLocationsLimit&&demandSet==true){
                if(me.getButton().equals(MouseButton.PRIMARY)) {
                    if(me.getClickCount() == 2){//If a double mouse click is detected
                        group2.getChildren().remove(instructionText);
                        demandSet=false;
                        CustomerID++;
                        NoOfLocationsSet++;
                        System.out.println("Locations set: "+NoOfLocationsSet);
                        String ID = String.valueOf(CustomerID);
         
                        if(CustomerID>0){
                            //Display a house image for ID>0
                            Image houseImage=null;
                            try {
                            houseImage = new Image(new FileInputStream("C:\\Users\\adina\\OneDrive\\Desktop\\UM Assignments\\Year 1 Sem 2\\WIA1002 Data Structure\\AOT_House.png"));
                            } catch (FileNotFoundException ex) {
                            Logger.getLogger(AOTJavaFX.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ImageView houseImageView = new ImageView(houseImage);
                            houseImageView.setX(me.getX()-27); 
                            houseImageView.setY(me.getY()-34); 
                            houseImageView.setFitWidth(60); 
                            houseImageView.setFitHeight(60); 
                            houseImageView.setPreserveRatio(true);
                            group2.getChildren().add(houseImageView);
                        }else{
                            //Display depot image for ID==0
                            Image depotImage=null;
                            try {
                            depotImage = new Image(new FileInputStream("C:\\Users\\adina\\OneDrive\\Desktop\\UM Assignments\\Year 1 Sem 2\\WIA1002 Data Structure\\AOT_Depot.png"));
                            } catch (FileNotFoundException ex) {
                            Logger.getLogger(AOTJavaFX.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ImageView depotImageView = new ImageView(depotImage);
                            depotImageView.setX(me.getX()-20); 
                            depotImageView.setY(me.getY()-25); 
                            depotImageView.setFitWidth(45); 
                            depotImageView.setFitHeight(45); 
                            depotImageView.setPreserveRatio(true);
                            group2.getChildren().add(depotImageView);
                        }
                        //Set text to be dispalyed when a location/customer is set
                        String circleText ="ID: "+ID+" ("+me.getX()+","+me.getY()+")";
                        Text text = new Text(me.getX() - 8,me.getY() - 30,circleText);
                        group2.getChildren().add(text);
                        
                        if(CustomerID>0&&NoOfLocationsSet>1){//If the location set is not the depot
                            //Text field to set the demand of each customer
                            TextField demand_TextField=new TextField();
                            demand_TextField.setLayoutX(me.getX()-27);
                            demand_TextField.setLayoutY(me.getY()+17);
                            demand_TextField.setPrefWidth(60);
                            demand_TextField.setPromptText("Demand");
                            //Button to set the demand
                            Button demandBttn = new Button("Set");
                            demandBttn.setLayoutX(me.getX()+40);
                            demandBttn.setLayoutY(me.getY()+16);
                            demandBttn.setStyle("-fx-background-color: #d1b3ff; ");
                            group2.getChildren().addAll(demand_TextField,demandBttn);
                    
                            //Demand button event handler
                            EventHandler<ActionEvent> setDemandEvent = (ActionEvent e) -> {
                                //If text field is not null
                                if (demand_TextField.getText() != null || !demand_TextField.getText().trim().isEmpty()) {

                                    if(Integer.parseInt(demand_TextField.getText())>0){
  
                                        System.out.println("Demand: "+demand_TextField.getText());
                                        group2.getChildren().removeAll(demand_TextField,demandBttn);
                                        text.setText(circleText+" Demand: "+demand_TextField.getText());
                                        customerDemand=Integer.parseInt(demand_TextField.getText());
                                        addCustomer(me.getX(),me.getY());//Add a location/customer
                                        group2.getChildren().remove(invalidDemand_Text);
                                        if(NoOfLocationsSet==NoOfLocationsLimit){
                                            //Text to tell users to wait for simulation results
                                            Text progressText = new Text("Simulation is progressing....");
                                            progressText.setLayoutX(330);
                                            progressText.setLayoutY(550);
                                            progressText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11)); 
                                            group2.getChildren().add(progressText);
      
                                        }
                                        demandSet=true;
                                    }else{
                                        invalidDemand_Text =new Text("Invalid demand");
                                        invalidDemand_Text.setFill(Color.RED);
                                        invalidDemand_Text.setLayoutX(me.getX()-24);
                                        invalidDemand_Text.setLayoutY(me.getY()+57);
                                        group2.getChildren().remove(invalidDemand_Text);
                                        group2.getChildren().add(invalidDemand_Text);
                                    }
                                }else{
                                    Text invalidInput_Text =new Text("No Input detected");
                                    invalidInput_Text.setFill(Color.RED);
                                    invalidInput_Text.setLayoutX(me.getX()-24);
                                    invalidInput_Text.setLayoutY(me.getY()+57);
                                    group2.getChildren().remove(invalidInput_Text);
                                    group2.getChildren().add(invalidInput_Text);
                                }
                            };
                        
                            //When demand button is pressed
                            demandBttn.setOnAction(setDemandEvent);
                        
                        }else{
                            addDepot(me.getX(),me.getY());//Add a location/customer
                            demandSet=true;
                        }
                    } 
                }
            }
        });
        
       
        
        //Text to set vehicle capacity
        Text capacityText = new Text("Set Vehicle Capacity:");
        capacityText.setLayoutX(250);
        capacityText.setLayoutY(520);
      
        //TextField to set vehicle capacity
        capacityLimit_TextField = new TextField();
        capacityLimit_TextField.setLayoutX(370);
        capacityLimit_TextField.setLayoutY(505);
        capacityLimit_TextField.setPrefWidth(50);
 
        //Button to set locations 
        Button locationBttn = new Button("Set Locations");
        locationBttn.setStyle("-fx-background-color: #d1b3ff; ");
        locationBttn.setLayoutX(430);
        locationBttn.setLayoutY(505);
        
        //Create  a tab pane to switch between tabs in scene3
        TabPane TabPane_scene3 = new TabPane();
        TabPane_scene3.setStyle("-fx-background-color: #e6e6ff; ");
        
        //Set capacity action handler
        EventHandler<ActionEvent> setLocationsEvent = (ActionEvent e) -> {
            System.out.println("LOCATION IS SET");
            System.out.println(capacityLimit_TextField.getText());
            
            //For Basic graph(DFS Search)
            Group basicGroup = createGraph(0);
            HBox basicHbox = new HBox();
            basicHbox.getChildren().addAll(basicGroup);
            basicHbox.setAlignment(Pos.CENTER);
           
            //For Greedy graph
            Group greedyGroup = createGraph(1);
            HBox greedyHbox = new HBox();
            greedyHbox.getChildren().addAll(greedyGroup);
            greedyHbox.setAlignment(Pos.CENTER);
            
            //For MCTS graph
            Group mctsGroup = createGraph(2);
            HBox mctsHbox = new HBox();
            mctsHbox.getChildren().addAll(mctsGroup);
            mctsHbox.setAlignment(Pos.CENTER);
  
            //For results display
            StackPane resultsStackPane = new StackPane();
            VBox resultsVbox = new VBox();
            HBox resultsHbox = new HBox();
            resultsHbox.setSpacing(15);
            
            Rectangle rect2 = new Rectangle();  
                rect2.setX(25); 
                rect2.setY(15); 
                rect2.setWidth(775); 
                rect2.setHeight(450);
                rect2.setArcWidth(30.0); 
                rect2.setArcHeight(20.0);  
                rect2.setFill(Color.WHITE);
                resultsStackPane.getChildren().add(rect2);
            
            for(int i=0;i<3;i++){
                resultTexts[i].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13)); 
                resultTexts[i].setFill(Color.web("#1a0033"));
            }
            resultsVbox.setAlignment(Pos.CENTER);
            resultsHbox.setAlignment(Pos.CENTER);
            resultsHbox.getChildren().addAll(resultTexts[0],resultTexts[1],resultTexts[2]);
            resultsVbox.getChildren().add(resultsHbox);
            resultsStackPane.getChildren().add(resultsVbox);
         
            //Set tabs for each search
            Tab results_Tab = new Tab("Simulation Results");
            Tab basicSearch_Tab = new Tab("Basic Simulation");
            Tab greedySearch_Tab = new Tab("Greedy Simulation");
            Tab mctsSearch_Tab = new Tab("MCTS Simulation");
            
            //Disable option to close tabs
            results_Tab.setClosable(false);
            basicSearch_Tab.setClosable(false);
            greedySearch_Tab.setClosable(false);
            mctsSearch_Tab.setClosable(false);
            
            //Set tab colours 
            results_Tab.setStyle("-fx-background-color: #d1b3ff; ");
            basicSearch_Tab.setStyle("-fx-background-color: #d1b3ff; ");
            greedySearch_Tab.setStyle("-fx-background-color: #d1b3ff; ");
            mctsSearch_Tab.setStyle("-fx-background-color: #d1b3ff; ");
            
            //Set tab contents
            results_Tab.setContent(resultsStackPane);
            basicSearch_Tab.setContent(basicHbox);
            greedySearch_Tab.setContent(greedyHbox);
            mctsSearch_Tab.setContent(mctsHbox);
            
            //Add tabs into TabPane
            TabPane_scene3.getTabs().addAll(results_Tab,basicSearch_Tab,greedySearch_Tab,mctsSearch_Tab);
 
            Scene scene3 = new Scene(TabPane_scene3, 824, 568);
            scene3.setFill(Color.web("#e6e6ff"));
            primaryStage.setScene(scene3);
            
        };
        
        // when capacity button is pressed
        locationBttn.setOnAction(setLocationsEvent);
        
        //Add all nodes to the scene
        group2.getChildren().addAll(locationBttn,capacityText,capacityLimit_TextField);
        
        //Show the scene
        primaryStage.show();
    }
    //Method to add depot
    public void addDepot(double x, double y){
        Tour.depot=new Customer(x,y,customerDemand,CustomerID);
        System.out.println("Depot added ID: "+CustomerID+" X: "+x+" Y: "+y);
    }
    
    //Method to add a customer
    public void addCustomer(double x, double y){
        Tour.allCustomer.add(new Customer(x,y,customerDemand,CustomerID));
        System.out.println("Customer added ID: "+CustomerID+" X: "+x+" Y: "+y);
            
       
    }
    

    //0 -> Basic search
    //1 -> Greedy search
    //2 -> Mcts search
    String [] searchType= new String[3];
    Tour []tours=new Tour[3];
    public Group createGraph(int search){
        Group graphGroup = new Group();
        switch (search) {
            case 0:
                DFS dfs = new DFS(Integer.parseInt(capacityLimit_TextField.getText()));
                tours[search]=dfs.rollout();
                tours[search].displayDFS(dfs);
                searchType[search]="Basic(DFS)";
                resultTexts[search] = new Text(tours[search].displayDFS(dfs));
                break;
            case 1:
                Greedy greedy = new Greedy(Integer.parseInt(capacityLimit_TextField.getText()));
                tours[search]=greedy.rollout();
                tours[search].displayGreedy(greedy);
                searchType[search]="Greedy";
                resultTexts[search] = new Text(tours[search].displayGreedy(greedy));
                break;
            case 2:
                MCT mct = new MCT(4,NoOfLocationsLimit,Integer.parseInt(capacityLimit_TextField.getText()));
                tours[search]= mct.search(4,150);
                tours[search].displayMCTS(mct);
                System.out.println(tours[search].displayMCTS(mct));
                searchType[search]="MCTS";
                resultTexts[search] = new Text(tours[search].displayMCTS(mct));
                break;
            default:
                break;
        }
        
        //rectangle to set graph bounds
        Rectangle rect2 = new Rectangle();  
        rect2.setX(25); 
        rect2.setY(25); 
        rect2.setWidth(775); 
        rect2.setHeight(450); 
        rect2.setArcWidth(30.0); 
        rect2.setArcHeight(20.0);  
        rect2.setFill(Color.WHITE);
        graphGroup.getChildren().add(rect2);
    
        // Draw edges for pairs of vertices
        for(int i=0;i<tours[search].getTourRoutes().getSize();i++){
            RouteGraph<Customer,Double> currentRoute = tours[search].getTourRoutes().get(i);
            for(int j=0;j<currentRoute.getSize();j++){
                ArrayList<Customer> neighbours = currentRoute.getNeighbours(currentRoute.getVertex(j));
                double x1 = currentRoute.getVertex(j).getX();
                double y1 = currentRoute.getVertex(j).getY();
                for(Customer v:neighbours){
                    double x2 = currentRoute.getVertex(v).getX();
                    double y2 = currentRoute.getVertex(v).getY();
                
                    // Draw an edge for (i, v)
                    graphGroup.getChildren().add(new Line(x1, y1, x2, y2)); 
                }
            }
        }
        
        //Draw vertices and text for vertices
        for(int i=0;i<tours[search].getTourRoutes().getSize();i++){
            RouteGraph<Customer,Double> currentRoute = tours[search].getTourRoutes().get(i);
            for(int j=0;j<currentRoute.getSize();j++){
                double x = currentRoute.getVertex(j).getX();
                double y = currentRoute.getVertex(j).getY();
                String circleText="ID: "+currentRoute.getVertex(j).getID()+" ("+x+","+y+")";
                if(j>0){
                    circleText +=" Demand: "+currentRoute.getVertex(j).getDemand();
                }
                if(j>0){
                    //Set depot image
                     Image houseImage=null;
                    try {
                        houseImage = new Image(new FileInputStream("C:\\Users\\adina\\OneDrive\\Desktop\\UM Assignments\\Year 1 Sem 2\\WIA1002 Data Structure\\AOT_House.png"));
                    } catch (FileNotFoundException ex) {
                    Logger.getLogger(AOTJavaFX.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ImageView houseImageView = new ImageView(houseImage);
                    houseImageView.setX(x-17); 
                    houseImageView.setY(y-34); 
                    houseImageView.setFitWidth(60); 
                    houseImageView.setFitHeight(60); 
                    houseImageView.setPreserveRatio(true);
                    graphGroup.getChildren().add(houseImageView);
                }else{
                    //Set depot image
                     Image depotImage=null;
                    try {
                        depotImage = new Image(new FileInputStream("C:\\Users\\adina\\OneDrive\\Desktop\\UM Assignments\\Year 1 Sem 2\\WIA1002 Data Structure\\AOT_Depot.png"));
                    } catch (FileNotFoundException ex) {
                    Logger.getLogger(AOTJavaFX.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ImageView depotImageView = new ImageView(depotImage);
                    depotImageView.setX(x-20); 
                    depotImageView.setY(y-25); 
                    depotImageView.setFitWidth(45); 
                    depotImageView.setFitHeight(45); 
                    depotImageView.setPreserveRatio(true);
                    graphGroup.getChildren().add(depotImageView);
                }
                graphGroup.getChildren().add(new Text(x - 8,y-30,circleText)); //display a text
            }
        }
        
        //Add button for delivery simulation
        Button simulationBttn = new Button("Simulate Delivery");
        simulationBttn.setStyle("-fx-background-color: #d1b3ff; ");
        simulationBttn.setLayoutX(350);
        simulationBttn.setLayoutY(495);
        graphGroup.getChildren().add(simulationBttn);
        
        //Set event for graph simulation
        EventHandler<ActionEvent> simulationEvent = (ActionEvent e) -> {
            System.out.println("Simulating Delivery for "+searchType[search]);
            
             ArrayList<Polyline>transitionRoutes = new ArrayList<>();
             ArrayList<Integer>duration=new ArrayList<>();
            for(int i=0;i<tours[search].getTourRoutes().getSize();i++){
                int time=0;
                MyLinkedList<Customer>stops = tours[search].getTourRoutes().get(i).stopLinkedList();
                Polyline polyline = new Polyline();//Initialise polyline for each route
                for(int j=0;j<stops.getSize();j++){
                    double x = stops.get(j).getX();
                    double y = stops.get(j).getY();
                    polyline.getPoints().add(x);
                    polyline.getPoints().add(y);//(int)Math.sqrt(Math.pow(x - stops.get(j+1).getX(), 2) +Math.pow(y - stops.get(j+1).getY(), 2))
                    if(j==stops.getSize()-1){
                        time+=(int)Tour.getStopCost(stops.get(j), Tour.depot);
                    }else{
                        time+=(int)Tour.getStopCost(stops.get(j), stops.get(j+1));
                    }
                }
                duration.add(time);
                transitionRoutes.add(polyline);
            }
            
            for(int i=0;i<transitionRoutes.size();i++){
                PathTransition transition = new PathTransition();
                Image vehicleImage=null;
                try {
                    vehicleImage = new Image(new FileInputStream("C:\\Users\\adina\\OneDrive\\Desktop\\UM Assignments\\Year 1 Sem 2\\WIA1002 Data Structure\\AOT_Truck.png"));
                } catch (FileNotFoundException ex) {
                Logger.getLogger(AOTJavaFX.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImageView vehicleImageView = new ImageView(vehicleImage);
            
                vehicleImageView.setFitWidth(35); 
                vehicleImageView.setFitHeight(25); 
                vehicleImageView.setPreserveRatio(true);
                graphGroup.getChildren().add(vehicleImageView);
                transition.setNode(vehicleImageView);
                
                transition.setPath(transitionRoutes.get(i));
                System.out.println("Duration: "+duration.get(i)/7);
                transition.setDuration(Duration.seconds(duration.get(i)/7));
                transition.play();
                //if transition stops, stop display vehicle
                transition.statusProperty().addListener(new ChangeListener<Status>() {
                    public void changed(ObservableValue<? extends Status> observableValue,Status oldValue, Status newValue) {
                        if(newValue==Status.STOPPED){
                            graphGroup.getChildren().remove(vehicleImageView);
                        }            
                    }
                });
            }   
        };
        
        // when simulate delivery button is pressed
        simulationBttn.setOnAction(simulationEvent);
        
        return graphGroup;
    }

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
