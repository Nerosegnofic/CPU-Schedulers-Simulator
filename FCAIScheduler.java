public class FCAIScheduler extends CPUScheduler {
    
    public FCAIScheduler(Process[] processes,int contextSwitch) {
        super(processes,contextSwitch);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

