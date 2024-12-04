import java.util.TreeSet;

public class PriorityScheduler extends CPUScheduler {
    
    public PriorityScheduler(Process[] processes,int contextSwitch) {
        super(processes,contextSwitch);
    }
    
    @Override
    public void execute() {
        TreeSet <Process> priority = new TreeSet<>(
            (Process p, Process s) -> Integer.compare(p.getPriority(),s.getPriority())
        );
        int idx = 0,time = 0;
        Process current = null;
        while(idx < processes.length || !priority.isEmpty()){
            while (idx < processes.length && time >= processes[idx].getArrivalTime() )
                priority.add(processes[idx++]);
            if (!priority.isEmpty()){
                if (!priority.first().equals(current)){
                    if (current != null){
                        System.out.println("terminated: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                        time += contextSwitch;
                    }
                    current = priority.first();
                }
                current.process(1);
                if (current.getRemainingTime() <= 0){
                    System.out.println("processed: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                    priority.remove(current);
                    if (!priority.isEmpty()){
                        current = priority.first();
                        time += contextSwitch;
                    }
                }
            }
            time++;
        }
    }
}

