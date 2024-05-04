package ui;

import CrearNuevoExp.NewExperimentInterface;
import AbrirArchivoConEXP.ProjectSelectionInterface;
import javax.swing.*;
import java.awt.*;

public class WelcomeInterface extends JFrame {
    private JButton openExperimentButton;
    private JButton newExperimentButton;
    private JButton createBacteriaPopulationButton;
    private JButton viewBacteriaPopulationsButton;
    private JButton deleteBacteriaPopulationButton;
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
        buttonPanel.setLayout(new GridLayout(6, 1)); // Change the grid layout to 6 rows instead of 7
        openExperimentButton = createButton("Abrir un archivo que contenga un experimento");
        newExperimentButton = createButton("Crear un nuevo experimento");
        createBacteriaPopulationButton = createButton("Crear una población de bacterias y añadirla al experimento actual");
        viewBacteriaPopulationsButton = createButton("Visualizar los nombres de todas las poblaciones de bacterias del experimento actual");
        deleteBacteriaPopulationButton = createButton("Borrar una población de bacterias del experimento actual");
        exitButton = createButton("Salir");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(openExperimentButton);
        buttonPanel.add(newExperimentButton);
        buttonPanel.add(createBacteriaPopulationButton);
        buttonPanel.add(viewBacteriaPopulationsButton);
        buttonPanel.add(deleteBacteriaPopulationButton);
        // Do not add the viewBacteriaPopulationDetailsButton
        buttonPanel.add(exitButton);
        splitPane.setRightComponent(buttonPanel);

        // Make the frame visible
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);

        // Add an action listener to the "Crear un nuevo experimento" button
        if (text.equals("Crear un nuevo experimento")) {
            button.addActionListener(e -> new NewExperimentInterface());
        } else if (text.equals("Abrir un archivo que contenga un experimento")) {
            button.addActionListener(e -> new ProjectSelectionInterface());
        }

        return button;
    }
}