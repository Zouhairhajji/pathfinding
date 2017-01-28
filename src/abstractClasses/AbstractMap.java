package abstractClasses;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import enums.Direction;
import entities.cellules.Empty;

public abstract class AbstractMap {

    public static int WIDTH = 20;
    protected boolean canMoveDiagonaly = true;
    private HashMap<Integer, AbstractNode> nodes;
    protected int higth;
    
    
    protected static int MAXDISTANCE = 100;

    public AbstractMap(int higth) {
        nodes = new HashMap<Integer, AbstractNode>();
        this.higth = higth;
        initEmptyNodes();
    }

    private void initEmptyNodes() {
        for (int i = 0; i <= WIDTH * this.higth-1; i++) {
            this.nodes.put(i, new Empty(i));
        }
    }

    
    public void setCell(AbstractNode node){
        this.nodes.put(node.getCellID(), node);
    }
    
    public void setWalkable(int x, int y, boolean state) {
        this.setWalkable(getCellID(x, y), state);
    }

    public void setWalkable(int cellID, boolean state) {
        nodes.get(cellID).setWalkable(state);
    }

    public int getXPosition(int cellID) {
        return cellID / WIDTH;
    }

    public int getYPosition(int cellID) {
        return cellID % WIDTH;
    }

    public int getCellID(int x, int y) {
        return x + y * WIDTH;
    }

    public final AbstractNode getNode(int x, int y) {
        return this.getNode(getCellID(x, y));
    }

    public final AbstractNode getNode(int cellID) {
        return this.nodes.get(cellID);
    }

    public void setCanMoveDiagonaly(boolean canMoveDiagonaly) {
        this.canMoveDiagonaly = canMoveDiagonaly;
    }
    
    

    public HashMap<Integer, AbstractNode> getNodes() {
        return nodes;
    }
    
    
    public String pringMap() {
        String mapToString = "";
        for (Entry<Integer, AbstractNode> node : this.nodes.entrySet()) {
            if (node.getKey() % WIDTH == 0) {
                mapToString += (char) 10;
            }
            mapToString += node.getValue().getCharID();
        }
        return mapToString;
    }

    /**
     * prints something to sto.
     */
    private void print(String s) {
        System.out.print(s);
    }

    public int getFuturID(int cellID, Direction direction) {
        if (!ifCellIDExist(cellID)) {
            return -1;
        }

        int futurX = cellID % WIDTH;
        int futurY = cellID / WIDTH;

        //System.out.println("Old    : [x=" + futurX+", y="+futurY+"]");
        switch (direction) {
            case EST:
                futurX += 1;
                futurY += 0;
                break;

            case EST_SOUTH:
                futurX += 1;
                futurY += 1;
                break;

            case NORTH:
                futurX += 0;
                futurY -= 1;
                break;

            case NORTH_EST:
                futurX += 1;
                futurY -= 1;
                break;

            case OUEST:
                futurX -= 1;
                futurY += 0;
                break;

            case OUEST_NORTH:
                futurX -= 1;
                futurY -= 1;
                break;

            case SOUTH:
                futurX += 0;
                futurY += 1;
                break;

            case SOUTH_OUEST:
                futurX -= 1;
                futurY += 1;
                break;

            default:
                return -1;
        }
        if (checkCoordinate(futurX, futurY)) {
            return futurY * WIDTH + futurX;
        } else {
            return -1;
        }
    }

    public boolean checkCoordinate(int x, int y) {
        if (x < 0 || x >= WIDTH) {
            return false;
        }
        if (y < 0 || y >= this.higth) {
            return false;
        }
        return true;
    }

    public boolean ifCellIDExist(int cellID) {
        int futurX = cellID / WIDTH;
        if (futurX < 0) {
            return false;
        }
        if (futurX >= WIDTH) {
            return false;
        }
        return this.nodes.containsKey(cellID);
    }

    /* Variables and methodes for path finding */
    // variables needed for path finding
    /**
     * list containing nodes not visited but adjacent to visited nodes.
     */
    private List<AbstractNode> openList;
    /**
     * list containing nodes already visited/taken care of.
     */
    private List<AbstractNode> closedList;
    /**
     * done finding path?
     */
    private boolean done = false;

    public final List<AbstractNode> findPath(int oldCellID, int newCellID, boolean includeFirst, boolean includeLast) {
        if(!ifCellIDExist(newCellID) || !ifCellIDExist(oldCellID))
            return null;
        
        
        openList = new LinkedList<AbstractNode>();
        closedList = new LinkedList<AbstractNode>();
        openList.add(nodes.get(oldCellID)); // add starting node to open list
        

        done = false;
        AbstractNode current;
        while (!done) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            if ( current.getCellID()== newCellID ) { // found goal
                return calcPath(nodes.get(oldCellID), current, includeFirst, includeLast);
            }

            // for all adjacent nodes:
            List<AbstractNode> adjacentNodes = getAdjacent(current);
            for (int i = 0; i < adjacentNodes.size(); i++) {
                AbstractNode currentAdj = adjacentNodes.get(i);
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setPrevious(current); // set current node as previous for this node
                    currentAdj.sethCosts(nodes.get(newCellID)); // set h costs of this node (estimated costs to goal)
                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    openList.add(currentAdj); // add node to openList
                
                // node is in openList
                } else if (currentAdj.getgCosts() > currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
                    currentAdj.setPrevious(current); // set current node as previous for this node
                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                }
            }

            if (openList.isEmpty()) { // no path exists
                return new LinkedList<AbstractNode>(); // return empty list
            }
        }
        return null; // unreachable
    }

    
    
    
    
    private List<AbstractNode> calcPath(AbstractNode start, AbstractNode goal, boolean includeFirst, boolean includeLast) {
        // TODO if invalid nodes are given (eg cannot find from
        // goal to start, this method will result in an infinite loop!)
        LinkedList<AbstractNode> path = new LinkedList<AbstractNode>();

        
        
        
        AbstractNode curr ;
        if(includeLast){
            curr = goal;
        }else{
            curr = goal.getPrevious();
        }
        
        boolean done = false;
        while (!done) {
            path.addFirst(curr);
            curr = curr.getPrevious();
            if (curr.equals(start)) {
                done = true;
                if(includeFirst){
                    path.addFirst(start);
                }
            }
        }
        
        
        return path;
    }

    /**
     * returns the node with the lowest fCosts.
     *
     * @return
     */
    private AbstractNode lowestFInOpen() {
        // TODO currently, this is done by going through the whole openList!
        AbstractNode cheapest = openList.get(0);
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).getfCosts() < cheapest.getfCosts()) {
                cheapest = openList.get(i);
            }
        }
        return cheapest;
    }

    /**
     * returns a LinkedList with nodes adjacent to the given node. if those
     * exist, are walkable and are not already in the closedList!
     */
    private List<AbstractNode> getAdjacent(AbstractNode node) {
        // TODO make loop
        int x = node.getxPosition();
        int y = node.getyPosition();
        List<AbstractNode> adj = new LinkedList<AbstractNode>();

        AbstractNode temp;
        int futurCellID = -1;

        futurCellID = getFuturID(node.getCellID(), Direction.NORTH);
        if (futurCellID != -1) {
            temp = this.getNode(futurCellID);
            if (temp.isWalkable() && !closedList.contains(temp)) {
                temp.setDiagonaly(false);
                adj.add(temp);
            }
        }

        futurCellID = getFuturID(node.getCellID(), Direction.SOUTH);
        if (futurCellID != -1) {
            temp = this.getNode(futurCellID);
            if (temp.isWalkable() && !closedList.contains(temp)) {
                temp.setDiagonaly(false);
                adj.add(temp);
            }
        }

        futurCellID = getFuturID(node.getCellID(), Direction.EST);
        if (futurCellID != -1) {
            temp = this.getNode(futurCellID);
            if (temp.isWalkable() && !closedList.contains(temp)) {
                temp.setDiagonaly(false);
                adj.add(temp);
            }
        }

        futurCellID = getFuturID(node.getCellID(), Direction.OUEST);
        if (futurCellID != -1) {
            temp = this.getNode(futurCellID);
            if (temp.isWalkable() && !closedList.contains(temp)) {
                temp.setDiagonaly(false);
                adj.add(temp);
            }
        }

        // add nodes that are diagonaly adjacent too:
        if (canMoveDiagonaly) {
            futurCellID = getFuturID(node.getCellID(), Direction.EST_SOUTH);
            if (futurCellID != -1) {
                temp = this.getNode(futurCellID);
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setDiagonaly(false);
                    adj.add(temp);
                }
            }

            futurCellID = getFuturID(node.getCellID(), Direction.NORTH_EST);
            if (futurCellID != -1) {
                temp = this.getNode(futurCellID);
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setDiagonaly(false);
                    adj.add(temp);
                }
            }

            futurCellID = getFuturID(node.getCellID(), Direction.OUEST_NORTH);
            if (futurCellID != -1) {
                temp = this.getNode(futurCellID);
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setDiagonaly(false);
                    adj.add(temp);
                }
            }

            futurCellID = getFuturID(node.getCellID(), Direction.SOUTH_OUEST);
            if (futurCellID != -1) {
                temp = this.getNode(futurCellID);
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setDiagonaly(false);
                    adj.add(temp);
                }
            }
        }
        return adj;
    }

}
