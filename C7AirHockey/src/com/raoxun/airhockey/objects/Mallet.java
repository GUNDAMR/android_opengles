package com.raoxun.airhockey.objects;

import static android.opengl.GLES20.*;

import com.raoxun.airhockey.Constants;
import com.raoxun.airhockey.data.VertexArray;
import com.raoxun.airhockey.programs.ColorShaderProgram;

public class Mallet {
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int COLOR_COMPONENT_COUNT = 3;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
			* Constants.BYTES_PER_FLOAT;

	private static final float[] VERTEX_DATA = { 0f, -0.4f, 0f, 0f, 1f, 0f,
			0.4f, 1f, 0f, 0f, };

	private final VertexArray vertexArray;

	public Mallet() {
		vertexArray = new VertexArray(VERTEX_DATA);
	}

	public void bindData(ColorShaderProgram program) {
		vertexArray.setVertexAttribPointer(0,
				program.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT, STRIDE);
		vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
				program.getColorAttributeLocation(), COLOR_COMPONENT_COUNT,
				STRIDE);
	}
	
	public void draw(){
		glDrawArrays(GL_POINTS, 0, 2);
	}
}
