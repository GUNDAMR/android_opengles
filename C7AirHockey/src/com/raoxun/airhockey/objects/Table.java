package com.raoxun.airhockey.objects;

import static android.opengl.GLES20.*;

import com.raoxun.airhockey.Constants;
import com.raoxun.airhockey.data.VertexArray;
import com.raoxun.airhockey.programs.TextureShaderProgram;

public class Table {
	private static final float[] VERTEX_DATA = {
			// X, Y, S, T
			0f, 0f, 0.5f, 0.5f, -0.5f, -0.8f, 0f, 0.9f, 0.5f, -0.8f, 1f, 0.9f,
			0.5f, 0.8f, 1f, 0.1f, -0.5f, 0.8f, 0f, 0.1f, -0.5f, -0.8f, 0f,
			0.9f, };

	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)
			* Constants.BYTES_PER_FLOAT;

	private final VertexArray vertexArray;

	public Table() {
		vertexArray = new VertexArray(VERTEX_DATA);
	}

	public void bindData(TextureShaderProgram textureProgram) {
		vertexArray.setVertexAttribPointer(0,
				textureProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, STRIDE);

		vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
				textureProgram.getTextureCoordinatesAttributeLocation(),
				TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
	}
	
	public void draw(){
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
	}
}
