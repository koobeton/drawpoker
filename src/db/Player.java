package db;

public class Player {

    private int id;
    private int credit;

    Player(int id, int credit) {
        this.id = id;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }
}
