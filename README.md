# cpuscheduling
Here’s a more detailed explanation of the mentioned CPU scheduling algorithms:

# 1. First-Come, First-Served (FCFS)
Working: Processes are executed in the order of their arrival in the ready queue. <br>
Advantages: Simple to implement; no starvation as each process eventually gets executed. <br>
Disadvantages: High average waiting time; the Convoy Effect can occur where shorter processes get stuck behind longer ones. <br>
# 2. Shortest Job Next (SJN) 
Working: The process with the smallest burst time (execution time) is selected for execution first. <br>
Advantages: Minimizes average waiting time and is optimal in terms of turnaround time. <br>
Disadvantages: Not practical for real-time systems as burst times are often not known in advance; can cause starvation for longer processes. <br>
# 3. Priority Scheduling
Working: Processes are scheduled based on priority. Higher priority processes are executed first. <br>
Advantages: Important tasks get priority and are executed earlier. <br>
Disadvantages: Can lead to starvation where low-priority processes may never get executed. Aging can be implemented to mitigate this by increasing priority over time. <br>
# 4. Round Robin (RR)
Working: Each process is assigned a fixed time slice (called quantum) and is executed in a cyclic order. If a process isn’t finished within its time slice, it’s placed at the end of the queue. <br>
Advantages: Fair allocation of CPU time; good for time-sharing systems. <br>
Disadvantages: Higher average turnaround time compared to SJN; performance heavily depends on the size of the time quantum (too small leads to overhead, too large makes it similar to FCFS). <br>

These algorithms are used depending on system requirements, like throughput, fairness, and response time.

# Contanarizing our application/program<br>
# Step 1: Write a Dockerfile.<br>
FROM openjdk:latest<br>
WORKDIR /app<br>
COPY . /app<br>
RUN javac CPUScheduling.java<br>
CMD ["java","CPUScheduling"]<br>

-> openjdk is base image, used to run the application.<br>
-> working directory is created in container , here app.<br>
-> copy everything from current directory to working directory of container.<br>
-> run to compile the dockerfile<br>
-> finally run the application<br>

# Step 2: To build the docker image from dockerfile.<br>
 docker build -t scheduling .<br>
