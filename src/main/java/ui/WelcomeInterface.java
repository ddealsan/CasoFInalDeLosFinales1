package ui;

import javax.swing.*;
import java.awt.*;

public class WelcomeInterface extends JFrame {
    private JButton openExperimentButton;
    private JButton newExperimentButton;
    private JButton createBacteriaPopulationButton;
    private JButton viewBacteriaPopulationsButton;
    private JButton deleteBacteriaPopulationButton;
    private JButton viewBacteriaPopulationDetailsButton;
    private JButton exitButton;

    public WelcomeInterface() {
        // Set up the frame
        setTitle("Aplicación para gestionar cultivos de bacterias");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the layout
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(400);
        splitPane.setEnabled(false);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Add welcome message and logo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Bienvenido a la aplicación para gestionar cultivos de bacterias", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ImageIcon logoIcon = new ImageIcon("resources/FotoLogo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(logoLabel, BorderLayout.CENTER);
        splitPane.setLeftComponent(leftPanel);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));
        openExperimentButton = createButton("Abrir un archivo que contenga un experimento");
        newExperimentButton = createButton("Crear un nuevo experimento");
        createBacteriaPopulationButton = createButton("Crear una población de bacterias y añadirla al experimento actual");
        viewBacteriaPopulationsButton = createButton("Visualizar los nombres de todas las poblaciones de bacterias del experimento actual");
        deleteBacteriaPopulationButton = createButton("Borrar una población de bacterias del experimento actual");
        viewBacteriaPopulationDetailsButton = createButton("Ver información detallada de una población de bacterias del experimento actual");
        exitButton = createButton("Salir");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(openExperimentButton);
        buttonPanel.add(newExperimentButton);
        buttonPanel.add(createBacteriaPopulationButton);
        buttonPanel.add(viewBacteriaPopulationsButton);
        buttonPanel.add(deleteBacteriaPopulationButton);
        buttonPanel.add(viewBacteriaPopulationDetailsButton);
        buttonPanel.add(exitButton);
        splitPane.setRightComponent(buttonPanel);

        // Make the frame visible
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        return button;
    }
}