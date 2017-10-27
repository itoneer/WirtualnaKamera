/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author itoneer
 */
public class Map {
    private List<Polygon> walls;
    
    public Map(FileInputStream in) {
        walls = new ArrayList<>();
        
        reloadMap(in);
    }
    
    public final void reloadMap(FileInputStream in) {
        //TODO: parsowanie
    }
}
