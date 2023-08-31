package pl.javastart.task.writer;

import pl.javastart.task.TournamentResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StatsWriter {
    public static final String STATS_FILE_NAME = "stats.csv";

    public static void writeToFile(List<TournamentResult> list) throws IOException {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(STATS_FILE_NAME))
        ) {
            for (TournamentResult result : list) {
                bufferedWriter.write(result.outputData());
                bufferedWriter.newLine();
            }
        }
    }
}
