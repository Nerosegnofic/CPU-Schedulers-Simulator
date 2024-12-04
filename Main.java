public class Main {
    public static void main(String[] args) {
        System.out.println();
        Process[] processes = {
            new Process("chrome", null, 0, 10, 0, 0),
            new Process("whatsapp", null, 2, 5, 0, 0),
            new Process("VS code", null, 2, 15, 0, 0),
            new Process("something else", null, 4, 3, 0, 0),
            new Process("My Computer", null, 6, 2, 0, 0),
        };
        CPUScheduler scheduler = new ShortestRemainingTimeFirstScheduler(processes,1);
        scheduler.execute();
    }
}