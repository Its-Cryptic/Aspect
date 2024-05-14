#version 150

#moj_import <aspect:transformations.glsl>
#moj_import <aspect:shader_textures.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform vec3 Center;
uniform vec3 CameraPos;
uniform float Progress;

in vec4 vertexColor;
in vec2 texCoord0;
in vec3 normal;
in vec3 worldPosition;

out vec4 fragColor;



void main() {
    vec4 materialTexture = texture(Sampler0, texCoord0);
    vec3 lookVector = normalize(CameraPos - worldPosition);
    vec3 normals = normal;

    float fresnel = 1-dot(lookVector, normals);
    //if (fresnel > 0.5) discard;

    float noise4D = fbm4D(vec4(worldPosition*5.0, Progress*15.0), 3);
    if (noise4D < 0.4) discard;
    vec4 color = noise4D <= 0.4 + 0.01 ? vec4(1, 1, 1, 1) : vec4(0, 0, 0, 1);
    fragColor = color;
    //fragColor = vec4(color, 1.0);

}
