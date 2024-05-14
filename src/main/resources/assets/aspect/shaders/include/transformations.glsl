#version 150

float linearizeDepth(float depth) {
    float near = 1.0;
    float far = 100.0;
    return (2.0 * near) / (far + near - depth * (far - near));
}

float fetch(samplerBuffer DataBuffer, int index) {
    return texelFetch(DataBuffer, index).r;
}

vec2 fetch2(samplerBuffer DataBuffer, int startIndex) {
    return vec2(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1));
}

vec3 fetch3(samplerBuffer DataBuffer, int startIndex) {
    return vec3(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1), fetch(DataBuffer, startIndex + 2));
}

vec4 fetch4(samplerBuffer DataBuffer, int startIndex) {
    return vec4(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1), fetch(DataBuffer, startIndex + 2), fetch(DataBuffer, startIndex + 3));
}

bool fetchBool(samplerBuffer DataBuffer, int index) {
    return fetch(DataBuffer, index) > 0.5;
}

float getDepth(sampler2D DepthBuffer, vec2 uv) {
    return texture(DepthBuffer, uv).r;
}

vec3 worldPos(float mainDepth, vec2 texCoord, mat4 invProjMat, mat4 invViewMat, vec3 cameraPos) {
    float z = mainDepth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 localSpacePosition = invViewMat * viewSpacePosition;
    return cameraPos + localSpacePosition.xyz;
}

vec2 worldToScreen(vec3 worldPos, mat4 projMat, mat4 modelViewMat) {
    vec4 clipSpacePosition = projMat * (modelViewMat * vec4(worldPos, 1.0));
    clipSpacePosition /= clipSpacePosition.w;
    return (clipSpacePosition.xy + 1.0) / 2.0;
}

vec3 calculateDerivative(vec2 uv, vec2 offset, sampler2D DepthBuffer, mat4 invProjMat, mat4 invModelViewMat, vec3 cameraPos) {
    float depthCenter = texture(DepthBuffer, uv).x;
    float depthOffset1 = texture(DepthBuffer, uv + offset).x;
    float depthOffset2 = texture(DepthBuffer, uv - offset).x;

    vec3 worldPosCenter = worldPos(depthCenter, uv, invProjMat, invModelViewMat, cameraPos);
    vec3 worldPosOffset1 = worldPos(depthOffset1, uv + offset, invProjMat, invModelViewMat, cameraPos);
    vec3 worldPosOffset2 = worldPos(depthOffset2, uv - offset, invProjMat, invModelViewMat, cameraPos);

    return (worldPosOffset1 - worldPosOffset2) / 2.0;
}

vec3 ReconstructNormal(sampler2D DepthBuffer, vec2 uv, vec2 oneTexel, mat4 invProjMat, mat4 invModelViewMat, vec3 cameraPos) {
    float depth = texture(DepthBuffer, uv).x;

    vec4 H;
    H.x = texture(DepthBuffer, uv - vec2(oneTexel.x, 0)).x;
    H.y = texture(DepthBuffer, uv + vec2(oneTexel.x, 0)).x;
    H.z = texture(DepthBuffer, uv - vec2(2.0 * oneTexel.x, 0)).x;
    H.w = texture(DepthBuffer, uv + vec2(2.0 * oneTexel.x, 0)).x;
    vec2 he = abs(vec2(H.xy * H.zw) * inversesqrt(2.0 * H.zw - H.xy) - depth);
    vec3 hDeriv;
    if (he.x > he.y)
    hDeriv = calculateDerivative(uv, vec2(oneTexel.x, 0), DepthBuffer, invProjMat, invModelViewMat, cameraPos);
    else
    hDeriv = calculateDerivative(uv, vec2(2.0 * oneTexel.x, 0), DepthBuffer, invProjMat, invModelViewMat, cameraPos);

    vec4 V;
    V.x = texture(DepthBuffer, uv - vec2(0, oneTexel.y)).x;
    V.y = texture(DepthBuffer, uv + vec2(0, oneTexel.y)).x;
    V.z = texture(DepthBuffer, uv - vec2(0, 2.0 * oneTexel.y)).x;
    V.w = texture(DepthBuffer, uv + vec2(0, 2.0 * oneTexel.y)).x;
    vec2 ve = abs(vec2(V.xy * V.zw) * inversesqrt(2.0 * V.zw - V.xy) - depth);
    vec3 vDeriv;
    if (ve.x > ve.y)
    vDeriv = calculateDerivative(uv, vec2(0, oneTexel.y), DepthBuffer, invProjMat, invModelViewMat, cameraPos);
    else
    vDeriv = calculateDerivative(uv, vec2(0, 2.0 * oneTexel.y), DepthBuffer, invProjMat, invModelViewMat, cameraPos);

    return normalize(cross(hDeriv, vDeriv));
}