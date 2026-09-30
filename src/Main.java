import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JellyTheme.install();
            JFrame frame=new JFrame("Курсовая работа. Михайлова Ольга, гр 40322");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new JellyApp());
            frame.setSize(1180,850); frame.setMinimumSize(new Dimension(980,740));
            frame.setLocationRelativeTo(null); frame.setVisible(true);
        });
    }
}
