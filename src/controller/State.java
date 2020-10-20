package controller;

import java.util.*;

/**
 * 
 */
public interface State {

    /**
     * 
     */
    public void addIntersection();

    /**
     * 
     */
    public void addSegment();

    /**
     * 
     */
    public void delete();

    /**
     * 
     */
    public void move();

    /**
     * 
     */
    public void undo();

    /**
     * 
     */
    public void redo();

    /**
     * 
     */
    public void save();

    /**
     * 
     */
    public void load();

    /**
     * 
     */
    public void mouseMoved();

    /**
     * 
     */
    public void keystroke();

    /**
     * 
     */
    public void rightClick();

    /**
     * 
     */
    public void leftClick();

}