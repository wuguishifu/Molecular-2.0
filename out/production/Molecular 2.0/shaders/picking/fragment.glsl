#version 330 core

uniform vec4 pickingColor;

// the output color
out vec4 outColor;

void main() {
    outColor = pickingColor;
}
