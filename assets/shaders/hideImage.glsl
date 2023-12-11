#version 120

#ifdef GL_ES
precision mediump float;
#endif

// Passed in values from Java
uniform float u_revealToY;
uniform vec2 u_resolution;

// texture 0
uniform sampler2D u_texture;

// in attributes from vertex shader
varying vec4 v_color;
varying vec2 v_texCoords;


void main(){
    // sample texture
    vec4 texColor = texture2D(u_texture, v_texCoords);

    float alpha = 1.0;
    if (gl_FragCoord.y > u_revealToY) {
        alpha =  0.0;
    }

    gl_FragColor = vec4(texColor.rgb, alpha);
}