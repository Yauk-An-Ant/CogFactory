import java.util.*;

public class Main {
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
        
        System.out.println("Improved:");
        for(int i = 0; i < 1000; i++)
            fs.run();

        int fullcogs = Math.abs(fs.getFullCogs());
        int fullwaste = Math.abs(fs.getFullWaste());
        System.out.println("\nTotal Cogs Produced by all Workers: " + fullcogs);
        System.out.println("Total Waste from all Workers: " + fullwaste);
        System.out.println("Average Cogs Produced Per Worker: " + fullcogs / 6000.0);
        System.out.println("Average Waste Per Worker: " + fullwaste / 6000.0);

        fs.resetFulls();
        //baseline
        System.out.println("\nBaseline:");
        for(int i = 0; i < 1000; i++)
            fs.runBase();

        fullcogs = Math.abs(fs.getFullCogs());
        fullwaste = Math.abs(fs.getFullWaste());
        System.out.println("\nTotal Cogs Produced by all Workers: " + fullcogs);
        System.out.println("Total Waste from all Workers: " + fullwaste);
        System.out.println("Average Cogs Produced Per Worker: " + fullcogs / 6000.0);
        System.out.println("Average Waste Per Worker: " + fullwaste / 6000.0);

    }
}
