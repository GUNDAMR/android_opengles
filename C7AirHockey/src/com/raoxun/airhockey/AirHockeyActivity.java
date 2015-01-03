package com.raoxun.airhockey;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class AirHockeyActivity extends Activity {

	private GLSurfaceView glSurfaceView;

	private boolean renderSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		glSurfaceView = new GLSurfaceView(this);
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager
				.getDeviceConfigurationInfo();
		final int glVersion = configurationInfo.reqGlEsVersion;
		final boolean supportes2 = glVersion >= 0x20000;

		if (supportes2 == false) {
			Toast.makeText(this, "OpenGL ES 2 is not support",
					Toast.LENGTH_SHORT).show();
			return;
		}

		glSurfaceView.setEGLContextClientVersion(2);
		Toast.makeText(
				this,
				"the supported GL version is " + Integer.toHexString(glVersion),
				Toast.LENGTH_SHORT).show();

		setContentView(glSurfaceView);
		glSurfaceView.setRenderer(new AirHockeyRender(this));
		renderSet = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (renderSet) {
			glSurfaceView.onPause();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (renderSet) {
			glSurfaceView.onResume();
		}
	}
}
