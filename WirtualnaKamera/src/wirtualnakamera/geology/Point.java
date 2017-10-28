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
public class Point implements Comparable<Point> {
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public char compareDims(Point p1, Point p2) {
        if (this.x == p1.x && this.x == p2.x) return 'x';
        if (this.y == p1.y && this.y == p2.y) return 'y';
        if (this.z == p1.z && this.z == p2.z) return 'z';
        return 'N';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    
    public boolean isDrawable(double focal) {
        //TODO: opisać
        
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        return equals ((Point) o);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    
    public boolean equals(Point p) {
        return this.x == p.x &&
               this.y == p.y &&
               this.z == p.z;
    }

    @Override
    public int compareTo(Point p) {
        if (this.x > p.x) return 1;
        if (this.x == p.x) {
            if (this.y > p.y) return 1;
            if (this.y == p.y) {
                if (this.z > p.z) return 1;
                if (this.z == p.z) return 0;
                return -1;
            }
            return -1;
        }
        return -1;
    }

    void transformByMatrix(double[][] d) {
        double xNew, yNew, zNew;
        
        xNew = d[0][0]*x + d[0][1]*y + d[0][2]*z + d[0][3];
        yNew = d[1][0]*x + d[1][1]*y + d[1][2]*z + d[1][3];
        zNew = d[2][0]*x + d[2][1]*y + d[2][2]*z + d[2][3];
        if (d[3][0] + d[3][1] + d[3][2] + d[3][3] != 1) throw new IllegalArgumentException("Podano błędną macierz dla wsp. jednorodnych.");
    
        x = xNew;
        y = yNew;
        z = zNew;
    }
}
