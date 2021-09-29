#version 460 core

// input values
layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec3 vNormal;
layout(location = 2) in vec4 vColor;

// output values
out vec4 passColor;
out vec3 passNormal;
out vec3 passFragPos;

// the model, view, and projection matrices to render at
uniform mat4 vModel;
uniform mat4 vView;
uniform mat4 vProjection;

void main() {

    // set the position of this vertex
    gl_Position = vProjection * vView * vModel * vec4(vPosition, 1.0);

    // set the fragment position of this vertex in relation to the model and pass it to the fragment shader
    passFragPos = vec3(vModel * vec4(vPosition, 1.0));

    // pass the normal vector
    passNormal = normalize(vec3(vModel * vec4(vNormal, 0.0)));

    // pass the color
    passColor = vColor;
}