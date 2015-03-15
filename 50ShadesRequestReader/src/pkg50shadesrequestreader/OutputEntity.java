/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg50shadesrequestreader;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author martin
 */
public class OutputEntity {
    
    private String label;
    private List<Point> points;
    
    public OutputEntity(String label, List<Point> points) {
        this.label = label;
        this.points = points;
    }
    
    public void addPoint(Point point) {
        this.points.add(point);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != String.class) return false;
        return obj.toString().equals(label);
    }
    
    
    
    
    
}
