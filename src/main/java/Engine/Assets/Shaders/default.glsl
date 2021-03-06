#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;

uniform mat4 uProj;
uniform mat4 uView;

in vec2 fCord;

out vec4 fColor;

void main()
{
    fColor = aColor;
    gl_Position = vec4(aPos, 1.0);
}

#type fragment
#version 330 core
uniform float uTime;

in vec4 fColor;

out vec4 FragColor;

void main()
{
    FragColor = fColor;
}