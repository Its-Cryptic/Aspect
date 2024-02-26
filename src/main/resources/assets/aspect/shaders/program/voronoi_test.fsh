#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

in vec2 texCoord;
out vec4 fragColor;

vec2 resolution = vec2(16, 9);

float S = 8.0;

void main() {
    vec2 uv = floor(S * texCoord * vec2(resolution.x / resolution.y, 1.0));

    float checker = mod(uv.x + uv.y, 2.0);

    if (checker < 1.0) {
        fragColor = texture(DiffuseSampler, texCoord);
    } else {
        fragColor = vec4(1.0, 0.0, 1.0, 1.0);
    }
}