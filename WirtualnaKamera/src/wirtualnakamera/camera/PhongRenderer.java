/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import wirtualnakamera.geology.Point;
import wirtualnakamera.geology.Polygon;
import wirtualnakamera.geology.Vector3D;

/**
 *
 * @author itoneer
 */
public class PhongRenderer {
    
    private double specular;
    private double diffuse;
    private double ambient;
    private int specularWeakenCoeff;
    
    public PhongRenderer() {
        specular = 0.3;
        diffuse = 0.2;
        ambient = 0.1;
        specularWeakenCoeff = 100;
    }
    
    public void render(List<Polygon> toRender, Graphics2D g, List<Point> lightSources) {
        toRender.forEach((p) -> {
            drawPolygon(p, g, lightSources);
        });
    }
    
    private void drawPolygon(Polygon p, Graphics2D g, List<Point> lightSources) {
        java.awt.Polygon toRender = p.toAwt();
        
        //bez cieniowania
        
        Vector3D surfaceNormal = p.getNormalVector();
        surfaceNormal.normalize();
        Point mid = p.getCentroid();
        List<Vector3D> lightVectors = new ArrayList<>();
        double len;
        
        for (Point light: lightSources) {
            Vector3D toLight = new Vector3D(mid, light);
            toLight.normalize();
            
            lightVectors.add(toLight);
        }
        
        Vector3D toObserver = new Vector3D(mid, new Point (0,0,0));
        toObserver.normalize();
        
        double illum = calculateIllumination(surfaceNormal, lightVectors, toObserver);
        Color base = p.getColor();
        
        int red = (int) (base.getRed()*ambient + illum);
        int blue = (int) (base.getBlue()*ambient + illum);
        int green =  (int) (base.getGreen()*ambient + illum);
        
        Color toUse = new Color(red > 255 ? 255 : red,
                                green > 255 ? 255 : green,
                                blue > 255 ? 255: blue);
        
        g.setColor(toUse);
        g.fill(toRender);
    }
    
    private double calculateIllumination(Vector3D normal, List<Vector3D> toLightSources, Vector3D toObserver) {
        double illum = 0;
        
        List<Vector3D> reflected = new ArrayList<>();
        toLightSources.forEach((i) -> {
            Vector3D r = normal.multiplyByScalar(normal.dotProduct(i)*2);
            r.subtractVector(i);
            reflected.add(r);
        });
        
        for (int i = 0; i < toLightSources.size(); i++) {
            illum += diffuse * toLightSources.get(i).dotProduct(normal)*255;
            if (reflected.get(i).dotProduct(normal) >= 0)
                illum += specular*Math.pow(reflected.get(i).dotProduct(toObserver), specularWeakenCoeff)*255;
        }
        
        return illum < 0 ? 0 : illum;
    }
    
    
}
