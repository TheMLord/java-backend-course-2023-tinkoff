package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Horseshoe implements Transform {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();

        double r = 1.0 / Math.sqrt(x * x + y * y);

        return new Point(
            r * (x - y) * (x + y),
            r * 2.0 * x * y
        );
    }
}
