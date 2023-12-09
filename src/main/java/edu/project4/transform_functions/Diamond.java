package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Diamond implements Transform {
    @Override
    public Point apply(Point point) {
        var x = point.x();
        var y = point.y();
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);

        return new Point(
            Math.sin(theta) * Math.cos(r),
            Math.cos(theta) * Math.sin(r)
        );
    }
}
