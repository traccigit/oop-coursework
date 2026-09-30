import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class StreamsPanel extends TaskPanel {
    final JComboBox<String> method=new JComboBox<>(new String[]{
        "1. Среднее значение", "2. Верхний регистр + new", "3. Квадраты уникальных элементов",
        "4. Последний элемент", "5. Сумма чётных чисел", "6. Строки → Map"});
    final JTextArea input=JellyTheme.area(true,4);
    private final JTextArea hint=JellyTheme.area(false,2);
    private final String[] drafts={"1 2 3 4 5","hello; мир; java","2 3 2 4","первый; второй; третий","1 2 3 4 5 6","apple; banana; cherry"};
    private int previous;
    public StreamsPanel() {
        super("Лабораторная работа 4","Шесть операций с числами и строками на основе Stream API.");
        addBlock(JellyTheme.label("ВЫБЕРИТЕ МЕТОД",11,true)); addBlock(JellyTheme.row(method));
        addBlock(hint); addBlock(JellyTheme.scroll(input));
        addBlock(JellyTheme.row(button("Выполнить",true,this::run)));
        method.addActionListener(e -> {drafts[previous]=input.getText(); previous=method.getSelectedIndex(); refresh();}); refresh();
    }
    private void refresh() {
        input.setText(drafts[previous]);
        hint.setText(previous==0||previous==2||previous==4?"Введите целые числа через пробел или перенос строки.":
            previous==5?"Введите строки через ;. Первые символы должны различаться.":
            previous==3?"Введите элементы через ;. Пустой ввод проверяет исключение для пустой коллекции.":"Введите строки через ;. К каждой строке добавится префикс new.");
    }
    static java.util.List<Integer> numbers(String text) {
        if(text.isBlank())return java.util.List.of();
        try {return Arrays.stream(text.trim().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());}
        catch(NumberFormatException ex) {throw new IllegalArgumentException("Нужны целые числа через пробел в диапазоне int.");}
    }
    static java.util.List<String> strings(String text) {
        if(text.isBlank())return java.util.List.of();
        java.util.List<String> result=Arrays.stream(text.split(";",-1)).map(String::trim).collect(Collectors.toList());
        if(result.stream().anyMatch(String::isEmpty))throw new IllegalArgumentException("Между разделителями ; не должно быть пустых строк.");
        return result;
    }
    void run() {
        String value=input.getText(); Object result;
        try {
            switch(method.getSelectedIndex()) {
                case 0: result=StreamTasks.getAverage(numbers(value));break;
                case 1: result=StreamTasks.toUpperCaseWithPrefix(strings(value));break;
                case 2: result=StreamTasks.getUniqueSquares(numbers(value));break;
                case 3: result=StreamTasks.getLastElement(strings(value));break;
                case 4: result=StreamTasks.getEvenSum(numbers(value).stream().mapToInt(Integer::intValue).toArray());break;
                default: result=StreamTasks.stringsToMap(strings(value));break;
            }
            info("Результат: "+result);
        } catch(NoSuchElementException ex) {throw new IllegalArgumentException("Коллекция пуста: последнего элемента нет.");}
        catch(IllegalStateException ex) {throw new IllegalArgumentException("Несколько строк имеют одинаковый первый символ. Ключи Map должны различаться.");}
        catch(ArithmeticException ex) {throw new IllegalArgumentException("Результат выходит за диапазон int. Уменьшите значения.");}
    }
}
