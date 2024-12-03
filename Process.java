public class Process{
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int turnAroundTime;
    private int completionTime;
    private int remainingTime;
    private int priority;
    private int quantum;
    private int FCAI_factor;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum){
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = quantum;
        this.remainingTime = burstTime;
    }

    public void process(int processTime){
        remainingTime -= processTime;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime(){
        return burstTime;
    }

    public void setBurstTime(int burstTime){
        this.burstTime = burstTime;
    }

    public int getWaitingTime(){
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime(){
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime){
        this.turnAroundTime = turnAroundTime;
    }

    public int getCompletionTime(){
        return completionTime;
    }

    public void setCompletionTime(int completionTime){
        this.completionTime = completionTime;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public int getRemainingTime(){
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime){
        this.remainingTime = remainingTime;
    }

    public int getQuantum(){
        return quantum;
    }

    public void setQuantum(int quantum){
        this.quantum = quantum;
    }

    public int getFCAI_factor(){
        return FCAI_factor;
    }

    public void setFCAI_factor(int FCAI_factor){
        this.FCAI_factor = FCAI_factor;
    }

    // @override
    public String toString(){
        return this.name;
    }
}
