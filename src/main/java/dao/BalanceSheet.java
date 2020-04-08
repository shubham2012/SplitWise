package dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class BalanceSheet {

    private static Map<UUID, Map<UUID, Double>> balanceSheet;

    public BalanceSheet() {
        balanceSheet = new LinkedHashMap<>();
    }

    public void setBalanceSheet(UUID from, UUID to, Double amount) {
        this.balanceSheet = new HashMap<>();
    }

    public Map<UUID, Map<UUID, Double>> getEntireBalanceSheet() {
        return balanceSheet;
    }

    public Map<UUID, Double> getBalanceSheet(UUID uuid) {
        return balanceSheet.get(uuid);
    }

    public void addExpense(UUID from, UUID to, Double amount) {
        if (from == to)
            return;
        Map<UUID, Double> expenseFrom = balanceSheet.getOrDefault(from, new LinkedHashMap<>());
        Map<UUID, Double> expenseTo = balanceSheet.getOrDefault(to, new LinkedHashMap<>());
        expenseFrom.put(to, format(expenseFrom.getOrDefault(to, 0.0) + amount));
        expenseTo.put(from, format(expenseTo.getOrDefault(from, 0.0) - amount));
        balanceSheet.put(from, expenseFrom);
        balanceSheet.put(to, expenseTo);
    }

    private Double format(double val) {
        val *= 100;
        val = Math.round(val);
        val /= 100;
        return val;
    }
}
