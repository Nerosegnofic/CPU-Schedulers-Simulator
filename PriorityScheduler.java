public class PriorityScheduler extends CPUScheduler {
    
    public PriorityScheduler(Process[] processes,int contextSwitch) {
        super(processes,contextSwitch);
    }
    
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

