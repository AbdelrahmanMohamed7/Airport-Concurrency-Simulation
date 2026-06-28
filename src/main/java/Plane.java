


import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Plane implements Runnable {
     private static AtomicInteger planeCounter = new AtomicInteger(0);
      private ATC atc;

    private static final int MAX_PASSENGERS = 50;

    private int planeId;
    private int numPassengers;
    private long landingTime;
    private long waitingTime;
     private long arrivalTime;

    public Plane(int planeId, ATC atc) {
        this.planeId = planeId;
        this.numPassengers = new Random().nextInt(MAX_PASSENGERS) + 1;
        this.atc = atc;
    }

    @Override
    public void run() {
        try {
              arrivalTime = System.currentTimeMillis();
        System.out.println("Plane " + planeId + " has arrived at the airport.");

            // Request landing permission
            System.out.println("Plane " + planeId + ": Requesting permission to land!");
            atc.addToLandingQueue(this);

            // Land on the runway
            System.out.println("Plane " + planeId + ": Landing on the runway.");
            AirportUtil.landOnRunway(planeId);

            // Dock to an available gate
            System.out.println("Plane " + planeId + ": Attempting to dock at a gate.");
            int gateId = AirportUtil.dockAtGate(planeId);
            System.out.println("Plane " + planeId + ": Docked at Gate " + gateId);

            // Refuel the plane
            System.out.println("Plane " + planeId + ": Refueling the plane.");
            AirportUtil.refuelPlane(planeId);

            // Embark passengers
            System.out.println("Plane " + planeId + ": Embarking passengers.");
            AirportUtil.embarkPassengers(planeId, numPassengers);

            // Disembark passengers
            System.out.println("Plane " + planeId + ": Disembarking passengers.");
            AirportUtil.disembarkPassengers(planeId);

            // Undock from the gate
            System.out.println("Plane " + planeId + ": Undocking from the gate.");
            AirportUtil.undockFromGate(planeId, gateId);

            // Take off from the runway
            System.out.println("Plane " + planeId + ": Taking off from the runway.");
            AirportUtil.takeOffFromRunway(planeId);

            // Update statistics
            long endTime = System.currentTimeMillis();
            waitingTime = endTime - landingTime;
            updateStatistics(waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateStatistics(long waitingTime) {
        synchronized (AsiaPacificAirportSimulation.class) {
            AsiaPacificAirportSimulation.planesServed++;
            AsiaPacificAirportSimulation.totalWaitingTime += waitingTime;
            if (waitingTime < AsiaPacificAirportSimulation.minWaitingTime || AsiaPacificAirportSimulation.minWaitingTime == 0) {
                AsiaPacificAirportSimulation.minWaitingTime = waitingTime;
            }
            if (waitingTime > AsiaPacificAirportSimulation.maxWaitingTime) {
                AsiaPacificAirportSimulation.maxWaitingTime = waitingTime;
            }
        }
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }

    public long getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(long landingTime) {
        this.landingTime = landingTime;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    
    
}
