package edu.simberbest.dcs.daoImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.exception.DaoException;

public class DcsInformationDaoImpl implements DcsInformationDao  {
	private static final String FILENAME = "C:\\test\\filename.txt";
	// Stub writing on file >>> will move to pie Data From Client
	@Override
	public boolean insertCurrentFeedToTextFile(PlugLoadInformationPacket infoPcket) throws DaoException {
		//System.out.println("A packet is received for file");
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String content = infoPcket.toString()+"\n";
			fw = new FileWriter(FILENAME,true);
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}
	@Override
	public boolean insertCurrentFeedToPi(PlugLoadInformationPacket infoPcket) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}
}
