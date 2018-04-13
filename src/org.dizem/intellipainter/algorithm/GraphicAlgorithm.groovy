package org.dizem.intellipainter.algorithm

/**
 * Created by IntelliJ IDEA.
 * User: dizem
 * Date: 2010-10-19
 * Time: 21:23:04
 */
enum GraphicAlgorithm {
    DDA_LINE,
    MID_POINT_LINE,
    BRESENHAM_LINE,
    BRESENHAM_CIRCLE,
    MID_POINT_CIRCLE,
    MPNC_CILRCLE,
    DDA_CIRCLE,

    SCAN_LINE_FILL,
    SEED_FILL,

    CS_CLIP,
    SH_CLIP,
    MID_POINT_CLIP,

    GRAPHIC_MOVING,
    GRAPHIC_ZOOMING,
    GRAPHIC_ROTATING,

    BEZIER
}
