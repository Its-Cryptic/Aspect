#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
#moj_import <projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

uniform vec3 Center;
uniform mat4 InvViewMat;

out vec4 vertexColor;
out vec2 texCoord0;
out vec3 normal;
out vec3 worldPosition;

void main() {
    vec4 pos = ProjMat * ModelViewMat * vec4(Position, 1.0);
    gl_Position = pos;


    vertexColor = Color;
    texCoord0 = UV0;

    worldPosition = (InvViewMat * (ModelViewMat * vec4(Position, 1.0))).xyz;
    normal = normalize(worldPosition-Center);
}



