/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;



/**
 *
 * @author zouhairhajji
 */
public enum Direction {
    
    NORTH("A"),
    NORTH_EST("B"),
    EST("C"),
    EST_SOUTH("D"),
    SOUTH("E"),
    SOUTH_OUEST(""),
    OUEST("G"),
    OUEST_NORTH("H");
    
    private String code;

    private Direction(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
}
