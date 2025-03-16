import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. First-Come, First-Served (FCFS)");
            System.out.println("2. Shortest-Job-First (SJF)");
            System.out.println("3. Shortest-Remaining-Time (SRT)");
            System.out.println("4. Round Robin (RR)");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();

            if (choice == 5) {
                break;
            }

            System.out.print("Enter the number of processes: ");
            int n = sc.nextInt();
            sc.nextLine(); // Consume newline character

            processes.clear(); // Clear previous processes
            for (int i = 0; i < n; i++) {
                System.out.print("Enter Process ID (e.g., P1, P2): ");
                String id = sc.nextLine();
                System.out.print("Enter Arrival Time: ");
                int arrivalTime = sc.nextInt();
                System.out.print("Enter Burst Time: ");
                int burstTime = sc.nextInt();
                sc.nextLine(); // Consume newline character
                processes.add(new Process(id, arrivalTime, burstTime));
            }

            switch (choice) {
                case 1:
                    CPUScheduling.FCFS(processes);
                    break;
                case 2:
                CPUScheduling.SJF(processes);
                    break;
                case 3:
                CPUScheduling.SRT(processes);
                    break;
                case 4:
                    System.out.print("Enter Time Quantum for Round Robin: ");
                    int quantum = sc.nextInt();
                    CPUScheduling.RR(processes, quantum);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    continue;
            }

            // Print output for the selected algorithm
            System.out.println("\nProcess ID\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
            for (Process p : processes) {
                System.out.println(p.id + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.waitingTime + "\t\t" + p.turnaroundTime);
            }
            System.out.println("Average Waiting Time: " + CPUScheduling.calculateAverageWaitingTime(processes));
            System.out.println("Average Turnaround Time: " + CPUScheduling.calculateAverageTurnaroundTime(processes));
        }
        sc.close();
    }
}
