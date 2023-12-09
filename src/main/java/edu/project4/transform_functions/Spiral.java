package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Spiral implements Transform {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();

        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);

        return new Point(
            (1.0 / r) * (Math.cos(theta) + Math.sin(r)),
            (1.0 / r) * (Math.sin(theta) - Math.cos(r))
        );
    }
}
