package org.snailysis.scenes.utilities.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import org.snailysis.model.utilities.save.SaveCustomBuild;

public class SaveCustomBuildimpl<X> implements SaveCustomBuild<X>{

	
//	private static final String PATCH = "res/org/snailysis/file/";
	

	public X load(final String fileID) throws Exception{
		FileInputStream fis;
		fis = new FileInputStream(fileID);
	    ObjectInputStream ois = new ObjectInputStream(fis);
		X obj = (X) ois.readObject();
		ois.close();
		return obj;
	}

	
	public void save(X obj, final String fileID) {
		File file = new File(fileID);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
