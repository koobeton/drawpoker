class Billing {

    private static final int DEFAULT_CREDIT = 100;
    private static final int DEFAULT_BET = 1;

    private int credit;
    private int bet;

    Billing() {

        credit = DEFAULT_CREDIT;
    }

    void makeBet() {

        System.out.printf("%nBet: %d\t\t\tCredit: %d%n%n",
                bet = DEFAULT_BET,
                credit -= bet);
    }

    void update(Combination combination) {

        int payout = combination != null ? combination.getPayout() : 0;
        int pays = payout * bet;
        System.out.printf("%nPays: %d x %d = %d\t\tCredit: %d%n",
                payout,
                bet,
                pays,
                credit += pays);
    }

    int getCredit() {
        return credit;
    }
}
