package edu.cmu.cs214.hw1;

import edu.cmu.cs214.hw1.cli.UI;
import edu.cmu.cs214.hw1.data.CardLoader;
import edu.cmu.cs214.hw1.data.CardStore;
import edu.cmu.cs214.hw1.data.InMemoryCardStore;
import edu.cmu.cs214.hw1.ordering.CardDeck;
import edu.cmu.cs214.hw1.ordering.CardOrganizer;
import edu.cmu.cs214.hw1.ordering.CombinedCardOrganizer;
import edu.cmu.cs214.hw1.ordering.prioritization.CardShuffler;
import edu.cmu.cs214.hw1.ordering.prioritization.MostMistakesFirstSorter;
import edu.cmu.cs214.hw1.ordering.prioritization.RecentMistakesFirstSorter;
import edu.cmu.cs214.hw1.ordering.repetition.NonRepeatingCardOrganizer;
import edu.cmu.cs214.hw1.ordering.repetition.RepeatingCardOrganizer;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class Main {

    private Main() {
        // Disable instantiating this class.
        throw new UnsupportedOperationException();
    }

    private static final Option Help = new Option("h","help",false,"Show this help");
    private static final Option InvertCards = new Option("i","invertcards",false,"If set, it flips answer and question for each card. That is, it \n" +
            "                  prompts with the card's answer and asks the user" +"to provide the corresponding question. \n" +
            "                  Default: false");
    private static final Option Order = Option.builder("o").longOpt("order").argName("order method").hasArg().desc("The type of ordering to use, default \"random\"\n" +
            "   \t\t\t\t\t\t[choices: \"random\", \"worst-first\", \"recent-mistakes-first\"] ").build();
    private static final Option Repetitions = Option.builder("r").longOpt("repetitions").argName("times").hasArg().desc("The number of times to each card should be answered\n" +
            "                  successfully. If not provided, every card is presented once,\n" +
            "                  regardless of the correctness of the answer. ").build();

    private static void printMessage (Options options){
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(System.out);
        helpFormatter.printUsage(pw,100,"flashcard <cards-file> [options] arguments");
        pw.println("Options:");
        helpFormatter.printOptions(pw,100,options,2,5);
        pw.close();
    }

    public static void main(String[] args) throws IOException, ParseException {
        boolean invertcard = false;
        int Times = 0;
        String OrderName = new String();
        CommandLineParser clp = new DefaultParser();
        Options options = new Options();

        options.addOption(Help);
        options.addOption(InvertCards);
        options.addOption(Order);
        options.addOption(Repetitions);

        try {
            CommandLine line =  clp.parse(options,args);
            if (line.hasOption(Help.getLongOpt())){
                printMessage(options);
            }
            else if (line.hasOption(InvertCards.getLongOpt())){
                 invertcard = true;
            }
            else if (line.hasOption(Order.getLongOpt())){
                String ordername = line.getOptionValue(Order.getLongOpt());
                if (ordername.trim().equals("random")){
                    OrderName = "CardShuffler";
                }
                else if (ordername.trim().equals("worst-first")){
                    OrderName = "MostMistakesFirstSorter";
                }
                else if (ordername.trim().equals("recent-mistakes-first") ){
                    OrderName = "RecentMistakesFirstOrder";
                }
                else{
                    printMessage(options);
                }
            }
            else if (line.hasOption(Repetitions.getLongOpt())){
                String times = line.getOptionValue(Repetitions.getLongOpt());
                Times = Integer.parseInt(times);
            }
            else if (line.getArgList().size()>0){
                System.out.println("ERROR! You have entered the wrong command!");
                printMessage(options);
            }
        }catch(ParseException exp){
            System.out.println("Unexpected exception:" + exp.getMessage());
        }

        /*Set the times of repetitions*/
        CardStore cards = new CardLoader().loadCardsFromFile(new File("cards/designpatterns.csv"));
        List<CardOrganizer> cardOrganizers = new ArrayList<>();
        if (Times>0){
            RepeatingCardOrganizer organizer1 = new RepeatingCardOrganizer(Times);
            cardOrganizers.add(organizer1);
        }else{
            NonRepeatingCardOrganizer organizer2 = new NonRepeatingCardOrganizer();
            cardOrganizers.add(organizer2);
        }

        /*Set the mode of card invert*/
        if (invertcard == true){
            cards = cards.invertCards();
            System.out.println("Cards have already been inverted!");
        }

        /*Set the mode of card organization*/
        if (OrderName.trim().equals("CardShuffler")) {
            CardShuffler cardShuffler = new CardShuffler();
            cardOrganizers.add(cardShuffler);
        }
        else if (OrderName.trim().equals("MostMistakesFirstSorter")){
            MostMistakesFirstSorter mostMistakesFirstSorter = new MostMistakesFirstSorter();
            cardOrganizers.add(mostMistakesFirstSorter);
        }
        else if (OrderName.trim().equals("RecentMistakesFirstOrder")){
            RecentMistakesFirstSorter recentMistakesFirstSorter = new RecentMistakesFirstSorter();
            cardOrganizers.add(recentMistakesFirstSorter);
        }
        else{
            System.out.println("You have not chosen the mode, the default mode is random!");
            CardShuffler cardShuffler = new CardShuffler();
            cardOrganizers.add(cardShuffler);
        }

        CombinedCardOrganizer combinedCardOrganizer = new CombinedCardOrganizer(cardOrganizers);
        CardDeck cardDeck = new CardDeck(cards.getAllCards(), combinedCardOrganizer);
        new UI().studyCards(cardDeck);

    }

}
