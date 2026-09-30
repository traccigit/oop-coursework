import javax.swing.*;

public class HeroPanel extends TaskPanel {
    private Hero hero=new Hero(new Point(0,0),new WalkingStrategy());
    final JComboBox<String> strategy=new JComboBox<>(new String[]{"Пешком","На лошади","Полёт"});
    final JTextField x=JellyTheme.field("10",8), y=JellyTheme.field("5",8);
    final JTextArea position=JellyTheme.area(false,1);
    public HeroPanel() {
        super("Лабораторная работа 1","Выберите способ передвижения и новую точку маршрута.");
        addBlock(JellyTheme.label("СПОСОБ ПЕРЕМЕЩЕНИЯ",11,true)); addBlock(JellyTheme.row(strategy));
        addBlock(JellyTheme.label("ТОЧКА НАЗНАЧЕНИЯ",11,true));
        addBlock(JellyTheme.row(JellyTheme.label("X",14,true),x,JellyTheme.label("Y",14,true),y));
        addBlock(JellyTheme.row(button("Переместить героя",true,this::move),button("Начать заново",false,() -> { hero=new Hero(new Point(0,0),new WalkingStrategy()); position.setText("Текущая позиция: (0, 0)"); info("Маршрут сброшен. Герой в точке (0, 0)."); })));
        position.setText("Текущая позиция: (0, 0)"); addBlock(position);
        info("Герой готов к путешествию. Начальная позиция: (0, 0).");
    }
    void move() {
        final int px,py;
        try { px=Integer.parseInt(x.getText().trim()); py=Integer.parseInt(y.getText().trim()); }
        catch(NumberFormatException e) { throw new IllegalArgumentException("Координаты X и Y должны быть целыми числами от −2147483648 до 2147483647."); }
        MovementStrategy[] choices={new WalkingStrategy(),new HorseRidingStrategy(),new FlyingStrategy()};
        hero.setMovementStrategy(choices[strategy.getSelectedIndex()]);
        String result=hero.move(new Point(px,py));
        output.append("\n"+result); output.setCaretPosition(output.getDocument().getLength());
        position.setText("Текущая позиция: "+hero.getPosition());
    }
}
