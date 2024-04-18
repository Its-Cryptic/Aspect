#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;
//uniform sampler2D CutoutDepthSampler;

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;
uniform float Red;
uniform float Green;
uniform float Blue;

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

float thickness = 0.25;
float lines = 1;
float lineSpacing = 4;
vec4 fillColor = vec4(0.45, 0.05, 0.75, 0.75);
vec3 center = vec3(102, 68, 482);

const float near = 1.0; // Near clip plane (prob shouldnt change)
const float far = 100.0; // Far clip plane

float linearizeDepth(float depth) {
    return (2.0 * near) / (far + near - depth * (far - near));
}

vec3 worldPos(float depth) {
    float z = depth * 2.0 - 1.0; //normalized device coordinates (2x-1)
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0); // texCoord is [0,1] so convert to clip space which is [-1,1]
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w; //why?
    vec4 worldSpacePosition = invViewMat * viewSpacePosition;

    return cameraPos + worldSpacePosition.xyz;
}

struct lineInfo {
    bool inLine;
    float dist;
};

lineInfo inLine(float dist) {
    float line = 1.5;
    if(dist <= line + (thickness/2) && dist >= line - (thickness/2)) {
        return lineInfo(true, 1 - (abs(dist - line) / thickness));
    }
    return lineInfo(false, 0);
}

void main() {
    float mainDepth = texture(MainDepthSampler, texCoord).r;
    vec3 worldPos = worldPos(mainDepth);
    //float depth = linearizeDepth(mainDepth);

    //vec4 depthWorld = vec4(depth, depth, depth, 1.0);
    //fragColor = depthWorld * vec4(Red, Green, Blue, 1.0);

    vec4 color = vec4(0, 0, 0, 0);

    //distance from world position to center, in blocks
    float dist = distance(worldPos, cameraPos);

    lineInfo info = inLine(dist);
    if(info.inLine && mainDepth < 1) {
        color = mix(vec4(0, 0, 0, 0), fillColor, 1-info.dist);
    }
    vec4 original = texture(DiffuseSampler, texCoord);
    fragColor = mix(original, color, color.a);

    //v
    //vec4 outColor = original * vec4(1.0, 0.0, 0.0, 1.0);
    //fragColor = outColor;
}