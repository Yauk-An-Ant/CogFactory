import java.util.*;

public class Simulator {
    
    ArrayList<Worker> workers;
    Queue<Integer> cogOrders;
    ArrayList<Integer> cogList;
    private boolean log = false;

    private int fulltotalcogs = 0;
    private int fulltotalwaste = 0;
    private int fullhours = 0;
    private double fullratio = 0;

    public int getFullCogs() { return fulltotalcogs;}
    public int getFullWaste() { return fulltotalwaste;}
    public int getFullHours() { return fullhours;}
    public double getFullRatio() { return fullratio;}
    public void resetFulls() {
        fulltotalcogs = 0;
        fulltotalwaste = 0;
        fullhours = 0;
        fullratio = 0;
    }

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
        resetQueue();

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

            int totalcogs = 0;
            int totalwaste = 0;
            double ratio = 0.0;
            //print relevant details
            if(log)
                System.out.println("Hours: " + hours);
            for(int i = 0; i < workers.size(); i++) {
                if(log) {
                    System.out.println("Name: " + workers.get(i).getName());
                    System.out.println("CPH: " + workers.get(i).getCph());
                    System.out.println("Total Cogs Produced: " + workers.get(i).getTotalCogsProduced());
                    System.out.println("Total Waste: " + workers.get(i).getTotalWaste());
                }

                totalcogs += workers.get(i).getTotalCogsProduced();
                totalwaste += workers.get(i).getTotalWaste();
                ratio = (double)totalcogs/totalwaste;
            }

            if(log) {
                System.out.println("\nTotal Cogs Produced by all Workers: " + totalcogs);
                System.out.println("Total Waste from all Workers: " + totalwaste);
                System.out.println("Average Cogs Produced Per Worker: " + totalcogs / 6.0);
                System.out.println("Average Waste Per Worker: " + totalwaste / 6.0);
            }
           
            fulltotalcogs += totalcogs;
            fulltotalwaste += totalwaste;
            fullhours += hours;
            fullratio += ratio;
    }

    public void runBase() {
        resetQueue();
        for (Worker w : workers){
            if(!cogOrders.isEmpty())
                w.assignOrder(cogOrders.poll());
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
        
        int totalcogs = 0;
        int totalwaste = 0;
        double ratio = 0.0;
        //print relevant details
        if(log)
            System.out.println("Hours: " + hours);
        for(int i = 0; i < workers.size(); i++) {
            if(log) {
                System.out.println("Name: " + workers.get(i).getName());
                System.out.println("CPH: " + workers.get(i).getCph());
                System.out.println("Total Cogs Produced: " + workers.get(i).getTotalCogsProduced());
                System.out.println("Total Waste: " + workers.get(i).getTotalWaste());
            }

            totalcogs += workers.get(i).getTotalCogsProduced();
            totalwaste += workers.get(i).getTotalWaste();
            ratio = (double)totalcogs/totalwaste;
        }

        if(log) {
            System.out.println("\nTotal Cogs Produced by all Workers: " + totalcogs);
            System.out.println("Total Waste from all Workers: " + totalwaste);
            System.out.println("Average Cogs Produced Per Worker: " + totalcogs / 6.0);
            System.out.println("Average Waste Per Worker: " + totalwaste / 6.0);
        }
           
        fulltotalcogs += totalcogs;
        fulltotalwaste += totalwaste;
        fullhours += hours;
        fullratio += ratio;
    }

    public boolean allDone() {
        boolean done = true;
        for(int i = 0; i < workers.size(); i++) {
            if(workers.get(i).isBusy())
                done = false;
        }
        
        return done;
    } 
}
