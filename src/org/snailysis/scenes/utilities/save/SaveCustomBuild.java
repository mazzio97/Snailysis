package org.snailysis.scenes.utilities.save;

import java.io.IOException;

public interface SaveCustomBuild<X> {
	
	/*
	 * Save a list of object in a Serializable File
	 */
	public void  save (X obj, final String fileID);
	
	/*
	 * Load a File and transform it in a List
	 */
	public X load (final String fileID) throws Exception;

}
