package edu.simberbest.dcs.dao;

import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
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
	public boolean insertCurrentFeedToTextFile (PlugLoadInformationPacket infoPcket) throws DaoException;
	/**
	 * @param infoPcket
	 * @return
	 * @throws DaoException
	 */
	public boolean insertCurrentFeedToPi (PlugLoadInformationPacket infoPcket) throws DaoException;
}
