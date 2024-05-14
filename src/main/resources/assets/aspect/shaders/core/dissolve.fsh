#version 150

#moj_import <aspect:transformations.glsl>
#moj_import <aspect:shader_textures.glsl>

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
in vec3 worldPosition;

out vec4 fragColor;

vec4 colorRamp (float value, vec4 lowColor, vec4 highColor) {
    return mix(lowColor, highColor, value);
}


void main() {
    vec4 materialTexture = texture(Sampler0, texCoord0);
    vec4 screenTexture = textureProj(ColorBuffer, screenUV);
    float mainDepth = textureProj(DepthBuffer, screenUV).r;
    vec4 depthWorld = vec4(vec3(linearizeDepth(mainDepth)), 1.0);

    float fresnel = 1-dot(normal.xyz, lookVector);

    float noise4D = fbm4D(vec4(worldPosition, 1), 3);

    //fragColor = depthWorld;
    fragColor = vec4(vec3(noise4D), 1.0);

}
