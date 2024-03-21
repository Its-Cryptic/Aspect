#version 150

float linearizeDepth(float depth) {
    float near = 1.0;
    float far = 100.0;
    return (2.0 * near) / (far + near - depth * (far - near));
}

vec3 worldPos(float mainDepth, vec2 texCoord, mat4 invProjMat, mat4 invModelViewMat, vec3 cameraPos) {
    float z = mainDepth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 localSpacePosition = invModelViewMat * viewSpacePosition;
    return cameraPos + localSpacePosition.xyz;
}

vec2 worldToScreen(vec3 worldPos, mat4 projMat, mat4 modelViewMat) {
    vec4 clipSpacePosition = projMat * (modelViewMat * vec4(worldPos, 1.0));
    clipSpacePosition /= clipSpacePosition.w;
    return (clipSpacePosition.xy + 1.0) / 2.0;
}