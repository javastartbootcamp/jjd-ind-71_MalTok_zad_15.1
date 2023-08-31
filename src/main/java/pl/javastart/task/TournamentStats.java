package pl.javastart.task;

import pl.javastart.task.comparator.FirstNameComparator;
import pl.javastart.task.comparator.LastNameComparator;
import pl.javastart.task.comparator.ResultComparator;
import pl.javastart.task.writer.StatsWriter;

import java.io.IOException;
import java.util.*;

public class TournamentStats {
    private static final String STOP = "STOP";
    private static final int SORT_FIRST_NAME = 1;
    private static final int SORT_LAST_NAME = 2;
    private static final int SORT_RESULT = 3;
    private static final int SORTING_ORDER_DESCENDING = 2;

    void run(Scanner scanner) {
        // tutaj dodaj swoje rozwiązanie
        // użyj przekazanego scannera do wczytywania wartości

        List<TournamentResult> results = getTournamentResults(scanner);

        if (results.isEmpty()) {
            System.out.println("Lista jest pusta");
        } else {
            Comparator<TournamentResult> resultsComparator = getComparator(scanner);
            results.sort(resultsComparator);
            printList(results);
            try {
                StatsWriter.writeToFile(results);
                System.out.println("Wyniki zapisane do pliku " + StatsWriter.STATS_FILE_NAME);
            } catch (IOException e) {
                System.err.println("Nie udało się zapisać pliku " + StatsWriter.STATS_FILE_NAME);
            }
        }
    }

    private List<TournamentResult> getTournamentResults(Scanner scanner) {
        List<TournamentResult> results = new ArrayList<>();
        String userInput;
        boolean stop;
        do {
            System.out.println("Podaj wynik kolejnego gracza (lub stop):");
            userInput = scanner.nextLine();
            stop = userInput.equalsIgnoreCase(STOP);
            if (!stop) {
                String[] splitedLine = userInput.split(" ");
                if (splitedLine.length == 3) {
                    TournamentResult tournamentResult = createTournamentResult(splitedLine);
                    results.add(tournamentResult);
                } else {
                    System.out.println("Błędne dane, spróbuj ponownie");
                }
            }
        } while (!stop);
        return results;
    }

    private TournamentResult createTournamentResult(String[] splitedLine) {
        String firstName = splitedLine[0];
        String lastName = splitedLine[1];
        Player player = new Player(firstName, lastName);
        int result = Integer.parseInt(splitedLine[2]);
        return new TournamentResult(player, result);
    }

    private Comparator<TournamentResult> getComparator(Scanner scanner) {
        Comparator<TournamentResult> resultsComparator = getComparatorType(scanner);
        System.out.println("Sortować rosnąco czy malejąco? (1 - rosnąco, 2 - malejąco)");
        int option = scanner.nextInt();
        if (option == SORTING_ORDER_DESCENDING) {
            return resultsComparator.reversed();
        }
        return resultsComparator;
    }

    private Comparator<TournamentResult> getComparatorType(Scanner scanner) {
        Comparator<TournamentResult> resultsComparator = null;
        boolean optionOk = false;
        do {
            System.out.println("Po jakim parametrze posortować? (1 - imię, 2 - nazwisko, 3 - wynik)");
            int option = scanner.nextInt();
            if (option == SORT_FIRST_NAME) {
                resultsComparator = new FirstNameComparator();
                optionOk = true;
            } else if (option == SORT_LAST_NAME) {
                resultsComparator = new LastNameComparator();
                optionOk = true;
            } else if (option == SORT_RESULT) {
                resultsComparator = new ResultComparator();
                optionOk = true;
            }
        } while (!optionOk);
        return resultsComparator;
    }

    private void printList(List<TournamentResult> list) {
        for (TournamentResult result : list) {
            System.out.println(result);
        }
    }
}
