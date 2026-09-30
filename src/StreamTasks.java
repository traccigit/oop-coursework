import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTasks {

    // 1. Возвращает среднее значение списка целых чисел
    public static double getAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    // 2. Приводит все строки в верхний регистр
    // и добавляет к ним префикс "new"
    public static List<String> toUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(String::toUpperCase)
                .map(string -> "new" + string)
                .collect(Collectors.toList());
    }

    // 3. Возвращает список квадратов элементов,
    // встречающихся в исходном списке только один раз
    public static List<Integer> getUniqueSquares(List<Integer> numbers) {
        Map<Integer, Long> frequencies = numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        return numbers.stream()
                .filter(number -> frequencies.get(number) == 1)
                .map(number -> Math.multiplyExact(number, number))
                .collect(Collectors.toList());
    }

    // 4. Возвращает последний элемент коллекции.
    // Если коллекция пуста, выбрасывает исключение
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(NoSuchElementException::new);
    }

    // 5. Возвращает сумму чётных чисел массива.
    // Если чётных чисел нет, возвращается 0
    public static int getEvenSum(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(number -> number % 2 == 0)
                .reduce(0, Math::addExact);
    }

    // 6. Преобразует список строк в Map:
    // первый символ строки — ключ,
    // оставшаяся часть строки — значение
    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(
                        string -> string.charAt(0),
                        string -> string.substring(1)
                ));
    }

}
