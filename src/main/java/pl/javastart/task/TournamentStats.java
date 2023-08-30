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
    private static final int SORTING_ORDER_ASCENDING = 1;
    private static final int SORTING_ORDER_DESCENDING = 2;

    private List<TournamentResult> results;

    void run(Scanner scanner) {
        // tutaj dodaj swoje rozwiązanie
        // użyj przekazanego scannera do wczytywania wartości

        results = getTournamentResults(scanner); //nie inicjuję listy w konstruktorze, ze względu na sposób korzystania ze skanera

        if (results.isEmpty()) {
            System.out.println("Lista jest pusta");
        } else {
            sort(scanner);
            printSortedList(results); //chcę drukować wyniki jeden pod drugim
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
        do {
            System.out.println("Podaj wynik kolejnego gracza (lub stop):");
            userInput = scanner.nextLine();
            if (!userInput.equalsIgnoreCase(STOP)) {
                String[] splitedLine = userInput.split(" ");
                if (splitedLine.length == 3) {
                    TournamentResult tournamentResult = createTournamentResult(splitedLine);
                    results.add(tournamentResult);
                } else {
                    System.out.println("Błędne dane, spróbuj ponownie");
                }
            }
        } while (!userInput.equalsIgnoreCase(STOP));
        return results;
    }

    private TournamentResult createTournamentResult(String[] splitedLine) {
        String firstName = splitedLine[0];
        String lastName = splitedLine[1];
        TournamentResult.Player player = new TournamentResult.Player(firstName, lastName);
        int result = Integer.parseInt(splitedLine[2]);
        return new TournamentResult(player, result);
    }

    private void sort(Scanner scanner) {
        Comparator<TournamentResult> resultsComparator = getComparator(scanner);
        sortInUserOrder(scanner, resultsComparator);
    }

    private Comparator<TournamentResult> getComparator(Scanner scanner) {
        System.out.println("Po jakim parametrze posortować? (1 - imię, 2 - nazwisko, 3 - wynik)");
        Comparator<TournamentResult> resultsComparator = null;
        int option = scanner.nextInt();
        switch (option) {
            case SORT_FIRST_NAME -> resultsComparator = new FirstNameComparator();
            case SORT_LAST_NAME -> resultsComparator = new LastNameComparator();
            case SORT_RESULT -> resultsComparator = new ResultComparator();
            default -> getComparator(scanner); //nie chcę błędnych opcji, ani zakończenia programu
        }
        return resultsComparator;
    }

    private void sortInUserOrder(Scanner scanner, Comparator<TournamentResult> resultsComparator) {
        System.out.println("Sortować rosnąco czy malejąco? (1 - rosnąco, 2 - malejąco)");
        int optionOrder = scanner.nextInt();
        switch (optionOrder) {
            case SORTING_ORDER_ASCENDING -> results.sort(resultsComparator);
            case SORTING_ORDER_DESCENDING -> results.sort(resultsComparator.reversed());
            default -> sortInUserOrder(scanner, resultsComparator); //nie chcę błędnych opcji, ani zakończenia programu
        }
    }

    private void printSortedList(List<TournamentResult> list) {
        for (TournamentResult result : list) {
            System.out.println(result);
        }
    }
}
