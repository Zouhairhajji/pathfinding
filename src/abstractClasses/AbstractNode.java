package abstractClasses;

import static abstractClasses.AbstractMap.WIDTH;



public abstract class AbstractNode {

    
    protected char charID;
    protected int cellID;
    protected boolean walkable;
    
    

    // pathfinding:
    protected AbstractNode previous;
    protected boolean diagonally;
    protected int movementPanelty;
    protected int gCosts;
    protected int hCosts;
    protected static final int BASICMOVEMENTCOST = 10;
    protected static final int DIAGONALMOVEMENTCOST = 14;

    
    
    
    
    
    public AbstractNode(int cellID, char charID, boolean walkable, int movementPenalty) {
        this.setCellID(cellID);
        this.setCharID(charID);
        this.setWalkable(walkable);
        this.setMovementPanelty(movementPanelty);
    }

    
    public boolean isDiagonaly() {
        return diagonally;
    }

    
    
    public void setDiagonaly(boolean isDiagonaly) {
        this.diagonally = isDiagonaly;
    }

    public char getCharID() {
        return charID;
    }

    public int getCellID() {
        return cellID;
    }

    
    
    public void setCharID(char charID) {
        this.charID = charID;
    }

    
    
    public void setCellID(int cellID) {
        this.cellID =cellID;
    }

    
    public int getxPosition() {
        return this.cellID / WIDTH;
    }

    
    public int getyPosition() {
        return this.cellID % WIDTH;
    }

    
    public boolean isWalkable() {
        return walkable;
    }

    
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    
    public AbstractNode getPrevious() {
        return previous;
    }
    
    
    public void setPrevious(AbstractNode previous) {
        this.previous = previous;
    }

    
    public void setMovementPanelty(int movementPanelty) {
        this.movementPanelty = movementPanelty;
    }

    
    public int getfCosts() {
        return gCosts + hCosts;
    }

    
    public int getgCosts() {
        return gCosts;
    }

    
    private void setgCosts(int gCosts) {
        this.gCosts = gCosts + movementPanelty;
    }

    
    public void setgCosts(AbstractNode previousAbstractNode, int basicCost) {
        setgCosts(previousAbstractNode.getgCosts() + basicCost);
    }

    
    public void setgCosts(AbstractNode previousAbstractNode) {
        if (diagonally) {
            setgCosts(previousAbstractNode, DIAGONALMOVEMENTCOST);
        } else {
            setgCosts(previousAbstractNode, BASICMOVEMENTCOST);
        }
    }
    
    
    
    
    
    
    public int calculategCosts(AbstractNode previousAbstractNode) {
        if (diagonally) {
            return (previousAbstractNode.getgCosts()
                    + DIAGONALMOVEMENTCOST + movementPanelty);
        } else {
            return (previousAbstractNode.getgCosts()
                    + BASICMOVEMENTCOST + movementPanelty);
        }
    }

    
    
    
    
    
    public int calculategCosts(AbstractNode previousAbstractNode, int movementCost) {
        return (previousAbstractNode.getgCosts() + movementCost + movementPanelty);
    }

    
    
    
    
    
    
    
    public int gethCosts() {
        return hCosts;
    }

    
    
    
    
    
    
    
    protected void sethCosts(int hCosts) {
        this.hCosts = hCosts;
    }

    
    
    
    
    
    
    
    public abstract void sethCosts(AbstractNode endAbstractNode);


    
    
    
    
    
    
    
    private int getMovementPanelty() {
        return movementPanelty;
    }

    
    
    
    
    
    
    
    @Override
    public String toString() {
        return "(" + cellID + ", " + getxPosition() + ", " + getyPosition() + "): h: "
                + gethCosts() + " g: " + getgCosts() + " f: " + getfCosts();
    }

    
    
    
    
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractNode other = (AbstractNode) obj;
        if (this.getxPosition() != other.getxPosition()) {
            return false;
        }
        if (this.getyPosition() != other.getyPosition()) {
            return false;
        }
        return true;
    }

    
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.getxPosition();
        hash = 17 * hash + this.getxPosition();
        return hash;
    }

}
