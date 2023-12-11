package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Spherical implements Transform {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();

        double r = 1.0 / (x * x + y * y);

        return new Point(x * r, y * r);
    }
}
