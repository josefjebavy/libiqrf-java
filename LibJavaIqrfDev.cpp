#include <jni.h>
#include <iqrf.h>
#include "cz_xeres_iqrf_libiqrf_LibJavaIqrfDev.h"

#define SPI_DATA_LENGTH        (35)

unsigned char buffChar[SPI_DATA_LENGTH];
jshortArray spi_cmd_data(JNIEnv * env, int buff_len, int write);

JNIEXPORT void JNICALL Java_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_iqrf_1device_1release
(JNIEnv *, jobject)
{
	iqrf_release_device();

}

JNIEXPORT jint JNICALL Java_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_iqrf_1device_1init(
		JNIEnv *, jobject) {

	iqrf_init_device();

}

JNIEXPORT void JNICALL Java_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_iqrf_1device_1reset
(JNIEnv *, jobject)
{
	iqrf_reset_device();

}

JNIEXPORT jshort JNICALL Java_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_iqrf_1spi_1get_1status(
		JNIEnv *, jobject) {
	return iqrf_get_spi_status();
}

/*
 * Class:     iqrf_LibJavaIqrfDev
 * Method:    spi_read
 * Signature: (I)[S
 */

JNIEXPORT jshortArray JNICALL Java_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_iqrf_1spi_1read(
		JNIEnv * env, jobject obj, jint stat) {

	return spi_cmd_data(env, stat, 0);
}

/*
 * Class:     iqrf_LibJavaIqrfDev
 * Method:    spi_write
 * Signature: ([S)[S
 */
JNIEXPORT void JNICALL Java_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_iqrf_1spi_1write( JNIEnv * env, jshortArray buff) {
	printf("LibJavaIqrfDev.cpp: spi_write");

	//inizializace objektu pro datove typy tohoto nativniho kodu
	jsize buff_len = env->GetArrayLength(buff);
	jshort *buffJShort = env->GetShortArrayElements(buff, 0);

	//overeni zdali se povedla inicializace
	if (buffJShort == NULL) {
		printf("LibJavaIqrfDev.cpp: exception occurred, vracim NULL");
//		return NULL; /* exception occurred */
	}

	//nakopiruji data jen - tu skutesnou cast
	for (char var = 0; var < buff_len; ++var) {
		buffChar[var] = (unsigned char) buffJShort[var];//& 0x00FF
	}

	//zapis dat
	spi_cmd_data(env, buff_len, 1);
	//uvolneni pameti
	env->ReleaseShortArrayElements(buff, buffJShort, 0);
	//return spi_cmd_data(env, buff_len, 1);

	//	 return 0;
}

JNIEXPORT jint JNICALL Java_cz_xeres_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_write_1read_1data(
		JNIEnv *env, jobject obj, jshortArray) {
	printf(
			"LibJavaIqrfDev.cpp:Java_LibJavaIqrfDev_write_read_data  NOT IMPLEMENTED");
	return 0;
}

JNIEXPORT jint JNICALL Java_cz_xeres_cz_xeres_iqrf_libiqrf_LibJavaIqrfDev_write_1data(
		JNIEnv *, jobject, jshortArray) {
	printf("LibJavaIqrfDev.cpp:Java_LibJavaIqrfDev_write_data  NOT IMPLEMENTED");
	return 0;
}

jshortArray spi_cmd_data(JNIEnv * env, int buff_len, int write) {
	int len = iqrf_read_write_spi_cmd_data(buffChar, buff_len, write);

	if (len < 0) {

		printf("LibJavaIqrfDev.cpp: vracena zaporna hodnota pole, vracim NULL");
		return NULL;
	}

	//inizializace pameti pro data
	jshortArray ret = env->NewShortArray(len);

	if (ret == NULL) {
		printf("LibJavaIqrfDev.cpp: out of memory error thrown ");
		return NULL; /* out of memory error thrown */
	}

	//misto abych to tam priradil, tak to tam nakopiruji a tim poresim jine datovy typ
	jshort *retJShort = env->GetShortArrayElements(ret, NULL);
	for (char var = 0; var < len; ++var) {
		retJShort[var] = buffChar[var];
	}

	//uvolneni pameti zde v nativnim kodu
	env->ReleaseShortArrayElements(ret, retJShort, 0);

	return ret;
}
