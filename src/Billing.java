class Billing {

    private static final int DEFAULT_CREDIT = 100;
    private static final int DEFAULT_BET = 1;

    private int credit;
    private int bet;

    Billing() {

        this(DEFAULT_CREDIT);
    }

    Billing(int credit) {

        this.credit = credit;
    }

    void makeBet() {

        makeBet(DEFAULT_BET);
    }

    void makeBet(int bet) {

        System.out.printf("%nBet: %d\t\t\tCredit: %d%n%n",
                            this.bet = bet,
                            credit -= this.bet);
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
