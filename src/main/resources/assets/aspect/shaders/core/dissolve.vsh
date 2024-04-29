#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
#moj_import <projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ChunkOffset;
uniform int FogShape;

uniform mat4 InvViewMat;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;
out vec4 screenUV;

void main() {
    vec4 viewSpacePosition = ModelViewMat * vec4(Position, 1.0);
    vec4 clipSpacePosition = ProjMat * ModelViewMat * vec4(Position, 1.0);
    gl_Position = clipSpacePosition;
    screenUV = projection_from_position(clipSpacePosition);


    vertexDistance = fog_distance(ModelViewMat, Position, FogShape);
    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
    texCoord0 = UV0;

    normal = vec4(Normal, 0.0);
    vec3 transformedNormal = normalize(mat3(ModelViewMat) * Normal);
    //normal = vec4(transformedNormal, 0.0);
}



