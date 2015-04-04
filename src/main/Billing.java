package main;

import cards.Combination;
import db.DBManager;
import misc.Paint;

import java.sql.SQLException;

class Billing {

    private static final int DEFAULT_CREDIT = 100;
    private static final int DEFAULT_BET = 1;

    private int credit;
    private int bet;

    Billing(int playerId) throws SQLException {

        credit = DBManager.getPlayer(playerId).getCredit();
        credit = credit <= 0 ? DEFAULT_CREDIT : credit;
    }

    void makeBet() {

        System.out.printf("%n%s %s\t\t\t%s %s%n%n",
                Paint.getAnsiString(Paint.WHITE, "Bet:"),
                Paint.getAnsiString(Paint.YELLOW, bet = DEFAULT_BET),
                Paint.getAnsiString(Paint.WHITE, "Credit:"),
                Paint.getAnsiString(Paint.GREEN, credit -= bet));
    }

    void update(Combination combination) {

        int payout = combination != null ? combination.getPayout() : 0;
        int pays = payout * bet;
        System.out.printf("%n%s %s x %s = %s\t\t%s %s%n",
                Paint.getAnsiString(Paint.WHITE, "Pays:"),
                Paint.getAnsiString(Paint.MAGENTA, payout),
                Paint.getAnsiString(Paint.YELLOW, bet),
                Paint.getAnsiString(Paint.GREEN, pays),
                Paint.getAnsiString(Paint.WHITE, "Credit:"),
                Paint.getAnsiString(Paint.GREEN, credit += pays));
    }

    int getCredit() {
        return credit;
    }
}
