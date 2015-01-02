package com.raoxun.airhockey;

import static android.opengl.Matrix.*;
import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import javax.microedition.khronos.opengles.GL10;

import com.raoxun.airhockey.util.LoggerConfig;
import com.raoxun.airhockey.util.MatrixHelper;
import com.raoxun.airhockey.util.ShaderHelper;
import com.raoxun.airhockey.util.TextResourceReader;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRender implements Renderer {

	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int COLOR_COMPONENT_COUNT = 3;
	private static final int BYTES_PER_FLOAT = 4;
	private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
			* BYTES_PER_FLOAT;

	private final FloatBuffer vertexData;

	private Context context;

	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;

	private static final String A_COLOR = "a_Color";
	private int aColorLocation;

	private static final String U_MATRIX = "u_Matrix";
	private int uMatrixLocation;
	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];

	public AirHockeyRender(Context context) {
		this.context = context;
		float[] tableVertices = {
				// Triangle Fan
				// p0
				0f, 0f, 1f, 1f, 1f,
				// p1
				-0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
				// p2
				0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
				// p3
				0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
				// p4
				-0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
				// p5
				-0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

				// L1 p1
				-0.5f, 0f, 1f, 0f, 0f,
				// L1 P2
				0.5f, 0f, 1f, 0f, 0f,

				// Point 1
				0f, -0.4f, 0f, 0f, 1f,

				// Point 2
				0f, 0.4f, 0f, 1f, 0f, };

		vertexData = ByteBuffer
				.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();

		vertexData.put(tableVertices);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0, 0, 0);
		String vertexShaderSource = TextResourceReader
				.readTextFileFromResource(context, R.raw.simple_vertex_shader);
		String fragmentShadeSource = TextResourceReader
				.readTextFileFromResource(context, R.raw.simple_fragment_shader);
		int vertexShaderId = ShaderHelper
				.compileVertexShader(vertexShaderSource);
		int fragmentShaderId = ShaderHelper
				.compileFragmentShader(fragmentShadeSource);
		int program = ShaderHelper
				.linkProgram(vertexShaderId, fragmentShaderId);
		if (LoggerConfig.ON) {
			ShaderHelper.validateProgram(program);
		}

		glUseProgram(program);
		aPositionLocation = glGetAttribLocation(program, A_POSITION);
		aColorLocation = glGetAttribLocation(program, A_COLOR);
		uMatrixLocation = glGetUniformLocation(program, U_MATRIX);

		vertexData.position(0);
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, STRIDE, vertexData);
		glEnableVertexAttribArray(aPositionLocation);

		vertexData.position(POSITION_COMPONENT_COUNT);
		glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
				false, STRIDE, vertexData);
		glEnableVertexAttribArray(aColorLocation);

		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, 0, 0, -3f);
		rotateM(modelMatrix, 0, -60f, 1f, 0, 0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
				/ (float) height, 1f, 10f);
		// perspectiveM(projectionMatrix, 0, 45, (float) width / (float) height,
		// 1f, 10f);
		final float[] tempMatrix = new float[16];
		multiplyMM(tempMatrix, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(tempMatrix, 0, projectionMatrix, 0, tempMatrix.length);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
		glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
		glDrawArrays(GL_LINES, 6, 2);
		glDrawArrays(GL_POINTS, 8, 1);
		glDrawArrays(GL_POINTS, 9, 1);
	}

}
