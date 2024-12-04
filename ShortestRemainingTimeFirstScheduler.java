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
        TreeSet <Process> set = new TreeSet<>(
            (Process p, Process s) -> Integer.compare(p.getRemainingTime(),s.getRemainingTime())
        );
        int idx = 0,time = 0;
        Process current = null;
        while(idx < processes.length || !set.isEmpty()){
            while (idx < processes.length && time >= processes[idx].getArrivalTime() )
                set.add(processes[idx++]);
            if (!set.isEmpty()){
                if (!set.first().equals(current)){
                    if (current != null){
                        System.out.println("terminated: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                        time += contextSwitch;
                    }
                    current = set.first();
                }
                current.process(1);
                if (current.getRemainingTime() <= 0){
                    System.out.println("processed: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                    set.remove(current);
                    if (!set.isEmpty()){
                        current = set.first();
                        time += contextSwitch;
                    }
                }
            }
            time++;
        }
    }

}