#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 ProjMat;
uniform mat4 InvModelViewMat;
uniform mat4 invProjMat;
uniform mat4 invViewMat;
uniform vec3 cameraPos;
uniform vec3 lookVector;
uniform vec3 upVector;
uniform vec3 leftVector;
uniform float fov;
uniform float aspectRatio;
uniform float nearPlaneDistance;
uniform float farPlaneDistance;

in vec2 texCoord;
out vec4 fragColor;

vec3 worldPos(float mainDepth) {
    float z = mainDepth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 worldSpacePosition = InvModelViewMat * viewSpacePosition;
    return cameraPos + worldSpacePosition.xyz;
    //return worldSpacePosition.xyz;
}


void main() {
    vec3 sphereCenter = vec3(16, -60, -18);
    //sphereCenter = vec3(0, 0, 0);
    float radius = 5.0;


    vec4 diffuseColor = texture(DiffuseSampler, texCoord);
    float mainDepth = texture(MainDepthSampler, texCoord).r;
    vec3 worldPosition = worldPos(mainDepth);

    float dist = distance(worldPosition, sphereCenter);

    if (dist <= radius) {
        fragColor = vec4(1-diffuseColor.rgb, 1.0);
    } else {
        fragColor = diffuseColor;
    }
}