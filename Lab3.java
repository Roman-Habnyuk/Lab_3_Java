import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZooService {

    // Метод для отримання списку тварин, які мають вказаний вік або старші
    public static List<Animal> filterAnimalsByAgeGreaterThan(List<Animal> animals, int age) {
        return animals.stream()
                .filter(animal -> animal.getAge() >= age)
                .collect(Collectors.toList());
    }

    // Метод для підрахунку кількості тварин за видом
    public static Map<String, Long> countAnimalsBySpecies(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));
    }

    // Метод для визначення середньої вмісткістю вольєрів
    public static double calculateAverageEnclosureCapacity(List<Enclosure> enclosures) {
        return enclosures.stream()
                .mapToInt(Enclosure::getCapacity)
                .average()
                .orElse(0);
    }

    // Метод для фільтрації вольєрів за типом та вмісткістю
    public static List<Enclosure> filterEnclosuresByTypeAndCapacity(List<Enclosure> enclosures, String type, int minCapacity, int maxCapacity) {
        return enclosures.stream()
                .filter(enclosure -> enclosure.getType().equalsIgnoreCase(type) &&
                        enclosure.getCapacity() >= minCapacity && enclosure.getCapacity() <= maxCapacity)
                .collect(Collectors.toList());
    }

   // Метод для об'єднання списку тварин та вольєрів за певним критерієм (наприклад, по розташуванню вольєру)
    public static Map<String, List<Object>> mergeAnimalsAndEnclosuresByLocation(List<Animal> animals, List<Enclosure> enclosures) {
        Map<String, List<Animal>> animalsByLocation = animals.stream()
                .collect(Collectors.groupingBy(animal -> getEnclosureLocation(enclosures, animal)));

        Map<String, List<Enclosure>> enclosuresByLocation = enclosures.stream()
                .collect(Collectors.groupingBy(Enclosure::getLocation));

        Map<String, List<Object>> mergedMap = new HashMap<>();

        for (String location : animalsByLocation.keySet()) {
            List<Object> mergedList = new ArrayList<>();
            mergedList.addAll(animalsByLocation.get(location));
            mergedList.addAll(enclosuresByLocation.getOrDefault(location, Collections.emptyList()));

            mergedMap.put(location, mergedList);
        }

        return mergedMap;
    }

    private static String getEnclosureLocation(List<Enclosure> enclosures, Animal animal) {
        // Логіка визначення розташування вольєру для тварини
        for (Enclosure enclosure : enclosures) {
            // Припустимо, що вольєр розташований в тому ж регіоні, як і тварина
            if (enclosure.getType().equalsIgnoreCase(animal.getSpecies())) {
                return enclosure.getLocation();
            }
        }
        return "Default Location"; 
    }
}