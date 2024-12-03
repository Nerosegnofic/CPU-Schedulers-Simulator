public abstract class CPUSchedular{
    protected Process[] processes;

    public CPUSchedular(Process[] processes){
        this.processes = processes;
    }

    public abstract void execute();
}