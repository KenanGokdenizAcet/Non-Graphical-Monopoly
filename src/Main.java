import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void createBoard(Square[] boardName) { // creating game board
        PropertyJsonReader readProperty = new PropertyJsonReader(); //reading property.json
        boardName[0] = new Go(1, "Go");
        boardName[2] = new CommunityChest(3, "Community Chest");
        boardName[4] = new Tax(5, "Income Tax");
        boardName[7] = new Chance(8, "Chance");
        boardName[10] = new Jail(11, "Jail");
        boardName[17] = new CommunityChest(18, "Community Chest");
        boardName[20] = new FreeParking(21, "Free Parking");
        boardName[22] = new Chance(23, "Chance");
        boardName[30] = new GoToJail(31, "Go to Jail");
        boardName[33] = new CommunityChest(34, "Community Chest");
        boardName[36] = new Chance(37, "Chance");
        boardName[38] = new Tax(39, "Super Tax");

        for (String[] i : PropertyJsonReader.landList) { //creating object for lands
            boardName[Integer.parseInt(i[0]) - 1] =  new Land(Integer.parseInt(i[0]), i[1], Integer.parseInt(i[2]));
        }

        for (String[] i : PropertyJsonReader.railroadList) { //creating objects for rail roads
            boardName[Integer.parseInt(i[0]) - 1] =  new Railroad(Integer.parseInt(i[0]), i[1], Integer.parseInt(i[2]));
        }

        for (String[] i : PropertyJsonReader.companyList) { //creating objects for companies
            boardName[Integer.parseInt(i[0]) - 1] =  new Company(Integer.parseInt(i[0]), i[1], Integer.parseInt(i[2]));
        }


    }

    public static void createCard() { // creating objects for cards
        ListJsonReader readCards = new ListJsonReader(); //reading list.json

        for (String i : ListJsonReader.cardsForChance) { // creating card object for every card that is read.
            Card.chanceCardList.add(new ChanceCard(i));
        }

        for (String i : ListJsonReader.cardsForCommunityChest) { // creating card object for every card that is read.
            Card.communityChestCardList.add(new CommunityChestCard(i));
        }
    }

    public static ArrayList<String[]> readCommands(String fileName) { // reading commands from command file
        ArrayList<String[]> commands = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner f = new Scanner(file);
            while (f.hasNextLine()) {
                String command = f.nextLine();
                commands.add(command.split(";")); // splitting line
            }
            f.close();
            return commands;
        } catch (Exception e) {
            System.out.println("Error!!!");
            return null;
        }
    }


    public static String show(Player player_1, Player player_2, Banker banker) { // it is for printing show() command's result
        String result = ""; // it is String that will be returned
        String winner = "";
        int player1money = player_1.getMoney();
        int player2money = player_2.getMoney();


        if (player_1.getMoney() > player_2.getMoney()) { // determining the winner
            winner = "Player 1";
        } else {
            winner = "Player 2";
        }

        if (player1money < 0) { // it is for printing zero instead of negative number
            player1money = 0;
        } else if (player2money < 0) {
            player2money = 0;
        }

        result += "-----------------------------------------------------\n";
        result += "Player 1\t" + player1money + "\thave\t" + String.join(" ,", player_1.getPropertyOwned()) + "\n";
        result += "Player 2\t" + player2money + "\thave\t" + String.join(" ,", player_2.getPropertyOwned()) + "\n";
        result += "Banker\t" + banker.getMoney() + "\n";
        result += "Winner\t" + winner;
        result += "\n-----------------------------------------------------\n";

        return result;
    }


    // it is for printing the result after every turn
    public static String writeLine(String[] command, Player currentPlayer, Player player1, Player player2, String output) {
        String line = command[0] + "\t" + command[1] + "\t" + (currentPlayer.getLocation() + 1) + "\t" + player1.getMoney() +
                "\t" + player2.getMoney() + "\t" + output + "\n";
        return line;
    }


    public static String play(ArrayList<String[]> commands, Square[] gameBoard, Banker banker, Player player1, Player player2) {

        String monitoring = ""; // String that will be returned

        for (String[] command : commands) {

            if (command[0].startsWith("Player")) {
                String output = "";
                Player currentPlayer = null;
                Player nextPlayer = null;
                String player = command[0];
                int dice = Integer.parseInt(command[1]);

                if (player.equals("Player 1")) { // deciding the player who play
                    currentPlayer = player1;
                    nextPlayer = player2;
                } else if (player.equals("Player 2")) {
                    currentPlayer = player2;
                    nextPlayer = player1;
                }

                if (currentPlayer.getJailCount() < 3) { //checking whether player is in jail
                    if (currentPlayer.getLocation() == 20) { // if player is in free parking
                        currentPlayer.setJailCount(5);
                        output += player + "\tis in Free Parking (count=1)";
                        monitoring += writeLine(command, currentPlayer, player1, player2, output);
                    } else {
                        currentPlayer.setJailCount(currentPlayer.getJailCount() + 1);
                        output += player + "\tis in Jail (count=" + currentPlayer.getJailCount() + ")";
                        monitoring += writeLine(command, currentPlayer, player1, player2, output);
                    }
                } else {
                    currentPlayer.walk(dice, banker);
                    while (true) {
                        if (currentPlayer.getLocation() == 0) { // Go Square
                            currentPlayer.give200(banker);
                            output += player + "\tis in Go Square";
                            monitoring += writeLine(command, currentPlayer, player1, player2, output);
                            break;
                        } else if (currentPlayer.getLocation() == 2 || currentPlayer.getLocation() == 17 || currentPlayer.getLocation() == 33) {
                            //community chest
                            String directive = CommunityChestCard.drawCard();
                            output += player + "\tdraw " + currentPlayer.actionCard(directive, gameBoard, dice, banker, nextPlayer);
                            monitoring += writeLine(command, currentPlayer, player1, player2, output);
                            break;
                        } else if (currentPlayer.getLocation() == 7 || currentPlayer.getLocation() == 22 || currentPlayer.getLocation() == 36) {
                            //chance
                            String directive = ChanceCard.drawCard();
                            //if player is in these squares, player go into the loop again;
                            if (directive.equals("Advance to Leicester Square") || directive.equals("Go back 3 spaces")) {
                                output += player + "\tdraw " +currentPlayer.actionCard(directive, gameBoard, dice, banker, nextPlayer) + " ";
                            } else {
                                output += player + "\tdraw " +currentPlayer.actionCard(directive, gameBoard, dice, banker, nextPlayer);
                                monitoring += writeLine(command, currentPlayer, player1, player2, output);
                                break;
                            }
                        } else if (currentPlayer.getLocation() == 4 || currentPlayer.getLocation() == 38) { //Income Tax, Super Tax
                            if (currentPlayer.getMoney() < 100) {
                                output += player + "\tgoes bankrupt";
                            } else {
                                currentPlayer.payTax(banker);
                                output += player + "\tpaid tax";
                            }
                            monitoring += writeLine(command, currentPlayer, player1, player2, output);
                            break;


                        } else if (currentPlayer.getLocation() == 10) { //Jail
                            currentPlayer.setJailCount(0);
                            output += player + "\twent to Jail";
                            monitoring += writeLine(command, currentPlayer, player1, player2, output);
                            break;
                        } else if (currentPlayer.getLocation() == 20) { //Free Parking
                            output += player + "\tis in Free Parking";
                            currentPlayer.setJailCount(2);
                            monitoring += writeLine(command, currentPlayer, player1, player2, output);
                            break;
                        } else if (currentPlayer.getLocation() == 30) { //Go to Jail
                            currentPlayer.walk(dice, 10, banker);
                            currentPlayer.setJailCount(0);
                            output += player + "\twent to Jail";
                            monitoring += writeLine(command, currentPlayer, player1, player2, output);
                            break;
                        } else {
                            Property property = (Property)gameBoard[currentPlayer.getLocation()];
                            if (property.getOwner() == nextPlayer) { // pay rent
                                if (currentPlayer.getMoney() < property.calculateRent(dice)) {
                                    output += player + "\tgoes bankrupt";
                                    monitoring += writeLine(command, currentPlayer, player1, player2, output);
                                    break;
                                } else {
                                    property.payRent(currentPlayer, property.calculateRent(dice));
                                    output += player + "\tpaid rent for " + property.getName();
                                    monitoring += writeLine(command, currentPlayer, player1, player2, output);
                                    break;
                                }
                            } else if (property.getOwner() == currentPlayer) { // do nothing
                                output += player + "\thas " + property.getName();
                                monitoring += writeLine(command, currentPlayer, player1, player2, output);
                                break;
                            } else {
                                if (currentPlayer.getMoney() < property.getCost()) { //buy property
                                    output += player + "\tgoes bankrupt";
                                    monitoring += writeLine(command, currentPlayer, player1, player2, output);
                                    break;
                                } else {
                                    property.buyProperty(currentPlayer, banker);
                                    output += player + "\tbought " + property.getName() ;
                                    monitoring += writeLine(command, currentPlayer, player1, player2, output);
                                    break;
                                }
                            }
                        }

                    }
                }
            } else {
                monitoring += show(player1, player2, banker);
            }

            if (monitoring.endsWith("bankrupt\n")) { // if there is any bankrupt, game over
                break;
            }


        }

        monitoring += show(player1, player2, banker);

        return monitoring;
    }

    public static void writeOutput(String content) { //writing the result to the output file
        try {
            FileWriter file = new FileWriter("monitoring.txt");
            file.write(content);
            file.close();
        } catch (IOException e) {
            System.out.println("An error occured!");
        }
    }



    public static void main(String[] args) {
        String output = "";
        Square[] gameBoard = new Square[40];
        People banker = new Banker(100000); // creating banker
        People player_1 = new Player(15000); // creating player 1
        People player_2 = new Player(15000); // creating player 2
        ArrayList<String[]> commands = new ArrayList<String[]>(); // commands list

        createBoard(gameBoard); // creating game board
        createCard(); // creating cards

        commands = readCommands(args[0]); // reading commands
        output = (play(commands ,gameBoard, (Banker)banker, (Player)player_1, (Player)player_2));

        writeOutput(output);
    }




}
