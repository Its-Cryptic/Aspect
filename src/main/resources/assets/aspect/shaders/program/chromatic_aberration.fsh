#version 150

uniform sampler2D DiffuseSampler;
uniform float Progress;
uniform float Time;
uniform float Scale;

in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;

vec2 origin = vec2(0.5, 0.5);

void main() {

    vec4 original = texture(DiffuseSampler, texCoord);

    vec2 direction = texCoord - origin;
    float amount = length(direction) * Scale;

    vec4 outColor;
    outColor.r = texture(DiffuseSampler, vec2(texCoord.x+amount,texCoord.y) ).r;
    outColor.g = texture(DiffuseSampler, texCoord ).g;
    outColor.b = texture(DiffuseSampler, vec2(texCoord.x-amount,texCoord.y) ).b;
    outColor.a = 1.0;


    fragColor = mix(original, outColor, Progress);

}