package com.company;

/**
 *
 * This program represents a polygon shape using points to form its vertices
 * @author Daniel Levin ID 315048587
 * @version 11.12.2020
 *
 *
 */

public class Polygon {

    private Point[] _vertices;
    private int _noOfVertices;
    private final int maxVertices = 10;//value of the max array size

    public static void main(String[] args) {
        // write your code here
    }

    /**
     *creates the polygon array
     */

    public Polygon() {

        _vertices = new Point[maxVertices];

    }

    /**
     * adds a vertex to the polygon
     * @param x x axis value of the point
     * @param y y axis value of the point
     * @return returns true or false if the point was added to the polygon
     */

    public boolean addVertex(double x, double y) {

        if (_noOfVertices != maxVertices) {//allows to add another vertex only if the number of vertices isnt at the max

            _vertices[_noOfVertices] = new Point(x, y);
            _noOfVertices++;//increments the number of vertices that construct the polygon
            return (true);

        }

        return (false);

    }

    /**
     * checks for the highest vertex point in the polygon
     * @return the point object with the highest position
     */
    public Point highestVertex() {

        if (_vertices[0] == null)//checks if an array even exists
            return (null);

        Point highestPoint = new Point(_vertices[0]);//starts with the logic that the first vertex is the highest

        for (int i = 1; i < _noOfVertices; i++) {//stating i at 1 because of the previous code line

            if (_vertices[i].isAbove(highestPoint))//using the isAbove point method to determine if the new vertex is above the current highest point
                highestPoint = new Point(_vertices[i]);

        }

        return (new Point(highestPoint));//return a new point object to avoid aliasing

    }

    /**
     * strings together all the vertices with their point values
     * @return the string of the polygon vertices point values
     */

    public String toString() {

        if (_vertices[0] == null)//checks if there are any vertices
            return ("The polygon has 0 vertices.");


        String message = _vertices[0].toString();//starts the message with the first vertex to be able to use the for loop with the ","

        for (int i = 1; i < _noOfVertices; i++) {

            message += "," + _vertices[i].toString();

        }

        return ("The polygon has " + _noOfVertices + " vertices:\n" + "(" + message + ")");

    }

    /**
     * calculates the polygon perimeter
     * @return the perimeter value of the polygon
     */

    public double calcPerimeter() {

        if (_noOfVertices == 0 || _noOfVertices == 1)//no perimeter if there are less then 2 vertices
            return (0.0);

        double polygonParameter = 0;//variable to store the added parameter value

        for (int i = 1; i < _noOfVertices; i++) {

            polygonParameter += _vertices[i].distance(_vertices[i - 1]);//using the distance method from point to get the parameter

        }

        polygonParameter += _vertices[0].distance(_vertices[_noOfVertices - 1]);//adds the last distance between the last point and start point
        return (polygonParameter);

    }

    //not writing API because its a private method not visible to the user
    private double triangleArea(Point a, Point b, Point c) {//method used to get the triangle area to calculate the polygon area

        double distanceAtoB = a.distance(b);
        double distanceBtoC = b.distance(c);
        double distanceCtoA = c.distance(a);
        //gets the three sides of the triangle using the distance method from point
        double rectangleParameter = distanceAtoB + distanceBtoC + distanceCtoA;
        double s = rectangleParameter / 2;//gets the "s" variable for the equation, that is half the parameter value

        double area = Math.sqrt(s * (s - distanceAtoB) * (s - distanceBtoC) * (s - distanceCtoA));//calculates the area of the triangle

        return (area);

    }

    /**
     * calculates the area of the polygon
     * @return the area value of the polygon
     */

    public double calcArea() {

        if (_noOfVertices < 3)//less then 3 vertices isnt a polygon
            return 0.0;

        double polygonArea = 0;//variable for the added area value

        for (int i = 0; i < (_noOfVertices - 2); i++) {

            polygonArea += triangleArea(_vertices[0], _vertices[i + 1], _vertices[i + 2]);
            //using the triangleArea method above the current method to get the triangles inside the polygon

        }

        return (polygonArea);

    }

    /**
     * checks if one polygon's area is bigger then the other
     * @param other the other polygon you wish to compare to
     * @return true if the polygon is bigger then the "other" polygon
     */

    public boolean isBigger(Polygon other) {

        return (this.calcArea() > other.calcArea());

    }

    /**
     * checks if the point provided is part of the polygon
     * @param p the point you wish to check
     * @return true or false if the point is in the polygon
     */

    public int findVertex(Point p) {

        for (int i = 0; i < _noOfVertices; i++) {

            if (_vertices[i].equals(p))//using the equals method of point to check if point "p" is in the array
                return i;

        }

        return (-1);

    }

    /**
     * gets the next vertex point object
     * @param p the point with which you wish to get the next point
     * @return the next point object
     */

    public Point getNextVertex(Point p) {

        int location = this.findVertex(p);
        //using the findVertex method above the current method to get the location of point "p" in the array

        if (location == _noOfVertices || _noOfVertices == 1)
            //checks if the next point is the first point or if its the only point
            return (new Point(_vertices[0]));
        else if (location != -1)//checks if point "p" is even in the array
            return (new Point(_vertices[location + 1]));
        else
            return null;

    }

    //not writing API because its a private method not visible to the user
    private Point lowestVertex() {//method to find the lowest point

        int lowestLocation = 0;

        for (int i = 1; i < _noOfVertices; i++) {

            if (_vertices[lowestLocation].isAbove(_vertices[i]))
                //if the lowest point is above another points its no longer the lowest point
                lowestLocation = i;

        }

        return (new Point(_vertices[lowestLocation]));//returns a new point object to avoid aliasing

    }

    //not writing API because its a private method not visible to the user
    private Point leftPoint() {//method to find the leftest point

        int mostLeftLocation = 0;

        for (int i = 1; i < _noOfVertices; i++) {

            if (_vertices[mostLeftLocation].isRight(_vertices[i]))
                //checks if the leftest point is to the right of another point, making it no longer the leftest point
                mostLeftLocation = i;

        }

        return (new Point(_vertices[mostLeftLocation]));//return a new point object to avoid aliasing

    }

    //not writing API because its a private method not visible to the user
    private Point rightPoint() {//method to find the rightest point

        int mostRightLocation = 0;

        for (int i = 1; i < _noOfVertices; i++) {

            if (_vertices[mostRightLocation].isLeft(_vertices[i]))
                mostRightLocation = i;
                /*
                using the same logic as the above method, if the rightest point is to the left of another
                its no longer the rightest point

                 */

        }

        return (new Point(_vertices[mostRightLocation]));//return new point object to avoid aliasing

    }

    /**
     * gets the box which forms around the polygon
     * @return the box as an polygon object
     */

    public Polygon getBoundingBox() {

        if (_noOfVertices < 3)//if the are less then 3 points there is no polygon
            return null;

        Polygon BoundingBox = new Polygon();

        BoundingBox.addVertex(this.leftPoint().getX(), this.lowestVertex().getY());
        BoundingBox.addVertex(this.rightPoint().getX(), this.lowestVertex().getY());
        BoundingBox.addVertex(this.rightPoint().getX(), this.highestVertex().getY());
        BoundingBox.addVertex(this.leftPoint().getX(), this.highestVertex().getY());
        /*
        using the private methods to find the leftest rightest highest and lowest point
        to construct the square around the polygon
        and then using those points to get the square tips
        down left, down right, top right, top left
         */

        return (BoundingBox);

    }
}
