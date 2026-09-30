import javax.swing.*;
import java.awt.*;

// Переключение между четырьмя заданиями.
public class JellyApp extends JellyTheme.Backdrop {
    public final JToggleButton[] navigation = new JToggleButton[4];
    public final CardLayout cards = new CardLayout();
    public final JPanel pages = new JPanel(cards);
    public final HeroPanel hero = new HeroPanel();
    public final ReflectionPanel reflection = new ReflectionPanel();
    public final TranslatorPanel translator = new TranslatorPanel();
    public final StreamsPanel streams = new StreamsPanel();
    public JellyApp() {
        JellyTheme.Card sidebar=new JellyTheme.Card(); sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240,0));
        JPanel top=JellyTheme.column();
        top.add(JellyTheme.label("ООП",30,true)); top.add(Box.createVerticalStrut(8));
        top.add(JellyTheme.label("Курсовая работа",12,false)); top.add(Box.createVerticalStrut(32));
        String[] names={"01   Перемещение", "02   Аннотации", "03   Переводчик", "04   Коллекции"};
        ButtonGroup group=new ButtonGroup();
        for(int i=0;i<names.length;i++) {
            final int index=i;
            JToggleButton b=new JellyTheme.NavButton(names[i]); navigation[i]=b; b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setFocusPainted(false); b.setFont(b.getFont().deriveFont(Font.BOLD,14f));
            b.setMaximumSize(new Dimension(210,52)); b.setBorder(BorderFactory.createEmptyBorder(15,14,15,8));
            b.setBackground(new Color(236,229,251)); b.setForeground(JellyTheme.INK);
            b.addItemListener(e -> { b.setBackground(b.isSelected()?new Color(207,190,242):new Color(243,240,250)); });
            b.addActionListener(e -> cards.show(pages,""+index)); group.add(b); top.add(b); top.add(Box.createVerticalStrut(10));
            if(i==0)b.setSelected(true);
        }
        sidebar.add(top,BorderLayout.NORTH);
        JPanel bottom=JellyTheme.column(); bottom.add(new JellyTheme.Jelly()); bottom.add(Box.createVerticalStrut(10));
        bottom.add(JellyTheme.label("JAVA / КУРСОВАЯ РАБОТА",11,true)); sidebar.add(bottom,BorderLayout.SOUTH);
        pages.setOpaque(false); pages.add(hero,"0"); pages.add(reflection,"1"); pages.add(translator,"2"); pages.add(streams,"3");
        add(sidebar,BorderLayout.WEST); add(pages,BorderLayout.CENTER);
    }
}
