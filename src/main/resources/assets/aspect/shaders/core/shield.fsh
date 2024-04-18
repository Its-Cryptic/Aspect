#version 150

#moj_import <aspect:transformations.glsl>

uniform sampler2D Sampler0;
uniform sampler2D DepthBuffer;
uniform sampler2D ColorBuffer;

uniform vec4 ColorModulator;
uniform vec3 lookVector;
uniform vec3 CameraPosition;
uniform vec3 ObjectPosition;
uniform float SphereRadius;
uniform float RenderTime;

in vec4 vertexColor;
in vec2 texCoord0;
in vec4 screenUV;
in vec4 normal;

out vec4 fragColor;

vec4 colorRamp (float value, vec4 lowColor, vec4 highColor) {
    return mix(lowColor, highColor, value);
}

void Raycast_float(vec3 RayOrigin, vec3 RayDirection, vec3 SphereOrigin, float SphereSize, out bool Hit, out vec3 HitPosition, out vec3 HitNormal) {
    HitPosition = vec3(0.0, 0.0, 0.0);
    HitNormal = vec3(0.0, 0.0, 0.0);

    float t = 0.0;
    vec3 L = SphereOrigin - RayOrigin;
    float tca = dot(L, -RayDirection);

    if (tca < 0)
    {
        Hit = false;
        return;
    }

    float d2 = dot(L, L) - tca * tca;
    float radius2 = SphereSize * SphereSize;

    if (d2 > radius2)
    {
        Hit = false;
        return;
    }

    float thc = sqrt(radius2 - d2);
    t = tca - thc;

    Hit = true;
    HitPosition = RayOrigin - RayDirection * t;
    HitNormal = normalize(HitPosition - SphereOrigin);
}


void main() {
    vec4 materialTexture = texture(Sampler0, texCoord0);
    vec4 screenTexture = textureProj(ColorBuffer, screenUV);
    float mainDepth = textureProj(DepthBuffer, screenUV).r;
    vec4 depthWorld = vec4(vec3(linearizeDepth(mainDepth)), 1.0);

    float fresnel = 1-dot(normal.xyz, lookVector);

    bool hit;
    vec3 hitPosition, hitNormal;
    Raycast_float(CameraPosition, lookVector, ObjectPosition, SphereRadius, hit, hitPosition, hitNormal);
    vec4 blackHoleColor = vec4(0.0, 0.0, 0.0, 1.0);


    vec4 blackHoleShader, blackHoleUV;
    blackHoleUV = screenUV + vec4(vec2(0.05*sin(screenUV.x+RenderTime*0.05),0), 0, 0);
    blackHoleShader = textureProj(ColorBuffer, blackHoleUV);
    fragColor = blackHoleShader;



}
