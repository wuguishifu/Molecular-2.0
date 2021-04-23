#version 330 core

// input vectors
in vec4 passColor;
in vec3 passNormal;
in vec3 passFragPos;

// the lighting values
uniform struct Light {
    vec3 lightPos;
    vec3 lightColor;
    float lightLevel;
} light;

uniform struct Material {
    int reflectiveness;
    float specularStrength;
    float roughness;
} material;

uniform vec3 viewPos;

// the output color
out vec4 outColor;

void main() {

    // get the color and alpha components
    vec3 color = vec3(passColor.rgb);
    float alpha = passColor.w;

    // calculate the ambient lighting
    vec3 ambientLight = light.lightLevel * light.lightColor;

    // calculate the diffusion lighting
    vec3 lightDir = normalize(light.lightPos - passFragPos);
    float diff = max(dot(passNormal, lightDir), 0.0);
    vec3 diffuseLight = diff * light.lightColor;

    // calculate the specular lighting
    vec3 viewDir = normalize(viewPos - passFragPos);
    vec3 reflectDir = reflect(-lightDir, passNormal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.reflectiveness);
    vec3 specularLight = material.specularStrength * spec * light.lightColor;

    // combine the ambient, diffusion, and specular light into the final fragment color
    vec3 colorResult = (ambientLight + diffuseLight + specularLight) * color;

    outColor = vec4(colorResult, alpha);

}
