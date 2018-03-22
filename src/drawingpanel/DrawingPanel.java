/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawingpanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements View {

    private HashMap<Integer, DrawableI> drawings;
    private Integer indexSelect;
    private int counter = 0;
    public static final String PROP_PUT = "put";
    public static final String PROP_REMOVE = "remove";
    private int rotation;
    private boolean betterGraphics;

    /* Constructor used to create a CanvasView with a
     * specified line colour, thickness and shape type
     */
    public DrawingPanel() {
        this.setBackground(Color.WHITE);
        this.drawings = new HashMap<>();
    }

    /*
     * paint the drawing including all shapes drawn so far
     *
     * paintComponent() is invoked when repaint() is called and
     * when the GUI needs redrawing e.g. because it has been covered
     * by another window
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Graphics2D needed to set line thickness
        Graphics2D g2d = (Graphics2D) g;
        setBackground(getBackground());
        if (getbetterGraphics()) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g2d.getStroke();

        // rotate the drawing by the current rotation amount
        double rotateTheta;
        rotateTheta = getRotation() * Math.PI / 180;
        g2d.rotate(rotateTheta, this.getWidth() / 2, this.getHeight() / 2);

        // Loop though the ArrayList drawing
        // all the shapes stored in it
        for (DrawableI aShape : getDrawings().values()) {
            DrawableI ld = (DrawableI) aShape;
            ld.draw(g2d);

        }

    }

    /**
     * @return drawingsList to be drawn
     */
    public HashMap<Integer, DrawableI> getDrawings() {
        return this.drawings;
    }

    /**
     * @param entity to add to currentList
     */
    public void addDrawing(DrawableI entity) {
        if (getDrawings() == null) {
            this.drawings = new HashMap<>();
        }
        Object old = getDrawings().put(counter, entity);

        firePropertyChange(PROP_PUT, old, entity);
        counter++;
        refresh();
    }

    public void setDrawing(int key, DrawableI entity) {
        if (getDrawings() == null) {
            this.drawings = new HashMap<>();
        }
        Object old = getDrawings().put(key, entity);
        firePropertyChange(PROP_PUT, old, entity);
        counter++;
        refresh();
    }

    public int getSelectedDrawingIndex() {
        return this.indexSelect;

    }

    /**
     * @return the entity that it is currently selected
     */
    public DrawableI getSelectedDrawing() {
        return this.drawings.get(this.indexSelect);
    }

    public void removeSelectedDrawing() {
        Object old = this.getDrawings().remove(getSelectedDrawingIndex());
        firePropertyChange(PROP_PUT, old, null);
        refresh();
    }

    public void resetDrawingList() {
        getDrawings().clear();
        refresh();
    }

    public void setDrawingSelected(Integer key) {
        this.indexSelect = key;
        refresh();
    }

    /* The whole drawing area can be rotated left or right.
     * The amount passed in is the amount in degrees to rotate.
     * A negative number roates to the left and a positive number to
     * the right
     */
    public void setRotation(int amount) {
        rotation += amount;
        refresh();
    }

    public int getRotation() {
        return this.rotation;
    }

    public boolean getbetterGraphics() {
        return this.betterGraphics;
    }

    public void setbetterGraphics(boolean b) {
        this.betterGraphics = b;
        refresh();
    }

    @Override
    public void refresh() {
        repaint();
    }

}
