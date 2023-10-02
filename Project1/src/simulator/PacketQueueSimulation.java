package simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PacketQueueSimulation {
    // Variables for the state of the queue
    private int pktInQ = 0;
    private int pktDropped = 0;
    private double lambda;
    private double mu;
    private int n;
    private Random rand;

    public PacketQueueSimulation(double lambda, double mu, int n) {
        this.lambda = lambda;
        this.mu = mu;
        this.n = n;
        this.rand = new Random();
    }

    public void simulateEvent() {
        double y = rand.nextDouble();

        double pArrival = lambda / (mu + lambda);

        if(y <= pArrival) {
            if(pktInQ < n) {
                pktInQ++;
            } else {
                pktDropped++;
            }
        } else {
            if(pktInQ > 0) {
                pktInQ--;
            }
        }
    }

    public void runSimulation(int x, BufferedWriter writer) throws IOException {
        for (int i = 0; i < x; i++) {
            simulateEvent();
            writer.write("Lambda: " + lambda + ", Mu: " + mu + ", Buffer Size: " + n + ", Packets in Queue: " + pktInQ + ", Packets Dropped: " + pktDropped + "\n");
        }
    }
}
