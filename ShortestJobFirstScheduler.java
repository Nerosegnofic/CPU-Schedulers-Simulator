import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShortestJobFirstScheduler extends CPUScheduler {
    int totalWaitTime = 0;
    int totalTurnaroundTime = 0;

    public ShortestJobFirstScheduler(List<Process> processes, int contextSwitch) {
        super(processes, contextSwitch);
        this.processes.sort(Comparator.comparing(Process::getBurstTime));
    }

    @Override
    public void execute() {
        for (Process process : processes) {
            process.process(process.getRemainingTime());
            System.out.println("processed: " + process);
        }
    }
}
