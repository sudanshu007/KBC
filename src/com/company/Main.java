package com.company;

import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        new Game();
    }
}
class Game  implements ActionListener {

    //Declaration of all components
    MP3Player mp3Player;
    File startTone=new File("C:\\Users\\Sumit\\IdeaProjects\\KBC2\\tones\\kbc_theme.mp3");
    JFrame gameWindow;
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    JLabel question;
    JButton optionA,optionB,optionC,optionD,expertAdvice,fifty50,swapQuestion,phoneAfriend,startGame;
    JProgressBar winningAmount;
    String questionByDB,optionAbyDB,optionBbyDB,optionCbyDB,optionDbyDB,correctAnswer,fifty1,fifty2;
    String name=null;
    int score=0,amount=0;
    Color Blue1 = new Color(5,123,129);
    Color Blue2 = new Color(150,203,187);
    Font optionFont = new Font("Verdana",Font.BOLD,20);
    Font lifeLineFont = new Font("PilGi",Font.BOLD,12);


    //Connecting with the Oracle Database (Table Name Questions)
    void connecting()
    {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Sumit\\IdeaProjects\\KBC2\\Database\\KBCDB.db");

            //connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "sumit123");
            statement = connection.createStatement();
            //statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("select * from QUESTIONS ");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Getting Data from the Database and storing them in respective Strings
    void getDataFromDB() throws SQLException {
        //resultSet.last();
        //System.out.println(resultSet.getRow());
        //int num = (int)(Math.random()*resultSet.getRow()+1);
        resultSet.next();
        {
            questionByDB = resultSet.getString(1);
            optionAbyDB = resultSet.getString(2);
            optionBbyDB = resultSet.getString(3);
            optionCbyDB = resultSet.getString(4);
            optionDbyDB = resultSet.getString(5);
            correctAnswer = resultSet.getString(6);
            fifty1 = resultSet.getString(6);
            fifty2 = resultSet.getString(7);
        }
    }

    //Setting Up the main Frame of the Application
    void setFrame()
    {
        gameWindow.setVisible(true);
        gameWindow.setLayout(null);
        gameWindow.getContentPane().setBackground(Blue2);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setSize(600,600);
        gameWindow.setResizable(false);
    }

    //Adding all components to the frame of the Game
    void addToGameWindow()
    {
        gameWindow.add(swapQuestion);
        gameWindow.add(question);
        gameWindow.add(optionA);
        gameWindow.add(optionB);
        gameWindow.add(optionC);
        gameWindow.add(optionD);
        gameWindow.add(expertAdvice);
        gameWindow.add(fifty50);
        gameWindow.add(winningAmount);
        gameWindow.add(phoneAfriend);
        gameWindow.add(startGame);

    }

    //(Optional) Adding Colors to the buttons and the Background
    void settingColors()
    {
        optionA.setBackground(Blue1);
        optionB.setBackground(Blue1);
        optionC.setBackground(Blue1);
        optionD.setBackground(Blue1);
        expertAdvice.setBackground(Blue1);
        fifty50.setBackground(Blue1);
        swapQuestion.setBackground(Blue1);
        phoneAfriend.setBackground(Blue1);
    }

    //(Optional) Setting Fonts of the Question,Option Buttons,Lifeline Buttons
    void settingFonts()
    {
        question.setFont(new Font("Verdana",Font.PLAIN,12));
        optionA.setFont(optionFont);
        optionB.setFont(optionFont);
        optionC.setFont(optionFont);
        optionD.setFont(optionFont);
        expertAdvice.setFont(lifeLineFont);
        fifty50.setFont(lifeLineFont);
        swapQuestion.setFont(lifeLineFont);
        phoneAfriend.setFont(lifeLineFont);
        startGame.setFont(optionFont);

    }

    //Adjusting the position of all the components on the Game Window
    void settingBounds()
    {
        winningAmount.setBounds(550,20,40,280);
        startGame.setBounds(50,250,500,100);
        question.setBounds(10,315,560,75);
        optionA.setBounds(10,390,275,75);
        optionB.setBounds(295,390,275,75);
        optionC.setBounds(10,475,275,75);
        optionD.setBounds(295,475,275,75);
        expertAdvice.setBounds(20,20,130,40);
        fifty50.setBounds(150,20,130,40);
        swapQuestion.setBounds(280,20,130,40);
        phoneAfriend.setBounds(410,20,130,40);
    }


    void starting()
    {
        score=0;
        optionA.setVisible(false);
        optionB.setVisible(false);
        optionC.setVisible(false);
        optionD.setVisible(false);
        question.setVisible(false);
        expertAdvice.setVisible(false);
        fifty50.setVisible(false);
        swapQuestion.setVisible(false);
        phoneAfriend.setVisible(false);
        winningAmount.setVisible(false);

    }

    void makeVisible()
    {
        optionA.setVisible(true);
        optionB.setVisible(true);
        optionC.setVisible(true);
        optionD.setVisible(true);
        question.setVisible(true);
        expertAdvice.setVisible(true);
        fifty50.setVisible(true);
        swapQuestion.setVisible(true);
        phoneAfriend.setVisible(true);
        winningAmount.setVisible(true);
        startGame.setVisible(false);
    }

    void setEnabled()
    {
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
    }
    void setValues()
    {
        question.setText(questionByDB);
        optionA.setText(optionAbyDB);
        optionB.setText(optionBbyDB);
        optionC.setText(optionCbyDB);
        optionD.setText(optionDbyDB);
        setEnabled();

    }

    void getName()
    {
        name=JOptionPane.showInputDialog("Enter Your name");
        if(name==null||name.equals(""))
            name="PLAYER";
    }


    public Game() throws SQLException {
        gameWindow = new JFrame("KBC");

        setFrame();
        connecting();
        getDataFromDB();
        getName();

        winningAmount = new JProgressBar(SwingConstants.VERTICAL,0,10);
        winningAmount.setStringPainted(true);
        winningAmount.setString(amount+" Lakh");
        winningAmount.setValue(amount);

        question = new JLabel(questionByDB);
        optionA = new JButton(optionAbyDB);
        optionB = new JButton(optionBbyDB);
        optionC = new JButton(optionCbyDB);
        optionD = new JButton(optionDbyDB);
        expertAdvice = new JButton("Expert Advice");
        fifty50 = new JButton("50 : 50");
        swapQuestion = new JButton("Swap Question");
        phoneAfriend = new JButton("Call a Friend");
        startGame = new JButton("Welcome "+name+" , let's Start The Game");

        addingActionListener();
        addToGameWindow();
        starting();
        settingBounds();
        settingColors();
        settingFonts();
    }


    void addingActionListener()
    {
        optionA.addActionListener(this);
        optionB.addActionListener(this);
        optionC.addActionListener(this);
        optionD.addActionListener(this);
        expertAdvice.addActionListener(this);
        fifty50.addActionListener(this);
        swapQuestion.addActionListener(this);
        phoneAfriend.addActionListener(this);
        startGame.addActionListener(this);
    }



    void check(JButton selectedBTN)
    {
        if(selectedBTN.getText().equals(correctAnswer))
        {
            if(score>=10)
            {
                JLabel scoreLabel = new JLabel("Congratulations "+name+" , you have won "+score+" Lakhs");
                starting();
                gameWindow.add(scoreLabel);
                scoreLabel.setBounds(20,250,500,100);
                scoreLabel.setFont(new Font("PiLiGi",Font.BOLD,20));
            }
            try {
                getDataFromDB();
                setValues();
                score++;
                amount = amount + 1;
                winningAmount.setValue(amount);
                winningAmount.setString(amount+" Lakh");


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            JFrame exitWindow = new JFrame("Game lost..");
            int a= JOptionPane.showConfirmDialog(exitWindow,"Sorry You have lost.. your score is "+score+"\n You want to play Again..?");
            if(a==JOptionPane.NO_OPTION)
            {
                gameWindow.dispose();
                System.exit(0);
            }
            if(a==JOptionPane.YES_OPTION)
            {
                startGame.setVisible(true);
                starting();
            }
            if(a==JOptionPane.CANCEL_OPTION)
            {
                JLabel scoreLabel = new JLabel("Congratulations "+name+" , you have won "+score+" Lakhs");
                starting();
                gameWindow.add(scoreLabel);
                scoreLabel.setBounds(20,250,500,100);
                scoreLabel.setFont(new Font("PiLiGi",Font.BOLD,20));

            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==optionA)
        {
            check(optionA);
        }
        else if(e.getSource()==optionB)
        {
            check(optionB);
        }
        else if(e.getSource()==optionC)
        {
            check(optionC);
        }
        else if(e.getSource()==optionD)
        {
            check(optionD);
        }
        else if(e.getSource()==expertAdvice)
        {
            JFrame expert = new JFrame("Expert says..");
            JOptionPane.showMessageDialog(expert,"Expert says the correct answer should be "+correctAnswer);
            expertAdvice.setEnabled(false);
        }
        else if(e.getSource() == fifty50)
        {
            JFrame fifty = new JFrame("50 : 50");
            JOptionPane.showMessageDialog(fifty,"Answer should be either "+fifty1+" or "+fifty2);
            if((!optionAbyDB.equals(fifty1))&&(!optionAbyDB.equals(fifty2)))
            {
                optionA.setEnabled(false);
            }
            if((!optionB.getText().equals(fifty1))&&(!optionB.getText().equals(fifty2)))
            {
                optionB.setEnabled(false);
            }
            if((!optionC.getText().equals(fifty1))&&(!optionC.getText().equals(fifty2)))
            {
                optionC.setEnabled(false);
            }
            if((!optionD.getText().equals(fifty1))&&(!optionD.getText().equals(fifty2)))
            {
                optionD.setEnabled(false);
            }
            fifty50.setEnabled(false);
        }
        else if(e.getSource()==swapQuestion)
        {
            setEnabled();
            try {
                getDataFromDB();
                setValues();
                swapQuestion.setEnabled(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(e.getSource()==phoneAfriend)
        {
            setEnabled();
            phoneAfriend.setEnabled(false);

        }
        else if(e.getSource()==startGame)
        {
            mp3Player=new MP3Player(startTone);
            mp3Player.play();
            makeVisible();
        }

    }
}
