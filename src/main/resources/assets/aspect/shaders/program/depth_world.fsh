#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

const float near = 1.0; // Near clip plane (prob shouldnt change)
const float far = 100.0; // Far clip plane

float linearizeDepth(float depth) {
    return (2.0 * near) / (far + near - depth * (far - near));
}

void main() {
    float mainDepth = texture(MainDepthSampler, texCoord).r;
    float depth = linearizeDepth(mainDepth);
    fragColor = vec4(depth, depth, depth, 1.0);
}