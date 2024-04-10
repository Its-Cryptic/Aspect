#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform vec3 NormalVec;
uniform vec3 LookVec;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    float fresnel = dot(normal.rgb, vec3(0.0, 1.0, 0.0));
    //color = vec4(vec3(fresnel), 1.0);
    //color = vec4(NormalVec, 1.0);
    //color *= vec4(normal.rgb, 1.0);
    fragColor = vec4(normal.xyz, 1.0);
    //fragColor = color;
    //fragColor = color * ColorModulator;
}
