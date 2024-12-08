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

        while (completedProcesses < n) {
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                    System.out.println("Time " + process.getArrivalTime() + ": " + process.getName() + " arrives.");
                }
            }

            readyQueue.sort((p1, p2) -> Double.compare(calculateFCAIFactor(p1), calculateFCAIFactor(p2)));

            if (!readyQueue.isEmpty()) {
                if (currentProcess != null && currentProcess.getRemainingTime() > 0 && currentProcess != readyQueue.getFirst()) {
                    currentTime += contextSwitchTime;
                    System.out.println("Time " + currentTime + ": Context switch to " + readyQueue.getFirst().getName());
                }

                if (currentProcess == null || currentProcess != readyQueue.getFirst()) {
                    currentProcess = readyQueue.getFirst();
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
                    currentProcess.setQuantum(currentProcess.getQuantum()-executionTime);
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
}