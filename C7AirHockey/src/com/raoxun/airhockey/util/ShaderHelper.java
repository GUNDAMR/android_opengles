package com.raoxun.airhockey.util;

import static android.opengl.GLES20.*;
import android.util.Log;

public class ShaderHelper {
	private static final String TAG = "ShaderHelper";
	private static RxLogger logger = new RxLogger(TAG);

	public static int compileShader(int type, String shaderCode) {
		final int shaderObjectId = glCreateShader(type);
		if (shaderObjectId == 0) {
			if (LoggerConfig.ON) {
				Log.w(TAG, "Couldn't create new shader.");
			}

			return 0;
		}

		glShaderSource(shaderObjectId, shaderCode);
		glCompileShader(shaderObjectId);

		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
		logger.v("Results of compiling source: " + "\n" + shaderCode + "\n:"
				+ glGetShaderInfoLog(shaderObjectId));

		if (compileStatus[0] == 0) {
			glDeleteShader(shaderObjectId);
			logger.w("Compilation of shader failed.");
			return 0;
		}

		return shaderObjectId;
	}

	public static int compileVertexShader(String shaderCode) {
		return compileShader(GL_VERTEX_SHADER, shaderCode);
	}

	public static int compileFragmentShader(String shaderCode) {
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
	}

	public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
		final int programObjectId = glCreateProgram();
		if (programObjectId == 0) {
			logger.w("Couldn't create new program");
			return 0;
		}

		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
		glLinkProgram(programObjectId);

		final int[] linkStatus = new int[1];
		glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
		logger.v("Results of linking programs:\n"
				+ glGetProgramInfoLog(programObjectId));

		if (linkStatus[0] == 0) {
			glDeleteProgram(programObjectId);
			logger.w("linking of program failed.");
			return 0;
		}

		return programObjectId;
	}

	public static boolean validateProgram(int programObjectId) {
		glValidateProgram(programObjectId);
		final int[] validateStatus = new int[1];
		glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
		logger.v("Result of vlidating program: " + validateStatus[0]
				+ "\nLog: " + glGetProgramInfoLog(programObjectId));

		return validateStatus[0] != 0;
	}

	public static int buildProgram(String vertexShaderSource,
			String fragmentShaderSource) {
		int program;
		
		int vertexShader = compileVertexShader(vertexShaderSource);
		int fragmentShader = compileFragmentShader(fragmentShaderSource);
		
		program = linkProgram(vertexShader, fragmentShader);
		if(LoggerConfig.ON){
			validateProgram(program);
		}
		
		return program;
	}
}
