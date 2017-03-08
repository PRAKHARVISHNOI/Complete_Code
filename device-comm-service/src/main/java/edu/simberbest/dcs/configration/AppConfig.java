package edu.simberbest.dcs.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import edu.simberbest.dcs.dao.DcsInformationDao;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImpl;
import edu.simberbest.dcs.daoImpl.DcsInformationDaoImplForPi;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;
import edu.simberbest.dcs.entity.PlugLoadInstructionPacket;
import edu.simberbest.dcs.serviceImpl.DcsClientTask;
import edu.simberbest.dcs.serviceImpl.DcsInformationServiceImpl;
import edu.simberbest.dcs.serviceImpl.InfoInsertionInPiProcess;
import edu.simberbest.dcs.serviceImpl.InfoInsertionInPiService;
import edu.simberbest.dcs.serviceImpl.InformationProcessingService;
import edu.simberbest.dcs.socketClient.InstructionClient;
import edu.simberbest.dcs.socketServer.ServerListener;

/**
 * @author sbbpvi
 *
 */
@Configuration
public class AppConfig {

	@Bean
	@Lazy
	public InfoInsertionInPiService infoInsertionInPiService() {
        return new InfoInsertionInPiService(new PlugLoadInformationPacket());
    }
	@Bean
	@Lazy
    public PlugLoadInstructionPacket plugLoadInstructionPacket() {
        return new PlugLoadInstructionPacket();
    }
	
	
	@Bean
	@Lazy
    public DcsClientTask dcsClientTask(PlugLoadInstructionPacket plugLoadInstructionPacket) {
        return new DcsClientTask(plugLoadInstructionPacket);
    }
	
	@Bean
	    public DcsInformationServiceImpl dataService() {
	        return new DcsInformationServiceImpl();
	    }
	@Bean
    public DcsInformationDao dataServiceDao() {
        return new DcsInformationDaoImpl();
    }

	@Bean
	public DcsInformationDaoImplForPi dcsInformationDaoImplForPi() {
		return new DcsInformationDaoImplForPi();
	}
	@Bean
    public InstructionClient instructionClient() {
        return new InstructionClient();
    }
	@Bean
    public ServerListener serverListener() {
		ServerListener serverListener= new ServerListener();
        return serverListener;
    }
	@Bean
    public InformationProcessingService informationProcessingService() {
        return new InformationProcessingService();
    }
	@Bean
    public InfoInsertionInPiProcess infoInsertionInPiProcess() {
        return new InfoInsertionInPiProcess();
    }
	
}
