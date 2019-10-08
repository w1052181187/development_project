package com.chengning.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.text.TextUtils;

public class SerializeUtil {

	public static String serialize(Serializable obj) {
		String str = "";
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			str = byteArrayOutputStream.toString("ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static Object deSerialize(String str) {
		Object obj = null;
		if (!TextUtils.isEmpty(str)) {
			try {
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
						str.getBytes("ISO-8859-1"));
				ObjectInputStream objectInputStream = new ObjectInputStream(
						byteArrayInputStream);
				obj = objectInputStream.readObject();
				objectInputStream.close();
				byteArrayInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	public static <T> T deSerialize(String str, Class<T> c) {
		Object obj = deSerialize(str);
		if(obj == null){
			try {
				obj = c.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(c.isInstance(obj)){
			return (T) obj;
		}
		return null;
	}

}
