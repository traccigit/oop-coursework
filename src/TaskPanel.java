import javax.swing.*;
import java.awt.*;

public abstract class TaskPanel extends JPanel {
    protected final JTextArea output=JellyTheme.area(false,7);
    protected final JPanel body=JellyTheme.column();
    protected TaskPanel(String title,String subtitle) {
        super(new BorderLayout(0,18)); setOpaque(false);
        JPanel heading=JellyTheme.column();
        heading.add(JellyTheme.label(title,30,true));
        heading.add(Box.createVerticalStrut(8)); JLabel sub=JellyTheme.label(subtitle,13,false); sub.setForeground(JellyTheme.MUTED); heading.add(sub);
        add(heading,BorderLayout.NORTH);
        JellyTheme.Card card=new JellyTheme.Card(); card.setLayout(new BorderLayout()); card.add(body);
        JScrollPane center=JellyTheme.scroll(card); center.setBorder(null); center.setOpaque(false); center.getViewport().setOpaque(false);
        add(center,BorderLayout.CENTER);
        JellyTheme.Card result=new JellyTheme.Card(); result.setLayout(new BorderLayout(0,10));
        result.add(JellyTheme.label("РЕЗУЛЬТАТ",11,true),BorderLayout.NORTH); result.add(JellyTheme.scroll(output));
        result.setPreferredSize(new Dimension(0,220)); add(result,BorderLayout.SOUTH);
        output.setText("Здесь появится результат выполнения.");
    }
    protected void addBlock(Component c) { if(c instanceof JComponent) ((JComponent)c).setAlignmentX(Component.LEFT_ALIGNMENT); body.add(c); body.add(Box.createVerticalStrut(12)); }
    protected void info(String value) { output.setText(value); output.setCaretPosition(0); }
    protected void error(Exception ex) { info("Ошибка: "+(ex.getMessage()==null?ex.getClass().getSimpleName():ex.getMessage())); }
    protected JellyTheme.Button button(String title, boolean primary, Runnable action) {
        JellyTheme.Button b=new JellyTheme.Button(title,primary); b.addActionListener(e -> { try {action.run();} catch(Exception ex) {error(ex);} }); return b;
    }
}
