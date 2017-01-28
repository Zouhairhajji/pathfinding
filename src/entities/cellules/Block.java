/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.cellules;

import abstractClasses.AbstractNode;
import static java.lang.Math.abs;

/**
 *
 * @author zouhairhajji
 */
public class Block extends AbstractNode{

    public Block(int cellID) {
        super(cellID, 'X', false, 0);
    }

    @Override
    public void sethCosts(AbstractNode endNode) {
        this.sethCosts((abs(this.getxPosition() - endNode.getxPosition())
                + abs(this.getyPosition() - endNode.getyPosition()))
                * BASICMOVEMENTCOST);
    }
    
}
