public abstract class CPUSchedular{
    protected Process[] processes;
    protected int contextSwitch;

    public CPUSchedular(Process[] processes,int contextSwitch){
        this.processes = processes;
        this.contextSwitch = contextSwitch;
    }

    public abstract void execute();
}