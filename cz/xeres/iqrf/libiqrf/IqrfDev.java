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
public class IqrfDev implements Iqrf {
	String className=this.getClass().getSimpleName();

	private LibJavaIqrfDev libIqrfDev;

	public IqrfDev() {

		/**
		 *kdyby byly knihovny i pro windous, tak by se dal rozlisit os a nacist
		 * jinou knihonu nebo JVM nascte sama jinou, protoze konvence nazvu
		 * knihoven na ruznych OS se lisi a ona podle toho vybira
		 */
		libIqrfDev = new LibJavaIqrfDev();
	}


	public void deviceRelease() {
		libIqrfDev.iqrf_device_release();

	}
	public void deviceReset() {

		libIqrfDev.iqrf_device_reset();
	}
	public int deviceInit() {

		return libIqrfDev.iqrf_device_init();
	}



	public short[] spiRead() {


		short stat;

		stat = spiGetStatus();



		if (stat >= 0x40 && stat <= 0x63) {

			System.out.println(className+": ctu data");

			// nastaveni delky dat pro vycteni
			// je obsazena v odpoved na get_spi_status muze byt 0x40 az 0x63
			// ale funguje to i bez otecteni 0x40
			stat -= 0x40;
			System.out.println(className+": volam libIqrfDev.spi_read");

			short[] buffNew = libIqrfDev.iqrf_spi_read(stat);

			return buffNew;

		} else {
//			System.out.println(className+": NEctu data!");
			return null;
		}

	}

	public void spiWrite(short[] data) {

		if (data.length > SPI_DATA_LENGTH) {
			throw new ArrayIndexOutOfBoundsException(
					"maximalni delka dat muze byt 35");

		}

	libIqrfDev.iqrf_spi_write(data);


	}

	public short spiGetStatus() {

		short stat;

		stat = libIqrfDev.iqrf_spi_get_status();

		switch (stat) {

		case NO_MODULE_ON_USB:
			System.out.println(className+": SPI not working (HW error)");
			return stat;

		case SPI_DISABLED:
			System.out.println(className+": SPI not working (disabled)");
			libIqrfDev.iqrf_device_reset();
			libIqrfDev.iqrf_device_init();
			return stat;

		case SPI_CRCM_OK:
			System.out
					.println(className+":  SPI not ready (full buffer, last CRCM ok)");
			return stat;
		case SPI_CRCM_ERR:
			System.out
					.println(className+": SPI not ready (full buffer, last CRCM error)");
			return stat;
		case COMMUNICATION_MODE:
			System.out.println(className+": SPI ready (communication mode)");
			return stat;
		case PROGRAMMING_MODE:
			System.out.println(className+":  SPI ready (programming  mode)");
			return stat;
		case DEBUG_MODE:
			System.out.println(className+": SPI ready (debugging mode)");
			return stat;
		case SPI_SLOW_MODE:
			System.out
					.println(className+": SPI is not working on the background - Slow mode");
			return stat;
		case SPI_USER_STOP:
			System.out.println(className+": state after stopSPI()");
			return stat;

		default:
			if (stat >= 0x40 && stat <= 0x63) {
				// case SPI_DATA_READY: // 0x40

				System.out.println(className+": SPI data ready; Value");

			} else {
				System.out.println(className+": Unkown SPI response!");
				// v pripade problemu reset+init zarizaeni
				libIqrfDev.iqrf_device_reset();
				libIqrfDev.iqrf_device_init();

			}
		}
		return stat;

	}

	

}
