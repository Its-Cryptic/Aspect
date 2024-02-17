#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

const float near = 1.0; // Near clip plane (prob shouldnt change)
const float far = 50.0; // Far clip plane
const float threshold = 0.9; // Distance threshold

vec3 worldPos(float depth) {
    float z = depth * 2.0 - 1.0; //normalized device coordinates (2x-1)
    vec4 clipSpacePosition = vec4(texCoord0 * 2.0 - 1.0, z, 1.0); // texCoord0 is [0,1] so convert to clip space which is [-1,1]
    vec4 viewSpacePosition = InvProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w; //why?
    vec4 worldSpacePosition = InvViewMat * viewSpacePosition;

    return CameraPos + worldSpacePosition.xyz;
}

float linearizeDepth(float depth) {
    return (2.0 * near) / (far + near - depth * (far - near));
}

vec4 getFragColor(vec2 coord) {
    float depth = texture(MainDepthSampler, coord).r;
    float linearDepth = linearizeDepth(depth);
    vec3 worldPosition = worldPos(depth);
    vec3 normal = texture(DiffuseSampler, coord).xyz;

    vec3 lightDir = normalize(LightPos - worldPosition);
    float NdotL = max(dot(normal, lightDir), 0.0);
    vec3 diffuse = NdotL * LightColor;

    return vec4(diffuse, 1.0);
}

void main() {
    fragColor = getFragColor(texCoord);
}