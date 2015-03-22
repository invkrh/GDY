package me.invkrh.gdy.model;

/**
 * Created by invkrh on 2015/3/21.
 */

public class Transaction {

    private Player from;
    private Player to;
    private int payment;
    private boolean pointChanged = false;

    public Transaction(Player p_from, Player p_to, int p_points) {
        from = p_from;
        to = p_to;
        payment = p_points;
    }

    public void commit() {
        if (from.points > payment) {
            from.points -= payment;
            to.points += payment;
            pointChanged = true;
        }
    }

    public void rollback() {
        if (pointChanged) {
            from.points += payment;
            to.points -= payment;
        }
    }

}
