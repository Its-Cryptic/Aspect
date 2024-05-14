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

vec3 hash3(vec3 p) {
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
                vec3 vectorToPoint = cellOffset + hash3(cellPosition + cellOffset) * randomness - localPosition;
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
                vec3 vectorToPoint = cellOffset + hash3(cellPosition + cellOffset) * randomness - localPosition;
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

float random2D (vec2 st) {
    return fract(sin(dot(st.xy,
    vec2(12.9898,78.233)))*
    43758.5453123);
}

float random3D (vec3 st) {
    return fract(sin(dot(st.xyz,
    vec3(12.9898,78.233, 45.543)))*
    43758.5453123);
}

float random4D(vec4 st) {
    return fract(sin(dot(st.xyzw,
    vec4(12.9898,78.233, 45.543, 94.654)))*
    43758.5453123);
}

// Based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd
float noise2D (vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    // Four corners in 2D of a tile
    float a = random2D(i);
    float b = random2D(i + vec2(1.0, 0.0));
    float c = random2D(i + vec2(0.0, 1.0));
    float d = random2D(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) +
    (c - a)* u.y * (1.0 - u.x) +
    (d - b) * u.x * u.y;
}

float fbm2D (vec2 st, int OCTAVES) {
    // Initial values
    float value = 0.0;
    float amplitude = .5;
    float frequency = 0.;
    //
    // Loop of octaves
    for (int i = 0; i < OCTAVES; i++) {
        value += amplitude * noise2D(st);
        st *= 2.;
        amplitude *= .5;
    }
    return value;
}

float noise3D (vec3 st) {
    vec3 i = floor(st);
    vec3 j = fract(st);

    float a = random3D(i);
    float b = random3D(i + vec3(1.0, 0.0, 0.0));
    float c = random3D(i + vec3(0.0, 1.0, 0.0));
    float d = random3D(i + vec3(1.0, 1.0, 0.0));
    float e = random3D(i + vec3(0.0, 0.0, 1.0));
    float f = random3D(i + vec3(1.0, 0.0, 1.0));
    float g = random3D(i + vec3(0.0, 1.0, 1.0));
    float h = random3D(i + vec3(1.0, 1.0, 1.0));

    float a2 = mix(a, b, j.x);
    float b2 = mix(c, d, j.x);
    float c2 = mix(e, f, j.x);
    float d2 = mix(g, h, j.x);

    float a3 = mix(a2, b2, j.y);
    float b3 = mix(c2, d2, j.y);

    return mix(a3, b3, j.z);
}

float fbm3D (vec3 st, int OCTAVES) {
    // Initial values
    float value = 0.0;
    float amplitude = .5;
    float frequency = 0.;
    //
    // Loop of octaves
    for (int i = 0; i < OCTAVES; i++) {
        value += amplitude * noise3D(st);
        st *= 2.;
        amplitude *= .5;
    }
    return value;
}

float noise4D(vec4 st) {
    vec4 y = floor(st);
    vec4 z = fract(st);

    float a = random4D(y);
    float b = random4D(y + vec4(1.0, 0.0, 0.0, 0.0));
    float c = random4D(y + vec4(0.0, 1.0, 0.0, 0.0));
    float d = random4D(y + vec4(1.0, 1.0, 0.0, 0.0));
    float e = random4D(y + vec4(0.0, 0.0, 1.0, 0.0));
    float f = random4D(y + vec4(1.0, 0.0, 1.0, 0.0));
    float g = random4D(y + vec4(0.0, 1.0, 1.0, 0.0));
    float h = random4D(y + vec4(1.0, 1.0, 1.0, 0.0));
    float i = random4D(y + vec4(0.0, 0.0, 0.0, 1.0));
    float j = random4D(y + vec4(1.0, 0.0, 0.0, 1.0));
    float k = random4D(y + vec4(0.0, 1.0, 0.0, 1.0));
    float l = random4D(y + vec4(1.0, 1.0, 0.0, 1.0));
    float m = random4D(y + vec4(0.0, 0.0, 1.0, 1.0));
    float n = random4D(y + vec4(1.0, 0.0, 1.0, 1.0));
    float o = random4D(y + vec4(0.0, 1.0, 1.0, 1.0));
    float p = random4D(y + vec4(1.0, 1.0, 1.0, 1.0));

    float a2 = mix(a, b, z.x);
    float b2 = mix(c, d, z.x);
    float c2 = mix(e, f, z.x);
    float d2 = mix(g, h, z.x);
    float e2 = mix(i, j, z.x);
    float f2 = mix(k, l, z.x);
    float g2 = mix(m, n, z.x);
    float h2 = mix(o, p, z.x);

    float a3 = mix(a2, b2, z.y);
    float b3 = mix(c2, d2, z.y);
    float c3 = mix(e2, f2, z.y);
    float d3 = mix(g2, h2, z.y);

    float e3 = mix(a3, b3, z.z);
    float f3 = mix(c3, d3, z.z);

    return mix(e3, f3, z.w); // Final mix using the w-coordinate
}

// 4D fbm function
float fbm4D(vec4 st, int OCTAVES) {
    float value = 0.0;
    float amplitude = 0.5;

    for (int i = 0; i < OCTAVES; i++) {
        value += amplitude * noise4D(st);
        st *= 2.0;
        amplitude *= 0.5;
    }

    return value;
}



