package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Disk implements Transform {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();

        double r = Math.sqrt(x * x + y * y) * Math.PI;
        double theta = Math.atan2(y, x) / Math.PI;

        return new Point(theta * Math.sin(r), theta * Math.cos(r));
    }
}
