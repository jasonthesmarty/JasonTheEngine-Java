#version 330

out vec4 fragColor;

in vec4 vertexColor;

in vec2 textCoords;

uniform sampler2D tex0;

void main() {
    fragColor = texture(tex0, textCoords);
}