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
        if (from.points != 0) {
            int amount = java.lang.Math.min(from.points, payment);
            from.points -= amount;
            to.points += amount;
            this.pointChanged = true;
        }
    }

    public void rollback() {
        if (pointChanged) {
            from.points += payment;
            to.points -= payment;
        }
    }

}
