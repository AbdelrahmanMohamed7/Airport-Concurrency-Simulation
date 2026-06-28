


import java.util.Random;
import java.util.concurrent.Semaphore;

class AirportUtil {
    private static final int MAX_AIRPLANES = 6;
    private static final int MAX_GATES = 2;
     
    private static Semaphore runwaySemaphore = new Semaphore(1);
    private static Semaphore gateSemaphore = new Semaphore(MAX_GATES);
    
    private static Random random = new Random();

    public static Random getRandom() {
        return random;
    }

    public static void landOnRunway(int planeId) throws InterruptedException {
    	String threadName = Thread.currentThread().getName();
    	System.out.println(threadName + " : Plane " + planeId + " requesting permission to land!");
        runwaySemaphore.acquire();
        System.out.println(threadName + " : Plane " + planeId + " has permission to land!");
        // Simulate landing time
        Thread.sleep(1000); // Adjust the sleep time as per your requirements
        System.out.println(threadName + " : Plane " + planeId + " has landed on the runway.");
    }

    public static int dockAtGate(int planeId) throws InterruptedException {
    	String threadName = Thread.currentThread().getName();
    	System.out.println(threadName + " : Plane " + planeId + " requesting gate to dock.");
        gateSemaphore.acquire();
        System.out.println(threadName + " : Plane " + planeId + " has docked at a gate.");
        // Simulate docking time
        Thread.sleep(2000); // Adjust the sleep time as per your requirements
        return getAvailableGateId();
    }

    public static void refuelPlane(int planeId) throws InterruptedException {
    	String threadName = Thread.currentThread().getName();
    	System.out.println(threadName + " : Refueling plane " + planeId);
        // Simulate refueling time
        Thread.sleep(1500); // Adjust the sleep time as per your requirements
        System.out.println("Refueling plane " + planeId+ "has been refueled");
    }

    public static void embarkPassengers(int planeId, int numPassengers) throws InterruptedException {
    	String threadName = Thread.currentThread().getName();
        // Simulate embarkation time
        Thread.sleep(1000); // Adjust the sleep time as per your requirements
        System.out.println("threadName  " + numPassengers + " passengers onto plane " + planeId);
        AsiaPacificAirportSimulation.passengersBoarded += numPassengers;
    }

    public static void disembarkPassengers(int planeId) throws InterruptedException {
    	String threadName = Thread.currentThread().getName();
    	System.out.println(threadName + " : Disembarking passengers from plane " + planeId);
        // Simulate disembarkation time
        Thread.sleep(1000); // Adjust the sleep time as per your requirements
        System.out.println(threadName + " : Passengers have disembarked from plane " + planeId);
    }

    public static void undockFromGate(int planeId, int gateId) {
    	String threadName = Thread.currentThread().getName();
    	System.out.println(threadName + " : Plane " + planeId + " undocking from gate " + gateId);
        gateSemaphore.release();
    }

    public static void takeOffFromRunway(int planeId) throws InterruptedException {
    	String threadName = Thread.currentThread().getName();
    	System.out.println(threadName + " : Plane " + planeId + " preparing to take off from the runway.");
        // Simulate takeoff time
        Thread.sleep(2000); // Adjust the sleep time as per your requirements
        System.out.println(threadName + " : Plane " + planeId + " has taken off from the runway.");
        runwaySemaphore.release();
    }

    private static int getAvailableGateId() {
        synchronized (AsiaPacificAirportSimulation.class) {
            for (int i = 0; i < MAX_GATES; i++) {
                if (!AsiaPacificAirportSimulation.gates[i].isOccupied()) {
                    return i + 1;
                }
            }
        }
        return -1; // No available gate
    }
}

