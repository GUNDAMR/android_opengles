package com.raoxun.firstopengl;

import static android.opengl.GLES20.*;

import javax.microedition.khronos.egl.EGLConfig;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;;

public class MyFirstRender implements Renderer {

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(1.0f, 0, 0, 0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
	}

}
