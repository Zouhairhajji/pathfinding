/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import abstractClasses.AbstractMap;
import abstractClasses.AbstractNode;
import entities.cellules.Block;
import entities.maps.Incarnam;
import java.util.List;

/**
 *
 * @author zouhairhajji
 */
public class main {
    
    public static void main(String[] args) {
        
        
        AbstractMap map = new Incarnam();
        map.setCanMoveDiagonaly(false);
        
        List<AbstractNode> nodesPath = map.findPath(0, 99, true, true);
        
        for(AbstractNode node : nodesPath){
            map.setCell(new Block(node.getCellID()));
        }
        System.out.println(map.pringMap());
    }
    
}
