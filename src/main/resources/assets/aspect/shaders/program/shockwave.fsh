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
    vec3 sphereCenter = vec3(16, -60, -18); // Center of the ripple effect
    float effectRadius = 5.0; // Radius of the ripple effect
    float rippleSpeed = 4.0; // Speed of ripple propagation
    float rippleMagnitude = 0.01; // Magnitude of the ripple effect

    // Original texture sampling
    vec4 diffuseColor = texture(DiffuseSampler, texCoord);

    // Calculate world position of current fragment
    float mainDepth = texture(MainDepthSampler, texCoord).r;
    vec3 worldPosValue = worldPos(mainDepth, texCoord, invProjMat, InvModelViewMat, cameraPos);

    // Calculate distance from the center of the ripple
    float dist = distance(worldPosValue, sphereCenter);

    // Modulate the ripple effect based on distance and angle to create a more realistic distortion
    if (dist <= effectRadius) {
        // Calculate a distortion factor that decreases with distance
        float distortionFactor = 1.0 - (dist / effectRadius);
        distortionFactor = pow((dist / effectRadius)-1, 2.0);
        // Scale the ripple effect based on the distortion factor
        float ripple = sin(dist * rippleSpeed - time) * rippleMagnitude * distortionFactor;

        // Apply distortion to texture coordinates
        vec2 direction = normalize(texCoord - vec2(0.5, 0.5)); // Direction from screen center to current texCoord
        vec2 distortedTexCoord = texCoord + ripple;

        // Re-sample the texture with the distorted texture coordinates
        vec4 distortedColor = texture(DiffuseSampler, distortedTexCoord);

        // Mix the original and distorted colors based on the distortion factor
        fragColor = distortedColor;
    } else {
        // Outside the effect radius, render without distortion
        fragColor = diffuseColor;
    }
}