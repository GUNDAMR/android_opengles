package com.raoxun.airhockey;

import static android.opengl.Matrix.*;
import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

import javax.microedition.khronos.opengles.GL10;

import com.raoxun.airhockey.objects.Mallet;
import com.raoxun.airhockey.objects.Table;
import com.raoxun.airhockey.programs.ColorShaderProgram;
import com.raoxun.airhockey.programs.TextureShaderProgram;
import com.raoxun.airhockey.util.LoggerConfig;
import com.raoxun.airhockey.util.MatrixHelper;
import com.raoxun.airhockey.util.ShaderHelper;
import com.raoxun.airhockey.util.TextResourceReader;
import com.raoxun.airhockey.util.TextureHelper;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRender implements Renderer {
	private final Context context;
	
	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	
	private Table table;
	private Mallet mallet;
	
	private TextureShaderProgram textureProgram;
	private ColorShaderProgram colorProgram;
	
	private int texture;
	
	public AirHockeyRender(Context context){
		this.context = context;
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, 0, 0, -3f);
		rotateM(modelMatrix, 0, -60f, 1f, 0, 0);
		MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
				/ (float) height, 1f, 10f);
		// perspectiveM(projectionMatrix, 0, 45, (float) width / (float) height,
		// 1f, 10f);
		final float[] tempMatrix = new float[16];
		multiplyMM(tempMatrix, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(tempMatrix, 0, projectionMatrix, 0, tempMatrix.length);
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0, 0, 0, 0);
		table = new Table();
		mallet = new Mallet();
		
		textureProgram = new TextureShaderProgram(context);
		colorProgram = new ColorShaderProgram(context);
		
		texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
		
		textureProgram.useProgram();
		textureProgram.setUniforms(projectionMatrix, texture);
		table.bindData(textureProgram);
		table.draw();
		
		colorProgram.useProgram();
		colorProgram.setUniforms(projectionMatrix);
		mallet.bindData(colorProgram);
		mallet.draw();
	}

}
