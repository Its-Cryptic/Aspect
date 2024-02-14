#version 150

uniform sampler2D DiffuseSampler;
uniform vec3 Color;

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

void main() {

    vec4 original = texture(DiffuseSampler, texCoord);

    vec4 outColor = original * vec4(Color, 1.0);

    fragColor = mix(original, outColor, 0.5);
}