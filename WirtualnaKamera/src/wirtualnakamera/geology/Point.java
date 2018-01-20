/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.geology;


import wirtualnakamera.camera.Camera;
import wirtualnakamera.camera.CameraPanel;

/**
 *
 * @author itoneer
 */
public class Point implements Comparable<Point> {

    static Point getMidPoint(Point p1, Point p2) {
        double midX = (p2.x - p1.x)/2 + p1.x;
        double midY = (p2.y - p1.y)/2 + p1.y;
        double midZ = (p2.z - p1.z)/2 + p1.z;
        
        return new Point(midX, midY, midZ);
    }
    
    private double x;
    private double y;
    private double z;
    private double[] normalVec;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        normalVec = null;
    }
    
    public Point(double x, double y, double z, Point ref) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        normalVec = new double[3];
        normalVec[0] = this.x - ref.x;
        normalVec[1] = this.y - ref.y;
        normalVec[2] = this.z - ref.z;
        
        normalizeVector();
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
        return x > 0;
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

    void transformByMatrix(double[][] d, boolean isRotation, Point center) {
        double xNew, yNew, zNew;
        
        xNew = d[0][0]*x + d[0][1]*y + d[0][2]*z + d[0][3];
        yNew = d[1][0]*x + d[1][1]*y + d[1][2]*z + d[1][3];
        zNew = d[2][0]*x + d[2][1]*y + d[2][2]*z + d[2][3];
        double normal = d[3][0] + d[3][1] + d[3][2] + d[3][3];
    
        x = xNew / normal;
        y = yNew / normal;
        z = zNew / normal;
        
        if (isRotation) {
            recomputeVector(center);
        }
    }
    
    public java.awt.Point toPoint2D() {
        double focal = Camera.getCamera().getFocal();
        int panelHeight = CameraPanel.getPanel().getHeight();
        int panelWidth = CameraPanel.getPanel().getWidth();
        int px, py;
        
        if (x <= 0) throw new IllegalStateException();
        
        px = (int) ((focal / x) * z  + panelWidth / 2) ;
        py = (int) ((panelHeight / 2 - (focal / x) * y));
        
        return new java.awt.Point(px, py);
    }
    
    private void normalizeVector() {
        double normalLen = Math.sqrt(normalVec[0]*normalVec[0] + normalVec[1]*normalVec[1] + normalVec[2]*normalVec[2]);
        
        normalVec[0] /= normalLen;
        normalVec[1] /= normalLen;
        normalVec[2] /= normalLen;
    }
    
    private void recomputeVector(Point c) {
        normalVec = new double[3];
        normalVec[0] = this.x - c.x;
        normalVec[1] = this.y - c.y;
        normalVec[2] = this.z - c.z;
        
        normalizeVector();
    }
}
