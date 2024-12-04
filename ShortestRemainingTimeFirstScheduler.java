import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class ShortestRemainingTimeFirstScheduler extends CPUScheduler {

    public ShortestRemainingTimeFirstScheduler(Process[] processes,int contextSwitch) {
        super(processes,contextSwitch);
        Arrays.sort(this.processes,Comparator.comparing(Process::getArrivalTime));
    }


    @Override
    public void execute() {
        TreeSet <Process> shortestRemaining = new TreeSet<>(
            (Process p, Process s) -> Integer.compare(p.getRemainingTime(),s.getRemainingTime())
        );
        int idx = 0,time = 0;
        Process current = null;
        while(idx < processes.length || !shortestRemaining.isEmpty()){
            while (idx < processes.length && time >= processes[idx].getArrivalTime() )
                shortestRemaining.add(processes[idx++]);
            if (!shortestRemaining.isEmpty()){
                if (!shortestRemaining.first().equals(current)){
                    if (current != null){
                        System.out.println("terminated: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                        time += contextSwitch;
                    }
                    current = shortestRemaining.first();
                }
                current.process(1);
                if (current.getRemainingTime() <= 0){
                    System.out.println("processed: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                    shortestRemaining.remove(current);
                    if (!shortestRemaining.isEmpty()){
                        current = shortestRemaining.first();
                        time += contextSwitch;
                    }
                }
            }
            time++;
        }
    }

}