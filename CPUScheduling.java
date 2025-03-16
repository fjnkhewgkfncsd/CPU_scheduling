import java.util.*;
public class CPUScheduling {

    // Method to calculate average waiting time
    public static double calculateAverageWaitingTime(List<Process> processes) {
        double totalWaitingTime = 0;
        for (Process p : processes) {
            totalWaitingTime += p.waitingTime;
        }
        return totalWaitingTime / processes.size();
    }

    // Calculate Average Turnaround Time
    public static double calculateAverageTurnaroundTime(List<Process> processes) {
        double totalTurnaroundTime = 0;
        for (Process p : processes) {
            totalTurnaroundTime += p.turnaroundTime;
        }
        return totalTurnaroundTime / processes.size();
    }

    // Print Gantt Chart
    public static void printGanttChart(List<String> ganttChart) {
        System.out.println("\nGantt Chart:");
        for (String p : ganttChart) {
            System.out.print(p + " | ");
        }
        System.out.println();
    }

    // FCFS Scheduling Algorithm
    public static void FCFS(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time
        int currentTime = 0;
        List<String> ganttChart = new ArrayList<>();

        for (Process p : processes) {
            if (p.arrivalTime > currentTime) {
                currentTime = p.arrivalTime;
            }

            ganttChart.add("P" + p.id);
            p.waitingTime = currentTime - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            currentTime += p.burstTime;
        }

        printGanttChart(ganttChart);
    }
    

    // SJF Scheduling Algorithm
    public static void SJF(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time
        int currentTime = 0, completed = 0;
        List<String> ganttChart = new ArrayList<>();
        List<Process> readyQueue = new ArrayList<>();

        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !readyQueue.contains(p) && p.remainingTime > 0) {
                    readyQueue.add(p);
                }
            }

            if (!readyQueue.isEmpty()) {
                Process shortest = readyQueue.stream().min(Comparator.comparingInt(p -> p.burstTime)).get();
                readyQueue.remove(shortest);
                ganttChart.add("P" + shortest.id);

                shortest.waitingTime = currentTime - shortest.arrivalTime;
                shortest.turnaroundTime = shortest.waitingTime + shortest.burstTime;
                currentTime += shortest.burstTime;
                shortest.remainingTime = 0;
                completed++;
            } else {
                currentTime++;
            }
        }

        printGanttChart(ganttChart);
    }

    // SRT Scheduling Algorithm (Preemptive SJF)
    public static void SRT(List<Process> processes) {
        int currentTime = 0, completed = 0;
        int n = processes.size();
        List<String> ganttChart = new ArrayList<>();  // Store execution order for Gantt Chart

        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        while (completed < n) {
            Process shortest = null;
            int minRemainingTime = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time that has arrived
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && p.remainingTime < minRemainingTime) {
                    shortest = p;
                    minRemainingTime = p.remainingTime;
                }
            }

            if (shortest == null) {
                // No process available, move time forward
                ganttChart.add("Idle");
                currentTime++;
            } else {
                // Execute the process for one time unit
                ganttChart.add("P" + shortest.id);
                shortest.remainingTime--;
                currentTime++;

                // If the process is finished
                if (shortest.remainingTime == 0) {
                    completed++;
                    shortest.completionTime = currentTime;
                    shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                    shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
                }
            }
        }
        printGanttChart(ganttChart);
    }

    // Round Robin Scheduling Algorithm
    public static void RR(List<Process> processes, int quantum) {
        Queue<Process> queue = new LinkedList<>(processes);
        int currentTime = 0;
        List<String> ganttChart = new ArrayList<>();

        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();

            if (currentProcess.remainingTime > quantum) {
                ganttChart.add("P" + currentProcess.id);
                currentTime += quantum;
                currentProcess.remainingTime -= quantum;
                queue.add(currentProcess);
            } else {
                ganttChart.add("P" + currentProcess.id);
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;
                currentProcess.turnaroundTime = currentTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
            }
        }
        printGanttChart(ganttChart);
    }
    
}