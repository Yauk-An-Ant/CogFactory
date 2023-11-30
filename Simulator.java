import java.util.*;

public class Simulator {
    
    ArrayList<Worker> workers;
    static Queue<Integer> cogOrders;
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
        while(!cogOrders.isEmpty()) {
            numbers.add(cogOrders.poll());
        }

        PriorityQueue<Worker> priorityWorkers = new PriorityQueue<Worker>();
        for(Worker w : workers) {
            priorityWorkers.add(w);
        }
        

        /*
        for (Worker w : workers){
            if(!cogOrders.isEmpty())
                w.assignOrder(numbers.poll());
        }
        */

        while(!priorityWorkers.isEmpty()) {
            if(!numbers.isEmpty()) {
                if(!priorityWorkers.peek().isBusy())
                    priorityWorkers.poll().assignOrder(numbers.poll());
            }
        }
        
        int hours = 0;
        
        Queue<Worker> q = new LinkedList<>();
        
        while (! (cogOrders.isEmpty() && allDone()) ) {
        
            hours++;
            for (Worker w: workers) {
                w.workOneHour();
                if (!w.isBusy())
                    q.add(w);
            }
        
            while(!q.isEmpty()){
                Worker w = q.poll();
                if (!cogOrders.isEmpty())
                    w.assignOrder(cogOrders.poll());
            }
            
        }

        if(log) {
            //print relevant details
            for(int i = 0; i < workers.size(); i++) {
                System.out.println("Hours: " + hours);
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
        workers.add(new Worker("B", 25));
        ArrayList<Integer> cogs = new ArrayList<Integer>();
        cogs.add(100);
        cogs.add(60);
        cogs.add(800);
        cogs.add(25);
        cogs.add(45);
        Simulator fs = new Simulator(workers, cogs);
        fs.run();
    }
}
