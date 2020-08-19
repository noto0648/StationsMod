#version 120

attribute vec4 position;

uniform mat4 projMat;
uniform vec2 outSize;

varying vec2 texCoord;

void main(){
    //vec4 outPos = projMat * vec4(Position.xyz, 1.0);
    gl_Position = vec4(position.xyz, 1.0);

    texCoord = position.xy / outSize;
    texCoord.y = 1.0 - texCoord.y;
}
