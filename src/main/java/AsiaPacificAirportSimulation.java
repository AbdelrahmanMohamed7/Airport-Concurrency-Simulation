

import java.util.Random;

public class AsiaPacificAirportSimulation {
    public static final int MAX_AIRPLANES = 6;
    public static final int SIMULATION_TIME_SECONDS = 60;
	private static final int MAX_GATES = 2;

    public ATC atc;
    public static Gate[] gates;
    public static Object channel = new Object();
    
    public static Thread[] planes;
    public static Random random = new Random();

    public static int planesServed;
    public static int passengersBoarded=0;
    public static long totalWaitingTime;
    public static long minWaitingTime;
    public static long maxWaitingTime;

    public static void main(String[] args) {
        // Initialize the gates array
        gates = new Gate[MAX_GATES];
        for (int i = 0; i < MAX_GATES; i++) {
            gates[i] = new Gate(i);
        }
        planes = new Thread[MAX_AIRPLANES];

   for(int i = 0; i < MAX_AIRPLANES; i++) {
	 final  int planeId = i + 1;
	 Thread  planeThread= new Thread(() -> {
		   try {
			   AirportUtil.landOnRunway(1);
			   int gateId = AirportUtil.dockAtGate(1);
			   if(gateId != -1) {
				   AirportUtil.refuelPlane(1);
				   AirportUtil.embarkPassengers(1, 100);
				   AirportUtil.undockFromGate(1, gateId);
				   AirportUtil.takeOffFromRunway(1);
			   }
		   }catch (InterruptedException e) {
			   Thread.currentThread().interrupt();
		   }
	   }, "Thread-Plane-" + planeId);
	    planes[i] = planeThread;
	   planeThread.start();
   }
            // Generate next plane arrival time
            int nextArrivalTime = random.nextInt(4) * 1000;
            try {
                Thread.sleep(nextArrivalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Runnable mainThreadRunnable = new Runnable() {
                @Override
                public void run() {
                    synchronized (channel) {
                        try {
                            channel.wait();
                            System.out.println(Thread.currentThread().getName() + ": ATC: Please wait and join the circle queue.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
             
          Runnable atcRunnable = new Runnable() {
        	  @Override
        	  public void run() {
        		  System.out.println(Thread.currentThread().getName() +":ATC: Please wait and join the circle queue.");
        		  synchronized (channel) {
                      channel.notify();
                  }  
        	  }
          };
          
          Runnable passengerRunnable = new Runnable() {
        	  @Override
        	  public void run() {
        		  System.out.println(Thread.currentThread().getName() + ": I’m boarding Plane 2 now.");  
        	  }
          };
            
          Thread atcThread = new Thread(atcRunnable, "Thread-ATC");
          Thread mainThread = new Thread(mainThreadRunnable, "MainThread");
          Thread passengerThread = new Thread(passengerRunnable, "Thread-Passenger-8"); 
            
           atcThread.start();
           mainThread.start();
           passengerThread.start();
    
            
            
            
        

        // Wait for all planes to finish
        try {
            Thread.sleep(SIMULATION_TIME_SECONDS * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print statistics
        System.out.println("Statistics:");
        System.out.println(Thread.currentThread().getName() + ": Total planes served: " + planesServed);
        if (planesServed > 0) {
            System.out.println(Thread.currentThread().getName() + ": Average waiting time for a plane: " + (totalWaitingTime / planesServed) + " ms");
            System.out.println(Thread.currentThread().getName() + ": Minimum waiting time for a plane: " + minWaitingTime + " ms");
            System.out.println(Thread.currentThread().getName() + ": Maximum waiting time for a plane: " + maxWaitingTime + " ms");
        } else {
            System.out.println(Thread.currentThread().getName() + ": No planes served yet. Average waiting time cannot be calculated.");
        }
        
        

        // Check that all gates are empty
        for (Gate gate : gates) {
            if (gate.isOccupied()) {
                System.out.println("Error: Gate " + gate.getGateId() + " is not empty.");
            }
        }
    }

    private static void initialize() {
        planesServed = 0;
        passengersBoarded = 0;
        totalWaitingTime = 0;
        minWaitingTime = 0;
        maxWaitingTime = 0;

        gates = new Gate[2];
        for (int i = 0; i < 2; i++) {
            gates[i] = new Gate(i + 1);
        }

        planes = new Thread[MAX_AIRPLANES];
    }
}
