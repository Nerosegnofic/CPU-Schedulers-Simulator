import java.util.*;

public class FCAIScheduler extends CPUScheduler {

    private int V1;
    private int V2;

    public FCAIScheduler(List<Process> processes, int contextSwitchTime) {
        super(processes, contextSwitchTime);
        calculateV1V2();
    }

    // Calculate V1 and V2 based on the problem requirements
    private void calculateV1V2() {
        int lastArrivalTime = processes.stream().mapToInt(Process::getArrivalTime).max().orElse(1);
        int maxBurstTime = processes.stream().mapToInt(Process::getBurstTime).max().orElse(1);
        V1 = lastArrivalTime / 10;
        V2 = maxBurstTime / 10;
    }

    @Override
    public void execute() {
        PriorityQueue<Process> queue = new PriorityQueue<>(
                Comparator.comparingInt(Process::getFCAI_factor).reversed() // Max-Heap based on FCAI_factor
        );

        int currentTime = 0;
        List<Process> completedProcesses = new ArrayList<>();

        while (!processes.isEmpty() || !queue.isEmpty()) {
            // Add processes that have arrived by current time to the queue
            for (Iterator<Process> it = processes.iterator(); it.hasNext();) {
                Process process = it.next();
                if (process.getArrivalTime() <= currentTime) {
                    process.setFCAI_factor(calculateFCAIFactor(process));
                    queue.add(process);
                    it.remove();
                }
            }

            if (!queue.isEmpty()) {
                Process current = queue.poll();
                int executionTime = Math.min(current.getQuantum(), current.getRemainingTime());
                System.out.println("Time " + currentTime + ": Executing " + current.getName() + " for " + executionTime + " units");

                // Simulate execution
                current.process(executionTime);
                currentTime += executionTime + contextSwitchTime;

                if (current.getRemainingTime() > 0) {
                    // Process is not completed, update quantum and FCAI_factor
                    current.setQuantum(current.getQuantum() + 2); // Increase quantum
                    current.setFCAI_factor(calculateFCAIFactor(current));
                    queue.add(current);
                } else {
                    // Process is completed, calculate and store metrics
                    current.setCompletionTime(currentTime - contextSwitchTime);
                    current.setTurnAroundTime(current.getCompletionTime() - current.getArrivalTime());
                    current.setWaitingTime(current.getTurnAroundTime() - current.getBurstTime());
                    completedProcesses.add(current);
                }
            } else {
                currentTime++; // Idle time increment
            }
        }

        displayResults(completedProcesses);
    }

    // Calculate FCAI Factor
    private int calculateFCAIFactor(Process process) {
        return (10 - process.getPriority()) +
                (process.getArrivalTime() / V1) +
                (process.getRemainingTime() / V2);
    }

    // Display results for the completed processes
    private void displayResults(List<Process> completedProcesses) {
        System.out.println("\n--- FCAI Scheduling Results ---");
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        for (Process process : completedProcesses) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnAroundTime += process.getTurnAroundTime();
            System.out.printf("Process %s: Waiting Time = %d, Turnaround Time = %d\n",
                    process.getName(), process.getWaitingTime(), process.getTurnAroundTime());
        }

        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaitingTime / completedProcesses.size());
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTurnAroundTime / completedProcesses.size());
    }
}
