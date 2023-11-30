import java.util.*;

public class Simulator {
    
    ArrayList<Worker> workers;
    Queue<Integer> cogOrders;
    ArrayList<Integer> cogList;
    private boolean log = true;

    public Simulator(ArrayList<Worker> workers, ArrayList<Integer> cogs) {
        this.workers = workers;
        this.cogList = cogs;
        resetQueue();
    }

    public void resetQueue() {
        cogOrders = new LinkedList<Integer>();
        for(int x : cogList)
            cogOrders.add(x);

    }

    public void run() {

        PriorityQueue<Integer> numbers = new PriorityQueue<Integer>();
        
        int hours = 0;
        
        PriorityQueue<Worker> priorityWorkers = new PriorityQueue<Worker>();
        for(Worker w : workers) {
            priorityWorkers.add(w);
            if(!cogOrders.isEmpty())
                numbers.add(cogOrders.poll());
        }

        while(!priorityWorkers.isEmpty()) {
            priorityWorkers.poll().assignOrder(numbers.poll());
        }
        
        while (! (cogOrders.isEmpty() && allDone()) ) {
            hours++;

            for (Worker w : workers) {
                w.workOneHour();
                if (!w.isBusy()) {
                    priorityWorkers.add(w);
                    if(!cogOrders.isEmpty())
                        numbers.add(cogOrders.poll());
                }
            }
        
            while(!priorityWorkers.isEmpty()){
                Worker w = priorityWorkers.poll();
                if (!numbers.isEmpty())
                    w.assignOrder(numbers.poll());
            }
            
        }

        if(log) {
            //print relevant details
            System.out.println("Hours: " + hours);
            for(int i = 0; i < workers.size(); i++) {
                System.out.println("Name: " + workers.get(i).getName());
                System.out.println("CPH: " + workers.get(i).getCph());
                System.out.println("Total Cogs Produced: " + workers.get(i).getTotalCogsProduced());
                System.out.println("Total Waste: " + workers.get(i).getTotalWaste());
            }
        }
    }

    public boolean allDone() {
        boolean done = true;
        for(int i = 0; i < workers.size(); i++) {
            if(workers.get(i).isBusy())
                done = false;
        }
        
        return done;
    }

 public static void main(String[] args) {
        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker("A", 30));
        workers.add(new Worker("B", 45));
        ArrayList<Integer> cogs = new ArrayList<Integer>();
        cogs.add(1000);
        cogs.add(1000);
        cogs.add(1000);
        cogs.add(1000);
        cogs.add(1000);
        Simulator fs = new Simulator(workers, cogs);
        fs.run();
    }
}
