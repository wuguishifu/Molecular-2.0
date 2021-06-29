#version 460 core

// input values
layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec2 vTextureCoord;
layout(location = 2) in vec3 vNormal;
layout(location = 3) in vec3 vTangent;
layout(location = 4) in vec3 vBitangent;

// output values
out vec2 passTextureCoord;
out vec3 passFragPos;
out vec3 passNormal;
out vec3 passTangent;
out vec3 passBitangent;
out mat3 TBN;

// the model, view, and projection matrices to render at
uniform mat4 vModel;
uniform mat4 vView;
uniform mat4 vProjection;

// the main runnable
void main() {

    // set the position of this vertex
    gl_Position = vProjection * vView * vModel * vec4(vPosition, 1.0);

    // set the fragment position of this vertex in relation to the model and pass it to the fragment shader
    passFragPos = vec3(vModel * vec4(vPosition, 1.0));

    // pass the normal, tangent, and bitangent vectors
    passNormal = normalize(vec3(vModel * vec4(vNormal, 0.0)));
    passTangent = normalize(vec3(vModel * vec4(vTangent, 0.0)));
    passBitangent = normalize(vec3(vModel * vec4(vBitangent, 0.0)));
    TBN = mat3(passTangent, passBitangent, passNormal);

    // pass the color
    passTextureCoord = vTextureCoord;
}