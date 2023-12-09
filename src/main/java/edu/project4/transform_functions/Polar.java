package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Polar implements Transform {
    @Override
    public Point apply(Point point) {
        return new Point(
            Math.atan2(point.y(), point.x()) / Math.PI,
            Math.sqrt(point.x() * point.x() + point.y() * point.y()) - 1.0
        );
    }
}
