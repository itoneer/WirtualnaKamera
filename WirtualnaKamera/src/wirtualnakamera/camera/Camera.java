/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wirtualnakamera.camera;

import wirtualnakamera.geology.Map;

/**
 *
 * @author itoneer
 */
public class Camera {
    
    private static Camera camera = null;
    
    private double focal;
    private Map map;
    private final CameraPanel panel;
    
    private static final double TRANSL_STEP = 5.0;
    private static final double ROTAT_STEP = 5.0 * Math.PI/180.0;
    private static final double ZOOM_STEP = 5.0;
    
    
    
    /**
     * Tworzy nową kamerę w początku układu współrzędnych zwróconą w kierunku rosnących wartości X.
     */
    public Camera() {
        focal = 20;
        panel = CameraPanel.getPanel();
        
    }
    
    public static Camera getCamera() {
        if (camera == null) camera = new Camera();
        return camera;
    }
    
    /**
     * Przesuwa kamerę w dół.
     */
    public void moveDown(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, TRANSL_STEP},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Przesuwa kamerę do góry.
     */
    public void moveUp() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, -TRANSL_STEP},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Przesuwa kamerę w prawo.
     */
    public void moveRight(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, TRANSL_STEP},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Przesuwa kamerę w lewo.
     */
    public void moveLeft() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, -TRANSL_STEP},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Przesuwa kamerę do tyłu.
     */
    public void moveBackward() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, TRANSL_STEP}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Przesuwa kamerę do przodu.
     */
    public void moveForward() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, -TRANSL_STEP}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Obraca kamerę w prawo.
     */
    public void rotateRight() {
        map.transformByMatrix( new double[][] {{Math.cos(ROTAT_STEP), 0.0, Math.sin(ROTAT_STEP), 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {-Math.sin(ROTAT_STEP), 0.0, Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Obraca kamerę w lewo.
     */
    public void rotateLeft() {
        map.transformByMatrix( new double[][] {{-Math.cos(ROTAT_STEP), 0.0, -Math.sin(ROTAT_STEP), 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {Math.sin(ROTAT_STEP), 0.0, -Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Obraca kamerę w tył.
     */
    public void rotateBackward() {
        map.transformByMatrix( new double[][] {{-Math.cos(ROTAT_STEP), Math.sin(ROTAT_STEP), 0.0, 0.0}, 
                                               {-Math.sin(ROTAT_STEP), -Math.cos(ROTAT_STEP), 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Obraca kamerę w przód.
     */
    public void rotateForward() {
        map.transformByMatrix( new double[][] {{Math.cos(ROTAT_STEP), -Math.sin(ROTAT_STEP), 0.0, 0.0}, 
                                               {Math.sin(ROTAT_STEP), Math.cos(ROTAT_STEP), 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
 
    
    /**
     * Obraca kamerę w prawo względem osi prostopadłej do rzutni.
     */ 
    public void rollRight(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, -Math.cos(ROTAT_STEP), Math.sin(ROTAT_STEP), 0.0},
                                               {0.0, -Math.sin(ROTAT_STEP), -Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }
    
    /**
     * Obraca kamerę w lewo względem osi prostopadłej do rzutni.
     */ 
    public void rollLeft(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, Math.cos(ROTAT_STEP), -Math.sin(ROTAT_STEP), 0.0},
                                               {0.0, Math.sin(ROTAT_STEP), Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map, focal);
    }

    
    /**
     * Zwiększa ogniskową kamery.
     */
    public void zoomIn() {
        focal += ZOOM_STEP;
        panel.redraw(map, focal);
    }
    
    /**
     * Zmniejsza ogniskową kamery.
     */
    public void zoomOut() {
        focal -= ZOOM_STEP;
        panel.redraw(map, focal);
    }
    
    public void reset() {
        focal = 20;
        map.reloadMap();
        panel.redraw(map, focal);
    }

    public double getFocal() {
        return focal;
    }
}
