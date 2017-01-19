package edu.simberbest.dcs.dao;

import edu.simberbest.dcs.entity.InformationPacket;
import edu.simberbest.dcs.exception.DaoException;

/**
 * @author sbbpvi
 *
 */
public interface DcsInformationDao {
	/**
	 * @param infoPcket
	 * @return
	 * @throws DaoException
	 */
	public boolean insertCurrentFeedToPie (InformationPacket infoPcket) throws DaoException;
}
