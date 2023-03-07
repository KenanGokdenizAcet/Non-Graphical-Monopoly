public class Square {
    private final int  squareNumber;
    private final String name;

    public Square(int squareNumber, String name) {
        this.name = name;
        this.squareNumber = squareNumber;
    }

    public String getName() {
        return name;
    }

    public int getSquareNumber() {
        return squareNumber;
    }

}

abstract class Property extends Square {
    private final int cost;
    private Player owner = null;

    public Property(int squareNumber, String name, int cost) {
        super(squareNumber, name);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public abstract void buyProperty(Player player, Banker banker);
    public abstract int calculateRent(int dice);
    public abstract void payRent(Player player, int rent);
}

class Land extends Property {
    public Land(int squareNumber, String name, int cost) {
        super(squareNumber, name, cost);
    }

    public int calculateRent(int dice) {
        int rent;
        if (this.getCost() > 0 && this.getCost() <= 2000) {
            rent = this.getCost() * 40 / 100;
        } else if (this.getCost() > 2000 && this.getCost() <= 3000){
            rent = this.getCost() * 30 / 100;
        } else {
            rent = this.getCost() * 35 / 100;
        }
        return rent;
    }

    public void payRent(Player player, int rent) {
        player.setMoney(player.getMoney() - rent);
        this.getOwner().setMoney(this.getOwner().getMoney() + rent);
    }

    public void buyProperty(Player player, Banker banker) {
        this.setOwner(player);
        player.addPropertyOwned(this.getName());
        player.setMoney(player.getMoney() - this.getCost());
        banker.setMoney(banker.getMoney() + this.getCost());
    }
}

class Railroad extends Property {
    public Railroad(int squareNumber, String name, int cost) {
        super(squareNumber, name, cost);
    }

    public int calculateRent(int dice) {
        return 25 * this.getOwner().getRailRoadsOwnedNumber();
    }

    public void payRent(Player player, int rent) {
        player.setMoney(player.getMoney() - rent);
        this.getOwner().setMoney(this.getOwner().getMoney() + rent);
    }

    public void buyProperty(Player player, Banker banker) {
        player.setRailRoadsOwnedNumber(player.getRailRoadsOwnedNumber() + 1);
        this.setOwner(player);
        player.addPropertyOwned(this.getName());
        player.setMoney(player.getMoney() - this.getCost());
        banker.setMoney(banker.getMoney() + this.getCost());
    }
}

class Company extends Property {
    public Company(int squareNumber, String name, int cost) {
        super(squareNumber, name, cost);
    }

    public int calculateRent(int dice) {
        return 4 * dice;
    }

    public void payRent(Player player, int rent) {
        player.setMoney(player.getMoney() - rent);
        this.getOwner().setMoney(this.getOwner().getMoney() + rent);
    }

    public void buyProperty(Player player, Banker banker) { // player who buys property
        this.setOwner(player);
        player.addPropertyOwned(this.getName());
        player.setMoney(player.getMoney() - this.getCost());
        banker.setMoney(banker.getMoney() + this.getCost());
    }
}

class ActionSquare extends Square {
    public ActionSquare(int squareNumber, String name) {
        super(squareNumber, name);
    }

}

class Chance extends ActionSquare {
    public Chance(int squareNumber, String name) {
        super(squareNumber, name);
    }
}

class CommunityChest extends ActionSquare {
    public CommunityChest(int squareNumber, String name) {
        super(squareNumber, name);
    }
}

class Go extends Square {
    public Go(int squareNumber, String name) {
        super(squareNumber, name);
    }
}

class Jail extends Square {
    public Jail(int squareNumber, String name) {
        super(squareNumber, name);
    }
}

class FreeParking extends Square {
    public FreeParking(int squareNumber, String name) {
        super(squareNumber, name);
    }
}

class GoToJail extends Square {
    public GoToJail(int squareNumber, String name) {
        super(squareNumber, name);
    }
}

class Tax extends Square {
    public Tax(int squareNumber, String name) {
        super(squareNumber, name);
    }
}
