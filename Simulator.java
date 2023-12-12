import java.util.*;

public class Simulator {
    
    ArrayList<Worker> workers;
    Queue<Integer> cogOrders;
    ArrayList<Integer> cogList;
    private boolean log = false;

    private int fulltotalcogs = 0;
    private int fulltotalwaste = 0;

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
            int totalcogs = 0;
            int totalwaste = 0;
            //print relevant details
            System.out.println("Hours: " + hours);
            for(int i = 0; i < workers.size(); i++) {
                System.out.println("Name: " + workers.get(i).getName());
                System.out.println("CPH: " + workers.get(i).getCph());
                System.out.println("Total Cogs Produced: " + workers.get(i).getTotalCogsProduced());
                System.out.println("Total Waste: " + workers.get(i).getTotalWaste());

                totalcogs += workers.get(i).getTotalCogsProduced();
                totalwaste += workers.get(i).getTotalWaste();
            }

            System.out.println("\nTotal Cogs Produced by all Workers: " + totalcogs);
            System.out.println("Total Waste from all Workers: " + totalwaste);
            System.out.println("Average Cogs Produced Per Worker: " + totalcogs / 6.0);
            System.out.println("Average Waste Per Worker: " + totalwaste / 6.0);

            fulltotalcogs += totalcogs;
            fulltotalwaste += totalwaste;
        }
    }

    public void runBase() {
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
        
        if(log) {
            int totalcogs = 0;
            int totalwaste = 0;
            //print relevant details
            System.out.println("Hours: " + hours);
            for(int i = 0; i < workers.size(); i++) {
                System.out.println("Name: " + workers.get(i).getName());
                System.out.println("CPH: " + workers.get(i).getCph());
                System.out.println("Total Cogs Produced: " + workers.get(i).getTotalCogsProduced());
                System.out.println("Total Waste: " + workers.get(i).getTotalWaste());

                totalcogs += workers.get(i).getTotalCogsProduced();
                totalwaste += workers.get(i).getTotalWaste();
            }

            System.out.println("\nTotal Cogs Produced by all Workers: " + totalcogs);
            System.out.println("Total Waste from all Workers: " + totalwaste);
            System.out.println("Average Cogs Produced Per Worker: " + totalcogs / 6.0);
            System.out.println("Average Waste Per Worker: " + totalwaste / 6.0);
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
        
        for(int i = 0; i < 6; i++) {
            workers.add(new Worker("" + i, (int)(Math.random() * 40 + 15)));
        }

        ArrayList<Integer> cogs = new ArrayList<Integer>();
        
        for(int i = 0; i < 100; i++) {
            cogs.add((int)(Math.random() * 80 + 20));
        }

        Simulator fs = new Simulator(workers, cogs);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while(running) {
            System.out.println("Which algorithm would you like to run? \nBaseline \nImproved");
            String answer = scanner.nextLine();
        
            if(answer.toLowerCase().equals("improved")) {
                for(int i = 0; i < 1000; i++) {
                    fs.run();
                }
                System.out.println("\nTotal Cogs Produced by all Workers: " + fulltotalcogs);
                System.out.println("Total Waste from all Workers: " + fulltotalwaste);
                System.out.println("Average Cogs Produced Per Worker: " + fulltotalcogs / 6000.0);
                System.out.println("Average Waste Per Worker: " + fulltotalwaste / 6000.0);
                running = false;
            } else if(answer.toLowerCase().equals("baseline")) {
                fs.runBase();
                running = false;
            } else
                System.out.println("That is not one of the algorithms");

        }

        scanner.close();
    }    
}
