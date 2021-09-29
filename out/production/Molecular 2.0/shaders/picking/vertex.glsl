#version 460 core

// input vertex items
layout(location = 0) in vec3 vPosition;

// the MVP matrices
uniform mat4 vModel;
uniform mat4 vView;
uniform mat4 vProjection;

void main() {
    gl_Position = vProjection * vView * vModel * vec4(vPosition, 1.0);
}
