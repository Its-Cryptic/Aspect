#version 150

#moj_import <aspect:shader_textures.glsl>
#moj_import <aspect:transformations.glsl>

in vec4 Position;

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 ProjMat;
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

uniform vec2 InSize;
uniform vec2 OutSize;
uniform vec2 ScreenSize;

uniform float time;

out vec2 texCoord;

void main() {
    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.2, 1.0);
    texCoord = Position.xy / OutSize;
}
