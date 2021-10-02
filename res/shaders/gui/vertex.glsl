#version 460 core

in vec3 position;
in vec2 textureCoord;

uniform mat4 vModel;
uniform mat4 vView;
uniform mat4 vProjection;

out vec2 passTextureCoord;

void main() {
    gl_Position = vProjection * vView * vModel * vec4(position, 1.0);
    passTextureCoord = textureCoord;
}