import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverterApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel usdLabel = new JLabel("Amount in USD:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usdLabel, gbc);

        JTextField amountText = new JTextField(20);
        gbc.gridx = 1;
        panel.add(amountText, gbc);

        JButton convertToINRButton = new JButton("Convert to INR");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(convertToINRButton, gbc);

        JLabel inrLabel = new JLabel("Converted Amount in INR:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(inrLabel, gbc);

        JLabel outputLabel = new JLabel("");
        gbc.gridx = 1;
        panel.add(outputLabel, gbc);

        JLabel inrLabel2 = new JLabel("Amount in INR:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(inrLabel2, gbc);

        JTextField inrText = new JTextField(20);
        gbc.gridx = 1;
        panel.add(inrText, gbc);

        JButton convertToUSDButton = new JButton("Convert to USD");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(convertToUSDButton, gbc);

        JLabel usdResultLabel = new JLabel("Converted Amount in USD:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(usdResultLabel, gbc);

        JLabel usdOutputLabel = new JLabel("");
        gbc.gridx = 1;
        panel.add(usdOutputLabel, gbc);

        convertToINRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = amountText.getText();
                try {
                    double amount = Double.parseDouble(amountStr);
                    double convertedAmount = convertCurrency(amount);
                    outputLabel.setText(String.format("₹ %.2f", convertedAmount));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid number.");
                }
            }
        });

        convertToUSDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inrAmountStr = inrText.getText();
                try {
                    double inrAmount = Double.parseDouble(inrAmountStr);
                    double convertedUSD = convertToUSD(inrAmount);
                    usdOutputLabel.setText(String.format("$ %.2f", convertedUSD));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid number.");
                }
            }
        });
    }

    private static double convertCurrency(double amount) {
        double conversionRate = 82.75; 
        return amount * conversionRate;
    }

    private static double convertToUSD(double amount) {
        double conversionRate = 1 / 82.75; 
        return amount * conversionRate;
    }
}
