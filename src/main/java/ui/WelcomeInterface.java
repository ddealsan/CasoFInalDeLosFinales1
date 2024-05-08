package ui;

import javax.swing.*;
import java.awt.*;

public class WelcomeInterface extends JFrame {
    private JButton startButton;
    private JButton exitButton;

    public WelcomeInterface() {
        // Set up the frame
        setTitle("Aplicación para gestionar cultivos de bacterias");
        setSize(1000, 600); // Increase the width
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the layout
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(500); // Adjust the divider location
        splitPane.setEnabled(false);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Add welcome message and logo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.WHITE); // Set the background color to white
        JLabel welcomeLabel = new JLabel("<html>Bienvenido a la aplicación<br>para gestionar cultivos de bacterias</html>", SwingConstants.CENTER); // Add a line break
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Ensure the image path is correct
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/FotoLogo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(logoLabel, BorderLayout.CENTER);
        splitPane.setLeftComponent(leftPanel);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1)); // Change the grid layout to 2 rows
        startButton = createButton("Iniciar");
        exitButton = createButton("Salir");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        splitPane.setRightComponent(buttonPanel);

        // Make the frame visible
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font size to 24

        // Add an action listener to the "Iniciar" button
        if (text.equals("Iniciar")) {
            button.addActionListener(e -> {
                new ExperimentInterface();
                this.dispose(); // This will close the WelcomeInterface window
            });
        }

        return button;
    }
}