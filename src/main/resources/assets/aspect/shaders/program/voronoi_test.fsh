#version 150

#moj_import <aspect:shader_textures.glsl>
#moj_import <aspect:transformations.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 InvModelViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;
uniform vec3 lookVector;
uniform vec3 upVector;
uniform vec3 leftVector;
uniform float fov;
uniform float aspectRatio;
uniform float nearPlaneDistance;
uniform float farPlaneDistance;

uniform float time;

in vec2 texCoord;
out vec4 fragColor;

vec4 voronoiTexture(float DistFromEdge, float solidThreshold, float fadeThreshold, vec4 edgeColor) {
    if (DistFromEdge < solidThreshold) {
        return edgeColor;
    } else if (DistFromEdge < fadeThreshold) {
        return mix(vec4(0.0,0.0,0.0,0.0), edgeColor, 1-(DistFromEdge - solidThreshold) / (fadeThreshold - solidThreshold));
    } else {
        return vec4(0.0,0.0,0.0,0.0);
    }
}

void main() {
    vec3 sphereCenter = vec3(16, -60, -18);
    float radius = 5.0;

    vec2 uv = texCoord;
    /// Fisheye Distortion ///

    float d=length(uv);
    float z = sqrt(1.0 - d * d);
    float r = atan(d, z) / 3.14159;
    float phi = atan(uv.y, uv.x);

    uv = vec2(r*cos(phi),r*sin(phi)+.5);

    /////////////////////////
    vec4 diffuseColor = texture(DiffuseSampler, texCoord);
    float mainDepth = texture(MainDepthSampler, texCoord).r;

    vec3 worldPos = worldPos(mainDepth, texCoord, invProjMat, InvModelViewMat, cameraPos);
    float dist = distance(worldPos, sphereCenter);
    dist = (
abs((worldPos.x-sphereCenter.x)-(worldPos.z-sphereCenter.z))
+abs((worldPos.x-sphereCenter.x)+(worldPos.z-sphereCenter.z))
)/2;


    float DistFromEdge;

    float randomness = (1-cos(time*0.1))/2;
     randomness = 1.0;
    DistFromEdge = voronoi_distance_to_edge(worldPos+vec3(0.5,0.5,0.5), 1.0, randomness);
    vec4 voronoiTexture = voronoiTexture(DistFromEdge, 0.01, 0.03, vec4(0.15, 0.5, 1.0, 1.0));
    if (dist <= radius + 0.0625 && dist >= radius - 0.0625) {
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
    } else {
        if (dist <= radius) {
        fragColor = mix(diffuseColor, voronoiTexture, voronoiTexture.a);
        } else {
        fragColor = diffuseColor;
        }
    }


    //fragColor = mix(diffuseColor*vec4(vec3((radius-dist)/radius),1.0), voronoiTexture, voronoiTexture.a);
}