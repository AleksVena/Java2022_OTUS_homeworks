package kz.alseco;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Atm {
    private final Map<Nominal, Cell> moneyStore = new EnumMap<>(Nominal.class);

    public Atm(List<Banknote> initialBanknotes) {
        addBanknotes(initialBanknotes);
    }

    public void cashIn(List<Banknote> banknotes) {
        addBanknotes(banknotes);
    }

    public List<Banknote> cashOut(int amount) {
        int amountCommon = getBalance();
        if (amountCommon < amount) {
            throw new NotEnoughMoneyException();
        }

        return split(amount).stream()
                .map(it -> {
                    Nominal nominal = Nominal.of(it);
                    return moneyStore.get(nominal).getBanknote();
                })
                .collect(Collectors.toList());
    }

    public int getBalance() {
        return moneyStore.values().stream()
                .mapToInt(Cell::getAmount)
                .sum();
    }

    public Map<Nominal, Integer> getBalanceCell() {
        return moneyStore.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getAmount()));
    }

    private void addBanknotes(List<Banknote> initialBanknotes) {
        Map<Nominal, List<Banknote>> byNominal = initialBanknotes.stream()
                .collect(Collectors.groupingBy(Banknote::getNominal));

        byNominal.forEach((nominal, banknotes) -> {
            moneyStore.compute(nominal, (k, cell) -> {
                if (cell == null) {
                    return new Cell(banknotes);
                } else {
                    cell.addBanknotes(banknotes);
                    return cell;
                }
            });
        });
    }

    private List<Integer> split(int amount) {
        Integer[] notes = Arrays.stream(Nominal.values())
                .map(Nominal::getValue)
                .sorted(Collections.reverseOrder())
                .toArray(Integer[]::new);

        int nominalCount = Nominal.values().length;
        int[] noteCounter = new int[nominalCount];

        for (int i = 0; i < nominalCount; i++) {
            if (amount >= notes[i]) {
                noteCounter[i] = amount / notes[i];
                amount = amount - noteCounter[i] * notes[i];
            }
        }

        return IntStream.range(0, nominalCount)
                .map(i -> {
                    if (noteCounter[i] != 0) {
                        return notes[i];
                    }
                    return 0;
                })
                .boxed()
                .filter(it -> it != 0)
                .collect(Collectors.toList());
    }
}
