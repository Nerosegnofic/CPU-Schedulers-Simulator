import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ShortestJobFirstScheduler extends CPUScheduler {
    int totalWaitingTime = 0;
    int totalTurnaroundTime = 0;

    public ShortestJobFirstScheduler(List<Process> processes, int contextSwitch) {
        super(processes, contextSwitch);
        this.processes.sort(Comparator.comparingInt(Process::getArrivalTime)
                .thenComparingInt(Process::getBurstTime));
    }

    @Override
    public void execute() {
        int currentTime = 0;
        int completedProcesses = 0;
        Process currentProcess = null;
        boolean processInExecution = false;

        while (completedProcesses < processes.size()) {
            List<Process> readyQueue = new ArrayList<>();
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.getRemainingTime() > 0) {
                    readyQueue.add(process);
                }
            }

            if (!readyQueue.isEmpty()) {
                int finalCurrentTime = currentTime;
                readyQueue.sort((p1, p2) -> {
                    // This doesn't actually modify the burst time of the processes,
                    // but it increases the process's priority in the ready queue.
                    int p1EffectiveBurst = p1.getRemainingTime() - (finalCurrentTime - p1.getArrivalTime()) / 2;
                    int p2EffectiveBurst = p2.getRemainingTime() - (finalCurrentTime - p2.getArrivalTime()) / 2;
                    return Integer.compare(p1EffectiveBurst, p2EffectiveBurst);
                });

                Process nextProcess = readyQueue.getFirst();

                if (currentProcess != nextProcess) {
                    if (processInExecution) {
                        System.out.printf("Time %d: %s finishes executing.%n", currentTime, currentProcess.getName());
                    }

                    System.out.printf("Time %d: %s starts executing.%n", currentTime, nextProcess.getName());
                    currentProcess = nextProcess;
                    processInExecution = true;
                }

                currentTime += currentProcess.getRemainingTime();
                currentProcess.setRemainingTime(0);

                currentProcess.setCompletionTime(currentTime);
                currentProcess.setTurnAroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());

                completedProcesses++;
                readyQueue.remove(currentProcess);

                if (completedProcesses == processes.size()) {
                    System.out.printf("Time %d: %s finishes executing.%n", currentTime, currentProcess.getName());
                }
            } else {
                currentTime++;
            }
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
