package cz.xeres.iqrf.libiqrf;
/**
 * <p>
 * Jabber: multi(at)jabber.sh.cvut.cz
 * </p>
 * <br>
 * 
 * <p>
 * e-mail:josef.jebavy@gmail.com
 * </p>
 * <br>
 * <p>
 * web:<a href="http://multi.xeres.cz">multi.xeres.cz"</a>
 * </p>
 * <br>
 * 
 * @author Josef Jebavý
 */
public interface Iqrf  {
	static final int SPI_DATA_LENGTH = 35;


	static final byte NO_MODULE_ON_USB = (byte) 0xFF; // SPI not working (HW error)
	static final byte SPI_DISABLED = (byte) 0x00; // SPI not working (disabled)
	static final byte SPI_CRCM_OK =  (byte) 0x3F; // SPI not ready (full buffer, last CRCM ok)
	static final byte SPI_CRCM_ERR = (byte) 0x3E; // SPI not ready (full buffer, last CRCM error)
	static final byte COMMUNICATION_MODE = (byte) 0x80; // SPI ready (communication mode)
	static final byte PROGRAMMING_MODE =  (byte) 0x81; // SPI ready (programming  mode)
	static final byte DEBUG_MODE = (byte) 0x82; // SPI ready (debugging mode)
	static final byte SPI_SLOW_MODE = (byte) 0x83; // SPI is not working on the background - Slow mode
	static final byte SPI_USER_STOP =  (byte)0x07; // state after stopSPI();
	static final byte SPI_DATA_READY = (byte) 0x40; // data ready


	public short[] spiRead();
	public void spiWrite(short[] data);
	public short spiGetStatus();
	public int deviceInit();
	public void deviceReset(); 
	public void deviceRelease();
	
}
