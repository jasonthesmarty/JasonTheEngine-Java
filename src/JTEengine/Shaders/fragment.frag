#version 330

out vec4 gl_FragColor;

in vec4 vertexColor;

in vec2 textCoords;

uniform sampler2D tex0;

void main() {
    gl_FragColor = texture2D(tex0, textCoords) * vertexColor;
}