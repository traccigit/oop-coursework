import javax.swing.*;

public class ReflectionPanel extends TaskPanel {
    public ReflectionPanel() {
        super("Лабораторная работа 2","Вызов защищённых и приватных методов через Reflection API.");
        addBlock(JellyTheme.label("@Repeat — повторение методов",20,true));
        JTextArea explanation=JellyTheme.area(false,7);
        explanation.setText("Аннотация задаёт количество вызовов.\n\nprotectedWithOneArg → 2 раза\nprotectedWithArgs → 3 раза\nprivateWithPrimitiveArgs → 2 раза\nprivateWithObjectArg → 4 раза\n\nПубличные методы и методы без аннотации пропускаются.");
        addBlock(explanation);
        addBlock(JellyTheme.row(button("Вызвать методы",true,this::run)));
    }
    void run() {
        StringBuilder result=new StringBuilder();
        java.util.function.Consumer<String> log=s -> result.append(s).append('\n');
        AnnotationMethodInvoker.invokeAnnotatedMethods(new DemoClass(log),log);
        info(result.toString());
    }
}
