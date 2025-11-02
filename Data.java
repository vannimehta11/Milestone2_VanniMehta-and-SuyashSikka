package app;

import java.util.*;
import java.util.stream.Collectors;

public class Data {
    public static final String[] DISEASES = {"Measles", "Polio", "DTP3"};
    public static final String[] REGIONS = {"Africa", "Americas", "EMRO", "Europe", "SEARO", "WPRO"};
    public static final Integer[] YEARS = {2023, 2022, 2021};

    public static final List<Row> ROWS = Arrays.asList(
        new Row("Australia", "WPRO", 2023, "Measles", 95.0),
        new Row("India", "SEARO", 2023, "Measles", 92.0),
        new Row("Kenya", "Africa", 2023, "Measles", 86.0),
        new Row("Brazil", "Americas", 2023, "Measles", 88.0),
        new Row("Germany", "Europe", 2023, "Measles", 93.0),
        new Row("Nepal", "SEARO", 2023, "Measles", null),
        new Row("Australia", "WPRO", 2023, "Polio", 94.0),
        new Row("India", "SEARO", 2023, "Polio", 90.0),
        new Row("Brazil", "Americas", 2023, "Polio", 91.0),
        new Row("Germany", "Europe", 2023, "Polio", 93.0)
    );

    public static double herdThreshold(String disease) {
        switch (disease) {
            case "Measles": return 95.0;
            case "Polio": return 90.0;
            default: return 90.0;
        }
    }

    public static List<Row> filter(int year, String disease, String regionOrAll, String query) {
        String q = query == null ? "" : query.trim().toLowerCase();
        return ROWS.stream()
            .filter(r -> r.year == year)
            .filter(r -> r.disease.equals(disease))
            .filter(r -> "All".equals(regionOrAll) || r.region.equals(regionOrAll))
            .filter(r -> q.isEmpty() || r.country.toLowerCase().contains(q))
            .sorted((a,b) -> {
                double av = a.vaccinationRate == null ? -1 : a.vaccinationRate;
                double bv = b.vaccinationRate == null ? -1 : b.vaccinationRate;
                return Double.compare(bv, av);
            })
            .collect(Collectors.toList());
    }
}
