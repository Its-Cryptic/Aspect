#version 150

#moj_import <aspect:shader_textures.glsl>
#moj_import <aspect:transformations.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 InvModelViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;
uniform vec3 lookVector;
uniform vec3 upVector;
uniform vec3 leftVector;
uniform float fov;
uniform float aspectRatio;
uniform float nearPlaneDistance;
uniform float farPlaneDistance;

uniform float time;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    // Center the texture coordinates
    vec2 centeredTexCoord = texCoord - vec2(0.5, 0.5);

    // Adjust for aspect ratio to maintain circular symmetry
    centeredTexCoord.x *= aspectRatio;
    float effect = -1.0;
    float effect_scale = 2-cos(time*0.05);

    // Calculate the distance from the center and the angle
    float d = length(centeredTexCoord) * 2.0; // Scale factor adjustment if necessary
    float z = sqrt(1.0 - d * d);
    z = sqrt(1.0 - d * d * effect);
    float r = atan(d, z) / 3.14159; // Pi is approximately 3.14159
    r *= effect_scale;
    float phi = atan(centeredTexCoord.y, centeredTexCoord.x);

    // Calculate the new UV coordinates
    vec2 uv = vec2(0.5, 0.5) + vec2(r * cos(phi), r * sin(phi)) / aspectRatio;

    // Map the adjusted coordinates back to 0-1 range and fetch the texture
    fragColor = texture(DiffuseSampler, uv);
}