import java.util.ArrayList;
import java.util.List;

public class FCAIScheduler extends CPUScheduler {

    private double V1;
    private double V2;

    public FCAIScheduler(List<Process> processes, int contextSwitchTime) {
        super(processes, contextSwitchTime);
        calculateFactors();
    }

    private void calculateFactors() {
        int lastArrivalTime = processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1);
        int maxBurstTime = processes.stream().mapToInt(Process::getBurstTime).max().orElse(1);
        V1 = lastArrivalTime / 10.0;
        V2 = maxBurstTime / 10.0;
    }

    private double calculateFCAIFactor(Process process) {
        return (10 - process.getPriority())
                + (process.getArrivalTime() / V1)
                + (process.getRemainingTime() / V2);
    }

    private boolean isAnotherArrived(List<Process> readyQueue, int currentTime) {
        boolean arrived = false;
        for (Process process : processes) {
            if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0 && !readyQueue.contains(process)) {
                readyQueue.add(process);
                arrived = true;
                System.out.println("Time " + process.getArrivalTime() + ": " + process.getName() + " arrives.");
            }
        }
        if (arrived)
            readyQueue.sort((p1, p2) -> Double.compare(calculateFCAIFactor(p1), calculateFCAIFactor(p2)));
        return arrived;
    }

    @Override
    public void execute() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        if (n == 0) {
            System.out.println("No processes to schedule.");
            return;
        }

        List<Process> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int completedProcesses = 0;
        Process currentProcess = null;
        int index = 0;
        isAnotherArrived(readyQueue, currentTime);
        while (completedProcesses < n) {
            if (!readyQueue.isEmpty()) {
                index %= readyQueue.size();
                if (currentProcess != null && currentProcess.getRemainingTime() > 0 && currentProcess != readyQueue.get(index)) {
                    System.out.println("Time " + currentTime + ": Context switch to " + readyQueue.get(index).getName());
                }

                if (currentProcess == null || currentProcess != readyQueue.get(index)) {
                    currentProcess = readyQueue.get(index);
                    System.out.println("Time " + currentTime + ": " + currentProcess.getName() + " starts executing.");
                }
            }

            if (currentProcess != null) {
                int quantum = currentProcess.getQuantum();
                int executionTime = (int) Math.ceil(quantum * 0.4);
                
                if (executionTime > currentProcess.getRemainingTime())
                    executionTime = currentProcess.getRemainingTime();
                
                currentProcess.process(executionTime);
                currentTime += executionTime;

                while(currentProcess.getRemainingTime() != 0){
                    if ((isAnotherArrived(readyQueue, currentTime) && currentProcess != readyQueue.getFirst()) || currentProcess != readyQueue.getFirst()){
                        index = 0;
                        break;
                    }
                    else if (executionTime == currentProcess.getQuantum()){
                        index++;
                        break;
                    }
                    currentProcess.process(1);
                    executionTime++;
                    currentTime++;
                }
                if (currentProcess.getRemainingTime() == 0) {
                    completedProcesses++;
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    readyQueue.remove(currentProcess);
                    System.out.println("Time " + currentTime + ": " + currentProcess.getName() + " finishes executing.");
                }
                else if (executionTime == currentProcess.getQuantum())
                    currentProcess.setQuantum(currentProcess.getQuantum() + 2);
                else
                    currentProcess.setQuantum(2*currentProcess.getQuantum() - executionTime);
            }
            else
                currentTime++;
        }
        System.out.println("Process Execution Complete:");
        System.out.println("ID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnAroundTime();
            System.out.printf("%s\t%d\t\t%d\t\t%d\t\t\t%d\t\t\t%d%n",
                    process.getName(), process.getArrivalTime(), process.getBurstTime(), process.getCompletionTime(),
                    process.getTurnAroundTime(), process.getWaitingTime());
        }
        double averageWaitingTime = (double) totalWaitingTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        System.out.printf("Average Waiting Time: %.2f\n", averageWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", averageTurnaroundTime);
    }

    // @override
    // public void execute(){

    // }
}