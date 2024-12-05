import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice = promptUserForAlgorithm();
        CPUScheduler scheduler;
        System.out.println();
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", null, 0, 7, 4, 0));
        processes.add(new Process("P2", null, 2, 4, 2, 0));
        processes.add(new Process("P3", null, 4, 1, 1, 0));
        processes.add(new Process("P4", null, 5, 4, 5, 0));
        if (choice == 1) {
            scheduler = new PriorityScheduler(processes, 0);
        } else if (choice == 2) {
            scheduler = new ShortestJobFirstScheduler(processes, 0);
        } else if (choice == 3) {
            scheduler = new ShortestRemainingTimeFirstScheduler(processes, 0);
        } else if (choice == 4) {
            scheduler = new FCAIScheduler(processes, 0);
        } else {
            return;
        }
        scheduler.execute();
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