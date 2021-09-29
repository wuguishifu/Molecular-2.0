#version 330 core

// input values
in vec3 passNormal;
in vec3 passFragPos;

// lighting values
uniform vec4 meshColor;
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;

// the position of the camera
uniform vec3 viewPos;
uniform float reflectiveness;

// the output color
out vec4 outColor;

void main() {
    vec3 color = meshColor.xyz;
    float alpha = meshColor.w;
    vec3 normal = passNormal;

    // ambient lighting
    vec3 ambientLight = lightLevel * lightColor;

    // diffusion lighting
    vec3 lightDir = normalize(lightPos - passFragPos);
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 diffuseLight = diff * lightColor;

    // specular lighting
    float specularStrength = 1;
    vec3 viewDir = normalize(viewPos - passFragPos);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), reflectiveness);
    vec3 specular = specularStrength * spec * lightColor;

    // combine to output color
    vec3 colorResult = (ambientLight + diffuseLight + specular) * color;
    outColor = vec4(colorResult, alpha);

}
