import java.util.*;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int remainingBurstTime;
    int priority;
    int completionTime;
    int waitingTime;
    int turnAroundTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
    }

    Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.priority = priority;
    }
}

public class CPUScheduling {

    // FCFS Scheduling
    public static void fcfsScheduling(List<Process> processes) {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.completionTime = currentTime + p.burstTime;
            p.turnAroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;

            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

            currentTime += p.burstTime;
        }

        printResults(processes, totalWaitingTime, totalTurnAroundTime);
    }

    // SJF Scheduling (Preemptive and Non-Preemptive)
    public static void sjfScheduling(List<Process> processes, boolean preemptive) {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int completed = 0;
        Process currentProcess = null;

        while (completed < processes.size()) {
            Process nextProcess = null;

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingBurstTime > 0) {
                    if (nextProcess == null || p.remainingBurstTime < nextProcess.remainingBurstTime) {
                        nextProcess = p;
                    }
                }
            }

            if (nextProcess == null) {
                currentTime++;
            } else {
                currentProcess = nextProcess;

                if (preemptive) {
                    currentProcess.remainingBurstTime--;
                    currentTime++;

                    if (currentProcess.remainingBurstTime == 0) {
                        currentProcess.completionTime = currentTime;
                        currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                        currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                        totalWaitingTime += currentProcess.waitingTime;
                        totalTurnAroundTime += currentProcess.turnAroundTime;
                        completed++;
                    }
                } else {
                    currentTime += currentProcess.remainingBurstTime;
                    currentProcess.remainingBurstTime = 0;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnAroundTime += currentProcess.turnAroundTime;
                    completed++;
                }
            }
        }

        printResults(processes, totalWaitingTime, totalTurnAroundTime);
    }

    // Priority Scheduling (Preemptive and Non-Preemptive)
    public static void priorityScheduling(List<Process> processes, boolean preemptive, boolean highPriority) {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int completed = 0;

        while (completed < processes.size()) {
            Process currentProcess = null;

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingBurstTime > 0) {
                    if (currentProcess == null || 
                       (highPriority && p.priority < currentProcess.priority) || 
                       (!highPriority && p.priority > currentProcess.priority)) {
                        currentProcess = p;
                    }
                }
            }

            if (currentProcess == null) {
                currentTime++;
            } else {
                if (preemptive) {
                    currentProcess.remainingBurstTime--;
                    currentTime++;

                    if (currentProcess.remainingBurstTime == 0) {
                        currentProcess.completionTime = currentTime;
                        currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                        currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                        totalWaitingTime += currentProcess.waitingTime;
                        totalTurnAroundTime += currentProcess.turnAroundTime;
                        completed++;
                    }
                } else {
                    currentTime += currentProcess.remainingBurstTime;
                    currentProcess.remainingBurstTime = 0;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnAroundTime += currentProcess.turnAroundTime;
                    completed++;
                }
            }
        }

        printResults(processes, totalWaitingTime, totalTurnAroundTime);
    }

    // Round Robin Scheduling
    public static void roundRobinScheduling(List<Process> processes, int quantum) {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int completed = 0;
        Queue<Process> queue = new LinkedList<>();

        for (Process p : processes) {
            if (p.arrivalTime <= currentTime) {
                queue.add(p);
            }
        }

        while (completed < processes.size()) {
            Process currentProcess = queue.poll();

            if (currentProcess != null) {
                if (currentProcess.remainingBurstTime > quantum) {
                    currentTime += quantum;
                    currentProcess.remainingBurstTime -= quantum;
                } else {
                    currentTime += currentProcess.remainingBurstTime;
                    currentProcess.remainingBurstTime = 0;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnAroundTime += currentProcess.turnAroundTime;
                    completed++;
                }

                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && p.remainingBurstTime > 0 && !queue.contains(p)) {
                        queue.add(p);
                    }
                }

                if (currentProcess.remainingBurstTime > 0) {
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }

        printResults(processes, totalWaitingTime, totalTurnAroundTime);
    }

    public static void printResults(List<Process> processes, int totalWaitingTime, int totalTurnAroundTime) {
        System.out.println("PID\tArrival\tBurst\tPriority\tCompletion\tTurnaround\tWaiting");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\t\t%d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.priority, p.completionTime, p.turnAroundTime, p.waitingTime);
        }

        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTurnAroundTime / processes.size());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the scheduling algorithm:");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.println("3. Priority Scheduling");
        System.out.println("4. Round Robin");

        int choice = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        System.out.println("Enter the number of processes:");
        int n = scanner.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.printf("Enter arrival time and burst time for Process %d:\n", i);
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes.add(new Process(i, arrivalTime, burstTime));
        }

        switch (choice) {
            case 1:
                fcfsScheduling(processes);
                break;
            case 2:
                System.out.println("Is the scheduling Preemptive? (true/false)");
                boolean preemptiveSJF = scanner.nextBoolean();
                sjfScheduling(processes, preemptiveSJF);
                break;
            case 3:
                System.out.println("Is the scheduling Preemptive? (true/false)");
                boolean preemptivePriority = scanner.nextBoolean();
                System.out.println("High Priority first? (true/false)");
                boolean highPriority = scanner.nextBoolean();
                System.out.println("Enter priorities for each process:");
                for (int i = 0; i < n; i++) {
                    System.out.printf("Enter priority for Process %d:\n", i + 1);
                    int priority = scanner.nextInt();
                    processes.get(i).priority = priority;
                }
                priorityScheduling(processes, preemptivePriority, highPriority);
                break;
            case 4:
                System.out.println("Enter the quantum time:");
                int quantum = scanner.nextInt();
                roundRobinScheduling(processes, quantum);
                break;
            default:
                System.out.println("Invalid choice");
        }

        scanner.close();
    }
}