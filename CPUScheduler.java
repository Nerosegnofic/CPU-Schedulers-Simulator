import java.util.List;

public abstract class CPUScheduler {
    protected List<Process> processes;
    protected int contextSwitchTime;
    protected int n;

    public CPUScheduler(List<Process> processes, int contextSwitchTime) {
        this.processes = processes;
        this.contextSwitchTime = contextSwitchTime;
        this.n = processes.size();
    }

    public abstract void execute();
}
