

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ATC implements Runnable {
    private BlockingQueue<Plane> landingQueue;

    public ATC() {
        landingQueue = new LinkedBlockingQueue<>();
    }

    public void addToLandingQueue(Plane plane) {
        landingQueue.add(plane);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Plane plane = landingQueue.take();
                processLanding(plane);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void processLanding(Plane plane) throws InterruptedException {
        System.out.println("ATC: Plane " + plane.getPlaneId() + " requesting permission to land!");

        // Check if there is an available gate for the plane to dock
        int gateId = AirportUtil.dockAtGate(plane.getPlaneId());

        if (gateId != -1) {
            System.out.println("ATC: Plane " + plane.getPlaneId() + " has landed on Runway and docked at Gate " + gateId);

            // Allow passengers to disembark
            AirportUtil.disembarkPassengers(plane.getPlaneId());

            // Refuel the plane
            AirportUtil.refuelPlane(plane.getPlaneId());

            // Embark new passengers
            int numPassengers = generatePassengerCount();
            AirportUtil.embarkPassengers(plane.getPlaneId(), numPassengers);

            // Undock the plane from the gate
            AirportUtil.undockFromGate(plane.getPlaneId(), gateId);

            // Allow the plane to take off from the runway
            AirportUtil.takeOffFromRunway(plane.getPlaneId());

            // Update statistics
            long waitingTime = System.currentTimeMillis() - plane.getArrivalTime();
            updateStatistics(waitingTime);

            // Increment the number of served planes
            synchronized (AsiaPacificAirportSimulation.class) {
                AsiaPacificAirportSimulation.planesServed++;
            }
        } else {
            // No available gate, put the plane back in the landing queue
            landingQueue.put(plane);
        }
    }

    private int generatePassengerCount() {
        // Generate a random passenger count for the plane (1-50)
        return AirportUtil.getRandom().nextInt(50) + 1;
    }

    private void updateStatistics(long waitingTime) {
        synchronized (AsiaPacificAirportSimulation.class) {
            AsiaPacificAirportSimulation.totalWaitingTime += waitingTime;
            if (waitingTime < AsiaPacificAirportSimulation.minWaitingTime || AsiaPacificAirportSimulation.minWaitingTime == 0) {
                AsiaPacificAirportSimulation.minWaitingTime = waitingTime;
            }
            if (waitingTime > AsiaPacificAirportSimulation.maxWaitingTime) {
                AsiaPacificAirportSimulation.maxWaitingTime = waitingTime;
            }
        }
    }
}
