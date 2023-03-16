/* Name: Artem Markelov
 * Class: CS1450
 * Due: March 20, 2023
 * Description: Assignment#7
 * Problem Statement: 
*/

import java.lang.Comparable;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class MarkelovArtemAssignment4 {

	public static void main(String[] args) throws IOException {
		
		//Create Scanner to read from trucks File
		File fileNameTrucks = new File("FedExTrucks7.txt");
		Scanner inputFileTrucks = new Scanner(fileNameTrucks);
		
		//Create Scanner to read from plane File
		File fileNamePlanes = new File("FedExPlanes7.txt");
		Scanner inputFilePlanes = new Scanner(fileNamePlanes);
	
		//Create variables of numberOfDocks and numberOfStands + read from file
		int numberOfDocks = (inputFileTrucks.nextInt() + 1);
		inputFileTrucks.nextLine();
		int numberOfStands = (inputFilePlanes.nextInt() + 1);
		inputFilePlanes.nextLine();
		
		//Create Cargo terminal with loading dock and a tarmac in it
		CargoTerminal cargoTerminal = new CargoTerminal (numberOfDocks, numberOfStands);
			
		while (inputFileTrucks.hasNext()) {
			
			//Read from file
			int dockNumber = inputFileTrucks.nextInt();
			int truckNumber = inputFileTrucks.nextInt();
			String destinationCityTrucks = inputFileTrucks.nextLine();
			
			//Create a semi-truck object and put it in the array
			SemiTruck semiTruck = new SemiTruck(truckNumber, destinationCityTrucks);
			cargoTerminal.addSemiTruck(dockNumber, semiTruck);
			
		}
			
		while (inputFilePlanes.hasNext()) {
			
			//Read from file
			int standNumber = inputFilePlanes.nextInt();
			String cargoType = inputFilePlanes.next();
			int flightNumber = inputFilePlanes.nextInt();
			String typeOfPlane = inputFilePlanes.next();
			double capacity = inputFilePlanes.nextDouble();
			String destinationCityPlanes = inputFilePlanes.nextLine();
			
			//Create a plane object and put it in the array
			CargoPlane plane = new CargoPlane(flightNumber, cargoType, typeOfPlane, capacity, destinationCityPlanes);
			cargoTerminal.addCargoPlane(standNumber, plane);
			
		}

		//Close inputs
		inputFilePlanes.close();
		inputFileTrucks.close();
			
		//Display the cargo Terminal
		cargoTerminal.displayTrucksAndPlanes();
		
		AirTrafficController controller = new AirTrafficController();
		Taxiways taxiways = new Taxiways();
		
		System.out.println("\n\n");
		
		controller.movePlanesToTaxiways(cargoTerminal, taxiways);
		
		
	}
	


}//END OF MAIN CLASS

//-----------------------------------------------//

//CARGOTERMINAL CLASS
class CargoTerminal {
	
	//Private data fields
	private int numberDocks;
	private int numberStands;
	private SemiTruck[] loadingDock;
	private CargoPlane[] tarmac;
	
	//Constructor
	public CargoTerminal(int numberDocks, int numberStands) {
		
		//Initialize numberDock and numberStands to the incoming value
		this.numberDocks = numberDocks;
		this.numberStands = numberStands;
		
		//Initialize size of loadingDock and tarmac
		loadingDock = new SemiTruck[numberDocks];
		tarmac = new CargoPlane[numberStands];
		
	}
	
	//Getters
	public int getNumberDocks() {
		
		return numberDocks;
		
	}
	
	public int getNumberStands() {
		
		return numberStands;
		
	}
	
	//Other public methods
	public void addSemiTruck(int dock, SemiTruck semiTruck) {
		
		//Check if there is a room in the dock
		if (dock <= numberDocks) {
			
			loadingDock[dock] = semiTruck;
			
		}
		
	}
	
	public void addCargoPlane(int stand, CargoPlane plane) {
		
		if (stand <= numberStands) {
			
			tarmac[stand] = plane;
			
		}
		
	}
	
	public CargoPlane removeCargoPlane(int stand) {
		
		CargoPlane plane = tarmac[stand];
		
		if (tarmac[stand] != null) {
			
			tarmac[stand] = null;
			return plane;
			
		} else {
			
			return null;
			
		}
		
		
	}
	
	public SemiTruck getSemiTruck(int dock) {
		
		if (loadingDock[dock] != null) {
			
			return loadingDock[dock];
			
		} else {
			
			return null;
			
		}
		
	}
	
	public CargoPlane getCargoPlane (int stand) {
		
		if (tarmac[stand] != null) {
			
			return tarmac[stand];
			
		} else {
			
			return null;
			
		}
		
	}
	
	public void displayTrucksAndPlanes() {
		
		System.out.println("\nLoading semi-trucks and planes into cargo terminal...");
		System.out.println("");
		
		for (int i = 1; i < getNumberDocks() - 1; i++) {
			
			System.out.print("Dock #" + i + "    ");
			
		}
		
		System.out.println("");
		
		for (int i = 1; i < getNumberDocks() - 1; i++) {
			
			if (loadingDock[i] != null) {
				
				System.out.printf("%-11d", loadingDock[i].getTruckNumber());
			
			} else {
				
				System.out.printf("%-11s","------");
				
			}
			
		}
			
		System.out.println("\n");
			

		
		for (int i = 1; i < getNumberStands() - 1; i++) {
			
			System.out.print("Stand #" + i + "   ");
			
		}
		
		System.out.println("");
		
		for (int i = 1; i < getNumberStands() - 1; i++) {
			
			if (tarmac[i] != null) {
				
				System.out.printf("%-11d", tarmac[i].getFlightNumber());
			
			} else {
				
				System.out.printf("%-11s","------");
				
			}
			
		}

	}
	
}

//--------------------------------------------------//

//SEMITRUCK CLASS
class SemiTruck {
	
	//Private data fields
	private int truckNumber;
	private String destinationCity;
	
	//Constructor
	public SemiTruck(int truckNumber, String destinationCity) {
		
		this.truckNumber = truckNumber;
		this.destinationCity = destinationCity;
		
	}
	
	//Getters
	public int getTruckNumber() {
		
		return truckNumber;
		
	}
	
	public String getDestinationCity() {
		
		return destinationCity;
		
	}
	
}

//--------------------------------------------------//

//CARGOPLANE CLASS
class CargoPlane implements Comparable<CargoPlane> {
	
	//Private data fields
	private int flightNumber;
	private String type;
	private double capacity;
	private String destinationCity;
	private String cargoType;
	
	//Constructor
	public CargoPlane(int flightNumber, String cargoType, String type, double capacity, String destinationCity) {
		
		this.flightNumber = flightNumber;
		this.type = type;
		this.capacity = capacity;
		this.destinationCity = destinationCity;
		this.cargoType = cargoType;
		
	}
	
	//Getters
	public int getFlightNumber() {
		
		return flightNumber;
		
	}
	
	public String getType() {
		
		return type;
		
	}
	
	public double getCapacity() {
		
		return capacity;
		
	}
	
	public String getDestinationCity() {
		
		return destinationCity;
		
	}
	
	//Other public methods
	public boolean isUrgent() {
		
		if (cargoType.equals("medical") || cargoType.equals("animals")) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public boolean isStandard() {
		
		if (cargoType.equals("packages")) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public String print() {
		
		return String.format(" %-6d%-15s%s",
				flightNumber, destinationCity, cargoType);
		
	}

	@Override
	public int compareTo(CargoPlane otherPlane) {
		
		if (this.cargoType.equals(otherPlane.cargoType)) {
			
			return 0;
			
		} else if (this.cargoType.equals("medical")) {
			
			 return -1;
			
		} else if (otherPlane.cargoType.equals("medical")) {
			
			return 1; 
			
		} else {
			
	        return 0; 
	     
		}
	}
	
}

//--------------------------------------------------//

class Taxiways {
	
	//Create 2 private queues
	private PriorityQueue<CargoPlane> urgentTaxiway;
	private Queue<CargoPlane> standardTaxiway;
	
	//Constructor
	public Taxiways() {
		
		urgentTaxiway = new PriorityQueue<>();
		standardTaxiway = new LinkedList<>();
		
	}
	
	//Other public methods
	public boolean isUrgentTaxiwayEmpty() {
		
		if(urgentTaxiway.isEmpty()) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public void addPlaneToUrgentTaxiway(CargoPlane plane) {
		
		urgentTaxiway.offer(plane);
		
	}
	
	public CargoPlane removePlaneFromUrgentTaxiway() {
			
			return urgentTaxiway.poll();
	
	}
	
	public boolean isStandardTaxiwayEmpty() {
		
		if(standardTaxiway.isEmpty()) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public void addPlaneToStandardTaxiway(CargoPlane plane) {
		
		standardTaxiway.offer(plane);
		
	}
	
	public CargoPlane removePlaneFromStandardTaxiway() {
			
			return standardTaxiway.poll();
	
	}
	
}

//--------------------------------------------------//

class Runway {
	
	//Create private data field
	private Queue<CargoPlane> runway;
	
	//Constructor
	public Runway() {
		
		runway = new LinkedList<>();
		
	}
	
	//Other public methods
	public boolean isRunwayEmpty() {
		
		if(runway.isEmpty()) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public void addPlaneToRunway(CargoPlane plane) {
		
		runway.offer(plane);
		
	}
	
	public CargoPlane removePlaneFromRunway() {
			
			return runway.poll();
	
	} 
	
}

//--------------------------------------------------//

class AirTrafficController {
	
	//Public methods
	public void movePlanesToTaxiways(CargoTerminal cargoTerminal, Taxiways taxiways) {
		
		String taxiwayLevel = "";
		
		//Go through list from the beginning since tarmac is array
		for (int i = 0; i < cargoTerminal.getNumberStands(); i++) {
			
			//Check that stand is not empty
			if (cargoTerminal.getCargoPlane(i) != null) {
				
				//Check if type == medical
				if (cargoTerminal.getCargoPlane(i).isUrgent() || cargoTerminal.getCargoPlane(i).isUrgent()) {
					
					taxiwayLevel = "Urgent";
					
					//Add to urgent taxiway
					taxiways.addPlaneToUrgentTaxiway(cargoTerminal.getCargoPlane(i));
					
					
				} else if (cargoTerminal.getCargoPlane(i).isStandard()){
					
					taxiwayLevel = "Standard";
					
					//Add to standard taxiway
					taxiways.addPlaneToStandardTaxiway(cargoTerminal.getCargoPlane(i));
				
					
				}
				
				System.out.printf("Moved to taxiway %-10s", taxiwayLevel);
				System.out.print("flight: " + cargoTerminal.getCargoPlane(i).print());
				System.out.println();
				
			}
	
			
		}
		
	}
	
	public void movePlanesToRunway(Taxiways taxiways, Runway runway) {
		
		while(!taxiways.isUrgentTaxiwayEmpty()) {
			
			CargoPlane urgentPlane = taxiways.removePlaneFromUrgentTaxiway();
			runway.addPlaneToRunway(urgentPlane);
			
			//Message
			System.out.println("Moved to runway flight: " + urgentPlane.print());
			
		}
		
		while (!taxiways.isStandardTaxiwayEmpty()) {
			
			CargoPlane standardPlane = taxiways.removePlaneFromStandardTaxiway();
			runway.addPlaneToRunway(standardPlane);
			
			//Message
			System.out.println("Moved to runway flight: " + standardPlane.print());
			
		}
		
	}
	
	public void clearedForTakeoff(Runway runway) {
		
		while (!runway.isRunwayEmpty()) {
			
			CargoPlane planeTakeoff = runway.removePlaneFromRunway();
			
			System.out.print("Cleared for takeoff flight: " + planeTakeoff.print());
			
		}
		
	}
	
}


