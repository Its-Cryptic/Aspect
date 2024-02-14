#version 150

uniform float Progress;
uniform sampler2D DiffuseSampler;
uniform vec3 Color;


in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;


//vec4 tintColor = vec4(vec3(250, 105, 37) / 255.0, 1.0);

void main() {

    vec4 original = texture(DiffuseSampler, texCoord);

    vec4 outColor = original * vec4(Color, 1.0);

//    vec4 red = vec4(1.0, 0.0, 0.0, 1.0);

    fragColor = mix(original, outColor, Progress);
//    fragColor = vec4(texCoord, 0.0, 1.0);
}