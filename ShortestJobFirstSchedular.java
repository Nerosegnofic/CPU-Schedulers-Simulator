import java.util.Arrays;
import java.util.Comparator;

public class ShortestJobFirstSchedular extends CPUSchedular{

    public ShortestJobFirstSchedular(Process[] processes) {
        super(processes);
        Arrays.sort(this.processes,Comparator.comparing(p -> p.getBurstTime()));
    }

    @Override
    public void execute() {
        for (Process process : processes){
            process.process(process.getRemainingTime());
            System.out.println("processed: " + process);
        }
    }
    
}
