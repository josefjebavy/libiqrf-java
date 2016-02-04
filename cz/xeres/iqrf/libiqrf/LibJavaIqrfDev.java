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
public class LibJavaIqrfDev {



	/**
	 * vola: ~iqrf_dev();
	 * 
	 * vola destruktor
	 */
	protected native void iqrf_device_release();
	/**
	 * 
	 * vola: int init_device();
	 * 
	 *  
	 * vraci 0 pro spatne
	 * 
	 * 
	 * @return result
	 */
	protected native int iqrf_device_init();

	/**
	 * vola: void reset_device();
	 * 
	 * po resetu je potreba provest znova init
	 */
	protected native void iqrf_device_reset();
	/**
	 * int get_spi_status(void);
	 * 
	 * vraci SPISTAT - SPI status modulu
	 * 
	 * @return SPISTAT
	 */
	protected native short iqrf_spi_get_status();

	
	/**
	 * 
	 * @param stat
	 * @return
	 */
	protected native short[] iqrf_spi_read(int stat);
	/**
	 * 
	 * @param data_buff
	 * @return
	 */
	protected native void iqrf_spi_write(short data_buff[]);
	
	/**
	 * 
	 * 
	 * vola: int write_read_data(unsigned char *data_buff, int tx_len, int
	 * rx_len, int check_crc);
	 * 
	 * 
	 * vraci len TODO not implemented in LibIqrfDev.cpp
	 * 
	 * @param data_buff
	 * @return ret
	 */
	protected native int write_read_data(short data_buff[]);

	/**
	 * 
	 * vola: int write_data(unsigned char *data_buff, int tx_len);
	 * 
	 * 
	 * vraci: ret_val TODO not implemented in LibIqrfDev.cpp sa pouziva iba pri
	 * programovani na posielanie specialnych sekvencii
	 * 
	 * 
	 * @param data_buff
	 * @return ret_val
	 */
	protected native int write_data(short data_buff[]);



	/**
	 * 
	 * konstruktor nahraje binarni knihovnu
	 */
	public LibJavaIqrfDev() {
		System.out.println("LibIqrfDev.java: louding C library IqrfDev");
		System.loadLibrary("JavaIqrfDev");

	}

}
