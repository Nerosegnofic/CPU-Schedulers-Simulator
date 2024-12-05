import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice = promptUserForAlgorithm();
        CPUScheduler scheduler;
        System.out.println();
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", null, 0, 10, 3, 0));
        processes.add(new Process("P2", null, 0, 1, 1, 0));
        processes.add(new Process("P3", null, 0, 2, 4, 0));
        processes.add(new Process("P4", null, 0, 1, 5, 0));
        processes.add(new Process("P5", null, 0, 5, 2, 0));
        scheduler = switch (choice) {
            case 1 -> new PriorityScheduler(processes,0);
            case 2 -> new ShortestJobFirstScheduler(processes,0);
            case 3 -> new ShortestRemainingTimeFirstScheduler(processes,0);
            case 4 -> new FCAIScheduler(processes,0);
            default -> null;
        };
        if (scheduler != null) {
            scheduler.execute();
        }
    }

    public static int promptUserForAlgorithm() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose Your Scheduling Algorithm:");
        System.out.println("1 - Priority Scheduling Using Context Switching (Non-preemptive).");
        System.out.println("2 - Shortest Job First (Non-preemptive).");
        System.out.println("3 - Shortest Remaining Time First Using Context Switching.");
        System.out.println("4 - FCAI Scheduling");

        System.out.print("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        scanner.close();

        return choice;
    }
}