#version 150

#moj_import <aspect:shader_textures.glsl>
#moj_import <aspect:transformations.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;
uniform sampler2D CutoutDepthSampler;
uniform sampler2D BEDepthSampler;
uniform sampler2D TranslucentDepthSampler;

uniform sampler2D BEDiffuseSampler;
uniform sampler2D TDiffuseSampler;


uniform samplerBuffer DataBuffer;
uniform int InstanceCount;
uniform int DataSize;

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;
uniform float time;

in vec2 texCoord;
in vec2 oneTexel;
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
    vec4 diffuseColor = texture(DiffuseSampler, texCoord);

    float mainDepth = getDepth(MainDepthSampler, texCoord);
    float cutoutDepth = getDepth(CutoutDepthSampler, texCoord);
    float beDepth = getDepth(BEDepthSampler, texCoord);
    float translucentDepth = getDepth(TranslucentDepthSampler, texCoord);

    vec3 worldPosMain = worldPos(mainDepth, texCoord, invProjMat, invViewMat, cameraPos);
    vec3 normalMain = ReconstructNormal(MainDepthSampler, texCoord, oneTexel, invProjMat, invViewMat, cameraPos);

    vec3 worldPosBE = worldPos(beDepth, texCoord, invProjMat, invViewMat, cameraPos);
    vec3 normalBE = ReconstructNormal(BEDepthSampler, texCoord, oneTexel, invProjMat, invViewMat, cameraPos);

    bool isCutout = cutoutDepth > mainDepth;

    fragColor = diffuseColor;
    for (int instance = 0; instance < InstanceCount; instance++) {
        int index = instance * DataSize;
        // 0-2: center, 3-5: color, 6: radius, 7: intensity, 8: isDirectional (0 or 1), 9: useNoise (0 or 1)
        vec3 center = fetch3(DataBuffer, index);
        vec3 color = fetch3(DataBuffer, index + 3);
        float radius = fetch(DataBuffer, index + 6);
        float intensity = fetch(DataBuffer, index + 7);
        bool isDirectional = fetchBool(DataBuffer, index + 8);
        bool useNoise = fetchBool(DataBuffer, index + 9);
        bool glassGlare = fetchBool(DataBuffer, index + 10);


        float dist = distance(worldPosBE, center);
        bool isTranslucent = linearizeDepth(translucentDepth) < linearizeDepth(beDepth) ? true : false;
        if (dist < radius) {

            vec3 translucentColor = isTranslucent ? vec3(0.2, 0.2, 0.9) : vec3(1.0, 1.0, 1.0);

            float fallOff = (radius - dist) / radius;
            vec3 lightDir = normalize(center - worldPosBE);
            float lightFacing = isDirectional ? max(0.0, dot(lightDir, normalBE)) : 1.0;
            float noise4D = useNoise ? fbm4D(vec4(worldPosBE, mod(time, 1000.0)/20), 4) * 0.5 + 0.5 : 1.0;


            fragColor.rgb *= (color * translucentColor * lightFacing * intensity * fallOff * noise4D + 1.0);
        }

        dist = distance(worldPosMain, center);
        if (dist < radius && isTranslucent && glassGlare) {
            // Add glare on stained glass
            float glareFactor = 0.3;

            float fallOff = (radius - dist) / radius;
            vec3 lightDir = normalize(center - worldPosMain);
            float lightFacing = isDirectional ? max(0.0, dot(lightDir, normalMain)) : 1.0;
            float noise4D = useNoise ? fbm4D(vec4(worldPosMain, mod(time, 1000.0)/20), 4) * 0.5 + 0.5 : 1.0;

            fragColor.rgb *= (color * lightFacing * intensity * fallOff * noise4D * glareFactor + 1.0);
        }
    }
}