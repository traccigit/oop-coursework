import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.*;

// Общие компоненты интерфейса, нарисованные средствами Java2D.
public final class JellyTheme {
    public static final Color INK = new Color(48, 43, 76);
    public static final Color MUTED = new Color(110, 104, 137);
    public static final Color PURPLE = new Color(120, 88, 216);
    public static final Color MINT = new Color(180, 235, 220);
    public static void install() {
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        for (Object key : UIManager.getDefaults().keySet().toArray()) {
            if (key.toString().endsWith(".font")) UIManager.put(key, font);
        }
        UIManager.put("TextArea.foreground", INK);
        UIManager.put("TextField.foreground", INK);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", INK);
        UIManager.put("ScrollBar.width", 10);
    }
    public static Graphics2D smooth(Graphics g) {
        Graphics2D a = (Graphics2D)g.create();
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        a.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return a;
    }
    public static JPanel column() {
        JPanel p = new JPanel(); p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); return p;
    }
    public static JPanel row(Component... cs) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); p.setOpaque(false);
        for(Component c:cs) p.add(c); return p;
    }
    public static JLabel label(String text, int size, boolean bold) {
        JLabel l = new JLabel(text); l.setForeground(INK);
        l.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size)); return l;
    }
    public static JTextField field(String value, int cols) {
        JTextField f = new JTextField(value, cols);
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(221,213,240)), new EmptyBorder(10,12,10,12)));
        return f;
    }
    public static JTextArea area(boolean editable, int rows) {
        JTextArea a = new JTextArea(rows, 25); a.setEditable(editable);
        a.setLineWrap(true); a.setWrapStyleWord(true);
        a.setBackground(editable ? Color.WHITE : new Color(246,244,252));
        a.setBorder(new EmptyBorder(14,16,14,16)); return a;
    }
    public static JScrollPane scroll(Component c) {
        JScrollPane s=new JScrollPane(c); s.setBorder(BorderFactory.createLineBorder(new Color(227,220,243)));
        s.getVerticalScrollBar().setUnitIncrement(16); return s;
    }
    public static class Card extends JPanel {
        public Card() { setOpaque(false); setBorder(new EmptyBorder(22,24,22,24)); }
        protected void paintComponent(Graphics g) {
            Graphics2D a=smooth(g);
            a.setColor(new Color(94,66,140,12)); a.fillRoundRect(2,5,getWidth()-4,getHeight()-6,30,30);
            a.setColor(new Color(255,255,255,220)); a.fillRoundRect(0,0,getWidth()-2,getHeight()-6,30,30);
            a.setColor(new Color(255,255,255,245)); a.drawRoundRect(1,1,getWidth()-4,getHeight()-8,30,30);
            a.dispose(); super.paintComponent(g);
        }
    }
    public static class Backdrop extends JPanel {
        public Backdrop() { super(new BorderLayout(24,0)); setBorder(new EmptyBorder(26,26,26,26)); }
        protected void paintComponent(Graphics g) {
            Graphics2D a=smooth(g);
            a.setPaint(new GradientPaint(0,0,new Color(234,226,251),getWidth(),getHeight(),new Color(221,243,239)));
            a.fillRect(0,0,getWidth(),getHeight());
            a.setColor(new Color(255,197,219,75)); a.fillOval(getWidth()-440,-170,620,520);
            a.setColor(new Color(189,174,244,65)); a.fillOval(-180,getHeight()-330,550,500);
            a.dispose();
        }
    }
    public static class Button extends JButton {
        private final boolean primary;
        public Button(String text, boolean primary) {
            super(text); this.primary=primary; setOpaque(false); setContentAreaFilled(false);
            setBorder(new EmptyBorder(13,20,13,20)); setFocusPainted(false);
            setForeground(primary?Color.WHITE:INK); setFont(getFont().deriveFont(Font.BOLD));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            getModel().addChangeListener(e -> repaint());
        }
        protected void paintComponent(Graphics g) {
            Graphics2D a=smooth(g); int y=getModel().isPressed()?3:0;
            Color top=primary?new Color(170,141,239):new Color(248,245,255);
            Color bottom=primary?new Color(116,82,204):new Color(222,211,246);
            if(getModel().isRollover()) top=top.brighter();
            a.setColor(new Color(99,65,157,35)); a.fillRoundRect(1,y+4,getWidth()-2,getHeight()-5,24,24);
            a.setPaint(new GradientPaint(0,y,top,0,getHeight(),bottom));
            a.fillRoundRect(1,y,getWidth()-2,getHeight()-5,24,24);
            a.setColor(new Color(255,255,255,125)); a.drawRoundRect(2,y+1,getWidth()-4,getHeight()-8,22,22);
            if(hasFocus()) { a.setColor(PURPLE); a.drawRoundRect(0,0,getWidth()-1,getHeight()-1,24,24); }
            a.dispose(); super.paintComponent(g);
        }
    }
    public static class NavButton extends JToggleButton {
        public NavButton(String text) {
            super(text); setOpaque(false); setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        protected void paintComponent(Graphics g) {
            Graphics2D a=smooth(g);
            a.setPaint(new GradientPaint(0,0,isSelected()?new Color(226,215,252):new Color(249,247,254),0,getHeight(),isSelected()?new Color(206,186,242):new Color(239,233,250)));
            a.fillRoundRect(0,0,getWidth(),getHeight()-2,22,22);
            if(isSelected()) {a.setColor(PURPLE);a.fillRoundRect(0,14,4,getHeight()-28,4,4);}
            if(hasFocus()) {a.setColor(PURPLE);a.drawRoundRect(1,1,getWidth()-3,getHeight()-4,22,22);}
            a.dispose(); super.paintComponent(g);
        }
    }

    public static class Jelly extends JComponent {
        public Jelly() { setPreferredSize(new Dimension(172,145)); }
        protected void paintComponent(Graphics g) {
            Graphics2D a=smooth(g); double s=Math.min(getWidth()/172.0,getHeight()/145.0); a.scale(s,s);
            a.setColor(new Color(128,100,194,25)); a.fillOval(24,119,130,18);
            Path2D p=new Path2D.Double(); p.moveTo(20,101); p.curveTo(16,50,43,12,84,12);
            p.curveTo(124,12,149,44,151,94); p.curveTo(158,141,19,141,20,101);
            a.setPaint(new GradientPaint(25,20,new Color(211,188,255),120,137,new Color(145,112,225)));
            a.fill(p); a.setColor(new Color(255,255,255,170)); a.setStroke(new BasicStroke(2)); a.draw(p);
            a.setColor(new Color(255,255,255,125)); a.fillOval(39,27,29,16);
            a.setColor(INK); a.fillOval(59,76,7,10); a.fillOval(108,76,7,10); a.drawArc(78,81,17,12,190,160);
            a.setColor(new Color(245,172,212,170)); a.fillOval(45,90,20,9); a.fillOval(110,90,20,9); a.dispose();
        }
    }
}
