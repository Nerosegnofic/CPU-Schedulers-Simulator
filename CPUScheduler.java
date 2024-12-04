public abstract class CPUScheduler{
    protected Process[] processes;
    protected int contextSwitch;

    public CPUScheduler(Process[] processes,int contextSwitch){
        this.processes = processes;
        this.contextSwitch = contextSwitch;
    }

    public abstract void execute();
}