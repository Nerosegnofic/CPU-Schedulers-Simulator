import java.util.List;
import java.util.TreeSet;

public class PriorityScheduler extends CPUScheduler {
    
    public PriorityScheduler(List<Process> processes, int contextSwitch) {
        super(processes,contextSwitch);
    }
    
    @Override
    public void execute() {

            TreeSet<Process> priority = new TreeSet<>(
                    (Process p, Process s) -> Integer.compare(p.getPriority(), s.getPriority())
            );
            int idx = 0, time = 0;
            Process current = null;
            while (idx < n || !priority.isEmpty()) {
                while (idx < n && time >= processes.get(idx).getArrivalTime())
                    priority.add(processes.get(idx++));
                if (!priority.isEmpty()) {
                    if (!priority.first().equals(current)) {
                        if (current != null) {
                            System.out.println("terminated: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                            time += contextSwitchTime;
                        }
                        current = priority.first();
                    }
                    current.process(1);
                    if (current.getRemainingTime() <= 0) {
                        System.out.println("processed: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                        priority.remove(current);
                        if (!priority.isEmpty()) {
                            current = priority.first();
                            time += contextSwitchTime;
                        }
                    }
                }
                time++;
            }
    }
}

