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
public class Vector3D {
    private double x;
    private double y;
    private double z;
    private double len;
    private boolean normalized;
    
    public Vector3D(Point from, Point to) {
        x = to.getX() - from.getX();
        y = to.getY() - from.getY();
        z = to.getZ() - from.getZ();
        
        len = computeLength();
        normalized = false;
    }
    
    public Vector3D(double [] coords) {
        x = coords[0];
        y = coords[1];
        z = coords[2];
        
        len = computeLength();
    }
    
    private double computeLength() {
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    public void normalize() {
        if (normalized) return;
        x /= len;
        y /= len;
        z /= len;
    }
    
    public void denormalize() {
        if (!normalized) return;
        x *= len;
        y *= len;
        z *= len;
    }
    
    public double dotProduct(Vector3D v) {
        denormalize();
        return x*v.x + y*v.y + z*v.z;
    }
    
    public Vector3D crossProduct(Vector3D v) {
        if (normalized && !v.normalized) v.normalize();
        else if (!normalized && v.normalized) v.denormalize();
        
        double[] products = new double[3];
        
        
        
        return new Vector3D(products);
    }
    
    public Vector3D multiplyByScalar(double d) {
        double nx = x * d;
        double ny = y * d;
        double nz = z * d;
        return new Vector3D(new double[] {nx, ny, nz});
    }
    
    public void subtractVector(Vector3D v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        normalized = false;
    }
}
