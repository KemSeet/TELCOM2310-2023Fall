import simulator.PacketQueueSimulation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int x = 1_000_000;

        try {
            // Create the output directory if it doesn't exist
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("Project1/output"));

            // Constant rates
            try (BufferedWriter constantWriter = new BufferedWriter(new FileWriter("Project1/output/ConstantRate.txt"))) {
                int[] lambdaValues = {25, 75, 125};
                int[] muValues = {60, 100, 125};
                int[] nValues = {60, 100, 150};

                for (int lambda : lambdaValues) {
                    for (int mu : muValues) {
                        for (int n : nValues) {
                            PacketQueueSimulation simulation = new PacketQueueSimulation(lambda, mu, n);
                            simulation.runSimulation(x, constantWriter);
                        }
                    }
                }
            }

            // Variable input rate
            try (BufferedWriter variableWriter = new BufferedWriter(new FileWriter("Project1/output/VariableRate.txt"))) {
                PacketQueueSimulation variableRateSim = new PacketQueueSimulation(70, 125, 100);
                for (int i = 0; i < x; i++) {
                    double percent = (double) i / x * 100;

                    if (percent <= 10) {
                        variableRateSim = new PacketQueueSimulation(70, 125, 100);
                    } else if (percent <= 70) {
                        variableRateSim = new PacketQueueSimulation(200, 125, 100);
                    } else if (percent <= 80) {
                        variableRateSim = new PacketQueueSimulation(130, 125, 100);
                    } else if (percent <= 90) {
                        variableRateSim = new PacketQueueSimulation(120, 125, 100);
                    } else {
                        variableRateSim = new PacketQueueSimulation(70, 125, 100);
                    }
                    variableRateSim.runSimulation(1, variableWriter);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
