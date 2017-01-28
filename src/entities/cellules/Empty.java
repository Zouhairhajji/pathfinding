/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.cellules;

import abstractClasses.AbstractNode;

/**
 *
 * @author zouhairhajji
 */
public class Empty extends AbstractNode {

    public Empty(int cellID) {
        super(cellID, '.', true, 0);
    }

    @Override
    public void sethCosts(AbstractNode endNode) {
        this.sethCosts((absolute(this.getxPosition() - endNode.getxPosition())
                + absolute(this.getyPosition() - endNode.getyPosition()))
                * BASICMOVEMENTCOST);
    }

    private int absolute(int a) {
        return a > 0 ? a : -a;
    }

}
