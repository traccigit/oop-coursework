public class DemoClass {
    private final java.util.function.Consumer<String> output;
    public DemoClass(java.util.function.Consumer<String> output) { this.output = output; }

    // Публичные методы (2 шт.)
    public void publicMethod(int number) {
        output.accept("publicMethod: " + number);
    }

    public void printMessage(String message, boolean important) {
        output.accept("printMessage: " + message + ", important=" + important);
    }

    // Защищённые методы (3 шт.)
    @Repeat(2)
    protected void protectedWithOneArg(String message) {
        output.accept("protectedWithOneArg: " + message);
    }

    @Repeat(3)
    protected void protectedWithArgs(String text, int number) {
        output.accept("protectedWithArgs: text=\"" + text + "\", number=" + number);
    }

    protected void protectedNotAnnotated(double value) {
        output.accept("protectedNotAnnotated: " + value);
    }

    // Приватные методы (3 шт.)
    @Repeat(2)
    private void privateWithPrimitiveArgs(boolean flag, double value, char symbol) {
        output.accept(
                "privateWithPrimitiveArgs: flag=" + flag
                        + ", value=" + value
                        + ", symbol=" + symbol
        );
    }

    @Repeat(4)
    private void privateWithObjectArg(SampleData data) {
        output.accept("privateWithObjectArg: " + data);
    }

    private void privateNotAnnotated(long id) {
        output.accept("privateNotAnnotated: " + id);
    }

    /**
     * Небольшой класс-параметр с конструктором без аргументов.
     * Он нужен, чтобы показать, что вызывающий код умеет передавать
     * не только примитивы и строки, но и реальные объекты (не null).
     */
    public static class SampleData {
        public SampleData() {
        }

        @Override
        public String toString() {
            return "SampleData{}";
        }
    }
}
