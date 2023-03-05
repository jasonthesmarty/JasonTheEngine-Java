#version 330

out vec4 gl_FragColor;

in vec4 vertexColor;

in vec2 textCoords;

uniform sampler2D tex0;

void main() {
    gl_FragColor = vertexColor + texture(tex0, textCoords);
}