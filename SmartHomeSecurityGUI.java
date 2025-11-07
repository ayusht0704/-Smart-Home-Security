import java.awt.*;
import javax.swing.*;

public class SmartHomeSecurityGUI extends JFrame {

    private final JCheckBox nightCheck;
    private final JCheckBox motionCheck;
    private final JCheckBox doorCheck;

    private final JLabel statusLabel;
    private final JLabel logicLabel;
    private final JTextArea evalArea;

    public SmartHomeSecurityGUI() {
        super("Smart Home Security System");

        // Try to use native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Top title banner
        JLabel title = new JLabel("Smart Home Security System", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Checkboxes panel (inputs)
        nightCheck  = new JCheckBox("It is Night (p)");
        motionCheck = new JCheckBox("Motion Detected (q)");
        doorCheck   = new JCheckBox("Door Open (r)");

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 6, 6));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Inputs (Propositions)"));
        inputPanel.add(nightCheck);
        inputPanel.add(motionCheck);
        inputPanel.add(doorCheck);

        // Logic expression label
        logicLabel = new JLabel("Alarm = p ∧ (q ∨ r)   →   Alarm = Night AND (Motion OR DoorOpen)");
        logicLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Evaluation text area (read-only)
        evalArea = new JTextArea(4, 30);
        evalArea.setEditable(false);
        evalArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        evalArea.setBorder(BorderFactory.createTitledBorder("Logical Evaluation"));

        // Status label (big, color-coded)
        statusLabel = new JLabel("System Safe. No intrusion detected.", SwingConstants.CENTER);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 16f));
        statusLabel.setOpaque(true);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        statusLabel.setPreferredSize(new Dimension(10, 44)); // ensure visible height

        // Center area
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(inputPanel, BorderLayout.WEST);
        center.add(new JScrollPane(
                evalArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        ), BorderLayout.CENTER);

        // Footer: stack status banner (top) + logic label (bottom)
        JPanel footer = new JPanel(new BorderLayout(6, 6));
        footer.add(statusLabel, BorderLayout.NORTH);
        footer.add(logicLabel, BorderLayout.SOUTH);

        // Root layout
        setLayout(new BorderLayout(10, 10));
        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH); // <- single SOUTH add (fix)

        // Listeners: live update when any checkbox changes
        nightCheck.addItemListener(e -> updateAlarm());
        motionCheck.addItemListener(e -> updateAlarm());
        doorCheck.addItemListener(e -> updateAlarm());

        // Initial compute
        updateAlarm();

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateAlarm() {
        boolean p = nightCheck.isSelected();
        boolean q = motionCheck.isSelected();
        boolean r = doorCheck.isSelected();

        boolean alarm = p && (q || r);

        if (alarm) {
            statusLabel.setText("ALARM TRIGGERED! Intruder Alert!");
            statusLabel.setBackground(new Color(200, 0, 0));
        } else {
            statusLabel.setText("System Safe. No intrusion detected.");
            statusLabel.setBackground(new Color(0, 140, 0));
        }

        // Update logical evaluation area
        StringBuilder sb = new StringBuilder();
        sb.append("p (Night)  : ").append(p).append('\n');
        sb.append("q (Motion) : ").append(q).append('\n');
        sb.append("r (Door)   : ").append(r).append('\n');
        sb.append("-----------------------------\n");
        sb.append("q ∨ r      = ").append(q || r).append('\n');
        sb.append("p ∧ (q ∨ r)= ").append(alarm).append("  ← Alarm");
        evalArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartHomeSecurityGUI::new);
    }
}
