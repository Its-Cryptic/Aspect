#version 150

vec2 randomVector2D(vec2 UV, float offset) {
    mat2 m = mat2(15.27, 47.63, 99.41, 89.98);
    UV = fract(sin(UV * m) * 46839.32);
    return vec2(sin(UV.y + offset) * 0.5 + 0.5, cos(UV.x * offset) * 0.5 + 0.5);
}

void CustomVoronoi_float2D(vec2 UV, float AngleOffset, float CellDensity, out float DistFromCenter, out float DistFromEdge) {
    ivec2 cell = ivec2(floor(UV * CellDensity));
    vec2 posInCell = fract(UV * CellDensity);

    DistFromCenter = 8.0;
    vec2 closestOffset;

    for(int y = -1; y <= 1; ++y) {
        for(int x = -1; x <= 1; ++x) {
            ivec2 cellToCheck = ivec2(x, y);
            vec2 cellOffset = vec2(cellToCheck) - posInCell + randomVector2D(vec2(cell + cellToCheck), AngleOffset);

            float distToPoint = dot(cellOffset, cellOffset);

            if(distToPoint < DistFromCenter) {
                DistFromCenter = distToPoint;
                closestOffset = cellOffset;
            }
        }
    }

    DistFromEdge = 8.0;

    for(int y = -1; y <= 1; ++y) {
        for(int x = -1; x <= 1; ++x) {
            ivec2 cellToCheck = ivec2(x, y);
            vec2 cellOffset = vec2(cellToCheck) - posInCell + randomVector2D(vec2(cell + cellToCheck), AngleOffset);

            float distToEdge = dot(0.5 * (closestOffset + cellOffset), normalize(cellOffset - closestOffset));

            DistFromEdge = min(DistFromEdge, distToEdge);
        }
    }
}

vec3 hash_float3_to_float3(vec3 p) {
    // Simple hash function - consider a more sophisticated approach for better randomness
    p = fract(p * 0.8);
    p += dot(p, p + 19.19);
    return fract(vec3(p.x * p.y, p.y * p.z, p.z * p.x));
}

float voronoi_distance_to_edge(vec3 coord, float scale, float randomness) {
    vec3 scaledCoord = coord * scale;
    vec3 cellPosition = floor(coord);
    vec3 localPosition = coord - cellPosition;

    vec3 vectorToClosest = vec3(0.0);
    float minDistance = 1e8; // Use a large number instead of FLT_MAX
    for (int k = -1; k <= 1; k++) {
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                vec3 cellOffset = vec3(i, j, k);
                vec3 vectorToPoint = cellOffset + hash_float3_to_float3(cellPosition + cellOffset) * randomness - localPosition;
                float distanceToPoint = dot(vectorToPoint, vectorToPoint);
                if (distanceToPoint < minDistance) {
                    minDistance = distanceToPoint;
                    vectorToClosest = vectorToPoint;
                }
            }
        }
    }

    minDistance = 1e8;
    for (int k = -1; k <= 1; k++) {
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                vec3 cellOffset = vec3(i, j, k);
                vec3 vectorToPoint = cellOffset + hash_float3_to_float3(cellPosition + cellOffset) * randomness - localPosition;
                vec3 perpendicularToEdge = vectorToPoint - vectorToClosest;
                if (dot(perpendicularToEdge, perpendicularToEdge) > 0.0001) {
                    float distanceToEdge = dot((vectorToClosest + vectorToPoint) / 2.0, normalize(perpendicularToEdge));
                    minDistance = min(minDistance, distanceToEdge);
                }
            }
        }
    }

    return minDistance;
}
