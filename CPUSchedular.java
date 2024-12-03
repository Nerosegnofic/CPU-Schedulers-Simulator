public abstract class CPUSchedular{
    private Process[] processes;

    public CPUSchedular(Process[] processes){
        this.processes = processes;
    }

    public abstract void execute();
}