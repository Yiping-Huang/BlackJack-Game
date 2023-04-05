package ui.gamephases.information;

// This class is a game label which is used to display messages to the user as instructions

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MessageAreaTextJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private int counter = 0;
    private final Timer timer = new Timer(10, this);

    // EFFECTS:  construct a message area label
    public MessageAreaTextJLabel(int messageIndex) {
        setText("<html><body>" + processIndex(messageIndex) + "</body></html>");
        setFont(new Font("Times New Roman", Font.BOLD, 20));
        setBounds(163,385,250,220);
        setLocation(163, 385);
        setVisible(false);
        setForeground(Color.BLACK);
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    // REQUIRES: messageIndex is among [0, 13]
    // EFFECTS:  display corresponding message according to the given messageIndex
    @SuppressWarnings("methodlength")
    public String processIndex(int messageIndex) {
        String message = "";
        if (messageIndex == 0) {
            message = "Betting Stage";
        } else if (messageIndex == 1) {
            message = "Dealing Stage";
        } else if (messageIndex == 2) {
            message = "You Bust!" + "<br>You lose all your bets.";
        } else if (messageIndex == 3) {
            message = "Push!" + "<br>You get bets back.";
        } else if (messageIndex == 4) {
            message = "Dealer wins!" + "<br>You lose all bets!";
        } else if (messageIndex == 5) {
            message = "Black Jack!" + "<br>You earn double bets!";
            playCheeringSound();
        } else if (messageIndex == 6) {
            message = "Dealer Busts!" + "<br>You earn the bets!";
            playCheeringSound();
        } else if (messageIndex == 7) {
            message = "You win!" + "<br>You earn the bets!";
            playCheeringSound();
        } else if (messageIndex == 8) {
            message = "Game over!" + "<br>You lose all assets.";
        } else if (messageIndex == 9) {
            message = "5-card Charlie!"
                    + "<br>You may withdraw bonus"
                    + "<br>or hit for a bigger bonus";
            playCheeringSound();
        } else if (messageIndex == 10) {
            message = "5-card Charlie!"
                    + "<br>You win big bonus!";
            playCheeringSound();
        } else if (messageIndex == 11) {
            message = "Do you want to"
                    + "<br>make an Insurance?";
        } else if (messageIndex == 12) {
            message = "The Dealer is Black Jack!"
                    + "<br>You lose all bets!"
                    + "<br>But you may earn bonus"
                    + "<br>if you took an insurance.";
        } else if (messageIndex == 13) {
            message = "The Dealer is not"
                    + "<br>Black Jack."
                    + "<br>You lose your insurance.";
        }
        return message;
    }

    public void setStatus(String s) {
        status = s;
    }

    private void playCheeringSound() {
        try {
            File soundFile = new File("./sound/Cheering.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            repaint();
        } else if (counter >= 0 && 40 > counter && status.equals("Delay Showing")) {
            counter += 1;
        } else if (counter == 40 && status.equals("Delay Showing")) {
            counter = 0;
            status = "Showing";
        } else if (status.equals("Raise")) {
            setVisible(true);
            repaint();
        }
    }
}
