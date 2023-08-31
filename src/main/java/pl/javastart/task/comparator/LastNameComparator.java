package pl.javastart.task.comparator;

import pl.javastart.task.TournamentResult;

import java.util.Comparator;

public class LastNameComparator implements Comparator<TournamentResult> {
    @Override
    public int compare(TournamentResult t1, TournamentResult t2) {
        return t1.getPlayer().getLastName().compareTo(t2.getPlayer().getLastName());
    }
}
