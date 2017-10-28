/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author itoneer
 */
public class Map {
    private List<Polygon> walls;
    private List<Point> points;
    

    /**
     * Tworzy nową mapę.
     * 
     * @param in plik zawierający definicję mapy.
     */
    public Map(FileInputStream in) {
        walls = new ArrayList<>();
        
        reloadMap(in);
    }
    
    
    public final void reloadMap(FileInputStream in) {
        //TODO: parsowanie
    }

    
    public void transformByMatrix(double[][] d) {
        points.forEach((Point p) -> {
            p.transformByMatrix(d);
        });
    }
}
