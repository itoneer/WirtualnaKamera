/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

import java.util.List;

/**
 *
 * @author itoneer
 */
public class Polygon {
    private List<Point> vertices;
    
    public Polygon(List<Point> vertices) {
        if (vertices.size() < 3) throw new IllegalArgumentException("Za mało punktów: " + vertices.size());
        
        int value;
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
            default:
                throw new IllegalArgumentException("Program nie obsługuje"
                + "wczytywania wielokątów na płaszczyznach niebędących prostopadłymi"
                + "do żadnej z osi układu współrzędnych.");
        }
    }
    
    public boolean isDrawable(int focal) {
        return vertices.stream().noneMatch((p) -> (!p.isDrawable(focal)));
    }
}
