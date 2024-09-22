# cpuscheduling
Here’s a more detailed explanation of the mentioned CPU scheduling algorithms:

# 1. First-Come, First-Served (FCFS)
Working: Processes are executed in the order of their arrival in the ready queue.

Advantages: Simple to implement; no starvation as each process eventually gets executed. '<br>' return
Disadvantages: High average waiting time; the Convoy Effect can occur where shorter processes get stuck behind longer ones.
# 2. Shortest Job Next (SJN)
Working: The process with the smallest burst time (execution time) is selected for execution first.
Advantages: Minimizes average waiting time and is optimal in terms of turnaround time.
Disadvantages: Not practical for real-time systems as burst times are often not known in advance; can cause starvation for longer processes.
# 3. Priority Scheduling
Working: Processes are scheduled based on priority. Higher priority processes are executed first.
Advantages: Important tasks get priority and are executed earlier.
Disadvantages: Can lead to starvation where low-priority processes may never get executed. Aging can be implemented to mitigate this by increasing priority over time.
# 4. Round Robin (RR)
Working: Each process is assigned a fixed time slice (called quantum) and is executed in a cyclic order. If a process isn’t finished within its time slice, it’s placed at the end of the queue.
Advantages: Fair allocation of CPU time; good for time-sharing systems.
Disadvantages: Higher average turnaround time compared to SJN; performance heavily depends on the size of the time quantum (too small leads to overhead, too large makes it similar to FCFS).

These algorithms are used depending on system requirements, like throughput, fairness, and response time.
