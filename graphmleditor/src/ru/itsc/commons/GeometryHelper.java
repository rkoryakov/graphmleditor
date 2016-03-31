/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.commons;

import javafx.geometry.Point2D;

/**
 *
 * @author macbookmacbook
 */
public class GeometryHelper {
    private static final double DELTA = 10e-3;
    
    public static boolean equal(double a, double b) {
        return Math.abs(a - b) <= DELTA;
    }
    
    public static boolean equal(double a, double b, double delta) {
        return Math.abs(a - b) <= delta;
    }
    
    public static boolean hasLinesegmentPoint(Point2D startLine, Point2D endLine, Point2D point) {
        double lineLen = endLine.distance(startLine);
        double dist = point.distance(startLine) + point.distance(endLine);
        return equal(lineLen, dist, 1.0);
    }
}
