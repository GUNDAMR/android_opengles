package com.raoxun.airhockey.programs;

import static android.opengl.GLES20.*;

import com.raoxun.airhockey.util.ShaderHelper;
import com.raoxun.airhockey.util.TextResourceReader;

import android.content.Context;

public class ShaderProgram {
	// uniform
	protected static final String U_MATRIX = "u_Matrix";
	protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

	// attributes
	protected static final String A_POSITION = "a_Position";
	protected static final String A_COLOR = "a_Color";
	protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

	// shader
	protected final int program;

	protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){
		// compile the shaders and link the program
		program = ShaderHelper.buildProgram(
				TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId), 
				TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
	}
	
	public void useProgram(){
		glUseProgram(program);
	}
}
