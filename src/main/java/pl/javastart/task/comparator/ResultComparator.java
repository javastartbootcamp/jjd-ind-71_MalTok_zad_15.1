package pl.javastart.task.comparator;

import pl.javastart.task.TournamentResult;

import java.util.Comparator;

public class ResultComparator implements Comparator<TournamentResult> {
    @Override
    public int compare(TournamentResult t1, TournamentResult t2) {
        return Integer.compare(t1.getResult(), t2.getResult());
    }
}
