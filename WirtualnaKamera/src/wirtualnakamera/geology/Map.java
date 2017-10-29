/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        points = new ArrayList<>();
        
        loadMap(in);
    }
    
    private final void loadMap(FileInputStream in) {
        //TODO: parsowanie; punkty dodawane i do 
    }
    
    public final void reloadMap() {
        FileInputStream in;
        try {
            in = new FileInputStream("map.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
            return;
        }
        
        loadMap(in);
    }

    public List<Polygon> getWalls() {
        return walls;
    }

    public List<Point> getPoints() {
        return points;
    }
    
    public void transformByMatrix(double[][] d) {
        points.forEach((Point p) -> {
            p.transformByMatrix(d);
        });
    }
}
