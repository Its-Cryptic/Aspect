#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

const float near = 1.0; // Near clip plane (prob shouldnt change)
const float far = 50.0; // Far clip plane
const float threshold = 0.9; // Distance threshold

float linearizeDepth(float depth) {
    return (2.0 * near) / (far + near - depth * (far - near));
}

vec4 getFragColor(vec2 coord) {
    vec4 original = texture(DiffuseSampler, coord);
    float mainDepth = texture(MainDepthSampler, coord).r;
    float depth = linearizeDepth(mainDepth);
    float cameraDistance = depth; // Approximate camera space distance

    if(cameraDistance < threshold) {
        return vec4(depth, depth, depth, 1.0); // Use grayscale depth visualization
    } else {
        return original; // Use the original color
    }
}

void main() {
    fragColor = getFragColor(texCoord);
}