#version 330

in vec3 vertexColorOut;

out vec4 fragColor;

void main() {
    fragColor = vec4(vertexColorOut, 1.0f);
}
