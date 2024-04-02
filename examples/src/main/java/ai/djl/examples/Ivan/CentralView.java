package ai.djl.examples.Ivan;


/**
 * class that manages the interface along with its GUI components
 * This is the view layer of the application
 * Author: Ivan Segade Carou
 */

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;



import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


@SuppressWarnings("serial")
        public class CentralView extends JFrame implements ActionListener, FocusListener, KeyListener {
    // declare the controller and the GUI components
    CentralController controller;
    JFrame centralFrame;

    JPanel centerPanel;
    JPanel buttonsPanel = new JPanel(new FlowLayout());


    JButton speakButton;
    JButton descriptionButton;
    JButton overlappingButton;
    JButton depthOverlapButton;
    JButton depthCentralPointButton;
    JButton depthBottomButton;
    JTextField textToSpeechField;

    KeyListener keyListener;

    /**
     * constructor of the class that receives the controller as a parameter to access its methods
     *
     * @param controller
     */

    CentralView(CentralController controller) {
        // the local controller is declared with the parameter
        this.controller = controller;

        // set the frame configuration
        setFrame();

        // add the listeners to the GUI components
        addListeners();

// add the GUI components to the frame
        addFrame();

    }// end constructor

    /**
     * method that is called to display the interface
     */
    public void display() {
        // with the true value the frame and window can be displayed
        centerPanel.setVisible(true);
        centralFrame.setVisible(true);
    }

    /**
     *The following methods are declared as private to avoid external access
     */


    /**
     * set the configuration of the frame
     */

    private void setFrame() {
// create the frame along with the title
        centralFrame = new JFrame("Central application");

        // set the size of the window
        centralFrame.setSize(700, 500);

        // close the window with the X button of the window
        centralFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // type of frame
        centralFrame.setLayout(new BorderLayout(2, 2));

        // the frame can get the focus
        //centralFrame.setFocusable(true);

// declare the panel and add the button and the textbox
        centerPanel = new JPanel(new GridLayout(1, 1));
        textToSpeechField = new JTextField(30);
        speakButton = new JButton("Load");
        descriptionButton = new JButton("Description");
        overlappingButton = new JButton("Overlapping");
        depthOverlapButton = new JButton("Depth by overlapping");
        depthCentralPointButton = new JButton("Depth by Central Point");
        depthBottomButton = new JButton("Depth by bottom");

    } // end set frame


    /**
     * method that add the GUI components to the frame
     */

    private void addFrame() {

        centerPanel.add(textToSpeechField);
        centerPanel.add(speakButton);

        buttonsPanel.add(descriptionButton);
        buttonsPanel.add(overlappingButton);
        buttonsPanel.add(depthOverlapButton);
        buttonsPanel.add(depthCentralPointButton);
        buttonsPanel.add(depthBottomButton);

        centralFrame.add(centerPanel, BorderLayout.CENTER);
centralFrame.add(buttonsPanel, BorderLayout.SOUTH);
buttonsPanel.setVisible(false);
speakButton.requestFocus();
    } // end add frame

    /**
     * method that adds the action, focus and key listeners to the GUI components
     */

    private void addListeners() {
        speakButton.addFocusListener(this);
        speakButton.addActionListener(this);
        descriptionButton.addActionListener(this);
        overlappingButton.addActionListener(this);
        depthOverlapButton.addActionListener(this);
        depthCentralPointButton.addActionListener(this);
        depthBottomButton.addActionListener(this);

        textToSpeechField.addFocusListener(this);

        addFrameListener();
        textToSpeechField.addKeyListener(this);
        speakButton.addKeyListener(keyListener);
    } // end add listeners

    /**
     * method that adds the listners to the frame
     */

    private void addFrameListener() {
        keyListener = new KeyListener() {
            // declare a key listener and allows to override the required methods
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

// get the code of the key pressed
                int keyCode = e.getKeyCode();

                // with the code, I get the text associated to the corresponding key
                String keyPressed = KeyEvent.getKeyText(keyCode);

                // call to the speak method in the controller to announce the text
                controller.speak(keyPressed);

            } // end key press

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        // add the declared key listener to the frame
        centralFrame.addKeyListener(keyListener);
    } // end add frame listener


    /**
     * method that declares the action listener
     * when the user presses the button
     *
     * @param e the event to be processed
     */

    public void actionPerformed(ActionEvent e) {
        // if the user pressed the button
        if (e.getSource() == speakButton) {
            try {
                controller.loadImage(textToSpeechField.getText());
            } catch (IOException ioe) {
                System.out.println(ioe);
            } catch (ModelException me) {
                System.out.println(me);
            } catch (TranslateException te) {
                System.out.println(te);
            }
        } // end if speak button
        else if (e.getSource() == descriptionButton) {
controller.displayMessage(Choice.DESCRIPTION);

        } // end if
        else if (e.getSource() == overlappingButton) {
controller.displayMessage(Choice.OVERLAPPING);
        } // end if
        else if (e.getSource() == depthOverlapButton) {
controller.displayMessage(Choice.DEPTHOVERLAP);
        } // end if
        else if(e.getSource() == depthCentralPointButton) {
            controller.displayMessage(Choice.DEPTHCENTRALPOINT);
        } // end if
              else  if (e.getSource() == depthBottomButton){
                  controller.displayMessage(Choice.DEPTHBOTTOM);
        } // end if which button pressed
    }// end action perform

    /**
     * method that declares the action when the GUI component has the focus
     *
     * @param e the event to be processed
     */

    public void focusGained(FocusEvent e) {
        String textFocus = "";

        // if the button has the focus
        if (e.getSource() == speakButton)
            //declare the text to speech
            textFocus = "Speak, Button";

            // if the textbox has the focus
        else if (e.getSource() == textToSpeechField)
            //declare the text to speech
            textFocus = "Enter the text to speech, Text edit";


        // call to the speak method in the controller to announce the text
        controller.speak(textFocus);

    } // end focus gained


    /**
     * method to be overide
     * they must be declared for inheriting the focus class
     * in this project they are not needed
     *
     * @param e the event to be processed
     */

    public void focusLost(FocusEvent e) {
    } // end focus lost


    /**
     * methods from the key listener
     * only the press method is required in this project
     *
     * @param e the event to be processed
     */

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
// get the code of the key pressed
        int keyCode = e.getKeyCode();

        // with the code, I get the text associated to the corresponding key
        String keyPressed = KeyEvent.getKeyText(keyCode);

        // call to the speak method in the controller to announce the text
        controller.speak(keyPressed);

    } // end key pressed


} //end of class


