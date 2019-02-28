package com.alibaba.csp.sentinel.dashboard.util.wuga;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Part of https://github.com/thedathoudarya/WAGU-data-in-table-view
 * Wagu helps you to prepare your data with neat, <br>
 * more readable and well organized by bringing “Table” and “Block” views with basic text formatting features.
 *
 * @author Thedath Oudarya
 */
class Charr {

    protected static final char S = ' ';

    protected static final char NL = '\n';

    protected static final char P = '+';

    protected static final char D = '-';

    protected static final char VL = '|';

    private final int x;

    private final int y;

    private final char c;

    protected Charr(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    protected char getC() {
        return c;
    }

}