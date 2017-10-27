/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;

/**
 *
 * @author itoneer
 */
public class Point {
    private int x;
    private int y;
    private int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public char compareDims(Point p1, Point p2) {
        if (this.x == p1.x && this.x == p2.x) return 'x';
        else if (this.y == p1.y && this.y == p2.y) return 'y';
        else if (this.z == p1.z && this.z == p2.z) return 'z';
        else return 'N';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
    
    
    
    public boolean isDrawable(int focal) {
        //TODO: opisać
        
        return true;
    }
}
