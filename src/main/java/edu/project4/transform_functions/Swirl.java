package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Swirl implements Transform {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();

        double r = x * x + y * y;

        return new Point(
            x * Math.sin(r) - y * Math.cos(r),
            x * Math.cos(r) + y * Math.sin(r)
        );
    }
}
