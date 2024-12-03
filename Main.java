public class Main {
    public static void main(String[] args) {
        System.out.println();
        Process[] processes = {
            new Process("whatsapp", null, 0, 5, 0, 0),
            new Process("chrome", null, 0, 10, 0, 0),
            new Process("VS code", null, 0, 15, 0, 0),
            new Process("My Computer", null, 0, 2, 0, 0),
            new Process("something else", null, 0, 3, 0, 0),
        };
        CPUSchedular schedular = new ShortestJobFirstSchedular(processes);
        schedular.execute();
    }
}
