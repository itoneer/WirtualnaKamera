/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

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
    public Map(File in) {
        walls = new ArrayList<>();
        points = new ArrayList<>();
        
        loadMap(in);
    }
    
    private void loadMap(File in) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(in);
            Element mapElement = document.getRootElement();
            List<Element> polygons = mapElement.getChildren();
            Point p;
            List<Point> pointList;
            List<Element> vertices;
            
            for (Element poly: polygons) {
                pointList = new ArrayList<>();
                vertices = poly.getChildren();
                for (Element vert: vertices) {
                    p = new Point(Integer.parseInt(vert.getAttributeValue("x")),
                                  Integer.parseInt(vert.getAttributeValue("y")),
                                  Integer.parseInt(vert.getAttributeValue("z")));
                    
                    if (!pointList.contains(p)) {
                        if (points.contains(p)) pointList.add(points.get(points.indexOf(p)));
                        else pointList.add(new Point(Integer.parseInt(vert.getAttributeValue("x")),
                                  Integer.parseInt(vert.getAttributeValue("y")),
                                  Integer.parseInt(vert.getAttributeValue("z"))));
                    }
                }
                
                walls.add(new Polygon(pointList));
                pointList.forEach((Point point) -> {
                    if (!points.contains(point)) points.add(point);
                });
            }
            
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public final void reloadMap() {
        walls = new ArrayList<>();
        points = new ArrayList<>();
        File in;
        in = new File("map.xml");
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
