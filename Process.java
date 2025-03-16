
class Process {
    String id;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnaroundTime;
    int remainingTime;
    int completionTime;
    
    public Process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.completionTime = 0;
        
    }
}
