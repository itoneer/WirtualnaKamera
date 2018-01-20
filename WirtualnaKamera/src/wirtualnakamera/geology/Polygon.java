/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author itoneer
 */
public class Polygon {
    private List<Point> vertices;
    private Point centroid;
    private Color color;
    
    public Polygon(List<Point> vertices, Color color) {
        if (vertices.size() < 3) throw new IllegalArgumentException("Za mało punktów: " + vertices.size());
        
        double value;
        char axis = vertices.get(0).compareDims(vertices.get(1), vertices.get(2));
        switch(axis) {
            case 'x':
                value = vertices.get(0).getX();
                break;
            case 'y':
                value = vertices.get(0).getY();
                break;
            case 'z':
                value = vertices.get(0).getZ();
                break;
            /*default:
                throw new IllegalArgumentException("Program nie obsługuje"
                + "wczytywania wielokątów na płaszczyznach niebędących prostopadłymi"
                + "do żadnej z osi układu współrzędnych.");*/
        }
        
        this.vertices = vertices;
        this.color = color;
    }
    
    public Polygon(Color color, Point... vertices) {
       this(Arrays.asList(vertices), color);
    }
    
    public List<Point> getVertices() {
        return vertices;
    }
    
    private double getPointX(int i) {
        return vertices.get(i).getX();
    }
    
    private double getPointY(int i) {
        return vertices.get(i).getY();
    }
    
    private double getPointZ(int i) {
        return vertices.get(i).getZ();
    }
    
    public Color getColor() {
        return color;
    }
    
    public Vector3D getNormalVector() {
        double [] normal = new double[3];
        double [] a = getVector(vertices.get(0), vertices.get(1));
        double [] b = getVector(vertices.get(1), vertices.get(2));
        
        normal[0] = - a[1]*b[2] + a[2]*b[1];
        normal[1] = - a[2]*b[0] + a[0]*b[2];
        normal[2] = - a[0]*b[1] + a[1]*b[0];
        
        return new Vector3D(normal);
    }
    
    public boolean isDrawable(double focal) {
        Point c = getCentroid();
        Vector3D fromObserver = new Vector3D(new Point(0,0,0), c);
        
        return !(getNormalVector().dotProduct(fromObserver) >=0 ||
                !vertices.stream().noneMatch((p) -> (!p.isDrawable(focal))));
    }

    private double[] getVector(Point p1, Point p2) {
        double[] vec = new double[3];
        
        vec[0] = p2.getX() - p1.getX();
        vec[1] = p2.getY() - p1.getY();
        vec[2] = p2.getZ() - p1.getZ();
                
        return vec;
    }
    
    public Point getCentroid() {
        if (vertices.size() > 4) throw new IllegalStateException("Znajdowanie środka ciężkośći jest dostępne tylko dla trójkątów i czworokątów.");
        
        double centrX, centrY, centrZ;
        
        if (vertices.size() == 3) {
            centrX = (getPointX(0) + getPointX(1) + getPointX(2))/3;
            centrY = (getPointY(0) + getPointY(1) + getPointY(2))/3;
            centrZ = (getPointZ(0) + getPointZ(1) + getPointZ(2))/3;
        }
        
        else {
            centrX = (getPointX(0) + getPointX(1) + getPointX(2) + getPointX(3))/4;
            centrY = (getPointY(0) + getPointY(1) + getPointY(2) + getPointY(3))/4;
            centrZ = (getPointZ(0) + getPointZ(1) + getPointZ(2) + getPointZ(3))/4;
        }
        return (centroid = new Point(centrX, centrY, centrZ));
    }

    public List<Polygon> breakdown() {
        if (vertices.size() == 3) return breakdownTriangle(1);
        else{
            List<Polygon> pieces = new ArrayList();
            for (int i = 2; i < vertices.size(); i++) {
                pieces.add(new Polygon(color, vertices.get(0), vertices.get(i-1), vertices.get(i)));
            }
            List<Polygon> result = new ArrayList();
            pieces.forEach((p) -> {
                result.addAll(p.breakdownTriangle(0));
            });
            return result;
        }
    }
    
    public List<Polygon> divide() {
        List<Polygon> pieces = new ArrayList();
            for (int i = 2; i < vertices.size(); i++) {
                pieces.add(new Polygon(color, vertices.get(0), vertices.get(i-1), vertices.get(i)));
            }
            List<Polygon> result = new ArrayList();
            pieces.forEach((p) -> {
                result.addAll(p.breakdownTriangle(0));
            });
            return result;
    }

    private List<Polygon> breakdownTriangle(int i) {
        if (vertices.size() > 3) throw new IllegalStateException("Ta metoda działa tylko dla trójkątów.");
        
        List<Polygon> pieces = new ArrayList();
        
        Point mid1 = Point.getMidPoint(vertices.get(0), vertices.get(1));
        Point mid2 = Point.getMidPoint(vertices.get(1), vertices.get(2));
        Point mid3 = Point.getMidPoint(vertices.get(2), vertices.get(0));
        
        pieces.add(new Polygon(color, vertices.get(0), mid1, mid3));
        pieces.add(new Polygon(color, vertices.get(1), mid2, mid1));
        pieces.add(new Polygon(color, vertices.get(2), mid3, mid2));
        pieces.add(new Polygon(color, mid3, mid1, mid2));
        
        return pieces;
    }
    
    public java.awt.Polygon toAwt() {
        List<java.awt.Point> points = new ArrayList();
        vertices.forEach((v) -> points.add(v.toPoint2D()));
        
        int [] px = new int[points.size()];
        int [] py = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            px[i] = points.get(i).x;
            py[i] = points.get(i).y;
        }
        
        return new java.awt.Polygon(px, py, points.size());
    }

    
}
