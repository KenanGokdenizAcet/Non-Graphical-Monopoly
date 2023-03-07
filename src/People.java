import java.util.ArrayList;

public class People {
    private int money;

    public People(int money) {
        this.setMoney(money);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}

class Player extends People {
    private int jailCount = 5; // jail count. I check whether count is less than 3, so default value is 5
    private int location = 0; //firs square

    public Player(int money) {
        super(money);
    }

    private ArrayList<String> propertyOwned = new ArrayList<>(); // properties which player has
    private int railRoadsOwnedNumber = 0;

    public ArrayList<String> getPropertyOwned() {
        return propertyOwned;
    }

    public void addPropertyOwned(String propertyName) { // adding new property to player's properties
        this.propertyOwned.add(propertyName);
    }

    public int getRailRoadsOwnedNumber() { // it is number of Railroads which player has;
        return railRoadsOwnedNumber;
    }

    public void setRailRoadsOwnedNumber(int railRoadsOwnedNumber) {
        this.railRoadsOwnedNumber = railRoadsOwnedNumber;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void payTax(People banker) {
        this.setMoney(this.getMoney() - 100);
        banker.setMoney(banker.getMoney() + 100);
    }


    public void walk(int step, Banker banker) { // depend on  dice
        int initialLocation = this.getLocation();
        this.setLocation((this.getLocation() + step) % 40);
        int finalLocation = this.getLocation();
        if (finalLocation < initialLocation) { //comparing final and initial location for deciding that player passes the starting point
            if (initialLocation == 30 || finalLocation == 0) { // if player is  in go to jail or go square

            } else {
                this.give200(banker);
            }
        }
    }

    public void walk(int dice, int targetLocation, Banker banker) { // depend on target location
        int initialLocation = this.getLocation();
        this.setLocation(targetLocation);
        if (targetLocation < initialLocation) { //comparing final and initial location for deciding that player passes the starting point
            if (targetLocation == 0 || targetLocation == 10) { // go square or jail

            } else {
                this.give200(banker);
            }
        }
    }

    public void give200(Banker banker) {
        this.setMoney(this.getMoney() + 200);
        banker.setMoney(banker.getMoney() - 200);
    }

    // it is for acting according to the directive
    // if player does not have enough money, function returns "goes bankrupt"; if player has, it returns directive
    public String actionCard(String directive, Square[] gameBoard, int dice, Banker banker, Player player_2) {

        if ("Advance to Go (Collect $200)".equals(directive)) {
            this.walk(dice, 0, banker);
            this.give200(banker);
        } else if ("Advance to Leicester Square".equals(directive)) {
            for (Square i : gameBoard) { // it is for find the square number of Leicester Square
                if (i.getName().equals("Leicester Square")) {
                    int targetLocation = (i.getSquareNumber() - 1);
                    this.walk(dice, targetLocation, banker);
                    break;
                }
            }
        } else if ("Go back 3 spaces".equals(directive)) {
            this.setLocation(this.getLocation() - 3);
        } else if ("Pay poor tax of $15".equals(directive)) {
            if (this.getMoney() < 15) {
                return "\tgoes bankrupt";
            } else {
                this.setMoney(this.getMoney() - 15);
                banker.setMoney(banker.getMoney() + 15);
            }
        } else if ("Your building loan matures - collect $150".equals(directive)) {
            this.setMoney(this.getMoney() + 150);
            banker.setMoney(banker.getMoney() - 150);
        } else if ("You have won a crossword competition - collect $100 ".equals(directive)) {
            this.setMoney(this.getMoney() + 100);
            banker.setMoney(banker.getMoney() - 100);
        } else if ("Bank error in your favor - collect $75".equals(directive)) {
            this.setMoney(this.getMoney() + 75);
            banker.setMoney(banker.getMoney() - 75);
        } else if ("Doctor's fees - Pay $50".equals(directive)) {
            if (this.getMoney() < 50) {
                return "\tgoes bankrupt";
            } else {
                this.setMoney(this.getMoney() - 50);
                banker.setMoney(banker.getMoney() + 50);
            }
        } else if ("It is your birthday Collect $10 from each player".equals(directive)) {
            this.setMoney(this.getMoney() + 10);
            player_2.setMoney(player_2.getMoney() - 10);
        } else if ("Grand Opera Night - collect $50 from every player for opening night seats".equals(directive)) {
            this.setMoney(this.getMoney() + 50);
            player_2.setMoney(player_2.getMoney() - 50);
        } else if ("Income Tax refund - collect $20".equals(directive)) {
            this.setMoney(this.getMoney() + 20);
            banker.setMoney(banker.getMoney() - 20);
        } else if ("Life Insurance Matures - collect $100".equals(directive)) {
            this.setMoney(this.getMoney() + 100);
            banker.setMoney(banker.getMoney() - 100);
        } else if ("Pay Hospital Fees of $100".equals(directive)) {
            if (this.getMoney() < 100) {
                return "\tgoes bankrupt";
            } else {
                this.setMoney(this.getMoney() - 100);
                banker.setMoney(banker.getMoney() + 100);
            }
        } else if ("Pay School Fees of $50".equals(directive)) {
            if (this.getMoney() < 50) {
                return "\tgoes bankrupt";
            } else {
                this.setMoney(this.getMoney() - 50);
                banker.setMoney(banker.getMoney() + 50);
            }
        } else if ("You inherit $100".equals(directive)) {
            this.setMoney(this.getMoney() + 100);
            banker.setMoney(banker.getMoney() - 100);
        } else { // From sale of stock you get $50
            this.setMoney(this.getMoney() + 50);
            banker.setMoney(banker.getMoney() - 50);
        }
        return directive;
    }

    public int getJailCount() {
        return jailCount;
    }

    public void setJailCount(int count) {
        this.jailCount = count;
    }
}

class Banker extends People {
    public Banker(int money) {
        super(money);
    }
}