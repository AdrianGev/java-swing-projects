import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class calendarApp {
    private JFrame frame;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private YearMonth currentYearMonth;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calendarApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Calendar App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        calendarPanel = new JPanel(new BorderLayout());
        frame.add(calendarPanel);

        currentYearMonth = YearMonth.now();
        monthYearLabel = new JLabel();
        monthYearLabel.setHorizontalAlignment(JLabel.CENTER);
        calendarPanel.add(monthYearLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        calendarPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Use ActionListener to handle button clicks
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMonth(-1);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMonth(1);
            }
        });

        updateCalendar();
        frame.setVisible(true);
    }

    private void changeMonth(int offset) {
        currentYearMonth = currentYearMonth.plusMonths(offset);
        updateCalendar();
    }

    private void updateCalendar() {
        // Remove all components from the center of the calendarPanel
        Component[] components = calendarPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof GridLayout) {
                calendarPanel.remove(comp);
                break;
            }
        }

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        LocalDate lastOfMonth = currentYearMonth.atEndOfMonth();
        
        JPanel daysPanel = new JPanel(new GridLayout(0, 7));
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) {
            daysPanel.add(new JLabel(dayName, JLabel.CENTER));
        }

        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < startDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= lastOfMonth.getDayOfMonth(); day++) {
            daysPanel.add(new JLabel(String.valueOf(day), JLabel.CENTER));
        }

        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthYearLabel.setText(currentYearMonth.format(formatter));
        frame.revalidate();
        frame.repaint();
    }
}
