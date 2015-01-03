package com.raoxun.airhockey.programs;

import static android.opengl.GLES20.*;

import com.raoxun.airhockey.R;

import android.content.Context;

public class ColorShaderProgram extends ShaderProgram {
	private final int uMatrixLocation;
	
	private final int aPositionLocation;
	private final int aColorLocation;
	
	public ColorShaderProgram(Context context) {
		super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
		uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
		aPositionLocation = glGetAttribLocation(program, A_POSITION);
		aColorLocation = glGetAttribLocation(program, A_COLOR);
	}
	
	public void setUniforms(float[] matrix){
		glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
	}
	
	public int getPositionAttributeLocation(){
		return aPositionLocation;
	}
	
	public int getColorAttributeLocation(){
		return aColorLocation;
	}
}
