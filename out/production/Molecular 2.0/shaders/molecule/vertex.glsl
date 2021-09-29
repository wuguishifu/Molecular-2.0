#version 460 core

// input vertex items
layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec3 vNormal;

// output values
out vec3 passNormal;
out vec3 passFragPos;

// the MVP matrices
uniform mat4 vModel;
uniform mat4 vView;
uniform mat4 vProjection;

void main() {
    gl_Position = vProjection * vView * vModel * vec4(vPosition, 1.0);
    passFragPos = vec3(vModel * vec4(vPosition, 1.0));
    passNormal = normalize(vec3(vModel * vec4(vNormal, 0.0)));
}
