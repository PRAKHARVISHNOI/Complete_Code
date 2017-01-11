package edu.simberbest.dcs.dao;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.exception.DaoException;

public interface DcsInformationDao {
	public boolean insertCurrentFeedToPie (InformationPacket infoPcket) throws DaoException;
}
