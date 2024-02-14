#version 150

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 InvViewMat;
uniform mat4 InvProjMat;
uniform vec3 CameraPos;
uniform sampler2D DepthBuffer;

uniform vec3 Center;
uniform float Radius;


in vec2 texCoord0;
out vec4 fragColor;


float thickness = 0.25;
float lines = 3;
float lineSpacing = 4;
vec4 fillColor = vec4(0.45, 0.05, 0.05, 0.75);


vec3 worldPos(float depth) {
    float z = depth * 2.0 - 1.0; //normalized device coordinates (2x-1)
    vec4 clipSpacePosition = vec4(texCoord0 * 2.0 - 1.0, z, 1.0); // texCoord0 is [0,1] so convert to clip space which is [-1,1]
    vec4 viewSpacePosition = InvProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w; //why?
    vec4 worldSpacePosition = InvViewMat * viewSpacePosition;

    return CameraPos + worldSpacePosition.xyz;
}

struct lineInfo {
    bool inLine;
    float dist;
};

lineInfo inLine(float dist) {
    for(int i = 0; i < lines; i++) {
        float line = Radius - (i*lineSpacing);
        if(dist <= line + (thickness/2) && dist >= line - (thickness/2)) {
            return lineInfo(true, 1 - (abs(dist - line) / thickness));
        }
    }
    return lineInfo(false, 0);
}


void main() {

    //texCoord0 goes from 0 to 1

    vec4 color = vec4(0, 0, 0, 0);

    float depth = texture2D(DepthBuffer, texCoord0).r;
    vec3 pos = worldPos(depth);

    //distance from world position to center, in blocks
    float dist = distance(pos, Center);

    lineInfo info = inLine(dist);
    if(info.inLine && depth < 1) {

        color = mix(vec4(0, 0, 0, 0), fillColor, info.dist);
    }
    fragColor = color;

}