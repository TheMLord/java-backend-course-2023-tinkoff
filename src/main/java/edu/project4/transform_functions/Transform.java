package edu.project4.transform_functions;

import edu.project4.models.Point;
import java.util.function.Function;

/**
 * Контракт нелинейных преобразований.
 */
public interface Transform extends Function<Point, Point> {
}
