#version 330 core

// input values
in vec2 passTextureCoord;
in vec3 passFragPos;
in vec3 passTangent;
in vec3 passBitangent;
in vec3 passNormal;
in mat3 TBN;

// the lighting values for shading
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;

// the position of the camera
uniform vec3 viewPos;

// the texture uniforms
layout(binding = 0) uniform sampler2D baseMap;
layout(binding = 1) uniform sampler2D specularMap;
layout(binding = 2) uniform sampler2D normalMap;

// the output color
out vec4 outColor;

void main() {

    // get the rgb components of the texture
    vec4 textureColor = texture(baseMap, passTextureCoord);
    vec3 color = vec3(textureColor.xyz);

    // get the alpha value of the texture
    float alpha = textureColor.w;

    vec3 normal = texture(normalMap, passTextureCoord).rgb;
    normal = normal * 2.0 - 1.0;
    normal = normalize(TBN * normal);

    // calculate the ambient lighting
    vec3 ambientLight = lightLevel * lightColor; // create the ambient light level

    // calculate diffusion lighting
    vec3 lightDir = normalize(lightPos - passFragPos); // find the direction of the light
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 diffuseLight = diff * lightColor;

    // calculate specular lighting
    float specularStrength = 1;
    vec3 viewDir = normalize(viewPos - passFragPos);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * vec3(texture(specularMap, passTextureCoord));

    // combine the ambient, diffusion, and specular lighting into the final fragment color
    vec3 colorResult = (ambientLight + diffuseLight + specular) * color; // combine the light components

    // pass the color
    outColor = vec4(colorResult, alpha);
}
