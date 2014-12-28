package com.raoxun.airhockey;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import javax.microedition.khronos.opengles.GL10;

import com.raoxun.airhockey.util.LoggerConfig;
import com.raoxun.airhockey.util.ShaderHelper;
import com.raoxun.airhockey.util.TextResourceReader;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRender implements Renderer {

	private static final int POSITION_COMPONENT_COUNT = 2;

	private final int BYTES_PER_FLOAT = 4;

	private final FloatBuffer vertexData;

	private Context context;

	private static final String U_COLOR = "u_Color";
	private int uColorLocation;

	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;

	public AirHockeyRender(Context context) {
		this.context = context;
		float[] tableVertices = {
				// Triangle 1
				-0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,

				// Triangle 2
				-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,

				// Line 1
				-0.5f, 0f, 0.5f, 0f,

				// Point 1
				0f, -0.25f, 
				
				// Point 2
				0f, 0.25f, };

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
		uColorLocation = glGetUniformLocation(program, U_COLOR);
		aPositionLocation = glGetAttribLocation(program, A_POSITION);

		vertexData.position(0);
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, 0, vertexData);
		glEnableVertexAttribArray(aPositionLocation);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
		glDrawArrays(GL_TRIANGLES, 0, 6);

		glUniform4f(uColorLocation, 1.0f, 0, 0, 1.0f);
		glDrawArrays(GL_LINES, 6, 2);

		glUniform4f(uColorLocation, 0, 1.0f, 0, 1.0f);
		glDrawArrays(GL_POINTS, 8, 1);

		glUniform4f(uColorLocation, 0, 0, 1.0f, 1.0f);
		glDrawArrays(GL_POINTS, 9, 1);
	}

}
