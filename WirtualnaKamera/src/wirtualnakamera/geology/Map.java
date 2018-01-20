/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.awt.Color;
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
    private Point center;
    private List<Point> lightSources;

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
            List<Element> objects = mapElement.getChildren();
            Point p;
            List<Point> pointList;
            List<Element> vertices;
            Color color;

            //for (Element o : objects) {
                pointList = new ArrayList<>();
                /*switch (o.getName()) {
                    case "light":
                        lightSources.add(new Point(Integer.parseInt(o.getAttributeValue("x")),
                                Integer.parseInt(o.getAttributeValue("y")),
                                Integer.parseInt(o.getAttributeValue("z"))));
                        break;
                    case "sphere":*/
                        double r = 20.0;            /*Double.parseDouble(o.getAttributeValue("radius"));*/
                        double latLines = 80;       //Integer.parseInt(o.getAttributeValue("latLines"));
                        double longLines = 160;     //Integer.parseInt(o.getAttributeValue("longLines"));
                        double x = 300;             //Double.parseDouble(o.getAttributeValue("x"));
                        double y = 0;               //Double.parseDouble(o.getAttributeValue("y"));
                        double z = 0;               //Double.parseDouble(o.getAttributeValue("z"));
                        center = new Point(x, y, z);
                        color = new Color(35, 140, 35);/*(o.getAttributeValue("color") == null) ? Color.MAGENTA : new Color(
                                Integer.valueOf(o.getAttributeValue("color").substring(0, 2), 16),
                                Integer.valueOf(o.getAttributeValue("color").substring(2, 4), 16),
                                Integer.valueOf(o.getAttributeValue("color").substring(4, 6), 16));*/
                        Point nPole = new Point(x, y + r, z, center);
                        Point sPole = new Point(x, y - r, z, center);
                        List<List<Point>> spherePoints = new ArrayList<>();
                        for (int i = 0; i < latLines; i++) {
                            spherePoints.add(new ArrayList<>());
                        }   for (double i = 0; i < latLines; i++) {
                            double theta = (((i+1) / (latLines+1)) - 0.5) * Math.PI;
                            double pY = y + r * Math.sin(theta);
                            for (double j = 0; j < longLines; j++) {
                                double phi = (j / longLines) * 2 * Math.PI;
                                double pX = x + r * Math.cos(theta) * Math.cos(phi);
                                double pZ = z + r * Math.cos(theta) * Math.sin(phi);
                                spherePoints.get((int)i).add(new Point(pX, pY, pZ, center));
                            }
                        }    for (int i = 0; i < longLines; i++) {
                            pointList.add(sPole);
                            pointList.add(spherePoints.get(0).get(i + 1 >= longLines ? 0 : i + 1));
                            pointList.add(spherePoints.get(0).get(i));
                            //pointList.add(spherePoints.get(0).get(i + 1 >= longLines ? 0 : i + 1));
                            walls.add(new Polygon(pointList, color));
                            pointList.forEach((Point point) -> {
                                if (!points.contains(point)) {
                                    points.add(point);
                                }
                            });
                            pointList = new ArrayList<>();
                        }   for (int i = 0; i < spherePoints.size() - 1; i++) {
                            for (int j = 0; j < longLines; j++) {
                                pointList.add(spherePoints.get(i).get(j));
                                pointList.add(spherePoints.get(i).get(j + 1 >= longLines ? 0 : j + 1));
                                pointList.add(spherePoints.get(i + 1).get(j + 1 >= longLines ? 0 : j + 1));
                                pointList.add(spherePoints.get(i + 1).get(j));
                                //pointList.add(spherePoints.get(i + 1).get(j + 1 >= longLines ? 0 : j + 1));
                               // pointList.add(spherePoints.get(i).get(j + 1 >= longLines ? 0 : j + 1));
                                
                                walls.add(new Polygon(pointList, color));
                                pointList.forEach((Point point) -> {
                                    if (!points.contains(point)) {
                                        points.add(point);
                                    }
                                });
                                pointList = new ArrayList<>();
                            }
                        }   for (int i = 0; i < longLines; i++) {
                            pointList.add(nPole);
                            pointList.add(spherePoints.get(spherePoints.size() - 1).get(i));
                            pointList.add(spherePoints.get(spherePoints.size() - 1).get(i + 1 >= longLines ? 0 : i + 1));
                            walls.add(new Polygon(pointList, color));
                            pointList.forEach((Point point) -> {
                                if (!points.contains(point)) {
                                    points.add(point);
                                }
                            });
                            pointList = new ArrayList<>();
                        }//   break;
/*                    default:
                        vertices = o.getChildren();
                        for (Element vert : vertices) {
                            p = new Point(Integer.parseInt(vert.getAttributeValue("x")),
                                    Integer.parseInt(vert.getAttributeValue("y")),
                                    Integer.parseInt(vert.getAttributeValue("z")));
                            
                            if (!pointList.contains(p)) {
                                if (points.contains(p)) {
                                    pointList.add(points.get(points.indexOf(p)));
                                } else {
                                    pointList.add(new Point(Integer.parseInt(vert.getAttributeValue("x")),
                                            Integer.parseInt(vert.getAttributeValue("y")),
                                            Integer.parseInt(vert.getAttributeValue("z"))));
                                }
                            }
                        }   color = (o.getAttributeValue("color") == null) ? Color.MAGENTA : new Color(
                                Integer.valueOf(o.getAttributeValue("color").substring(0, 2), 16),
                                Integer.valueOf(o.getAttributeValue("color").substring(2, 4), 16),
                                Integer.valueOf(o.getAttributeValue("color").substring(4, 6), 16));
                        walls.add(new Polygon(pointList, color));
                        pointList.forEach((Point point) -> {
                            if (!points.contains(point)) {
                                points.add(point);
                            }
                        }); break;
                }*/
            //}

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        
        lightSources = new ArrayList<>();
        lightSources.add(new Point(50, 900, -900));
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

    public void transformByMatrix(double[][] d, boolean isRotation) {
        if (points != null) points.forEach((Point p) -> {
            p.transformByMatrix(d, isRotation, center);
        });
        
        if (lightSources != null) lightSources.forEach((Point p) -> {
            p.transformByMatrix(d, false, null);
        });
    }
    
    public List<Point> getLightSources() {
        return lightSources;
    }
    
}
