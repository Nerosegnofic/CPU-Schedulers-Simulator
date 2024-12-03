import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class ShortestRemainingTimeFirstScheduler extends CPUSchedular {

    public ShortestRemainingTimeFirstScheduler(Process[] processes,int contextSwitch) {
        super(processes,contextSwitch);
        Arrays.sort(this.processes,Comparator.comparing(p -> p.getArrivalTime()));
    }


    @Override
    public void execute() {
        TreeSet <Process> set = new TreeSet<>(
            (Process p, Process s) -> p.getRemainingTime() - s.getRemainingTime()
        );
        int idx = 0,time = 0;
        Process current = null;
        while(idx != processes.length || !set.isEmpty()){
            if (idx < processes.length && processes[idx].getArrivalTime() <= time)
                set.add(processes[idx++]);
            if (!set.isEmpty()){
                if (set.first() != current){
                    if (current != null)
                        System.out.println("processed: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                    time += contextSwitch;
                    current = set.first();
                }
                if (current.getRemainingTime() == 0){
                    System.out.println("processed: " + current + " till: " + time + " remaining: " + current.getRemainingTime());
                    set.remove(current);
                }
                else
                    current.process(1);
            }
            time++;
        }
    }

}