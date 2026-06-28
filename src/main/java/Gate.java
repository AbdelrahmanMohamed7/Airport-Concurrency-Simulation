

public class Gate {
    private int gateId;
    private boolean occupied;

    public Gate(int gateId) {
        this.gateId = gateId;
        this.occupied = false;
    }

    

	public int getGateId() {
        return gateId;
    }

    public  boolean isOccupied() {
        return occupied;
    }

    public  void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public void occupy() {
    	occupied=true;
    }
   
}
