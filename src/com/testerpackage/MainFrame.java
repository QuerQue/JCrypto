package com.testerpackage;

import com.cipherlibrary.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * MainFrame of Crypto Tester program showing abilities of Cipher Library.
 * User can choose cipher and cipher mode provided from the library to encrypt or decrypt given String.
 * @see CryptoTester
 * @see com.cipherlibrary
 */
public class MainFrame extends JFrame {
    private JButton encryptButton;
    private JButton decryptButton;
    private JComboBox cipherComboBox;
    private JTextArea decryptedTextArea;
    private JTextArea encryptedTextArea;
    private JTextField keyTextField;
    private JPanel rootPanel;
    private JComboBox cipherModeCombo;
    private JLabel keyLabel;
    private JPanel vigenerePanel;
    private JPanel affinePanel;
    private JLabel aLabel;
    private JTextField aTextField;
    private JLabel bLabel;
    private JTextField bTextField;
    private JPanel railFencePanel;
    private JLabel fenceLabel;
    private JTextField fenceTextField;
    private JFormattedTextField aFormattedTextField;
    private JFormattedTextField bFormattedTextField;
    private JFormattedTextField fenceFormattedTextField;
    private Encryptable activeEncryptor;
    private CipherMode activeMode;

    /**
     * MainFrame constructor initializing all of the frames components.
     * @param title Title of the program.
     * @throws HeadlessException method may throw HeadlessException
     */
    public MainFrame(String title) throws HeadlessException {
        super(title);
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);
        setContentPane(rootPanel);
        pack();
        activeMode = CipherMode.LETTERS_ONLY;
        activeEncryptor = new AffineEncryptor(5, 8, activeMode);
        requestFocusInWindow();
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                encryptButton.setEnabled(isEncryptionPrepared());
                decryptButton.setEnabled(isDecryptionPrepared());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                encryptButton.setEnabled(isEncryptionPrepared());
                decryptButton.setEnabled(isDecryptionPrepared());
            }
        };

        cipherComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectionChanged();
            }
        });
        cipherModeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedModeChanged();
            }
        });

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEncryptor();
                encryptedTextArea.setText(activeEncryptor.encrypt(decryptedTextArea.getText()));
                decryptButton.setEnabled(true);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEncryptor();
                decryptedTextArea.setText(activeEncryptor.decrypt(encryptedTextArea.getText()));
                encryptButton.setEnabled(true);
            }
        });

        aFormattedTextField.addKeyListener(keyListener);
        bFormattedTextField.addKeyListener(keyListener);
        decryptedTextArea.addKeyListener(keyListener);
        encryptedTextArea.addKeyListener(keyListener);
        fenceFormattedTextField.addKeyListener(keyListener);
        keyTextField.addKeyListener(keyListener);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void selectedModeChanged() {
        switch (cipherModeCombo.getSelectedItem().toString()) {
            case "Letters Only":
                activeMode = CipherMode.LETTERS_ONLY;
                activeEncryptor.setMode(activeMode);
                break;
            case "Punctuation Marks":
                activeMode = CipherMode.PUNCTUATION_MARKS;
                activeEncryptor.setMode(activeMode);
                break;
            case "Spaces":
                activeMode = CipherMode.SPACES;
                activeEncryptor.setMode(activeMode);
                break;
        }
    }

    private void selectionChanged() {
        vigenerePanel.setVisible(false);
        affinePanel.setVisible(false);
        railFencePanel.setVisible(false);

        if (cipherComboBox.getSelectedItem().toString() == "Vigenere") {
            vigenerePanel.setVisible(true);
        } else if (cipherComboBox.getSelectedItem().toString() == "Affine") {
            affinePanel.setVisible(true);
        } else if (cipherComboBox.getSelectedItem().toString() == "Rail Fence") {
            railFencePanel.setVisible(true);
        }
        encryptButton.setEnabled(isEncryptionPrepared());
        decryptButton.setEnabled(isDecryptionPrepared());
    }

    private void updateEncryptor() {
        switch (cipherComboBox.getSelectedItem().toString()) {
            case "Vigenere":
                activeEncryptor = new VigenereEncrytpor(keyTextField.getText(), activeMode);
                return;
            case "Caesar":
                activeEncryptor = new CaesarEncryptor(activeMode);
                return;
            case "Rot 13":
                activeEncryptor = new Rot13Encryptor(activeMode);
                return;
            case "Rot 47":
                activeEncryptor = new Rot47Encryptor(activeMode);
                return;
            case "Affine":
                activeEncryptor = new AffineEncryptor(Integer.valueOf(aFormattedTextField.getText()),
                        Integer.valueOf(bFormattedTextField.getText()), activeMode
                );
                return;
            case "Rail Fence":
                activeEncryptor = new RailFenceEncryptor(Integer.valueOf(fenceFormattedTextField.getText()),
                        activeMode);
                return;
            case "At Bash":
                activeEncryptor = new AtBashEncryptor(activeMode);
                return;
        }
    }

    private boolean areAllFieldsFilled() {
        boolean isOk = true;
        if (cipherComboBox.getSelectedItem().toString() == "Affine") {
            isOk = !(aFormattedTextField.getText().isEmpty() || bFormattedTextField.getText().isEmpty())
                    && isNumeric(aFormattedTextField.getText());
        } else if (cipherComboBox.getSelectedItem().toString() == "Rail Fence") {
            isOk = !(fenceFormattedTextField.getText().isEmpty()) && isNumeric(fenceFormattedTextField.getText());
        } else if (cipherComboBox.getSelectedItem().toString() == "Vigenere") {
            isOk = !(keyTextField.getText().isEmpty()) && keyTextField.getText().matches("^[a-zA-Z]+$");;
        }
        return isOk;
    }

    private boolean isEncryptionPrepared() {
        String input = decryptedTextArea.getText();
        boolean isOk = areAllFieldsFilled();
        return isOk && !(input.isEmpty());
    }

    private boolean isDecryptionPrepared() {
        String input = encryptedTextArea.getText();
        boolean isOk = areAllFieldsFilled();
        return isOk && !(input.isEmpty());
    }

    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        encryptButton = new JButton();
        encryptButton.setEnabled(false);
        encryptButton.setText("Encrypt");
        panel1.add(encryptButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setVisible(true);
        panel1.add(panel2, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        cipherComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Affine");
        defaultComboBoxModel1.addElement("At Bash");
        defaultComboBoxModel1.addElement("Caesar");
        defaultComboBoxModel1.addElement("Rail Fence");
        defaultComboBoxModel1.addElement("Rot 13");
        defaultComboBoxModel1.addElement("Rot 47");
        defaultComboBoxModel1.addElement("Vigenere");
        cipherComboBox.setModel(defaultComboBoxModel1);
        panel2.add(cipherComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Cipher:");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        vigenerePanel = new JPanel();
        vigenerePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        vigenerePanel.setVisible(false);
        panel2.add(vigenerePanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        keyLabel = new JLabel();
        keyLabel.setEnabled(true);
        keyLabel.setText("Key:");
        vigenerePanel.add(keyLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyTextField = new JTextField();
        keyTextField.setEnabled(true);
        vigenerePanel.add(keyTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        affinePanel = new JPanel();
        affinePanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        affinePanel.setVisible(true);
        panel2.add(affinePanel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        aLabel = new JLabel();
        aLabel.setText("a:");
        affinePanel.add(aLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bLabel = new JLabel();
        bLabel.setText("b:");
        affinePanel.add(bLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aFormattedTextField = new JFormattedTextField();
        aFormattedTextField.setColumns(15);
        aFormattedTextField.setVerifyInputWhenFocusTarget(true);
        affinePanel.add(aFormattedTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        bFormattedTextField = new JFormattedTextField();
        affinePanel.add(bFormattedTextField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        railFencePanel = new JPanel();
        railFencePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        railFencePanel.setVisible(false);
        panel2.add(railFencePanel, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        fenceLabel = new JLabel();
        fenceLabel.setText("Fence:");
        railFencePanel.add(fenceLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fenceFormattedTextField = new JFormattedTextField();
        railFencePanel.add(fenceFormattedTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        decryptedTextArea = new JTextArea();
        decryptedTextArea.setText("");
        panel3.add(decryptedTextArea, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Decrypted:");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Encrypted:");
        panel3.add(label3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        encryptedTextArea = new JTextArea();
        encryptedTextArea.setEditable(true);
        panel3.add(encryptedTextArea, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        cipherModeCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Letters Only");
        defaultComboBoxModel2.addElement("Punctuation Marks");
        defaultComboBoxModel2.addElement("Spaces");
        cipherModeCombo.setModel(defaultComboBoxModel2);
        panel1.add(cipherModeCombo, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Cipher mode:");
        panel1.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        decryptButton = new JButton();
        decryptButton.setEnabled(false);
        decryptButton.setText("Decrypt");
        panel1.add(decryptButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
