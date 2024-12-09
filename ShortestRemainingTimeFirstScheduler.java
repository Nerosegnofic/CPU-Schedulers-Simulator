import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShortestRemainingTimeFirstScheduler extends CPUScheduler {

    public ShortestRemainingTimeFirstScheduler(List<Process> processes, int contextSwitchTime) {
        super(processes, contextSwitchTime);
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
                    System.out.println("Time " + currentTime + ": " + process.getName() + " arrives.");
                }
            }
//             readyQueue.sort(Comparator.comparingInt(Process::getRemainingTime));
            int finalCurrentTime = currentTime;
            readyQueue.sort((p1, p2) -> {
                // This doesn't actually modify the burst time of the processes,
                // but it increases the process's priority in the ready queue.
                int p1EffectiveBurst = p1.getRemainingTime() - (finalCurrentTime - p1.getArrivalTime()) / 2;
                int p2EffectiveBurst = p2.getRemainingTime() - (finalCurrentTime - p2.getArrivalTime()) / 2;
                return Integer.compare(p1EffectiveBurst, p2EffectiveBurst);
            });
            
            if (!readyQueue.isEmpty()) {
                if (currentProcess != null && currentProcess.getRemainingTime() > 0 && currentProcess != readyQueue.getFirst()) {
                    currentTime += contextSwitchTime;
                    System.out.println("Time " + currentTime + ": Context switch to " + readyQueue.getFirst().getName());
                }

                if (currentProcess != readyQueue.getFirst()) {
                    currentProcess = readyQueue.getFirst();
                    System.out.println("Time " + currentTime + ": " + currentProcess.getName() + " starts executing.");
                }
            }
            
            if (currentProcess != null) {
                currentProcess.process(1);
                currentTime++;
                
                if (currentProcess.getRemainingTime() == 0) {
                    completedProcesses++;
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getBurstTime());
                    readyQueue.remove(currentProcess);
                    System.out.println("Time " + currentTime + ": " + currentProcess.getName() + " finishes executing.");
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
