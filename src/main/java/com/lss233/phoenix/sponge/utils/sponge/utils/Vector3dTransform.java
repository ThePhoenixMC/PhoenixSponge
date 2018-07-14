package com.lss233.phoenix.sponge.utils.sponge.utils;

import com.flowpowered.math.vector.Vector3d;
import com.lss233.phoenix.math.Vector;

public interface Vector3dTransform {
    default Vector toPhoenix(Vector3d vector3d){
        return new Vector(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }
    default Vector3d toSponge(Vector vector) {
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
    }
}
