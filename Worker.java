public class Worker implements Comparable<Worker>{

    private String name;
    private int cph;
    private int totalCogsProduced;
    private int totalWaste;
    private int cogsInProgress;

    public Worker(String name, int cph) {
        this.name = name;
        this.cph = cph;
        totalCogsProduced = 0;
        totalWaste = 0;
        cogsInProgress = 0;
    }

    public String getName() { return name; }
    public int getCph() { return cph; }
    public int getTotalCogsProduced() { return totalCogsProduced; }
    public int getTotalWaste() { return totalWaste; }

    public void reset() {
        totalCogsProduced = 0;
        totalWaste = 0;
        cogsInProgress = 0;
    }

    public boolean isBusy() {
        return cogsInProgress > 0;
    }

    public void assignOrder(int x) {
        cogsInProgress = x;
    }

    public void workOneHour() {
        if(cogsInProgress > cph) {
            cogsInProgress -= cph;
            totalCogsProduced += cph;
        } else {
            totalWaste += cph-cogsInProgress;
            totalCogsProduced += cogsInProgress;
            cogsInProgress = 0;
        }
    }

    @Override
    public int compareTo(Worker o) {
        if(cph == o.getCph())
            return 0;
        else if(cph < o.getCph())
            return -1;
        else
            return 1;
    }

}