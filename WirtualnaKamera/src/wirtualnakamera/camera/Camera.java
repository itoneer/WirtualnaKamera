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
    private CameraPanel panel;
    
    private static final double TRANSL_STEP = 5.0;
    private static final double ROTAT_STEP = 5.0 * Math.PI/180.0;
    private static final double ZOOM_STEP = 5.0;
    
    
    
    /**
     * Tworzy nową kamerę w początku układu współrżędnych zwróconą w kierunku rosnących wartości X.
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
        panel.redraw(map);
    }
    
    /**
     * Przesuwa kamerę do góry.
     */
    public void moveUp() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, -TRANSL_STEP},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Przesuwa kamerę w prawo.
     */
    public void moveRight(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, -TRANSL_STEP},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Przesuwa kamerę w lewo.
     */
    public void moveLeft() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, TRANSL_STEP},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Przesuwa kamerę do tyłu.
     */
    public void moveBackward() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, TRANSL_STEP}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Przesuwa kamerę do przodu.
     */
    public void moveForward() {
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, -TRANSL_STEP}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Obraca kamerę w prawo.
     */
    public void rotateRight() {
        map.transformByMatrix( new double[][] {{Math.cos(ROTAT_STEP), 0.0, Math.sin(ROTAT_STEP), 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {-Math.sin(ROTAT_STEP), 0.0, Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Obraca kamerę w lewo.
     */
    public void rotateLeft() {
        map.transformByMatrix( new double[][] {{-Math.cos(ROTAT_STEP), 0.0, -Math.sin(ROTAT_STEP), 0.0}, 
                                               {0.0, 1.0, 0.0, 0.0},
                                               {Math.sin(ROTAT_STEP), 0.0, -Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Obraca kamerę w tył.
     */
    public void rotateBackward() {
        map.transformByMatrix( new double[][] {{-Math.cos(ROTAT_STEP), Math.sin(ROTAT_STEP), 0.0, 0.0}, 
                                               {-Math.sin(ROTAT_STEP), -Math.cos(ROTAT_STEP), 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Obraca kamerę w przód.
     */
    public void rotateForward() {
        map.transformByMatrix( new double[][] {{Math.cos(ROTAT_STEP), -Math.sin(ROTAT_STEP), 0.0, 0.0}, 
                                               {Math.sin(ROTAT_STEP), Math.cos(ROTAT_STEP), 0.0, 0.0},
                                               {0.0, 0.0, 1.0, 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
 
    
    /**
     * Obraca kamerę w prawo względem osi prostopadłej do rzutni.
     */ 
    public void rollRight(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, -Math.cos(ROTAT_STEP), Math.sin(ROTAT_STEP), 0.0},
                                               {0.0, -Math.sin(ROTAT_STEP), -Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }
    
    /**
     * Obraca kamerę w lewo względem osi prostopadłej do rzutni.
     */ 
    public void rollLeft(){
        map.transformByMatrix( new double[][] {{1.0, 0.0, 0.0, 0.0}, 
                                               {0.0, Math.cos(ROTAT_STEP), -Math.sin(ROTAT_STEP), 0.0},
                                               {0.0, Math.sin(ROTAT_STEP), Math.cos(ROTAT_STEP), 0.0},
                                               {0.0, 0.0, 0.0, 1.0}});
        panel.redraw(map);
    }

    
    /**
     * Zwiększa ogniskową kamery.
     */
    public void zoomIn() {
        
        
        
        
        panel.redraw(map);
    }
    
    /**
     * Zmniejsza ogniskową kamery.
     */
    public void zoomOut() {
        
        
        
        
        panel.redraw(map);
    }
}
